package com.example.proyectorepasoexamen2ev.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.proyectorepasoexamen2ev.R
import com.example.proyectorepasoexamen2ev.modelo.Ruta
import com.example.proyectorepasoexamen2ev.ui.Pantallas.PantallaActualizarPersonas
import com.example.proyectorepasoexamen2ev.ui.Pantallas.PantallaActualizarProfesor
import com.example.proyectorepasoexamen2ev.ui.Pantallas.PantallaInsertarPersonas
import com.example.proyectorepasoexamen2ev.ui.Pantallas.PantallaInsertarProfesor
import com.example.proyectorepasoexamen2ev.ui.Pantallas.PantallaPersonas
import com.example.proyectorepasoexamen2ev.ui.Pantallas.PantallasProfesores

// Enum que define las diferentes pantallas del proyecto con sus títulos
enum class PantallasProyecto(@StringRes val titulo: Int) {
    Inicio(titulo = R.string.inicio),
    Insertar(titulo = R.string.insertar),
    Actualizar(titulo = R.string.actualizar),
    ListadoProfesores(titulo = R.string.listado_profesores),
    InsertarProfesor(titulo = R.string.insertar_profesor),
    ActualizarProfesores(titulo = R.string.actualizar_profesores)
}

val listaRutas = listOf(
    Ruta(
        PantallasProyecto.Inicio.titulo,
        PantallasProyecto.Inicio.name,
        Icons.Filled.AccountCircle,
        Icons.Outlined.AccountCircle
    ),
    Ruta(
        PantallasProyecto.ListadoProfesores.titulo,
        PantallasProyecto.ListadoProfesores.name,
        Icons.Filled.Face,
        Icons.Outlined.Face
    )

)

// Función principal de la aplicación que maneja la navegación y el estado de la UI
@Composable
fun PersonasApp(
    viewModelJSON: PersonasViewModel = viewModel(factory = PersonasViewModel.Factory),
    viewModelRoom: ProfesoresViewModel = viewModel(factory = ProfesoresViewModel.Factory),
    navController: NavHostController = rememberNavController()
) {
    val pilaRetroceso by navController.currentBackStackEntryAsState()

    // Obtener la pantalla actual en función de la pila de retroceso
    val pantallaActual = PantallasProyecto.valueOf(
        pilaRetroceso?.destination?.route ?: PantallasProyecto.Inicio.name
    )

    var selectedItem by remember { mutableStateOf(0) }

    Scaffold(

        bottomBar = {
            NavigationBar {
                listaRutas.forEachIndexed { indice, ruta ->
                    NavigationBarItem(
                        icon = {
                            if(selectedItem == indice)
                                Icon(
                                    imageVector = ruta.iconoLleno,
                                    contentDescription = stringResource(id = ruta.nombre)
                                )
                            else
                                Icon(
                                    imageVector = ruta.iconoVacio,
                                    contentDescription = stringResource(id = ruta.nombre)
                                )
                        },
                        label = { Text(stringResource(id = ruta.nombre)) },
                        selected = selectedItem == indice,
                        onClick = {
                            selectedItem = indice
                            navController.navigate(ruta.ruta)
                        }
                    )
                }
            }
        },
        modifier = Modifier.fillMaxSize(),

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

            // Botón flotante para insertar personas en la pantalla de listado de profesores
            if (pantallaActual.titulo == R.string.listado_profesores) {
                FloatingActionButton(
                    onClick = { navController.navigate(route = PantallasProyecto.InsertarProfesor.name) }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(R.string.insertar_profesor)
                    )
                }
            }
        }
    ) { innerPadding ->
        val uiStateJSON = viewModelJSON.personasUiState
        val uiStateRoom = viewModelRoom.profesoresUiState

        // Configuración del NavHost para manejar la navegación entre pantallas
        NavHost(
            navController = navController,
            startDestination = PantallasProyecto.Inicio.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Grafo de rutas para cada pantalla
            composable(route = PantallasProyecto.Inicio.name) {
                PantallaPersonas(
                    appUiState = uiStateJSON,
                    onPersonasObtenidas = {
                        viewModelJSON.obtenerPersonas() // Obtener lista de personas cuando se carga la pantalla de inicio
                    },
                    onPersonaEliminada = {
                        viewModelJSON.eliminarPersona(it) // Eliminar persona seleccionada
                    },
                    onPersonaPulsada = {
                        viewModelJSON.actualizarPersonaPulsada(it) // Actualizar persona pulsada
                        navController.navigate(PantallasProyecto.Actualizar.name) // Navegar a la pantalla de actualización
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(route = PantallasProyecto.Insertar.name) {
                PantallaInsertarPersonas(
                    personas = viewModelJSON.personaPulsada,
                    onInsertarPulsado = {
                        viewModelJSON.insertarPersonas(it) // Insertar nueva persona
                        navController.popBackStack(
                            PantallasProyecto.Inicio.name,
                            inclusive = false
                        ) // Navegar de vuelta a la pantalla de inicio
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(route = PantallasProyecto.Actualizar.name) {
                PantallaActualizarPersonas(
                    personas = viewModelJSON.personaPulsada,
                    onPersonaActualizada = {
                        viewModelJSON.actualizarPersona(it.id, it) // Actualizar datos de la persona
                        navController.popBackStack(
                            PantallasProyecto.Inicio.name,
                            inclusive = false
                        ) // Navegar de vuelta a la pantalla de inicio
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(route = PantallasProyecto.ListadoProfesores.name) {
                PantallasProfesores(
                    appUiState = uiStateRoom,
                    onProfesoresObtenidos = { viewModelRoom.obtenerProfesores() },
                    onProfesorPulsado = {
                        viewModelRoom.obtenerProfesor(it)
                        navController.navigate(PantallasProyecto.ActualizarProfesores.name)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(route = PantallasProyecto.InsertarProfesor.name) {
                PantallaInsertarProfesor(
                    onInsertarPulsado = {
                        viewModelRoom.insertarProfesor(it)
                        navController.navigate(PantallasProyecto.ListadoProfesores.name)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(route = PantallasProyecto.ActualizarProfesores.name) {
                PantallaActualizarProfesor(
                    profesor = viewModelRoom.profesorPulsado,
                    onProfesorActualizado = {
                        viewModelRoom.actualizarProfesor(it)
                        navController.navigate(PantallasProyecto.ListadoProfesores.name)
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
