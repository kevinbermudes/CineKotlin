package org.example.cine.peliculas.mapers


import org.example.cine.peliculas.dto.ButacaDto
import org.example.cine.peliculas.models.Butaca

fun Butaca.toDto(): ButacaDto {
    return ButacaDto(
        id = this.id,
        estado = this.estado,
        imagen = this.imagen
    )
}

fun ButacaDto.toModel(): Butaca {
    return Butaca(
        id = this.id,
        estado = this.estado,
        imagen = this.imagen,
        precio = 0.0
    )
}

fun List<Butaca>.toDtoList(): List<ButacaDto> {
    return this.map { it.toDto() }
}

fun List<ButacaDto>.toModelList(): List<Butaca> {
    return this.map { it.toModel() }
}