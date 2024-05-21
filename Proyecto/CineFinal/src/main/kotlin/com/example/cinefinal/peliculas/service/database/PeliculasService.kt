package org.example.cinefinal.peliculas.service.database

import org.example.cinefinal.peliculas.errors.PeliculaError
import org.example.cinefinal.peliculas.models.Pelicula

interface PeliculasService {
    fun findAll(): Result<List<Pelicula>, PeliculaError>
    fun deleteAll(): Result<Unit, PeliculaError>
    fun saveAll(peliculas: List<Pelicula>): Result<List<Pelicula>, PeliculaError>
    fun save(pelicula: Pelicula): Result<Pelicula, PeliculaError>
    fun deleteById(id: Long): Result<Unit, PeliculaError>
    fun findById(id: Long): Result<Pelicula, PeliculaError>
}