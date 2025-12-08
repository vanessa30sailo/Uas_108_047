package com.example.uas_perangkat.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_perangkat.data.Event
import com.example.uas_perangkat.repository.EventRepository
import kotlinx.coroutines.launch

class EventViewModel : ViewModel() {

    private val repository = EventRepository()

    var eventList by mutableStateOf<List<Event>>(emptyList())
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    fun loadEvents() {
        viewModelScope.launch {
            try {
                isLoading = true
                val result = repository.getAllEvents()
                eventList = result ?: emptyList()
            } catch (e: Exception) {
                errorMessage = e.message ?: "Error terjadi"
            } finally {
                isLoading = false
            }
        }
    }
}
