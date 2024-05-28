package org.example.cine.pago.models

import org.example.cine.peliculas.models.Butaca
import org.example.cine.productos.models.Producto


data class Carrito(
    val butacas: MutableList<Butaca> = mutableListOf(),
    val productos: MutableList<Producto> = mutableListOf()
) {
    val total: Double
        get() = butacas.sumOf { it.precio } + productos.sumOf { it.precio }
}