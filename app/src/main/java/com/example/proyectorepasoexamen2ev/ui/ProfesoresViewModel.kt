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
import com.example.proyectorepasoexamen2ev.datos.ProfesoresRepositorio
import com.example.proyectorepasoexamen2ev.modelo.Profesores
import kotlinx.coroutines.launch

sealed interface ProfesoresUiState{
    data class ObtenerExitoTodos(val profesores: List<Profesores>): ProfesoresUiState
    data class ObtenerExito(val profesores: Profesores): ProfesoresUiState

    object CrearExito: ProfesoresUiState
    object ActualizarExito: ProfesoresUiState
    object Error: ProfesoresUiState
    object Cargando: ProfesoresUiState
}

class ProfesoresViewModel(private val profesoresRepositorio: ProfesoresRepositorio): ViewModel(){
    var profesoresUiState: ProfesoresUiState by mutableStateOf(ProfesoresUiState.Cargando)
        private set

    var profesorPulsado: Profesores by mutableStateOf(Profesores(0,"","","",0))
        private set

    init {
        obtenerProfesores()
    }

    fun obtenerProfesores(){
        viewModelScope.launch {
            profesoresUiState = try {
                val lista = profesoresRepositorio.obtenerTodosProfesores()
                ProfesoresUiState.ObtenerExitoTodos(lista)
            }catch (e:Exception){
                ProfesoresUiState.Error
            }
        }
    }

    fun obtenerProfesor(id: Int){
        viewModelScope.launch {
            profesoresUiState = try {
                val profesor = profesoresRepositorio.obtenerProfesor(id)
                profesorPulsado = profesor
                ProfesoresUiState.ObtenerExito(profesor)
            }catch (e:Exception){
                ProfesoresUiState.Error
            }
        }
    }

    fun insertarProfesor(profesores: Profesores) {
        viewModelScope.launch {
            profesoresUiState = try {
                profesoresRepositorio.insertarProfesor(profesores)
                ProfesoresUiState.CrearExito
            }catch (e: Exception){
                ProfesoresUiState.Error
            }
        }
    }

    fun actualizarProfesor(profesores: Profesores){
        viewModelScope.launch {
            profesoresUiState = try {
                profesoresRepositorio.actualizarProfesor(profesores)
                ProfesoresUiState.ActualizarExito
            }catch (e: Exception){
                ProfesoresUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val aplicacion = (this[APPLICATION_KEY] as PersonasAplicacion)
                val productoRepositorio = aplicacion.contenedor.profesoresRepositorio
                ProfesoresViewModel(profesoresRepositorio = productoRepositorio)
            }
        }
    }
}