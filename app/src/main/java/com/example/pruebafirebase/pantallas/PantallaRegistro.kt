package com.example.pruebafirebase.pantallas

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun PantallaRegistro(auth: FirebaseAuth, navegaAtras:()->Unit) {
    var email by remember { mutableStateOf("") }
    var contra by remember { mutableStateOf("") }
    var repeContra by remember { mutableStateOf("") }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text("Registro de usuario/a", fontSize = 30.sp)

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            singleLine = true
        )
        OutlinedTextField(
            value = contra,
            onValueChange = { contra = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Contraseña") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            singleLine = true
        )
        OutlinedTextField(
            value = repeContra,
            onValueChange = { repeContra = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Repite contraseña") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if(contra.equals(repeContra)) {
                auth.createUserWithEmailAndPassword(email, contra)
                    .addOnSuccessListener {
                        navegaAtras()
                    }
                    .addOnFailureListener {
                        Log.e("Login", "Error al iniciar sesión", it)
                    }
                }
            }, modifier = Modifier.fillMaxWidth()) {
            Text("Regístrate")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {navegaAtras()}, modifier = Modifier.fillMaxWidth()) {
            Text("Cancelar")
        }


    }
}