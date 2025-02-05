package com.example.proyectorepasoexamen2ev.conexion

import android.app.Person
import com.example.proyectorepasoexamen2ev.modelo.Personas
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PersonasServicioApi{
    @GET("personas")
    suspend fun obtenerPersonas(): List<Personas>

    @POST("personas")
    suspend fun insertarPersonas(
        @Body personas: Personas
    ):Personas

    @PUT("personas/{id}")
    suspend fun actualizarPersonas(
        @Path("id") id:String,
        @Body personas: Personas
    ):Personas

    @DELETE("personas/{id}")
    suspend fun eliminarPersonas(
        @Path("id") id:String
    ):Personas
}
