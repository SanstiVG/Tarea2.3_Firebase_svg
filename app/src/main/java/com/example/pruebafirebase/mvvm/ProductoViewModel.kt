package com.example.pruebafirebase.mvvm

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProductoViewModel: ViewModel() {

    private val db = Firebase.firestore
    private val productosCollection = db.collection("productos")

    private val _uiState = MutableStateFlow<List<ProductoUiState>>(emptyList())
    val uiState: StateFlow<List<ProductoUiState>> = _uiState

    init {
        getProductos()
    }

    fun updateProducto(idProducto:String, nuevoNombre:String, nuevoPrecio:Double?, nuevoDescripcion:String, nuevoImagen: String) {
        val datosActualizados = mutableMapOf<String, Any>()
        if (nuevoNombre.isNotBlank()) {
            datosActualizados["nombre"] = nuevoNombre
        }
        if (nuevoPrecio != null) {
            datosActualizados["precio"] = nuevoPrecio
        }
        if (nuevoNombre.isNotBlank()) {
            datosActualizados["descripcion"] = nuevoDescripcion
        }
        if (nuevoImagen.isNotBlank()) {
            datosActualizados["imagen"] = nuevoImagen
        }
        productosCollection.document(idProducto)
            .update(datosActualizados as Map<String, Any>)
            .addOnSuccessListener {
                Log.i("Firebase","Actualizado correcto")
            }
            .addOnFailureListener { e ->
                Log.e("Error Firebase","Error al actualizar: ${e.message}",e)
        }
    }

    fun deleteProducto(idProducto:String) {
        productosCollection.document(idProducto)
            .delete()
            .addOnSuccessListener {
                Log.i("Firebase","Borrado correcto")
            }
            .addOnFailureListener { e ->
                Log.e("Error Firebase","Error al borrar: ${e.message}",e)
            }

    }

    fun addProducto(nombre:String, precio:Double,descripcion: String,imagen: String) {
        val producto = ProductoUiState(nombre = nombre, precio = precio, descripcion = descripcion, imagen = imagen)
        productosCollection.add(producto)
            .addOnSuccessListener {}
            .addOnFailureListener { e ->
                Log.e("Error Firebase","Error al guardar: ${e.message}")
            }
            .addOnCompleteListener {}
    }

    private fun getProductos() {
        productosCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val productosList = snapshot.documents.mapNotNull { doc ->
                    val producto = doc.toObject(ProductoUiState::class.java)
                    producto?.id = doc.id
                    producto
                }
                _uiState.value = productosList
            }
        }
    }
}