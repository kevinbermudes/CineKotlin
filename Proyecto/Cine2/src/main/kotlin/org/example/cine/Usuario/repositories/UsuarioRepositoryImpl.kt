package org.example.cine.repositories

import org.example.cine.Usuario.mappers.toModel
import org.example.cine.Usuario.models.Usuario
import org.example.cine.Usuario.repositories.UsuarioRepository
import org.example.cine.database.SqlDeLightClient
import java.time.LocalDateTime
import org.lighthousegames.logging.logging
private val logger = logging()
class UsuarioRepositoryImpl(
    private val databaseClient: SqlDeLightClient
) : UsuarioRepository {

    val db = databaseClient.userQueries

    override fun findByNombreUsuario(nombre: String): Usuario? {
        return db.selectByNombreUsuario(nombre).executeAsOneOrNull()?.toModel()
    }

    override fun save(usuario: Usuario): Usuario {
        logger.debug { "Guardando usuario: $usuario" }
        val timeStamp = LocalDateTime.now().toString()
        db.transaction {
            db.insert(
                nombre = usuario.nombre,
                contrasena = usuario.contrasena,
                email = usuario.email,
                rol = usuario.rol.name,
                created_at = timeStamp,
                updated_at = timeStamp
            )
        }
        return db.selectLastInserted().executeAsOne().toModel()
    }



    override fun update(usuario: Usuario): Usuario {
        logger.debug { "Actualizando usuario: $usuario" }
        val timeStamp = LocalDateTime.now().toString()
        db.update(
            id = usuario.id,
            nombre = usuario.nombre,
            contrasena = usuario.contrasena,
            email = usuario.email,
            rol = usuario.rol.name,
            updated_at = timeStamp
        )
        return usuario
    }
}
