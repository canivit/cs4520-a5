package com.cs4520.assignment5

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object Api {
    private const val BASE_URL: String = "https://kgtttq6tg9.execute-api.us-east-2.amazonaws.com/"
    const val ENDPOINT: String = "prod/random/"

    val apiClient: ProductApiClient by lazy {
        retrofit.create(ProductApiClient::class.java)
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    interface ProductApiClient {
        @GET(ENDPOINT)
        suspend fun getProducts(): Response<List<Product>>
    }
}