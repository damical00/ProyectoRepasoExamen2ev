package com.example.proyectorepasoexamen2ev.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.proyectorepasoexamen2ev.modelo.Profesores

@Dao
interface DaoProfesores {

    @Query("SELECT * from Profesores WHERE id = :id")
    suspend fun obtenerProfesor(id:Int): Profesores

    @Query("SELECT * from Profesores ORDER BY nombre ASC")
    suspend fun obtenerTodosProfesores(): List<Profesores>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarProfesor(profesores: Profesores)

    @Update
    suspend fun actualizarProfesor(profesores: Profesores)

    @Delete
    suspend fun eliminarProfesor(profesores: Profesores)


}