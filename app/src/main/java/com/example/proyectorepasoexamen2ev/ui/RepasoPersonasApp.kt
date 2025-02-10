package com.example.proyectorepasoexamen2ev.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.proyectorepasoexamen2ev.R
import com.example.proyectorepasoexamen2ev.modelo.Personas
import com.example.proyectorepasoexamen2ev.ui.Pantallas.PantallaActualizarPersonas
import com.example.proyectorepasoexamen2ev.ui.Pantallas.PantallaInsertarPersonas
import com.example.proyectorepasoexamen2ev.ui.Pantallas.PantallaPersonas

// Enum que define las diferentes pantallas del proyecto con sus títulos
enum class PantallasProyecto(@StringRes val titulo: Int) {
    Inicio(titulo = R.string.inicio),
    Insertar(titulo = R.string.insertar),
    Actualizar(titulo = R.string.actualizar)
}

// Función principal de la aplicación que maneja la navegación y el estado de la UI
@Composable
fun PersonasApp(
    viewModel: PersonasViewModel = viewModel(factory = PersonasViewModel.Factory),
    navController: NavHostController = rememberNavController()
) {
    val pilaRetroceso by navController.currentBackStackEntryAsState()

    // Obtener la pantalla actual en función de la pila de retroceso
    val pantallaActual = PantallasProyecto.valueOf(
        pilaRetroceso?.destination?.route ?: PantallasProyecto.Inicio.name
    )

    Scaffold(
        topBar = {
            // Barra superior de la aplicación
            AppTopBar(
                pantallaActual = pantallaActual,
                puedeNavegarAtras = navController.previousBackStackEntry != null,
                onNavegarAtras = { navController.navigateUp() }
            )
        },
        floatingActionButton = {
            // Botón flotante para insertar personas en la pantalla de inicio
            if (pantallaActual.titulo == R.string.inicio) {
                FloatingActionButton(
                    onClick = { navController.navigate(route = PantallasProyecto.Insertar.name) }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(R.string.insertar_persona)
                    )
                }
            }
        }
    ) { innerPadding ->
        val uiState = viewModel.personasUiState

        // Configuración del NavHost para manejar la navegación entre pantallas
        NavHost(
            navController = navController,
            startDestination = PantallasProyecto.Inicio.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Grafo de rutas para cada pantalla
            composable(route = PantallasProyecto.Inicio.name) {
                PantallaPersonas(
                    appUiState = uiState,
                    onPersonasObtenidas = {
                        viewModel.obtenerPersonas() // Obtener lista de personas cuando se carga la pantalla de inicio
                    },
                    onPersonaEliminada = {
                        viewModel.eliminarPersona(it) // Eliminar persona seleccionada
                    },
                    onPersonaPulsada = {
                        viewModel.actualizarPersonaPulsada(it) // Actualizar persona pulsada
                        navController.navigate(PantallasProyecto.Actualizar.name) // Navegar a la pantalla de actualización
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(route = PantallasProyecto.Insertar.name) {
                PantallaInsertarPersonas(
                    personas = viewModel.personaPulsada,
                    onInsertarPulsado = {
                        viewModel.insertarPersonas(it) // Insertar nueva persona
                        navController.popBackStack(PantallasProyecto.Inicio.name, inclusive = false) // Navegar de vuelta a la pantalla de inicio
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(route = PantallasProyecto.Actualizar.name) {
                PantallaActualizarPersonas(
                    personas = viewModel.personaPulsada,
                    onPersonaActualizada = {
                        viewModel.actualizarPersona(it.id, it) // Actualizar datos de la persona
                        navController.popBackStack(PantallasProyecto.Inicio.name, inclusive = false) // Navegar de vuelta a la pantalla de inicio
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

// Barra superior de la aplicación con título y botón de navegación
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    pantallaActual: PantallasProyecto,
    puedeNavegarAtras: Boolean,
    onNavegarAtras: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(text = stringResource(id = pantallaActual.titulo)) }, // Título de la pantalla actual
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        navigationIcon = {
            if (puedeNavegarAtras) {
                IconButton(onClick = onNavegarAtras) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.atras) // Botón de navegación atrás
                    )
                }
            }
        },
        modifier = modifier
    )
}
