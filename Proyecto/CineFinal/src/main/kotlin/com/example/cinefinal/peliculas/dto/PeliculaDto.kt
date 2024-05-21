package org.example.cinefinal.peliculas.dto

@Serializable
data class PeliculaDto(
    val id: Long,
    val nombre: String,
    val duracion: String,
    val fechaEstreno: String,
    val descripcion: String,
    val categoria: String,
    val image: String,
    val createdAt: String,
    val updatedAt: String
)

