package org.example.cine.peliculas.models

import kotlinx.serialization.Serializable

@Serializable
data class ButacasContainer(
    val butacas: List<Butaca>
)

@Serializable
data class Butaca(
    val id: String,
    val estado: String,
    val imagen: String
) {
    enum class Estado {
        libre, ocupado, mantenimiento, vip
    }
}
