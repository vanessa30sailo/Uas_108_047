package com.example.uas_perangkat.data

import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "http://104.248.153.158/event-api/"

    private val client = OkHttpClient.Builder()
        .protocols(listOf(Protocol.HTTP_1_1))   // FIX PROTOCOL_ERROR
        .build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
