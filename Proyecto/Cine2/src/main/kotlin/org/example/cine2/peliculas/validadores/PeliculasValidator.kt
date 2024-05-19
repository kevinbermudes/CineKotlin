package org.example.cine2.peliculas.validadores

import org.example.cine2.peliculas.errors.PeliculaError
import org.example.cine2.peliculas.models.Pelicula
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import java.time.LocalDate
import java.util.EnumSet
fun Pelicula.validate(): Result<Pelicula, PeliculaError> {
    if (this.nombre.isEmpty() || this.nombre.isBlank()) {
        return Err(PeliculaError.ValidationProblem("El nombre no puede estar vacío"))
    }
    if (this.duracion.isEmpty() || this.duracion.isBlank()) {
        return Err(PeliculaError.ValidationProblem("La duración no puede estar vacía"))
    }
    if (this.fechaEstreno.isAfter(LocalDate.now())) {
        return Err(PeliculaError.ValidationProblem("La fecha de estreno no puede ser posterior a hoy"))
    }
    if (this.descripcion.isEmpty() || this.descripcion.isBlank()) {
        return Err(PeliculaError.ValidationProblem("La descripción no puede estar vacía"))
    }
    if (!EnumSet.allOf(Pelicula.Categoria::class.java).contains(this.categoria)) {
        return Err(PeliculaError.ValidationProblem("La categoría no es válida"))
    }
    return Ok(this)
}