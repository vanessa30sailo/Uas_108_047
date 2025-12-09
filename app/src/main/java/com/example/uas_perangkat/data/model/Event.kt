package com.example.uas_perangkat.data.model

/**
 * Data class untuk Event
 * Representasi data event yang diterima dari API
 */
data class Event(
    val id: String,
    val title: String,
    val date: String,
    val time: String,
    val location: String,
    val description: String,
    val capacity: String,
    val status: String,
    val createdAt: String,
    val updatedAt: String
)

/**
 * Data class untuk Statistics
 * Representasi statistik event
 */
data class Statistics(
    val total: Int,
    val upcoming: Int,
    val ongoing: Int,
    val completed: Int,
    val cancelled: Int
)

/**
 * Data class untuk request create/update event
 */
data class EventRequest(
    val title: String,
    val date: String,
    val time: String,
    val location: String,
    val description: String,
    val capacity: Int,
    val status: String
)

/**
 * Sealed class untuk handle API response state
 */
sealed class ApiResponse<out T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error(val message: String) : ApiResponse<Nothing>()
    object Loading : ApiResponse<Nothing>()
}