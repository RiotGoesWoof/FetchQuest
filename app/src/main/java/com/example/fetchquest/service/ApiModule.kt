package com.example.fetchquest.service

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiModule {
    companion object {
        val BASE_URL = "https://fetch-hiring.s3.amazonaws.com/"
    }

    private val service: FetchService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(FetchService::class.java)
    }

    suspend fun getList(): JsonArray {
        return service.getList()
    }
}