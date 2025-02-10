package com.example.proyectorepasoexamen2ev.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.proyectorepasoexamen2ev.PersonasAplicacion
import com.example.proyectorepasoexamen2ev.datos.PersonasRepositorios
import com.example.proyectorepasoexamen2ev.modelo.Personas
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

// Interfaz sellada que define los diferentes estados de la UI para las personas
sealed interface PersonasUiState {
    data class ObtenerExito(val personas: List<Personas>) : PersonasUiState // Estado de éxito al obtener personas
    data class CrearExito(val personas: Personas) : PersonasUiState // Estado de éxito al crear una persona
    data class ActualizarExito(val personas: Personas) : PersonasUiState // Estado de éxito al actualizar una persona
    data class EliminarExito(val id: String) : PersonasUiState // Estado de éxito al eliminar una persona

    object Error : PersonasUiState // Estado de error
    object Cargando : PersonasUiState // Estado de carga
}

// ViewModel para gestionar el estado y las operaciones relacionadas con personas
class PersonasViewModel(private val personasRepositorios: PersonasRepositorios) : ViewModel() {
    var personasUiState: PersonasUiState by mutableStateOf(PersonasUiState.Cargando)
        private set

    var personaPulsada: Personas by mutableStateOf(Personas("", "", "", "", ""))
        private set

    // Actualiza la persona seleccionada
    fun actualizarPersonaPulsada(personas: Personas) {
        personaPulsada = personas
    }

    init {
        obtenerPersonas() // Obtener lista de personas al inicializar
    }

    // Función para obtener la lista de personas desde el repositorio
    fun obtenerPersonas() {
        viewModelScope.launch {
            println("Llamando a obtenerPersonas()")
            personasUiState = PersonasUiState.Cargando
            personasUiState = try {
                val listaPersonas = personasRepositorios.obtenerPersonas()
                println("Datos obtenidos: $listaPersonas")
                PersonasUiState.ObtenerExito(listaPersonas) // Estado de éxito con la lista obtenida
            } catch (e: IOException) {
                println("Error de red: ${e.message}")
                PersonasUiState.Error // Estado de error en caso de excepción de red
            } catch (e: HttpException) {
                println("Código de error HTTP: ${e.code()}, Mensaje: ${e.message}")
                PersonasUiState.Error // Estado de error en caso de excepción HTTP
            }
        }
    }

    // Función para insertar una persona en el repositorio
    fun insertarPersonas(personas: Personas) {
        viewModelScope.launch {
            personasUiState = PersonasUiState.Cargando
            personasUiState = try {
                val personaInsertado = personasRepositorios.insertarPersonas(personas)
                PersonasUiState.CrearExito(personaInsertado) // Estado de éxito con la persona insertada
            } catch (e: IOException) {
                PersonasUiState.Error // Estado de error en caso de excepción de red
            } catch (e: HttpException) {
                PersonasUiState.Error // Estado de error en caso de excepción HTTP
            }
        }
    }

    // Función para actualizar una persona en el repositorio
    fun actualizarPersona(id: String, personas: Personas) {
        viewModelScope.launch {
            personasUiState = PersonasUiState.Cargando
            personasUiState = try {
                val personaActualizada = personasRepositorios.actualizarPersonas(id, personas)
                PersonasUiState.ActualizarExito(personaActualizada) // Estado de éxito con la persona actualizada
            } catch (e: IOException) {
                PersonasUiState.Error // Estado de error en caso de excepción de red
            } catch (e: HttpException) {
                PersonasUiState.Error // Estado de error en caso de excepción HTTP
            }
        }
    }

    // Función para eliminar una persona del repositorio
    fun eliminarPersona(id: String) {
        viewModelScope.launch {
            personasUiState = PersonasUiState.Cargando
            personasUiState = try {
                personasRepositorios.eliminarPersonas(id)
                PersonasUiState.EliminarExito(id) // Estado de éxito con la persona eliminada
            } catch (e: IOException) {
                PersonasUiState.Error // Estado de error en caso de excepción de red
            } catch (e: HttpException) {
                PersonasUiState.Error // Estado de error en caso de excepción HTTP
            }
        }
    }

    // Fábrica para crear instancias de PersonasViewModel
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val aplicacion = (this[APPLICATION_KEY] as PersonasAplicacion)
                val personasRepositorio = aplicacion.contenedor.personasRepositorios
                PersonasViewModel(personasRepositorios = personasRepositorio)
            }
        }
    }
}
