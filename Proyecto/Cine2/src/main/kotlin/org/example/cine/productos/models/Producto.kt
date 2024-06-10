package org.example.cine.productos.models

import java.text.NumberFormat
import java.time.LocalDateTime
import java.util.*

data class Producto(
    val id: Long = NEW_PRODUCTO,
    val nombre: String,
    val precio: Double,
    val categoria: Categoria,
    val imagen: String,
    var stock: Int = 0,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    enum class Categoria {
        BOTANA, BEBIDAS, FRUTOS_SECOS
    }

    companion object {
        const val NEW_PRODUCTO = -1L
    }

    val isNewProducto: Boolean
        get() = id == NEW_PRODUCTO

    val precioLocal: String
        get() = precio.toLocalCurrency()

    // Método auxiliar para convertir el precio a formato de moneda local de España (Euro)
    private fun Double.toLocalCurrency(): String {
        val format: NumberFormat = NumberFormat.getCurrencyInstance(Locale("es", "ES"))
        return format.format(this)
    }

    fun reduceStock() {
        if (stock > 0) {
            stock -= 1
        }
    }

    fun increaseStock() {
        stock += 1
    }

}
