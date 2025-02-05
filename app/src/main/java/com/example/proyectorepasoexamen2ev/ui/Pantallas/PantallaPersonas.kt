package com.example.proyectorepasoexamen2ev.ui.Pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.proyectorepasoexamen2ev.modelo.Personas
import com.example.proyectorepasoexamen2ev.ui.PantallasProyecto
import com.example.proyectorepasoexamen2ev.ui.PersonasUiState

@Composable
fun PantallaPersonas(
    appUiState: PersonasUiState,
    onPersonasObtenidas:() -> Unit,
    onPersonaPulsada: (Personas) -> Unit,
    onPersonaEliminada: (String) -> Unit,
    modifier: Modifier = Modifier
){
    when(appUiState){
        is PersonasUiState.Cargando -> PantallaCargando(modifier = modifier.fillMaxSize())
        is PersonasUiState.Error -> PantallaError(modifier = modifier.fillMaxWidth())
    }
}

@Composable
fun PantallaCargando(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription ="Cargando"
    )
}

@Composable
fun PantallaError(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.error),
        contentDescription = stringResource(R.string.error)
    )
}