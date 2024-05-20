package org.example.cinefinal.productos.repositories

import org.example.cinefinal.productos.models.Producto

interface ProductosRepository {
    fun findAll(): List<Producto>
    fun findById(id: Long): Producto?
    fun save(producto: Producto): Producto
    fun deleteById(id: Long)
    fun deleteAll()
    fun saveAll(productos: List<Producto>): List<Producto>
}