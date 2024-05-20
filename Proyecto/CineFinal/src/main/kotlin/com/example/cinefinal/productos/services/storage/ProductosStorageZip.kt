package org.example.cinefinal.productos.services.storage

import com.github.michaelbull.result.Result
import org.example.cinefinal.productos.errors.ProductoError
import org.example.cinefinal.productos.models.Producto
import java.io.File

interface ProductosStorageZip {
    fun exportToZip(fileToZip: File, data: List<Producto>): Result<File, ProductoError>
    fun loadFromZip(fileToUnzip: File): Result<List<Producto>, ProductoError>
}