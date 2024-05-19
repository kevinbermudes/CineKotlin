package org.example.cine2.productos.dto.json

data class ProductoDto(
    val id: Long,
    val nombre: String,
    val precio: Double,
    val categoria: Enum<Categoria>
)

enum class Categoria {
Botana,Bebida, Bebidas,Frutos_Secos
}
