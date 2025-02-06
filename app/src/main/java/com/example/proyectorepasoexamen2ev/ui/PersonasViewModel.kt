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
import androidx.navigation.NavBackStackEntry
import com.example.proyectorepasoexamen2ev.PersonasAplicacion
import com.example.proyectorepasoexamen2ev.datos.PersonasRepositorios
import com.example.proyectorepasoexamen2ev.modelo.Personas
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface PersonasUiState {

    data class ObtenerExito(val personas: List<Personas>): PersonasUiState
    data class CrearExito(val personas: Personas):PersonasUiState
    data class ActualizarExito(val personas: Personas): PersonasUiState
    data class EliminarExito(val id:String): PersonasUiState

    object Error: PersonasUiState
    object Cargando: PersonasUiState
}

class PersonasViewModel(private val personasRepositorios: PersonasRepositorios):ViewModel(){
        var personasUiState: PersonasUiState by mutableStateOf(PersonasUiState.Cargando)
        private set

        var personaPulsada: Personas by mutableStateOf(Personas("", "", "", "", ""))
        private set

        fun actualizarPersonaPulsada(personas: Personas){
            personaPulsada = personas
        }

        init{
            obtenerPersonas()
        }

    fun obtenerPersonas(){
        viewModelScope.launch {
            personasUiState = PersonasUiState.Cargando
            personasUiState = try {
                val listaPersonas = personasRepositorios.obtenerPersonas()
                PersonasUiState.ObtenerExito(listaPersonas)
            } catch (e: IOException){
                PersonasUiState.Error
            } catch (e: HttpException){
                PersonasUiState.Error
            }
        }
    }

    fun insertarPersonas(personas: Personas){
        viewModelScope.launch {
            personasUiState = PersonasUiState.Cargando
            personasUiState = try {
                val personaInsertado = personasRepositorios.insertarPersonas(personas)
                PersonasUiState.CrearExito(personaInsertado)

            } catch (e: IOException){
                PersonasUiState.Error
            } catch (e: HttpException){
                PersonasUiState.Error
            }
        }
    }

    fun actualizarPersona(id:String, personas: Personas){
        viewModelScope.launch {
            personasUiState = PersonasUiState.Cargando
            personasUiState = try {
                val personaActualizada = personasRepositorios.actualizarPersonas(
                    id,
                    personas
                )
                PersonasUiState.ActualizarExito(personaActualizada)
            } catch (e: IOException){
                PersonasUiState.Error
            } catch (e: HttpException){
                PersonasUiState.Error
            }
        }
    }

    fun eliminarPersona(id:String){
        viewModelScope.launch {
            personasUiState = PersonasUiState.Cargando
            personasUiState = try {
                personasRepositorios.eliminarPersonas(id)
                PersonasUiState.EliminarExito(id)
            }
            catch (e: IOException){
                PersonasUiState.Error
            } catch (e: HttpException){
                PersonasUiState.Error
            }
        }
    }

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