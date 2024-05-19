package org.example.cine2.productos.services.database

import com.github.michaelbull.result.Result
import org.example.cine2.productos.errors.ProductoError
import org.example.cine2.productos.models.Producto

interface ProductosService {
    fun findAll(): Result<List<Producto>, ProductoError>
    fun deleteAll(): Result<Unit, ProductoError>
    fun saveAll(productos: List<Producto>): Result<List<Producto>, ProductoError>
    fun save(producto: Producto): Result<Producto, ProductoError>
    fun deleteById(id: Long): Result<Unit, ProductoError>
    fun findById(id: Long): Result<Producto, ProductoError>
}