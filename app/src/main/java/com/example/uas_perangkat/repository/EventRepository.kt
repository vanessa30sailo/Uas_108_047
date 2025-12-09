package com.example.uas_perangkat.repository

import com.example.uas_perangkat.data.ApiClient
import com.example.uas_perangkat.data.Event

class EventRepository {

    suspend fun getAllEvents(): List<Event>? {
        val response = ApiClient.apiService.getEvents()
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}