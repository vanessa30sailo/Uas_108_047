package com.example.uas_perangkat.data.remote

import com.example.uas_perangkat.data.model.Event
import com.example.uas_perangkat.data.model.EventRequest
import com.example.uas_perangkat.data.model.Statistics
import retrofit2.Response
import retrofit2.http.*

/**
 * Interface untuk Retrofit API Service
 */
interface ApiService {

    @GET("api.php")
    suspend fun getAllEvents(): Response<ApiResponseWrapper<List<Event>>>

    @GET("api.php")
    suspend fun getEventsByStatus(@Query("status") status: String): Response<ApiResponseWrapper<List<Event>>>

    @GET("api.php")
    suspend fun getStatistics(@Query("stats") stats: Int = 1): Response<ApiResponseWrapper<Statistics>>

    @GET("api.php")
    suspend fun getEventById(@Query("id") id: String): Response<ApiResponseWrapper<Event>>

    @POST("api.php")
    suspend fun createEvent(@Body event: EventRequest): Response<ApiResponseWrapper<Event>>

    @PUT("api.php")
    suspend fun updateEvent(
        @Query("id") id: String,
        @Body event: EventRequest
    ): Response<ApiResponseWrapper<Event>>

    @DELETE("api.php")
    suspend fun deleteEvent(@Query("id") id: String): Response<ApiResponseWrapper<String>>
}

/**
 * Data class untuk wrapper response dari API
 */
data class ApiResponseWrapper<T>(
    val status: Int,
    val message: String,
    val data: T
)