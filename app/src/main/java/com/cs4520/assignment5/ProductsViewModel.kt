package com.cs4520.assignment5

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class ProductsViewModel(
    private val workManager: WorkManager,
    private val databaseHandle: Database.DatabaseHandle
) : ViewModel() {
    private var _productsResult =
        MutableLiveData<Result<List<Product>>>(Result.Success(emptyList()))
    val productsResult: LiveData<Result<List<Product>>> = _productsResult

    fun loadProducts(lifecycleOwner: LifecycleOwner) {
        val workConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<LoadProductsWorker>(1, TimeUnit.HOURS)
            .setConstraints(workConstraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            "loadProducts",
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            workRequest
        )

        workManager.getWorkInfoByIdLiveData(workRequest.id).observe(lifecycleOwner) {
            loadProductsFromDb()
        }
    }

    private fun loadProductsFromDb() {
        CoroutineScope(Dispatchers.IO).launch {
            val products = databaseHandle.productDao().getAllProducts()
            if (products.isEmpty()) {
                updateProductsResult(Result.Error("No products available"))
            } else {
                updateProductsResult(Result.Success(products))
            }
        }
    }

    private suspend fun updateProductsResult(result: Result<List<Product>>) {
        withContext(Dispatchers.Main) {
            _productsResult.value = result
        }
    }
}