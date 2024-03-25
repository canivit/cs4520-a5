package com.cs4520.assignment5

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp

@Composable
fun ProductsScreen(
    viewModel: ProductsViewModel = viewModel(
        factory = ProductsViewModelFactory(
            LocalContext.current
        )
    )
) {
    val productsResult: MutableState<Result<List<Product>>> = remember {
        mutableStateOf(
            Result.Success(
                listOf()
            )
        )
    }
    viewModel.loadProducts()
    viewModel.productsResult.observe(LocalLifecycleOwner.current) { result ->
        productsResult.value = result
    }

    when (val result = productsResult.value) {
        is Result.Error -> Toast.makeText(LocalContext.current, result.msg, Toast.LENGTH_LONG)
            .show()

        is Result.Success -> ProductList(list = result.value, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun ProductList(list: List<Product>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(items = list, key = { product -> product.name }) { product ->
            ProductListItem(product = product)
        }
    }
}

@Composable
fun ProductListItem(product: Product) {
    val backgroundColor = if (product.type == Product.Type.Food) {
        colorResource(id = R.color.food_product_background)
    } else {
        colorResource(id = R.color.equipment_product_background)
    }
    val painter = if (product.type == Product.Type.Food) {
        painterResource(id = R.drawable.food)
    } else {
        painterResource(id = R.drawable.equipment)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(color = backgroundColor)
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Image(
            painter = painter,
            contentDescription = "Product Image",
            Modifier.size(100.dp, 100.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = product.name,
                fontSize = 20.sp,
                modifier = Modifier.padding(PaddingValues(vertical = 4.dp))
            )
            if (product.expiryDate != null) {
                Text(
                    text = product.expiryDate,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(PaddingValues(vertical = 4.dp))
                )
            }
            Text(
                text = product.price.toString(),
                fontSize = 20.sp,
                modifier = Modifier.padding(PaddingValues(vertical = 4.dp))
            )
        }
    }
}