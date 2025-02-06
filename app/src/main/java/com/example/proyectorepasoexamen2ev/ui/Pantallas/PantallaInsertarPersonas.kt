package com.example.proyectorepasoexamen2ev.ui.Pantallas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.proyectorepasoexamen2ev.R
import com.example.proyectorepasoexamen2ev.modelo.Personas

@Composable
fun PantallaInsertarPersonas(
    onInsertarPulsado: (Personas) -> Unit,
    modifier: Modifier = Modifier
) {
    var dni by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
    ) {

        Spacer(Modifier.height(15.dp))

        TextField(
            value = dni,
            label = { Text(text = stringResource(R.string.dni))},
            onValueChange = {dni = it}
        )

        Spacer(Modifier.height(15.dp))

        TextField(
            value = nombre,
            label = { Text(text = stringResource(R.string.nombre))},
            onValueChange = {nombre = it}
        )

        Spacer(Modifier.height(15.dp))

        TextField(
            value = apellido,
            label = { Text(text = stringResource(R.string.apellido))},
            onValueChange = {apellido = it}
        )

        Spacer(Modifier.height(15.dp))

        TextField(
            value = fechaNacimiento,
            label = { Text(text = stringResource(R.string.fechaNacimiento))},
            onValueChange = {fechaNacimiento = it}
        )

        Spacer(Modifier.height(15.dp))

        TextField(
            value = nombre,
            label = { Text(text = stringResource(R.string.nombre))},
            onValueChange = {nombre = it}
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                val coche = Personas(dni=dni, nombre = nombre, apellido = apellido, fechaNacimiento = fechaNacimiento)
                onInsertarPulsado(coche)
            }
        ) {
            Text(text = stringResource(R.string.insertar))
        }
    }
}