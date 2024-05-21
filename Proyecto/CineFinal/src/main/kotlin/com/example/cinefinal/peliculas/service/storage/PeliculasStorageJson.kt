package org.example.cinefinal.peliculas.service.storage

import org.example.cinefinal.peliculas.errors.PeliculaError
import org.example.cinefinal.peliculas.models.Pelicula
import java.io.File

interface PeliculasStorageJson {
    fun storeDataJson(file: File, data: List<Pelicula>): Result<Long, PeliculaError>
    fun loadDataJson(file: File): Result<List<Pelicula>, PeliculaError>
}