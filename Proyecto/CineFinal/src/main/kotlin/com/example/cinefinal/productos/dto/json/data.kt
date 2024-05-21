package org.example.cinefinal.productos.dto.json

import java.time.LocalDateTime

data class ProductoDto(
    val id: Long,
    val nombre: String,
    val precio: Double,
    val categoria: String,
    val imagen: String,
    val createdAt: String,
    val updatedAt: String
)