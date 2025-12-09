package com.example.uas_perangkat.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_perangkat.data.model.ApiResponse
import com.example.uas_perangkat.data.model.Event
import com.example.uas_perangkat.data.model.EventRequest
import com.example.uas_perangkat.data.model.Statistics
import com.example.uas_perangkat.data.repository.EventRepository
import kotlinx.coroutines.launch

/**
 * ViewModel untuk mengelola UI state dan business logic
 */
class EventViewModel : ViewModel() {

    private val repository = EventRepository()

    // LiveData untuk events
    private val _events = MutableLiveData<ApiResponse<List<Event>>>()
    val events: LiveData<ApiResponse<List<Event>>> = _events

    // LiveData untuk statistics
    private val _statistics = MutableLiveData<ApiResponse<Statistics>>()
    val statistics: LiveData<ApiResponse<Statistics>> = _statistics

    // LiveData untuk create event
    private val _createEventResult = MutableLiveData<ApiResponse<Event>>()
    val createEventResult: LiveData<ApiResponse<Event>> = _createEventResult

    // LiveData untuk update event
    private val _updateEventResult = MutableLiveData<ApiResponse<Event>>()
    val updateEventResult: LiveData<ApiResponse<Event>> = _updateEventResult

    // LiveData untuk delete event
    private val _deleteEventResult = MutableLiveData<ApiResponse<String>>()
    val deleteEventResult: LiveData<ApiResponse<String>> = _deleteEventResult

    /**
     * Load semua events
     */
    fun loadAllEvents() {
        viewModelScope.launch {
            repository.getAllEvents().collect { response ->
                _events.value = response
            }
        }
    }

    /**
     * Load events berdasarkan status
     */
    fun loadEventsByStatus(status: String) {
        viewModelScope.launch {
            if (status == "all") {
                repository.getAllEvents().collect { response ->
                    _events.value = response
                }
            } else {
                repository.getEventsByStatus(status).collect { response ->
                    _events.value = response
                }
            }
        }
    }

    /**
     * Load statistics
     */
    fun loadStatistics() {
        viewModelScope.launch {
            repository.getStatistics().collect { response ->
                _statistics.value = response
            }
        }
    }

    /**
     * Create event baru
     */
    fun createEvent(
        title: String,
        date: String,
        time: String,
        location: String,
        description: String,
        capacity: Int,
        status: String
    ) {
        viewModelScope.launch {
            val eventRequest = EventRequest(
                title = title,
                date = date,
                time = time,
                location = location,
                description = description,
                capacity = capacity,
                status = status
            )

            repository.createEvent(eventRequest).collect { response ->
                _createEventResult.value = response
            }
        }
    }

    /**
     * Update event
     */
    fun updateEvent(
        id: String,
        title: String,
        date: String,
        time: String,
        location: String,
        description: String,
        capacity: Int,
        status: String
    ) {
        viewModelScope.launch {
            val eventRequest = EventRequest(
                title = title,
                date = date,
                time = time,
                location = location,
                description = description,
                capacity = capacity,
                status = status
            )

            repository.updateEvent(id, eventRequest).collect { response ->
                _updateEventResult.value = response
            }
        }
    }

    /**
     * Delete event
     */
    fun deleteEvent(id: String) {
        viewModelScope.launch {
            repository.deleteEvent(id).collect { response ->
                _deleteEventResult.value = response
            }
        }
    }

    /**
     * Reset create event result
     */
    fun resetCreateEventResult() {
        _createEventResult.value = null
    }

    /**
     * Reset update event result
     */
    fun resetUpdateEventResult() {
        _updateEventResult.value = null
    }

    /**
     * Reset delete event result
     */
    fun resetDeleteEventResult() {
        _deleteEventResult.value = null
    }
}