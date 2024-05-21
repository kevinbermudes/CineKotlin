package org.example.cine.productos.services.storage

import com.github.michaelbull.result.Result
import org.example.cine.productos.errors.ProductoError
import org.example.cine.productos.models.Producto
import java.io.File

interface ProductosStorageJson {
    fun storeDataJson(file: File, data: List<Producto>): Result<Long, ProductoError>
    fun loadDataJson(file: File): Result<List<Producto>, ProductoError>
}