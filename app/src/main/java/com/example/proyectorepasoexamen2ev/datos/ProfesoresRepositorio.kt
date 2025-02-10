package com.example.proyectorepasoexamen2ev.datos

import com.example.proyectorepasoexamen2ev.dao.DaoProfesores
import com.example.proyectorepasoexamen2ev.modelo.Profesores

interface ProfesoresRepositorio {
    suspend fun obtenerProfesor(id: Int): Profesores
    suspend fun obtenerTodosProfesores(): List<Profesores>
    suspend fun insertarProfesor(profesores: Profesores)
    suspend fun actualizarProfesor(profesores: Profesores)
    suspend fun eliminarProfesor(profesores: Profesores)
}

class ConexionProfesoresRepositorio(
    private val daoProfesores: DaoProfesores
):ProfesoresRepositorio{
    override suspend fun obtenerProfesor(id: Int): Profesores = daoProfesores.obtenerProfesor(id)
    override suspend fun obtenerTodosProfesores(): List<Profesores> = daoProfesores.obtenerTodosProfesores()
    override suspend fun insertarProfesor(profesores: Profesores) = daoProfesores.insertarProfesor(profesores)
    override suspend fun actualizarProfesor(profesores: Profesores) = daoProfesores.insertarProfesor(profesores)
    override suspend fun eliminarProfesor(profesores: Profesores) = daoProfesores.insertarProfesor(profesores)
}