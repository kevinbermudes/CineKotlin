package org.example.cine2.productos.services.storage

import com.github.michaelbull.result.Result
import org.example.cine2.productos.errors.ProductoError
import java.io.File

interface ProductosStorageImages {
    fun saveImage(fileName: File): Result<File, ProductoError>
    fun loadImage(fileName: String): Result<File, ProductoError>
    fun deleteImage(fileName: File): Result<Unit, ProductoError>
    fun deleteAllImages(): Result<Long, ProductoError>
    fun updateImage(imagenName: String, newFileImage: File): Result<File, ProductoError>
}