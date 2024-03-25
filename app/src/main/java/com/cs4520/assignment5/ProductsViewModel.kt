package com.cs4520.assignment5

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.WorkManager

class ProductsViewModel(
    private val workManager: WorkManager,
    private val databaseHandle: Database.DatabaseHandle
) : ViewModel() {
    private var _productsResult =
        MutableLiveData<Result<List<Product>>>(Result.Success(emptyList()))
    val productsResult: LiveData<Result<List<Product>>> = _productsResult
    private val products = listOf(
        Product("Apple", 5.0, "2020-01-01", Product.Type.Food),
        Product("Pencil", 3.0, null, Product.Type.Equipment),
        Product("Banana", 4.0, "2020-01-01", Product.Type.Food),
        Product("Calculator", 9.0, null, Product.Type.Equipment),
        Product("Apple2", 5.0, "2020-01-01", Product.Type.Food),
        Product("Pencil2", 3.0, null, Product.Type.Equipment),
        Product("Banana2", 4.0, "2020-01-01", Product.Type.Food),
        Product("Calculator2", 9.0, null, Product.Type.Equipment),
        Product("Apple3", 5.0, "2020-01-01", Product.Type.Food),
        Product("Pencil3", 3.0, null, Product.Type.Equipment),
        Product("Banana3", 4.0, "2020-01-01", Product.Type.Food),
        Product("Calculator3", 9.0, null, Product.Type.Equipment),
    )
    fun loadProducts() {
        _productsResult.value = Result.Success(products)
    }
}