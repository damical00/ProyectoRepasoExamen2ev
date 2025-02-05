package com.example.proyectorepasoexamen2ev.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Personas(
    @SerialName(value = "id")
    val id: String="",

    @SerialName(value = "dni")
    val dni:String="",

    @SerialName(value = "nombre")
    val nombre:String="",

    @SerialName(value = "apellido")
    val apellido: String="",

    @SerialName(value ="fecha_nacimiento")
    val fecha_nacimiento: String=""
) {

}