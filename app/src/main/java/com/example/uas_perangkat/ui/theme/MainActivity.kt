package com.example.uas_perangkat.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_perangkat.R
import com.example.uas_perangkat.data.model.ApiResponse
import com.example.uas_perangkat.data.model.Event
import com.example.uas_perangkat.ui.adapter.EventAdapter
import com.example.uas_perangkat.ui.viewmodel.EventViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout

/**
 * MainActivity - Entry point aplikasi Event Management
 * Menggunakan MVVM architecture pattern
 */
class MainActivity : AppCompatActivity() {

    // UI Components
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EventAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var emptyView: TextView
    private lateinit var tabLayout: TabLayout
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var tvTotal: TextView
    private lateinit var tvUpcoming: TextView
    private lateinit var tvOngoing: TextView
    private lateinit var tvCompleted: TextView

    // ViewModel
    private lateinit var viewModel: EventViewModel

    // State
    private var currentFilter = "all"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this)[EventViewModel::class.java]

        initViews()
        setupRecyclerView()
        setupTabLayout()
        setupFab()
        observeViewModel()

        // Load initial data
        loadData()
    }

    /**
     * Initialize all views
     */
    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        emptyView = findViewById(R.id.emptyView)
        tabLayout = findViewById(R.id.tabLayout)
        fabAdd = findViewById(R.id.fabAdd)
        tvTotal = findViewById(R.id.tvTotal)
        tvUpcoming = findViewById(R.id.tvUpcoming)
        tvOngoing = findViewById(R.id.tvOngoing)
        tvCompleted = findViewById(R.id.tvCompleted)
    }

    /**
     * Setup RecyclerView with adapter
     */
    private fun setupRecyclerView() {
        adapter = EventAdapter(
            onItemClick = { event -> showEventDetail(event) },
            onEditClick = { event -> showEditDialog(event) },
            onDeleteClick = { event -> confirmDelete(event) }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    /**
     * Setup TabLayout untuk filter status
     */
    private fun setupTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("Semua"))
        tabLayout.addTab(tabLayout.newTab().setText("Akan Datang"))
        tabLayout.addTab(tabLayout.newTab().setText("Berlangsung"))
        tabLayout.addTab(tabLayout.newTab().setText("Selesai"))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                currentFilter = when (tab?.position) {
                    0 -> "all"
                    1 -> "upcoming"
                    2 -> "ongoing"
                    3 -> "completed"
                    else -> "all"
                }
                viewModel.loadEventsByStatus(currentFilter)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    /**
     * Setup FloatingActionButton
     */
    private fun setupFab() {
        fabAdd.setOnClickListener {
            showCreateDialog()
        }
    }

    /**
     * Observe ViewModel LiveData
     */
    private fun observeViewModel() {
        // Observe events
        viewModel.events.observe(this) { response ->
            when (response) {
                is ApiResponse.Loading -> {
                    showLoading(true)
                }
                is ApiResponse.Success -> {
                    showLoading(false)
                    adapter.submitList(response.data)
                    updateEmptyView(response.data.isEmpty())
                }
                is ApiResponse.Error -> {
                    showLoading(false)
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Observe statistics
        viewModel.statistics.observe(this) { response ->
            when (response) {
                is ApiResponse.Success -> {
                    tvTotal.text = response.data.total.toString()
                    tvUpcoming.text = response.data.upcoming.toString()
                    tvOngoing.text = response.data.ongoing.toString()
                    tvCompleted.text = response.data.completed.toString()
                }
                is ApiResponse.Error -> {
                    // Handle error silently for statistics
                }
                else -> {}
            }
        }

        // Observe create event result
        viewModel.createEventResult.observe(this) { response ->
            when (response) {
                is ApiResponse.Success -> {
                    Toast.makeText(this, "Event berhasil dibuat!", Toast.LENGTH_SHORT).show()
                    loadData()
                    viewModel.resetCreateEventResult()
                }
                is ApiResponse.Error -> {
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                    viewModel.resetCreateEventResult()
                }
                else -> {}
            }
        }

        // Observe update event result
        viewModel.updateEventResult.observe(this) { response ->
            when (response) {
                is ApiResponse.Success -> {
                    Toast.makeText(this, "Event berhasil diupdate!", Toast.LENGTH_SHORT).show()
                    loadData()
                    viewModel.resetUpdateEventResult()
                }
                is ApiResponse.Error -> {
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                    viewModel.resetUpdateEventResult()
                }
                else -> {}
            }
        }

        // Observe delete event result
        viewModel.deleteEventResult.observe(this) { response ->
            when (response) {
                is ApiResponse.Success -> {
                    Toast.makeText(this, "Event berhasil dihapus!", Toast.LENGTH_SHORT).show()
                    loadData()
                    viewModel.resetDeleteEventResult()
                }
                is ApiResponse.Error -> {
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                    viewModel.resetDeleteEventResult()
                }
                else -> {}
            }
        }
    }

    /**
     * Load data (events dan statistics)
     */
    private fun loadData() {
        viewModel.loadEventsByStatus(currentFilter)
        viewModel.loadStatistics()
    }

    /**
     * Show create event dialog
     */
    private fun showCreateDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_event_form, null)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Buat Event Baru")
            .setView(dialogView)
            .setPositiveButton("Simpan", null)
            .setNegativeButton("Batal", null)
            .create()

        dialog.setOnShowListener {
            val btnSave = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            btnSave.setOnClickListener {
                val title = dialogView.findViewById<EditText>(R.id.etTitle).text.toString()
                val date = dialogView.findViewById<EditText>(R.id.etDate).text.toString()
                val time = dialogView.findViewById<EditText>(R.id.etTime).text.toString()
                val location = dialogView.findViewById<EditText>(R.id.etLocation).text.toString()
                val description = dialogView.findViewById<EditText>(R.id.etDescription).text.toString()
                val capacity = dialogView.findViewById<EditText>(R.id.etCapacity).text.toString()
                val status = dialogView.findViewById<Spinner>(R.id.spinnerStatus).selectedItem.toString()

                if (validateInput(title, date, time, location)) {
                    viewModel.createEvent(
                        title = title,
                        date = date,
                        time = time,
                        location = location,
                        description = description,
                        capacity = capacity.toIntOrNull() ?: 0,
                        status = status
                    )
                    dialog.dismiss()
                } else {
                    Toast.makeText(this, "Lengkapi semua field wajib!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Setup status spinner
        setupStatusSpinner(dialogView)
        dialog.show()
    }

    /**
     * Show edit event dialog
     */
    private fun showEditDialog(event: Event) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_event_form, null)

        // Pre-fill form
        dialogView.findViewById<EditText>(R.id.etTitle).setText(event.title)
        dialogView.findViewById<EditText>(R.id.etDate).setText(event.date)
        dialogView.findViewById<EditText>(R.id.etTime).setText(event.time)
        dialogView.findViewById<EditText>(R.id.etLocation).setText(event.location)
        dialogView.findViewById<EditText>(R.id.etDescription).setText(event.description)
        dialogView.findViewById<EditText>(R.id.etCapacity).setText(event.capacity)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Edit Event")
            .setView(dialogView)
            .setPositiveButton("Update", null)
            .setNegativeButton("Batal", null)
            .create()

        dialog.setOnShowListener {
            val btnUpdate = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            btnUpdate.setOnClickListener {
                val title = dialogView.findViewById<EditText>(R.id.etTitle).text.toString()
                val date = dialogView.findViewById<EditText>(R.id.etDate).text.toString()
                val time = dialogView.findViewById<EditText>(R.id.etTime).text.toString()
                val location = dialogView.findViewById<EditText>(R.id.etLocation).text.toString()
                val description = dialogView.findViewById<EditText>(R.id.etDescription).text.toString()
                val capacity = dialogView.findViewById<EditText>(R.id.etCapacity).text.toString()
                val status = dialogView.findViewById<Spinner>(R.id.spinnerStatus).selectedItem.toString()

                if (validateInput(title, date, time, location)) {
                    viewModel.updateEvent(
                        id = event.id,
                        title = title,
                        date = date,
                        time = time,
                        location = location,
                        description = description,
                        capacity = capacity.toIntOrNull() ?: 0,
                        status = status
                    )
                    dialog.dismiss()
                } else {
                    Toast.makeText(this, "Lengkapi semua field wajib!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Setup spinner and set current status
        setupStatusSpinner(dialogView, event.status)
        dialog.show()
    }

    /**
     * Show event detail dialog
     */
    private fun showEventDetail(event: Event) {
        val message = """
            Tanggal: ${event.date}
            Waktu: ${event.time}
            Lokasi: ${event.location}
            Deskripsi: ${event.description}
            Kapasitas: ${event.capacity} orang
            Status: ${getStatusLabel(event.status)}
        """.trimIndent()

        AlertDialog.Builder(this)
            .setTitle(event.title)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }

    /**
     * Confirm delete event
     */
    private fun confirmDelete(event: Event) {
        AlertDialog.Builder(this)
            .setTitle("Hapus Event")
            .setMessage("Yakin ingin menghapus '${event.title}'?")
            .setPositiveButton("Hapus") { _, _ ->
                viewModel.deleteEvent(event.id)
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    /**
     * Setup status spinner
     */
    private fun setupStatusSpinner(dialogView: View, currentStatus: String? = null) {
        val spinner = dialogView.findViewById<Spinner>(R.id.spinnerStatus)
        val statuses = arrayOf("upcoming", "ongoing", "completed", "cancelled")
        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, statuses)

        currentStatus?.let {
            val position = statuses.indexOf(it)
            if (position >= 0) {
                spinner.setSelection(position)
            }
        }
    }

    /**
     * Validate input form
     */
    private fun validateInput(title: String, date: String, time: String, location: String): Boolean {
        return title.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty() && location.isNotEmpty()
    }

    /**
     * Show/hide loading indicator
     */
    private fun showLoading(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
        recyclerView.visibility = if (show) View.GONE else View.VISIBLE
    }

    /**
     * Update empty view visibility
     */
    private fun updateEmptyView(isEmpty: Boolean) {
        emptyView.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    /**
     * Get status label in Bahasa Indonesia
     */
    private fun getStatusLabel(status: String): String {
        return when (status.lowercase()) {
            "upcoming" -> "Akan Datang"
            "ongoing" -> "Berlangsung"
            "completed" -> "Selesai"
            "cancelled" -> "Dibatalkan"
            else -> status
        }
    }
}