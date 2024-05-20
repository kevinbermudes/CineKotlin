package org.example.cinefinal.productos.services.storage

import com.github.michaelbull.result.Result
import org.example.cinefinal.productos.errors.ProductoError
import org.example.cinefinal.productos.models.Producto
import java.io.File

interface ProductosStorageJson {
    fun storeDataJson(file: File, data: List<Producto>): Result<Long, ProductoError>
    fun loadDataJson(file: File): Result<List<Producto>, ProductoError>
}