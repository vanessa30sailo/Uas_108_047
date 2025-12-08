package com.example.uas_perangkat.data

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("api.php")
    suspend fun getEvents(): Response<List<Event>>
}
