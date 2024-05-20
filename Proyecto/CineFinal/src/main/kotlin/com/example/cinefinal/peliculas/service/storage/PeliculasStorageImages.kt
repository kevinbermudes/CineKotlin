package org.example.cinefinal.peliculas.service.storage

import org.example.cinefinal.peliculas.errors.PeliculaError
import java.io.File

interface PeliculasStorageImages {
    fun saveImage(fileName: File): Result<File, PeliculaError>
    fun loadImage(fileName: String): Result<File, PeliculaError>
    fun deleteImage(fileName: File): Result<Unit, PeliculaError>
    fun deleteAllImages(): Result<Long, PeliculaError>
    fun updateImage(imageName: String, newFileImage: File): Result<File, PeliculaError>
}