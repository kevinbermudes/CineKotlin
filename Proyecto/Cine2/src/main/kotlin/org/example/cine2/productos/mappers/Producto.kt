package org.example.cine2.productos.mappers


import database.ProductoEntity
import org.example.cine2.peliculas.models.Pelicula
import org.example.cine2.productos.dto.json.ProductoDto
import org.example.cine2.productos.models.Producto
import org.koin.core.component.getScopeName
import java.time.LocalDateTime

fun ProductoDto.toModel(): Producto {
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

fun List<ProductoDto>.toModel(): List<Producto> {
    return this.map { it.toModel() }
}

fun Producto.toDto(): ProductoDto {
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

fun List<Producto>.toDto(): List<ProductoDto> {
    return this.map { it.toDto() }
}

fun ProductoEntity.toModel(): Producto {
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
