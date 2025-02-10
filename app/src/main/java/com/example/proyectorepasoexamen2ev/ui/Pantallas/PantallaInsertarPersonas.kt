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
fun PantallaInsertarPersonas(
    personas: Personas,
    onInsertarPulsado: (Personas) -> Unit,
    modifier: Modifier
) {
    // Estados para almacenar los valores de los campos de texto y la fecha seleccionada
    var dni by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var fechaElegida: Long? by remember { mutableStateOf(null) }
    var botonFechaPulsado by remember { mutableStateOf(false) }

    // Columna principal que organiza los elementos de la interfaz de usuario verticalmente
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
    ) {
        // Espaciador para añadir espacio entre los elementos
        Spacer(Modifier.height(15.dp))

        // Campo de texto para ingresar el DNI
        TextField(
            value = dni,
            label = { Text(text = stringResource(R.string.dni)) },
            onValueChange = { dni = it }
        )

        Spacer(Modifier.height(15.dp))

        // Campo de texto para ingresar el nombre
        TextField(
            value = nombre,
            label = { Text(text = stringResource(R.string.nombre)) },
            onValueChange = { nombre = it }
        )

        Spacer(Modifier.height(15.dp))

        // Campo de texto para ingresar el apellido
        TextField(
            value = apellido,
            label = { Text(text = stringResource(R.string.apellido)) },
            onValueChange = { apellido = it }
        )

        Spacer(Modifier.height(15.dp))

        // Botón para abrir el selector de fecha
        Button(onClick = { botonFechaPulsado = true }) {
            Text(text = stringResource(R.string.fechaNacimiento))
        }

        // Mostrar el DatePickerDialog si el botón de fecha ha sido pulsado
        if (botonFechaPulsado) {
            DatePickerMostrado(
                onConfirm = { fecha ->
                    fechaElegida = fecha  // Almacena la fecha seleccionada
                    botonFechaPulsado = false  // Cierra el diálogo
                },
                onDismiss = { botonFechaPulsado = false }  // Cierra el diálogo sin seleccionar fecha
            )
        }

        // Mostrar la fecha seleccionada o un mensaje si no se ha seleccionado ninguna fecha
        if (fechaElegida != null) {
            val formattedDate =
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(fechaElegida!!))
            Text(text = "Fecha seleccionada: $formattedDate")
        } else {
            Text(text = stringResource(R.string.ninguna_fecha_seleccinada))
        }

        Spacer(Modifier.height(16.dp))

        // Formatear la fecha seleccionada o usar la fecha original si no se ha cambiado
        val formattedFecha = fechaElegida?.let {
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(it))
        } ?: personas.fechaNacimiento

        // Botón para insertar la persona con los datos ingresados
        Button(
            onClick = {
                val persona = Personas(
                    dni = dni,
                    nombre = nombre,
                    apellido = apellido,
                    fechaNacimiento = formattedFecha
                )
                onInsertarPulsado(persona)  // Llama a la función de callback con la nueva persona
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
    // Estado del DatePicker
    val datePickerState = rememberDatePickerState()

    // Diálogo de selección de fecha
    DatePickerDialog(
        onDismissRequest = onDismiss,  // Cierra el diálogo si se pulsa fuera de él
        confirmButton = {
            TextButton(onClick = {
                datePickerState.selectedDateMillis?.let { onConfirm(it) }  // Confirma la fecha seleccionada
                onDismiss()  // Cierra el diálogo
            }) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {  // Cierra el diálogo sin confirmar
                Text("Cancelar")
            }
        }
    ) {
        DatePicker(state = datePickerState)  // Muestra el DatePicker
    }
}