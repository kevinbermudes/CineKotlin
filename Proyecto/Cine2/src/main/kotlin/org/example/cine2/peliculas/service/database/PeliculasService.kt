package org.example.cine2.peliculas.service.database

import org.example.cine2.peliculas.errors.PeliculaError
import org.example.cine2.peliculas.models.Pelicula
import com.github.michaelbull.result.Result

interface PeliculasService {
    fun findAll(): Result<List<Pelicula>, PeliculaError>
    fun deleteAll(): Result<Unit, PeliculaError>
    fun saveAll(peliculas: List<Pelicula>): Result<List<Pelicula>, PeliculaError>
    fun save(pelicula: Pelicula): Result<Pelicula, PeliculaError>
    fun deleteById(id: Long): Result<Unit, PeliculaError>
    fun findById(id: Long): Result<Pelicula, PeliculaError>
}