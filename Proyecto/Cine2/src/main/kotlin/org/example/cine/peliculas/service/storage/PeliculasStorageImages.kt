package org.example.cine.peliculas.service.storage

import com.github.michaelbull.result.Result
import org.example.cine.peliculas.errors.PeliculaError
import java.io.File

interface PeliculasStorageImages {
    fun saveImage(fileName: File): Result<File, PeliculaError>
    fun loadImage(fileName: String): Result<File, PeliculaError>
    fun deleteImage(fileName: File): Result<Unit, PeliculaError>
    fun deleteAllImages(): Result<Long, PeliculaError>
    fun updateImage(imageName: String, newFileImage: File): Result<File, PeliculaError>
}