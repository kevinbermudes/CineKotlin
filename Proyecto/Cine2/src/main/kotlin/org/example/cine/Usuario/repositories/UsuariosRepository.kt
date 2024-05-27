package org.example.cine.Usuario.repositories

import org.example.cine.Usuario.models.Usuario

interface UsuarioRepository {
    fun save(usuario: Usuario): Usuario
    fun findByNombreUsuario(nombre: String): Usuario?
    fun update(usuario: Usuario): Usuario
}