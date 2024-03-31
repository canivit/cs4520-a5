package com.cs4520.assignment5

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class LoadProductsWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context,
    params
) {
    override suspend fun doWork(): Result {
        return try {
            val response = Api.apiClient.getProducts()
            if (response.isSuccessful) {
                val products = response.body()!!
                Database.getInstance(applicationContext).productDao().insertAllProducts(products)
                Result.success()
            } else {
                Result.failure()
            }
        } catch (e: Exception) {
            Result.failure()
        }
    }
}