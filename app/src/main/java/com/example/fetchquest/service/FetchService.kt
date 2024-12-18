package com.example.fetchquest.service

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.http.GET

interface FetchService {
    @GET("/hiring.json")
    suspend fun getList(): JsonArray

}