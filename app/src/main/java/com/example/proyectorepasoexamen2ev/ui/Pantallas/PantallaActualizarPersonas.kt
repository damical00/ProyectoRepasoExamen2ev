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

/**
 * Pantalla para actualizar la información de una persona.
 *
 * @param personas Objeto de tipo Personas que contiene la información actual.
 * @param onPersonaActualizada Función lambda que maneja la actualización de la persona.
 * @param modifier Modificador para ajustar el diseño del componente.
 */
@Composable
fun PantallaActualizarPersonas(
    personas: Personas,
    onPersonaActualizada: (Personas) -> Boolean,
    modifier: Modifier
) {
    // Variables de estado para los campos de entrada y control de la fecha seleccionada
    var dni by remember { mutableStateOf(personas.dni) }
    var nombre by remember { mutableStateOf(personas.nombre) }
    var apellido by remember { mutableStateOf(personas.apellido) }
    var fechaElegida: Long? by remember { mutableStateOf(null) }
    var botonFechaPulsado by remember { mutableStateOf(false) }

    // Columna que organiza los elementos verticalmente
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Spacer(Modifier.height(15.dp)) // Espaciador para separación vertical

        // Campo de texto para mostrar el ID (solo lectura)
        TextField(
            value = personas.id,
            label = { Text(text = stringResource(R.string.id)) },
            onValueChange = {},
            enabled = false
        )

        Spacer(Modifier.height(15.dp))

        // Campo de texto para el nombre
        TextField(
            value = nombre,
            label = { Text(text = stringResource(R.string.nombre)) },
            onValueChange = { nombre = it },
        )

        Spacer(Modifier.height(15.dp))

        // Campo de texto para el apellido
        TextField(
            value = apellido,
            label = { Text(text = stringResource(R.string.apellido)) },
            onValueChange = { apellido = it },
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
                    fechaElegida = fecha // Asigna la fecha seleccionada
                    botonFechaPulsado = false // Cierra el diálogo
                },
                onDismiss = { botonFechaPulsado = false } // Cierra el diálogo sin seleccionar
            )
        }

        // Mostrar la fecha seleccionada o un mensaje si no se ha seleccionado ninguna
        if (fechaElegida != null) {
            val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(fechaElegida!!))
            Text(text = "Fecha seleccionada: $formattedDate")
        } else {
            Text(text = stringResource(R.string.ninguna_fecha_seleccinada))
        }

        Spacer(Modifier.height(15.dp))

        // Botón para actualizar la información de la persona
        Button(
            onClick = {
                // Formatea la fecha seleccionada o usa la original si no se ha cambiado
                val formattedFecha = fechaElegida?.let {
                    SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(it))
                } ?: personas.fechaNacimiento

                // Crea un nuevo objeto Personas con la información actualizada
                val personaActualizada = Personas(
                    id = personas.id,
                    dni = dni,
                    nombre = nombre,
                    apellido = apellido,
                    fechaNacimiento = formattedFecha
                )

                // Llama a la función de actualización y maneja el resultado
                val actualizado = onPersonaActualizada(personaActualizada)
            }
        ) {
            Text(text = stringResource(R.string.actualizar))
        }
    }
}

/**
 * Composable que muestra un diálogo de selección de fecha.
 *
 * @param onConfirm Función lambda que maneja la confirmación de la fecha seleccionada.
 * @param onDismiss Función lambda que maneja la cancelación del diálogo.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerMostrado(
    onConfirm: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState() // Estado del DatePicker

    // Diálogo que contiene el DatePicker
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                datePickerState.selectedDateMillis?.let { onConfirm(it) } // Llama a onConfirm con la fecha seleccionada
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
        DatePicker(state = datePickerState) // Componente DatePicker con su estado
    }
}
