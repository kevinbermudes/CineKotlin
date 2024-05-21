package org.example.cinefinal.productos.services.cache

import org.example.cinefinal.productos.models.Producto

interface ProductosCache {
    fun put(key: Long, value: Producto)
    fun get(key: Long): Producto?
    fun remove(key: Long): Producto?
    fun clear()
}