package org.example.cine2.productos.mappers


import org.example.cine2.productos.dto.json.ProductoDto
import org.example.cine2.productos.models.Producto
import java.time.LocalDateTime

fun ProductoDto.toModel(): Producto {
    return Producto(
        id = id,
        nombre = nombre,
        precio = precio,
        categoria = categoria,
        imagen = imagen,
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now()
    )
}

fun List<ProductoDto>.toModel(): List<Producto> {
    return this.map { it.toModel() }
}

fun Producto.toDto(): ProductoDto {
    return ProductoDto(
        id = id,
        nombre = nombre,
        precio = precio,
        categoria = categoria,
        imagen = imagen,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun List<Producto>.toDto(): List<ProductoDto> {
    return this.map { it.toDto() }
}
