package com.example.proyectorepasoexamen2ev.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "Profesores")
data class Profesores(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val apellido: String,
    val departamento: String,
    val anyosExp: Int
) {
}