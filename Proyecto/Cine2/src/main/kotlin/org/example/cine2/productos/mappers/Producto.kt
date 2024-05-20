package org.example.cine2.productos.mappers

import database.ProductoEntity
import org.example.cine2.productos.dto.json.ProductoDto
import org.example.cine2.productos.models.Producto
import java.time.LocalDateTime

// Extension functions to convert DTO to Model
fun ProductoDto.toModel(): Producto {
    return Producto(
        id = id,
        nombre = nombre,
        precio = precio,
        categoria = Producto.Categoria.valueOf(categoria.uppercase()),
        imagen = imagen,
        createdAt = LocalDateTime.parse(createdAt),
        updatedAt = LocalDateTime.parse(updatedAt)
    )
}

fun List<ProductoDto>.toModelFromDtoList(): List<Producto> {
    return this.map { it.toModel() }
}

// Extension functions to convert Model to DTO
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

fun List<Producto>.toDtoList(): List<ProductoDto> {
    return this.map { it.toDto() }
}

// Extension functions to convert Entity to Model
fun ProductoEntity.toModel(): Producto {
    return Producto(
        id = id,
        nombre = nombre,
        precio = precio,
        categoria = Producto.Categoria.valueOf(categoria.uppercase()),
        imagen = imagen,
        createdAt = LocalDateTime.parse(created_at),
        updatedAt = LocalDateTime.parse(updated_at)
    )
}

fun List<ProductoEntity>.toModelFromEntityList(): List<Producto> {
    return map { it.toModel() }
}

// Extension functions to convert Model to Entity
fun Producto.toEntity(): ProductoEntity {
    return ProductoEntity(
        id = id,
        nombre = nombre,
        precio = precio,
        categoria = categoria.name,
        imagen = imagen,
        created_at = createdAt.toString(),
        updated_at = updatedAt.toString()
    )
}

fun List<Producto>.toEntityList(): List<ProductoEntity> {
    return this.map { it.toEntity() }
}
