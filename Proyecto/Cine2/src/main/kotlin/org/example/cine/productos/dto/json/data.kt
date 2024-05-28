package org.example.cine.productos.dto.json

data class ProductoDto(
    val id: Long,
    val nombre: String,
    val precio: Double,
    val categoria: String,
    val imagen: String,
    val stock: Double,
    val createdAt: String,
    val updatedAt: String
)