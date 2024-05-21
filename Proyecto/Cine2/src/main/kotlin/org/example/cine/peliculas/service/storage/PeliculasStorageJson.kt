package org.example.cine.peliculas.service.storage

import org.example.cine.peliculas.errors.PeliculaError
import org.example.cine.peliculas.models.Pelicula
import java.io.File
import com.github.michaelbull.result.Result

interface PeliculasStorageJson {
    fun storeDataJson(file: File, data: List<Pelicula>): Result<Long, PeliculaError>
    fun loadDataJson(file: File): Result<List<Pelicula>, PeliculaError>
}