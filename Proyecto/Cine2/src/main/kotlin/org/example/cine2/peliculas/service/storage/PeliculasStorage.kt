package org.example.cine2.peliculas.service.storage

import org.example.cine2.peliculas.errors.PeliculaError
import org.example.cine2.peliculas.models.Pelicula
import java.io.File
import com.github.michaelbull.result.Result


interface PeliculasStorage {
    fun storeDataJson(file: File, data: List<Pelicula>): Result<Long, PeliculaError>
    fun loadDataJson(file: File): Result<List<Pelicula>, PeliculaError>
    fun saveImage(fileName: File): Result<File, PeliculaError>
    fun loadImage(fileName: String): Result<File, PeliculaError>
    fun deleteImage(fileName: File): Result<Unit, PeliculaError>
    fun deleteAllImages(): Result<Long, PeliculaError>
    fun updateImage(imageName: String, newFileImage: File): Result<File, PeliculaError>
    fun exportToZip(fileToZip: File, data: List<Pelicula>): Result<File, PeliculaError>
    fun loadFromZip(fileToUnzip: File): Result<List<Pelicula>, PeliculaError>
}