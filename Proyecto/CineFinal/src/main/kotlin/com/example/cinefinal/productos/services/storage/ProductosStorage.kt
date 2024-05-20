package org.example.cinefinal.productos.services.storage

import com.github.michaelbull.result.Result
import org.example.cinefinal.productos.errors.ProductoError
import org.example.cinefinal.productos.models.Producto
import java.io.File

interface ProductosStorage {
    fun storeDataJson(file: File, data: List<Producto>): Result<Long, ProductoError>
    fun loadDataJson(file: File): Result<List<Producto>, ProductoError>
    fun saveImage(fileName: File): Result<File, ProductoError>
    fun loadImage(fileName: String): Result<File, ProductoError>
    fun deleteImage(fileName: File): Result<Unit, ProductoError>
    fun deleteAllImages(): Result<Long, ProductoError>
    fun updateImage(imagenName: String, newFileImage: File): Result<File, ProductoError>
    fun exportToZip(fileToZip: File, data: List<Producto>): Result<File, ProductoError>
    fun loadFromZip(fileToUnzip: File): Result<List<Producto>, ProductoError>
}