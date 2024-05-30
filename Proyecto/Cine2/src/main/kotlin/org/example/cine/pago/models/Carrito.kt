package org.example.cine.pago.models

import org.example.cine.peliculas.models.Butaca
import org.example.cine.productos.models.Producto


class Carrito private constructor() {

    val productos: MutableList<Producto> = mutableListOf()
    val butacas: MutableList<Butaca> = mutableListOf()

    val total: Double
        get() = productos.sumOf { it.precio } + butacas.sumOf { it.precio }

    companion object {
        val instance: Carrito by lazy { Carrito() }
    }
}