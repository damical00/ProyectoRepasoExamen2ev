package com.example.proyectorepasoexamen2ev.ui.Pantallas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PantallaInsertarPersonas(personas: Personas,onInsertarPulsado: (Personas) -> Unit, modifier: Modifier) {
    var dni by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var fechaElegida: Long? by remember { mutableStateOf(null) }
    var botonFechaPulsado by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
    ) {

        Spacer(Modifier.height(15.dp))

        TextField(
            value = dni,
            label = { Text(text = stringResource(R.string.dni)) },
            onValueChange = { dni = it }
        )

        Spacer(Modifier.height(15.dp))

        TextField(
            value = nombre,
            label = { Text(text = stringResource(R.string.nombre)) },
            onValueChange = { nombre = it }
        )

        Spacer(Modifier.height(15.dp))

        TextField(
            value = apellido,
            label = { Text(text = stringResource(R.string.apellido)) },
            onValueChange = { apellido = it }
        )

        Spacer(Modifier.height(15.dp))

        Button(onClick = { botonFechaPulsado = true }) {
            Text(text = stringResource(R.string.fechaNacimiento))
        }

        if (botonFechaPulsado) {
            DatePickerMostrado(
                onConfirm = { fecha ->
                    fechaElegida =
                        fecha  // Asegúrate de que `fecha` ya sea un String con el formato correcto
                    botonFechaPulsado = false
                },
                onDismiss = { botonFechaPulsado = false }
            )
        }

        if (fechaElegida != null) {
            val formattedDate =
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(fechaElegida!!))
            Text(text = "Fecha seleccionada: $formattedDate")
        } else {
            Text(text = stringResource(R.string.ninguna_fecha_seleccinada))
        }


        Spacer(Modifier.height(16.dp))

        val formattedFecha = fechaElegida?.let {
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(it))
        } ?: personas.fechaNacimiento // Si no se cambia la fecha, usa la original

        Button(
            onClick = {
                val persona = Personas(
                    dni = dni,
                    nombre = nombre,
                    apellido = apellido,
                    fechaNacimiento = formattedFecha
                )
                onInsertarPulsado(persona)
            }
        ) {
            Text(text = stringResource(R.string.insertar))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerInsertar(
    onConfirm: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                datePickerState.selectedDateMillis?.let { onConfirm(it) }
                onDismiss()
            }) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}