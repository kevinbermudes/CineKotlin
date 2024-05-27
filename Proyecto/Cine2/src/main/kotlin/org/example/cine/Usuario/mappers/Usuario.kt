package org.example.cine.Usuario.mappers

import database.UsuarioEntity
import org.example.cine.Usuario.models.Usuario
import java.time.LocalDateTime

fun UsuarioEntity.toModel(): Usuario {
    return Usuario(
        id = this.id,
        nombre = this.nombre,
        contrasena = this.contrasena,
        email = this.email,
        rol = Usuario.Rol.valueOf(this.rol),
        createdAt = LocalDateTime.parse(this.created_at),
        updatedAt = LocalDateTime.parse(this.updated_at)
    )
}