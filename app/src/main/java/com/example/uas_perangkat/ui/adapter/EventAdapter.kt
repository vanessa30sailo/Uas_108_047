package com.example.uas_perangkat.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_perangkat.R
import com.example.uas_perangkat.data.model.Event

/**
 * Adapter untuk RecyclerView Event
 * Menggunakan ListAdapter untuk performa lebih baik dengan DiffUtil
 */
class EventAdapter(
    private val onItemClick: (Event) -> Unit,
    private val onEditClick: (Event) -> Unit,
    private val onDeleteClick: (Event) -> Unit
) : ListAdapter<Event, EventAdapter.EventViewHolder>(EventDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvEventTitle)
        private val tvDate: TextView = itemView.findViewById(R.id.tvEventDate)
        private val tvLocation: TextView = itemView.findViewById(R.id.tvEventLocation)
        private val tvStatus: TextView = itemView.findViewById(R.id.tvEventStatus)
        private val btnEdit: ImageButton = itemView.findViewById(R.id.btnEdit)
        private val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)

        fun bind(event: Event) {
            tvTitle.text = event.title
            tvDate.text = "${event.date} | ${event.time}"
            tvLocation.text = event.location
            tvStatus.text = getStatusLabel(event.status)

            // Set status background color
            tvStatus.setBackgroundResource(getStatusBackground(event.status))

            // Click listeners
            itemView.setOnClickListener {
                onItemClick(event)
            }

            btnEdit.setOnClickListener {
                onEditClick(event)
            }

            btnDelete.setOnClickListener {
                onDeleteClick(event)
            }
        }

        private fun getStatusLabel(status: String): String {
            return when (status.lowercase()) {
                "upcoming" -> "Akan Datang"
                "ongoing" -> "Berlangsung"
                "completed" -> "Selesai"
                "cancelled" -> "Dibatalkan"
                else -> status
            }
        }

        private fun getStatusBackground(status: String): Int {
            return when (status.lowercase()) {
                "upcoming" -> R.drawable.bg_status_upcoming
                "ongoing" -> R.drawable.bg_status_ongoing
                "completed" -> R.drawable.bg_status_completed
                "cancelled" -> R.drawable.bg_status_cancelled
                else -> R.drawable.bg_status_upcoming
            }
        }
    }

    /**
     * DiffUtil Callback untuk performa update list yang lebih efisien
     */
    class EventDiffCallback : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem == newItem
        }
    }
}