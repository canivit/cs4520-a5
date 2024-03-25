package com.cs4520.assignment5

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey val name: String,
    val price: Double,
    val expiryDate: String?,
    val type: Type
) {
    enum class Type {
        Food,
        Equipment
    }
}