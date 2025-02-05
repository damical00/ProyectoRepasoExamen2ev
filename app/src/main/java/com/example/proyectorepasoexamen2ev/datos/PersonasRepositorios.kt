package com.example.proyectorepasoexamen2ev.datos

import android.app.Person
import com.example.proyectorepasoexamen2ev.conexion.PersonasServicioApi
import com.example.proyectorepasoexamen2ev.modelo.Personas

interface PersonasRepositorios {
    suspend fun obtenerPersonas():List<Personas>
    suspend fun insertarPersonas(personas: Personas):Personas
    suspend fun actualizarPersonas(id:String, personas: Personas): Personas
    suspend fun eliminarPersonas(id:String):Personas
}

class ConexionPersonasRepositorio(
    private val personasServicioApi: PersonasServicioApi
):PersonasRepositorios{
    override suspend fun obtenerPersonas(): List<Personas> = personasServicioApi.obtenerPersonas();
    override suspend fun insertarPersonas(personas: Personas): Personas = personasServicioApi.insertarPersonas(personas)
    override suspend fun actualizarPersonas(id: String, personas: Personas): Personas = personasServicioApi.actualizarPersonas(id, personas)
    override suspend fun eliminarPersonas(id: String): Personas = personasServicioApi.eliminarPersonas(id)
}