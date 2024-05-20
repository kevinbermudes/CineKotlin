package org.example.cine2.peliculas.mapers

import database.PeliculaEntity
import org.example.cine2.peliculas.ViewModel.CineViewModel.PeliculaFormState
import org.example.cine2.peliculas.dto.PeliculaDto
import org.example.cine2.peliculas.models.Pelicula
import java.time.LocalDate
import java.time.LocalDateTime


fun PeliculaDto.toModel(): Pelicula {
    return Pelicula(
        id,
        nombre,
       duracion,
       LocalDate.parse(fechaEstreno),
        descripcion,
        Pelicula.Categoria.valueOf(categoria.toUpperCase()),
        image,
        LocalDateTime.parse(createdAt),
       LocalDateTime.parse(updatedAt)
    )
}

fun List<PeliculaDto>.toModel(): List<Pelicula> {
    return map { it.toModel() }
}

// Extension functions to convert Model to DTO
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

fun List<Pelicula>.toDto(): List<PeliculaDto> {
    return map { it.toDto() }
}

// Extension functions to convert Entity to Model
fun PeliculaEntity.toModel(): Pelicula {
    return Pelicula(
        id = id,
        nombre = nombre,
        duracion = duracion,
        fechaEstreno = LocalDate.parse(fechaEstreno),
        descripcion = descripcion,
        categoria = Pelicula.Categoria.valueOf(categoria.toUpperCase()),
        imagen = "sin-imagen.png",
        createdAt = LocalDateTime.parse(created_at),
        updatedAt = LocalDateTime.parse(updated_at)
    )
}

// Extension functions to convert Model to Entity
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

fun List<PeliculaEntity>.toModel(): List<Pelicula> {
    return map { it.toModel() }
}

fun List<Pelicula>.toEntity(): List<PeliculaEntity> {
    return map { it.toEntity() }
}

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