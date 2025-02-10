package com.example.proyectorepasoexamen2ev.conexion

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.example.proyectorepasoexamen2ev.dao.DaoProfesores
import com.example.proyectorepasoexamen2ev.modelo.Profesores


@Database(entities = [Profesores::class], version = 1, exportSchema = false)
abstract class ProfesoresBaseDatos: RoomDatabase() {

    abstract fun daoProfesores(): DaoProfesores

    companion object {
        @Volatile
        private var Instance: ProfesoresBaseDatos? = null

        fun obtenerBaseDatos(context: Context): ProfesoresBaseDatos {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, ProfesoresBaseDatos::class.java, "inventariodb")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}