package org.example.cine2.productos.services.cache

import org.example.cine2.productos.models.Producto

interface ProductosCache {
    fun put(key: Long, value: Producto)
    fun get(key: Long): Producto?
    fun remove(key: Long): Producto?
    fun clear()
}