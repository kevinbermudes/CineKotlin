package org.example.cine.productos.dto.json

data class ProductoDto(
    val id: Long,
    val nombre: String,
    val precio: Double,
    val categoria: String,
    val stock: Int,
    val imagen: String,
    val createdAt: String,
    val updatedAt: String
)