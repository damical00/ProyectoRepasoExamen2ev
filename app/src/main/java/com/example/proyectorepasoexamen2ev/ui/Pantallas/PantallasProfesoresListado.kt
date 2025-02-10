package com.example.proyectorepasoexamen2ev.ui.Pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.proyectorepasoexamen2ev.R
import com.example.proyectorepasoexamen2ev.modelo.Profesores
import com.example.proyectorepasoexamen2ev.ui.ProfesoresUiState

// Composable principal que maneja los diferentes estados de la pantalla
@Composable
fun PantallasProfesores(
    appUiState: ProfesoresUiState, // Estado actual de la UI
    onProfesoresObtenidos: () -> Unit, // Acción cuando los profesores son obtenidos
    onProfesorPulsado: (Int) -> Unit, // Acción cuando un profesor es seleccionado
    modifier: Modifier// Modificador para la composición
) {
    when (appUiState) {
        is ProfesoresUiState.Cargando -> PantallaCargando(modifier = modifier.fillMaxSize()) // Muestra la pantalla de carga

        is ProfesoresUiState.Error -> PantallaError(modifier = modifier.fillMaxWidth()) // Muestra la pantalla de error

        is ProfesoresUiState.ObtenerExitoTodos -> PantallasProfesoresListado(
            onProfesorPulsado = onProfesorPulsado,
            modifier = Modifier.fillMaxWidth(),
            lista = appUiState.profesores,  // Aquí ya estás pasando correctamente la lista de profesores
            appUiState = appUiState,  // Aquí pasa appUiState en lugar de uiStateRoom
            onProfesoresObtenidos = { onProfesoresObtenidos() }
        )


        is ProfesoresUiState.ObtenerExito, is ProfesoresUiState.CrearExito, is ProfesoresUiState.ActualizarExito -> onProfesoresObtenidos() // Llama a la función cuando se completa la operación
    }


    // Composable que muestra una imagen en caso de error
    @Composable
    fun PantallaError(modifier: Modifier) {
        Image(
            modifier = modifier.size(200.dp),
            painter = painterResource(R.drawable.error), // Imagen de error
            contentDescription = stringResource(R.string.error) // Descripción accesible
        )
    }

    // Composable que muestra una imagen de carga mientras se obtienen los datos
    @Composable
    fun PantallaCargando(modifier: Modifier) {
        Image(
            modifier = modifier.size(200.dp),
            painter = painterResource(R.drawable.loading), // Imagen de carga
            contentDescription = "Cargando" // Texto descriptivo
        )
    }
}
// Composable que muestra la lista de profesores en una LazyColumn
@Composable
fun PantallasProfesoresListado(
    onProfesorPulsado: (Int) -> Unit,
    modifier: Modifier = Modifier,  // Valor predeterminado para modifier
    lista: List<Profesores>,
    appUiState: ProfesoresUiState,
    onProfesoresObtenidos: () -> Unit
) {
    LazyColumn(modifier = modifier) { // Usa el modifier directamente
        items(lista) { profesor ->
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .clickable(onClick = { onProfesorPulsado(profesor.id) })
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Divider(Modifier.height(2.dp))
                    Text(text = profesor.nombre)
                    Text(text = profesor.apellido)
                    Text(text = profesor.departamento)
                    Text(text = profesor.anyosExp.toString())
                }
            }
        }
    }

}
