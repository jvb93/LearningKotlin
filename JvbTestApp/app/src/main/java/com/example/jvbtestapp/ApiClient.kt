package com.example.jvbtestapp

import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object ApiClient {
    private val baseUrl = "https://jsonplaceholder.typicode.com/"

    fun getClient(): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
}