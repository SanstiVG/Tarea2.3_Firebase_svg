package com.example.pruebafirebase.mvvm

import kotlinx.serialization.Serializable

@Serializable
data class ProductoUiState(
    var id:String = "",
    val nombre:String = "",
    val precio: Double = 0.0,
    val descripcion: String = "",
    val imagen: String = ""
)
