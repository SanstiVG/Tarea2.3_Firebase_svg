package com.example.pruebafirebase.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pruebafirebase.R
import com.example.pruebafirebase.mvvm.ProductoViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pruebafirebase.mvvm.ProductoUiState
import com.google.firebase.auth.FirebaseAuth

@Composable
fun PantallaHome(viewModel: ProductoViewModel = viewModel(), auth: FirebaseAuth, navegaAtras:()->Unit, navegaProducto:(ProductoUiState)->Unit) {
    val uiState by viewModel.uiState.collectAsState()
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var imagen by remember { mutableStateOf("") }
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp,vertical= 8.dp)
                    .fillMaxWidth()
            ) {
                Text("Bienvenido ${auth.currentUser?.email}", fontSize = 16.sp,
                    modifier = Modifier.weight(1f).padding(start = 8.dp))
                //Spacer(modifier = Modifier.width(32.dp))
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = "",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable(onClick = { navegaAtras() })
                        .padding(end = 8.dp)
                        //.weight(1f)
                )
            }

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 2.dp),
                label = { Text("Nombre del producto") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                singleLine = true
            )

            OutlinedTextField(
                value = precio,
                onValueChange = { precio = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 2.dp),
                label = { Text("Precio") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true
            )

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 2.dp),
                label = { Text("Descripción") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),

            )

            OutlinedTextField(
                value = imagen,
                onValueChange = { imagen = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 2.dp),
                label = { Text("URL imagen") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                singleLine = true
            )

            Spacer(Modifier.height(8.dp))
            Button(onClick = {
                viewModel.addProducto(nombre, precio.toDouble(),descripcion,imagen)
                nombre = ""
                precio = ""
                descripcion = ""
                imagen=""
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar Producto")

            }
            Spacer(Modifier.height(4.dp))

            LazyColumn() {
                items (uiState) { prod ->
                    ProductoItem(
                        producto = prod,
                        onBorrar = { viewModel.deleteProducto(prod.id) },
                        onEditar = { viewModel.updateProducto(prod.id,
                            nombre,
                            precio.toDouble(),
                            descripcion,
                            imagen)
                                   nombre=""
                                   precio=""
                                   descripcion=""
                                   imagen=""},
                        onVer = { navegaProducto(prod) }
                    )
                }
            }
        }
    }
}

@Composable
fun ProductoItem(producto: ProductoUiState, onBorrar: () -> Unit, onEditar: () -> Unit, onVer: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal=16.dp, vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = producto.nombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = producto.precio.toString(),
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }

            IconButton(onClick = onVer) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = ""
                )
            }

            IconButton(onClick = onEditar) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = ""
                )
            }

            IconButton(onClick = onBorrar) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "",
                    tint = Color.Red
                )
            }
        }
    }

}
