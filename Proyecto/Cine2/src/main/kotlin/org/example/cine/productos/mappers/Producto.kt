package org.example.cine.productos.mappers

import database.ProductoEntity
import org.example.cine.productos.dto.json.ProductoDto
import org.example.cine.productos.models.Producto
import java.time.LocalDateTime

// Funcion para convertir de DTO a Model
fun ProductoDto.toModel(): Producto {
    return Producto(
        id = id,
        nombre = nombre,
        precio = precio,
        categoria = Producto.Categoria.valueOf(categoria.uppercase()),
        imagen = imagen,
        stock = stock,  // Actualizar para incluir stock
        createdAt = LocalDateTime.parse(createdAt),
        updatedAt = LocalDateTime.parse(updatedAt)
    )
}

fun List<ProductoDto>.toModelFromDtoList(): List<Producto> {
    return this.map { it.toModel() }
}

// Funcion para convertir de Model a DTO
fun Producto.toDto(): ProductoDto {
    return ProductoDto(
        id = id,
        nombre = nombre,
        precio = precio,
        categoria = categoria.name,
        imagen = imagen,
        stock = stock,  // Actualizar para incluir stock
        createdAt = createdAt.toString(),
        updatedAt = updatedAt.toString()
    )
}

fun List<Producto>.toDtoList(): List<ProductoDto> {
    return this.map { it.toDto() }
}

// Funcion para convertir de Entity a Model
fun ProductoEntity.toModel(): Producto {
    return Producto(
        id = id,
        nombre = nombre,
        precio = precio,
        categoria = Producto.Categoria.valueOf(categoria.uppercase()),
        imagen = imagen,
        stock = stock.toInt(),
        createdAt = LocalDateTime.parse(created_at),
        updatedAt = LocalDateTime.parse(updated_at)
    )
}

fun List<ProductoEntity>.toModelFromEntityList(): List<Producto> {
    return map { it.toModel() }
}

// Funcion para convertir de Model a Entity
fun Producto.toEntity(): ProductoEntity {
    return ProductoEntity(
        id = id,
        nombre = nombre,
        precio = precio,
        categoria = categoria.name,
        imagen = imagen,
        stock = stock.toLong(),  // Convertir stock a Long
        created_at = createdAt.toString(),
        updated_at = updatedAt.toString()
    )
}

fun List<Producto>.toEntityList(): List<ProductoEntity> {
    return this.map { it.toEntity() }
}
