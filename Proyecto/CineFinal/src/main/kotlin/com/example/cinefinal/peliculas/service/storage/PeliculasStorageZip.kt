package org.example.cinefinal.peliculas.service.storage

import org.example.cinefinal.peliculas.errors.PeliculaError
import org.example.cinefinal.peliculas.models.Pelicula
import java.io.File

interface PeliculasStorageZip {
    fun exportToZip(fileToZip: File, data: List<Pelicula>): Result<File, PeliculaError>
    fun loadFromZip(fileToUnzip: File): Result<List<Pelicula>, PeliculaError>
}