package org.example.cine2.productos.dto.json

import java.time.LocalDateTime

data class ProductoDto(
    val id: Long,
    val nombre: String,
    val precio: Double,
    val categoria: Enum<Categoria>,
    val imagen: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

enum class Categoria {
    BOTANA, BEBIDA, BEBIDAS, FRUTOS_SECOS
}
