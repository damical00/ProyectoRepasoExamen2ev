package com.example.proyectorepasoexamen2ev.ui.Pantallas

import android.widget.NumberPicker
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.proyectorepasoexamen2ev.R
import com.example.proyectorepasoexamen2ev.modelo.Profesores

@Composable
fun PantallaInsertarProfesor(
    onInsertarPulsado: (Profesores) -> Unit,
    modifier: Modifier
) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var departamento by remember { mutableStateOf("") }
    var anyosExp by remember { mutableStateOf(0) } // Estado del NumberPicker

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Spacer(Modifier.height(16.dp))

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

        TextField(
            value = departamento,
            label = { Text(text = stringResource(R.string.departamento)) },
            onValueChange = { departamento = it }
        )
        Spacer(Modifier.height(15.dp))

        // Integrando el NumberPicker
        StepperNumberPicker(
            value = anyosExp,
            onValueChange = { anyosExp = it },
            minValue = 0,
            maxValue = 100
        )


        /*
        TextField(
            // Convertimos el valor entero a String para mostrarlo en el TextField
            TextField(
                value = anyosExp.toString(), // Convierte el Int a String para el TextField
                onValueChange = { newValue ->
                    // Intentamos convertir el nuevo valor a Int, si no es válido, asignamos 0
                    anyosExp = newValue.toIntOrNull() ?: 0 // Esto maneja casos donde el usuario ingresa texto no numérico
                },
                label = { Text(text = stringResource(id = R.string.anyosExp)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        */

        Button(
            onClick = {
                val profesor = Profesores(
                    nombre = nombre,
                    apellido = apellido,
                    departamento = departamento,
                    anyosExp = anyosExp
                )
                onInsertarPulsado(profesor)
            }
        ) {
            Text(text = stringResource(R.string.insertar))
        }
    }
}

@Composable
fun StepperNumberPicker(
    value: Int, // Valor actual
    onValueChange: (Int) -> Unit, // Callback para actualizar el valor
    minValue: Int = 0, // Valor mínimo
    maxValue: Int = 40 // Valor máximo
) {
    AndroidView(
        factory = { context ->
            NumberPicker(context).apply {
                this.minValue = minValue
                this.maxValue = maxValue
                this.value = value
                setOnValueChangedListener { _, _, newVal ->
                    onValueChange(newVal)
                }
            }
        },
        update = { numberPicker ->
            numberPicker.value = value
        }
    )
}
