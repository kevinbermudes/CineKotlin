package org.example.cine.pago.models

data class Pago(
    val id: Int,
    val usuarioId: Int,
    val carritoId: Int,
    val numeroTarjeta: String,
    val titular: String,
    val codigoSeguridad: String,
    val mesExpiracion: String,
    val anoExpiracion: String,
    val monto: Double,
    val estado: String
)