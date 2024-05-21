package org.example.cine.peliculas.models

import java.time.LocalDate
import java.time.LocalDateTime

data class Pelicula(
    val id: Long = NEW_PELICULA,
    val nombre: String,
    val duracion: String,
    val fechaEstreno: LocalDate,
    val descripcion: String,
    val categoria: Categoria,
    val imagen: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        const val NEW_PELICULA = -1L
    }

    val isNewPelicula: Boolean
        get() = id == NEW_PELICULA

    enum class Categoria {
        TERROR, ANIMADA, FANTASIA
    }
}