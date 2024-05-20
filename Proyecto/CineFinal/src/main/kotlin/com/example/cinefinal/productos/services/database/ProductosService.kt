package org.example.cinefinal.productos.services.database

import com.github.michaelbull.result.Result
import org.example.cinefinal.productos.errors.ProductoError
import org.example.cinefinal.productos.models.Producto

interface ProductosService {
    fun findAll(): Result<List<Producto>, ProductoError>
    fun deleteAll(): Result<Unit, ProductoError>
    fun saveAll(productos: List<Producto>): Result<List<Producto>, ProductoError>
    fun save(producto: Producto): Result<Producto, ProductoError>
    fun deleteById(id: Long): Result<Unit, ProductoError>
    fun findById(id: Long): Result<Producto, ProductoError>
}