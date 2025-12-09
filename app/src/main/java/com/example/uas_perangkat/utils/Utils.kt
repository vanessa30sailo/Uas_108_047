package com.example.uas_perangkat.utils

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.*

/**
 * Utility class untuk helper functions
 */
object Utils {

    /**
     * Format date untuk display
     */
    fun formatDate(date: String): String {
        return try {
            val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val parsedDate = parser.parse(date)
            formatter.format(parsedDate ?: Date())
        } catch (e: Exception) {
            date
        }
    }

    /**
     * Format time untuk display
     */
    fun formatTime(time: String): String {
        return try {
            val parser = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
            val parsedTime = parser.parse(time)
            formatter.format(parsedTime ?: Date())
        } catch (e: Exception) {
            time
        }
    }

    /**
     * Show DatePicker Dialog
     */
    fun showDatePicker(context: Context, editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format(
                    "%04d-%02d-%02d",
                    selectedYear,
                    selectedMonth + 1,
                    selectedDay
                )
                editText.setText(formattedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    /**
     * Show TimePicker Dialog
     */
    fun showTimePicker(context: Context, editText: EditText) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            context,
            { _, selectedHour, selectedMinute ->
                val formattedTime = String.format(
                    "%02d:%02d:00",
                    selectedHour,
                    selectedMinute
                )
                editText.setText(formattedTime)
            },
            hour,
            minute,
            true
        )

        timePickerDialog.show()
    }

    /**
     * Get status color resource
     */
    fun getStatusColor(status: String): Int {
        return when (status.lowercase()) {
            "upcoming" -> android.R.color.holo_blue_light
            "ongoing" -> android.R.color.holo_green_light
            "completed" -> android.R.color.darker_gray
            "cancelled" -> android.R.color.holo_red_light
            else -> android.R.color.holo_blue_light
        }
    }

    /**
     * Validate email format
     */
    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * Validate date format (yyyy-MM-dd)
     */
    fun isValidDate(date: String): Boolean {
        return try {
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            format.isLenient = false
            format.parse(date)
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Validate time format (HH:mm:ss)
     */
    fun isValidTime(time: String): Boolean {
        return try {
            val format = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            format.isLenient = false
            format.parse(time)
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Get current date in yyyy-MM-dd format
     */
    fun getCurrentDate(): String {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return format.format(Date())
    }

    /**
     * Get current time in HH:mm:ss format
     */
    fun getCurrentTime(): String {
        val format = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return format.format(Date())
    }
}