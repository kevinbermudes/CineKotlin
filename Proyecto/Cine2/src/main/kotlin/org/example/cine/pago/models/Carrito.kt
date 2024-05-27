package org.example.cine.pago.models

import org.example.cine.productos.models.Producto

data class Carrito(
    val id: Int,
    val usuarioId: Int,
    val productos: MutableList<Producto> = mutableListOf(),
    var precioTotal: Double = 0.0
) {
    fun agregarProducto(producto: Producto) {
        productos.add(producto)
        precioTotal += producto.precio
    }

    fun eliminarProducto(producto: Producto) {
        productos.remove(producto)
        precioTotal -= producto.precio
    }

    fun limpiarCarrito() {
        productos.clear()
        precioTotal = 0.0
    }
}