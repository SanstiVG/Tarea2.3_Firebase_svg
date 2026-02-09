package com.example.pruebafirebase.navegacion

import androidx.navigation3.runtime.NavKey
import com.example.pruebafirebase.mvvm.ProductoUiState
import kotlinx.serialization.Serializable

sealed class Routes: NavKey {
    @Serializable
    data object Login: Routes()
    @Serializable
    data object Registro: Routes()

    @Serializable
    data class Home(val id: String): Routes()

    @Serializable
    data class Producto(val producto: ProductoUiState): Routes()

    @Serializable
    data object Error: Routes()
}