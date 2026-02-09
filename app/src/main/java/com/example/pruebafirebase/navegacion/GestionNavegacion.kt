package com.example.pruebafirebase.navegacion

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.pruebafirebase.pantallas.PantallaHome
import com.example.pruebafirebase.pantallas.PantallaLogin
import com.example.pruebafirebase.pantallas.PantallaProducto
import com.example.pruebafirebase.pantallas.PantallaRegistro
import com.google.firebase.auth.FirebaseAuth

@Composable
fun GestionNavegacion(auth: FirebaseAuth) {
    val pilaNavegacion = rememberNavBackStack(Routes.Login)

    NavDisplay(
        backStack = pilaNavegacion,
        onBack = {pilaNavegacion.removeLastOrNull()},
        entryProvider = { key->
            when(key) {
                is Routes.Login -> NavEntry(key){
                    PantallaLogin(
                        auth = auth,
                        navegaRegistro = {
                            pilaNavegacion.add(Routes.Registro)
                        },
                        navegaHome = {
                            id -> pilaNavegacion.add(Routes.Home(id))
                        }
                    )
                }
                is Routes.Registro -> NavEntry(key){
                    PantallaRegistro(
                        auth = auth,
                        navegaAtras = {
                            pilaNavegacion.removeLastOrNull()
                        }
                    )
                }
                is Routes.Home -> NavEntry(key){

                    PantallaHome( auth = auth,
                        navegaAtras = {
                            auth.signOut()
                            pilaNavegacion.removeLastOrNull()
                        },
                        navegaProducto = { producto -> pilaNavegacion.add(Routes.Producto(producto))
                        }
                    )
                }
                is Routes.Producto -> NavEntry(key){

                    PantallaProducto(
                        producto = key.producto,
                        navegaAtras = {
                            pilaNavegacion.removeLastOrNull()
                        },
                    )
                }
                else -> NavEntry(Routes.Error){
                    Text("Error")
                }
            }
        }
    )
}