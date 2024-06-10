package org.example.cine.pago.models

data class Venta(
    val id: Long = NEW_VENTA,
    var pelicula: String,
    var precioEntrada: Double,
    var butacas: String,
    var complementos: String?,
    var precioComplementos: Double?,
    var total: Double,
) {
    companion object {
        const val NEW_VENTA = -1L
    }

    val isNewVenta: Boolean
        get() = id == NEW_VENTA
}

