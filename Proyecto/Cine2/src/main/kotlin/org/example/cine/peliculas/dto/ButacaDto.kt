package org.example.cine.peliculas.dto

import kotlinx.serialization.Serializable

@Serializable
data class ButacaDto(
    val id: String,
    val estado: String,
    val imagen: String
)