package com.example.proyectorepasoexamen2ev.datos

import com.example.proyectorepasoexamen2ev.conexion.PersonasServicioApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface ContenedorApp{
    val personasRepositorios:PersonasRepositorios
}

class PersonasContenedorApp:ContenedorApp{
    private val baseUrl = "http://10.0.2.2:3000"
    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val servicioRetrofit: PersonasServicioApi by lazy {
        retrofit.create(PersonasServicioApi::class.java)
    }

    override val personasRepositorios: PersonasRepositorios by lazy{
        ConexionPersonasRepositorio(servicioRetrofit)
    }
}
