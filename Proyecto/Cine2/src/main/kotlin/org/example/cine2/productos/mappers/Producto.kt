package org.example.cine2.productos.mappers

import org.example.cine2.productos.dto.json.ProductoDto
import org.example.cine2.productos.models.Producto

fun ProductoDto.toModel(): Producto {
    return Producto(
        id,
        nombre,
        precio,
        categoria
    )
}

fun List<ProductoDto>.toModel(): List<Producto> {
    return this.map { it.toModel() }
}

fun Producto.toDto(): ProductoDto {
    return ProductoDto(
        id,
        nombre,
        precio,
        categoria
    )
}
fun List<Producto>.toDto(): List<ProductoDto> {
    return this.map { it.toDto() }
}