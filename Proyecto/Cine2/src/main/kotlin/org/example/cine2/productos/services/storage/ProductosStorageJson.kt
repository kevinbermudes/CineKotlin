package org.example.cine2.productos.services.storage

import com.github.michaelbull.result.Result
import org.example.cine2.productos.errors.ProductoError
import org.example.cine2.productos.models.Producto
import java.io.File

interface ProductosStorageJson {
    fun storeDataJson(file: File, data: List<Producto>): Result<Long, ProductoError>
    fun loadDataJson(file: File): Result<List<Producto>, ProductoError>
}