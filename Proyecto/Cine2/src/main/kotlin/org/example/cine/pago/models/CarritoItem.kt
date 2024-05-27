package org.example.cine.pago.models

import org.example.cine.peliculas.models.Butaca
import org.example.cine.productos.models.Producto

sealed class CarritoItem {
    data class ProductoItem(val producto: Producto) : CarritoItem() {
        val id: Long = producto.id
        val nombre: String = producto.nombre
        val precio: Double = producto.precio
    }

    data class ButacaItem(val butaca: Butaca) : CarritoItem() {
        val id: String = butaca.id
        val nombre: String = "Butaca ${butaca.id}"
        val precio: Double = butaca.precio
    }
}