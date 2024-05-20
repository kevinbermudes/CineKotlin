package org.example.cinefinal.peliculas.mapers

import database.PeliculaEntity
import org.example.cinefinal.peliculas.ViewModel.CineViewModel.PeliculaFormState
import org.example.cinefinal.peliculas.dto.PeliculaDto
import org.example.cinefinal.peliculas.models.Pelicula
import java.time.LocalDate
import java.time.LocalDateTime

// Funciones de extensión para PeliculaDto
fun PeliculaDto.toModel(): Pelicula {
    return Pelicula(
        id,
        nombre,
        duracion,
        LocalDate.parse(fechaEstreno),
        descripcion,
        Pelicula.Categoria.valueOf(categoria.uppercase()),
        image,
        LocalDateTime.parse(createdAt),
        LocalDateTime.parse(updatedAt)
    )
}

fun List<PeliculaDto>.toPeliculaModelList(): List<Pelicula> {
    return map { it.toModel() }
}

// Funciones de extensión para PeliculaEntity
fun PeliculaEntity.toModel(): Pelicula {
    return Pelicula(
        id = id,
        nombre = nombre,
        duracion = duracion,
        fechaEstreno = LocalDate.parse(fechaEstreno),
        descripcion = descripcion,
        categoria = Pelicula.Categoria.valueOf(categoria.uppercase()),
        imagen = "sin-imagen.png",
        createdAt = LocalDateTime.parse(created_at),
        updatedAt = LocalDateTime.parse(updated_at)
    )
}

fun List<PeliculaEntity>.toPeliculaModelListFromEntity(): List<Pelicula> {
    return map { it.toModel() }
}

// Funciones de extensión para Pelicula
fun Pelicula.toDto(): PeliculaDto {
    return PeliculaDto(
        id = id,
        nombre = nombre,
        duracion = duracion,
        fechaEstreno = fechaEstreno.toString(),
        descripcion = descripcion,
        categoria = categoria.name,
        image = imagen,
        createdAt = createdAt.toString(),
        updatedAt = updatedAt.toString()
    )
}

fun List<Pelicula>.toPeliculaDtoList(): List<PeliculaDto> {
    return map { it.toDto() }
}

fun Pelicula.toEntity(): PeliculaEntity {
    return PeliculaEntity(
        id = id,
        nombre = nombre,
        duracion = duracion,
        fechaEstreno = fechaEstreno.toString(),
        descripcion = descripcion,
        categoria = categoria.name,
        image = imagen,
        created_at = createdAt.toString(),
        updated_at = updatedAt.toString()
    )
}

fun List<Pelicula>.toPeliculaEntityList(): List<PeliculaEntity> {
    return map { it.toEntity() }
}

// Función de extensión para PeliculaFormState
fun PeliculaFormState.toModel(): Pelicula {
    return Pelicula(
        id = if (id.trim().isBlank()) Pelicula.NEW_PELICULA else id.toLong(),
        nombre = nombre.trim(),
        duracion = duracion.trim(),
        fechaEstreno = fechaEstreno,
        descripcion = descripcion.trim(),
        categoria = categoria,
        imagen = fileImage?.name ?: "sin-imagen.png",
        createdAt = LocalDate.now().atStartOfDay(),
        updatedAt = LocalDate.now().atStartOfDay()
    )
}
