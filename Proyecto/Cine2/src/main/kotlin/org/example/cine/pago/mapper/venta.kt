package org.example.cine.pago.mapper

import database.VentaEntity
import org.example.cine.pago.models.Venta
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun VentaEntity.toModel(): Venta {
    return Venta(
        id = id,
        pelicula = pelicula,
        precioEntrada = precioEntrada,
        butacas = butacas,
        complementos = complementos,
        precioComplementos = precioComplementos,
        total = total,
    )
}

fun Venta.toEntity(): VentaEntity {
    return VentaEntity(
        id = id,
        pelicula = pelicula,
        precioEntrada = precioEntrada,
        butacas = butacas,
        complementos = complementos,
        precioComplementos = precioComplementos,
        total = total,
    )
}