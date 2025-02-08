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
fun PantallaActualizarPersonas(
    personas: Personas,
    onPersonaActualizada: (Personas) -> Boolean,
    modifier: Modifier
) {
    var dni by remember { mutableStateOf(personas.dni) }
    var nombre by remember { mutableStateOf(personas.nombre) }
    var apellido by remember { mutableStateOf(personas.apellido) }
    var fechaNacimiento by remember { mutableStateOf(personas.fechaNacimiento) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Spacer(Modifier.height(15.dp))

        TextField(
            value = personas.id,
            label = { Text(text = stringResource(R.string.id)) },
            onValueChange = {},
            enabled = false
        )

        Spacer(Modifier.height(15.dp))

        TextField(
            value = personas.nombre,
            label = { Text(text = stringResource(R.string.nombre)) },
            onValueChange = {},
            enabled = false
        )

        Spacer(Modifier.height(15.dp))

        TextField(
            value = personas.apellido,
            label = { Text(text = stringResource(R.string.apellido)) },
            onValueChange = {},
            enabled = false
        )

        Spacer(Modifier.height(15.dp))

        TextField(
            value = personas.fechaNacimiento,
            label = { Text(text = stringResource(R.string.fechaNacimiento)) },
            onValueChange = {},
            enabled = false
        )

        Spacer(Modifier.height(15.dp))

        Button(
            onClick = {
                val persona = Personas(dni=dni, nombre = nombre, apellido = apellido, fechaNacimiento = fechaNacimiento)
                onPersonaActualizada(persona)
            }
        ) {
            Text(text = stringResource(R.string.actualizar))
        }
    }

}