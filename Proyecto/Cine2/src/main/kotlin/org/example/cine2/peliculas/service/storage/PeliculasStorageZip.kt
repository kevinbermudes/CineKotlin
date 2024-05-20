package org.example.cine2.peliculas.service.storage
import com.github.michaelbull.result.Result
import org.example.cine2.peliculas.errors.PeliculaError
import org.example.cine2.peliculas.models.Pelicula
import java.io.File

interface PeliculasStorageZip {
    fun exportToZip(fileToZip: File, data: List<Pelicula>): Result<File, PeliculaError>
    fun loadFromZip(fileToUnzip: File): Result<List<Pelicula>, PeliculaError>
}