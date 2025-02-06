package com.example.proyectorepasoexamen2ev.ui.Pantallas

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.proyectorepasoexamen2ev.R
import com.example.proyectorepasoexamen2ev.modelo.Personas
import com.example.proyectorepasoexamen2ev.ui.PantallasProyecto
import com.example.proyectorepasoexamen2ev.ui.PersonasUiState
import androidx.compose.material3.Text


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
        is PersonasUiState.ObtenerExito -> PantaListadoPersonas(
            lista = appUiState.personas,
            onPersonaPulsada = onPersonaPulsada,
            onPersonaEliminada = onPersonaEliminada,
            modifier = modifier.fillMaxWidth()
        )
        is  PersonasUiState.CrearExito -> onPersonasObtenidas()
        is  PersonasUiState.ActualizarExito -> onPersonasObtenidas()
        is  PersonasUiState.EliminarExito -> onPersonasObtenidas()
    }
}

@Composable
fun PantallaError(modifier: Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.error),
        contentDescription = stringResource(R.string.error)
    )
}

@Composable
fun PantallaCargando(modifier: Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription ="Cargando"
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PantaListadoPersonas(
    lista: List<Personas>,
    onPersonaPulsada: (Personas) -> Unit,
    onPersonaEliminada: (String) -> Unit,
    modifier: Modifier
) {
    LazyColumn(modifier = modifier) {
        items(lista) { persona ->
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .combinedClickable(
                        onClick = { onPersonaPulsada(persona) },
                        onLongClick = { onPersonaEliminada(persona.id) }
                    )
            ){
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Divider(Modifier.height(2.dp))

                            Text(text = persona.dni)

                            Text(text = persona.nombre)

                            Text(text = persona.apellido)

                            Text(text = persona.fechaNacimiento)

                        }
                    }

        }
    }
}
