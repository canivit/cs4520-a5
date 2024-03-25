package com.cs4520.assignment5

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager

@Suppress("UNCHECKED_CAST")
class ProductsViewModelFactory(private val context: Context) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductsViewModel(
            WorkManager.getInstance(context),
            Database.getInstance(context)
        ) as T
    }
}