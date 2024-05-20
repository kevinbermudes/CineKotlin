package org.example.cine2.peliculas.service.storage

import org.example.cine2.peliculas.errors.PeliculaError
import org.example.cine2.peliculas.models.Pelicula
import java.io.File
import com.github.michaelbull.result.Result

interface PeliculasStorageJson {
    fun storeDataJson(file: File, data: List<Pelicula>): Result<Long, PeliculaError>
    fun loadDataJson(file: File): Result<List<Pelicula>, PeliculaError>
}