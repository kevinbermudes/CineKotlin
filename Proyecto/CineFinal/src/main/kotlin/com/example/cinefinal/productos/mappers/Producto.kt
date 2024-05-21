package org.example.cinefinal.productos.mappers

import database.ProductoEntity
import org.example.cine2.productos.dto.json.ProductoDto
import org.example.cine2.productos.models.Producto
import java.time.LocalDateTime

// Funciones de extensión para ProductoDto
fun ProductoDto.toProductoModel(): Producto {
    return Producto(
        id = id,
        nombre = nombre,
        precio = precio,
        categoria = Producto.Categoria.valueOf(categoria.toUpperCase()),
        imagen = imagen,
        createdAt = LocalDateTime.parse(createdAt),
        updatedAt = LocalDateTime.parse(updatedAt)
    )
}

fun List<ProductoDto>.toProductoModelList(): List<Producto> {
    return this.map { it.toProductoModel() }
}

// Funciones de extensión para ProductoEntity
fun ProductoEntity.toProductoModel(): Producto {
    return Producto(
        id = id,
        nombre = nombre,
        precio = precio,
        categoria = Producto.Categoria.valueOf(categoria.toUpperCase()),
        imagen = imagen,
        createdAt = LocalDateTime.parse(created_at),
        updatedAt = LocalDateTime.parse(updated_at)
    )
}

fun List<ProductoEntity>.toProductoModelListFromEntity(): List<Producto> {
    return this.map { it.toProductoModel() }
}

// Funciones de extensión para Producto
fun Producto.toProductoDto(): ProductoDto {
    return ProductoDto(
        id = id,
        nombre = nombre,
        precio = precio,
        categoria = categoria.name,
        imagen = imagen,
        createdAt = createdAt.toString(),
        updatedAt = updatedAt.toString()
    )
}

fun List<Producto>.toProductoDtoList(): List<ProductoDto> {
    return this.map { it.toProductoDto() }
}
