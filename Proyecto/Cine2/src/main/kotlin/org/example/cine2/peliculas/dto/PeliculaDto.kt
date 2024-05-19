package org.example.cine2.peliculas.dto

import kotlinx.serialization.Serializable

@Serializable
data class PeliculaDto(
    val id: Long,
    val nombre: String,
    val duracion: String,
    val fechaEstreno: String,
    val descripcion: String,
    val categoria: String,
    val createdAt: String,
    val updatedAt: String
)

