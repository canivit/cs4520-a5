package com.cs4520.assignment5

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavHostController

@Composable
fun LoginScreen(navHostController: NavHostController) {
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val context = LocalContext.current

        TextField(
            value = userName,
            onValueChange = { value -> userName = value },
            label = { Text(text = "Username") })
        TextField(
            value = password,
            onValueChange = { value -> password = value },
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Button(onClick = {
            if (userName == "admin" && password == "admin") {
                navHostController.navigate(Screen.PRODUCTS.name)
            } else {
                Toast.makeText(context, "Invalid username or password", Toast.LENGTH_LONG).show()
            }
        }) {
            Text(text = "Login")
        }

    }
}