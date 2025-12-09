package com.example.uas_perangkat.data.repository

import com.example.uas_perangkat.data.model.ApiResponse
import com.example.uas_perangkat.data.model.Event
import com.example.uas_perangkat.data.model.EventRequest
import com.example.uas_perangkat.data.model.Statistics
import com.example.uas_perangkat.data.remote.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Repository untuk mengelola data Event
 * Bertindak sebagai single source of truth
 */
class EventRepository {

    private val apiService = ApiClient.apiService

    /**
     * Mengambil semua event
     */
    fun getAllEvents(): Flow<ApiResponse<List<Event>>> = flow {
        emit(ApiResponse.Loading)
        try {
            val response = apiService.getAllEvents()
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                if (body.status == 200) {
                    emit(ApiResponse.Success(body.data))
                } else {
                    emit(ApiResponse.Error(body.message))
                }
            } else {
                emit(ApiResponse.Error("Gagal mengambil data"))
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message ?: "Terjadi kesalahan"))
        }
    }.flowOn(Dispatchers.IO)

    /**
     * Mengambil event berdasarkan status
     */
    fun getEventsByStatus(status: String): Flow<ApiResponse<List<Event>>> = flow {
        emit(ApiResponse.Loading)
        try {
            val response = apiService.getEventsByStatus(status)
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                if (body.status == 200) {
                    emit(ApiResponse.Success(body.data))
                } else {
                    emit(ApiResponse.Error(body.message))
                }
            } else {
                emit(ApiResponse.Error("Gagal mengambil data"))
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message ?: "Terjadi kesalahan"))
        }
    }.flowOn(Dispatchers.IO)

    /**
     * Mengambil statistik event
     */
    fun getStatistics(): Flow<ApiResponse<Statistics>> = flow {
        emit(ApiResponse.Loading)
        try {
            val response = apiService.getStatistics()
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                if (body.status == 200) {
                    emit(ApiResponse.Success(body.data))
                } else {
                    emit(ApiResponse.Error(body.message))
                }
            } else {
                emit(ApiResponse.Error("Gagal mengambil statistik"))
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message ?: "Terjadi kesalahan"))
        }
    }.flowOn(Dispatchers.IO)

    /**
     * Membuat event baru
     */
    fun createEvent(eventRequest: EventRequest): Flow<ApiResponse<Event>> = flow {
        emit(ApiResponse.Loading)
        try {
            val response = apiService.createEvent(eventRequest)
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                if (body.status == 201 || body.status == 200) {
                    emit(ApiResponse.Success(body.data))
                } else {
                    emit(ApiResponse.Error(body.message))
                }
            } else {
                emit(ApiResponse.Error("Gagal membuat event"))
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message ?: "Terjadi kesalahan"))
        }
    }.flowOn(Dispatchers.IO)

    /**
     * Update event
     */
    fun updateEvent(id: String, eventRequest: EventRequest): Flow<ApiResponse<Event>> = flow {
        emit(ApiResponse.Loading)
        try {
            val response = apiService.updateEvent(id, eventRequest)
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                if (body.status == 200) {
                    emit(ApiResponse.Success(body.data))
                } else {
                    emit(ApiResponse.Error(body.message))
                }
            } else {
                emit(ApiResponse.Error("Gagal update event"))
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message ?: "Terjadi kesalahan"))
        }
    }.flowOn(Dispatchers.IO)

    /**
     * Delete event
     */
    fun deleteEvent(id: String): Flow<ApiResponse<String>> = flow {
        emit(ApiResponse.Loading)
        try {
            val response = apiService.deleteEvent(id)
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                if (body.status == 200) {
                    emit(ApiResponse.Success(body.message))
                } else {
                    emit(ApiResponse.Error(body.message))
                }
            } else {
                emit(ApiResponse.Error("Gagal menghapus event"))
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message ?: "Terjadi kesalahan"))
        }
    }.flowOn(Dispatchers.IO)
}