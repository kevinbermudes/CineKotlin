package org.example.cine.Usuario.models

import java.time.LocalDateTime

data class Usuario(
    val id: Long= NEW_USUARIO,
    val nombre: String,
    var contrasena: String,
    val email: String,
    val rol: Rol,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    enum class Rol {
        ADMIN, USER
    }
    companion object {
        const val NEW_USUARIO = -1L
    }

    val isNewUsuario: Boolean
        get() = id == NEW_USUARIO
}