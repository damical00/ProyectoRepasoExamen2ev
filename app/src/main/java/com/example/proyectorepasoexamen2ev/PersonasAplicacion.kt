package com.example.proyectorepasoexamen2ev

import android.app.Application
import com.example.proyectorepasoexamen2ev.datos.ContenedorApp
import com.example.proyectorepasoexamen2ev.datos.PersonasContenedorApp

class PersonasAplicacion:Application() {
    lateinit var contenedor: ContenedorApp
    override fun onCreate() {
        super.onCreate()
        contenedor = PersonasContenedorApp(this)
    }
}