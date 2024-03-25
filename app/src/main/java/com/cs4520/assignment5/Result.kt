package com.cs4520.assignment5

sealed interface Result<T> {
    data class Success<T>(val value: T) : Result<T>
    data class Error<T>(val msg: String) : Result<T>
}