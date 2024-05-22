package org.example.cine.peliculas.repositories

import org.example.cine.database.SqlDeLightClient
import org.example.cine.peliculas.mapers.toModel
import org.example.cine.peliculas.models.Pelicula
import org.lighthousegames.logging.logging
import java.time.LocalDateTime

private val logger = logging()

class PeliculasRepositoryImpl(
    private val databaseClient: SqlDeLightClient
) : PeliculasRepository {

    private val db = databaseClient.dbQueries

    override fun findAll(): List<Pelicula> {
        logger.debug { "findAll" }
        return db.selectAll().executeAsList().map { it.toModel() }
    }

    override fun findById(id: Long): Pelicula? {
        logger.debug { "findById: $id" }
        return db.selectById(id).executeAsOneOrNull()?.toModel()
    }

    override fun save(pelicula: Pelicula): Pelicula {
        logger.debug { "save: $pelicula" }
        return if (pelicula.isNewPelicula) {
            create(pelicula)
        } else {
            update(pelicula)
        }
    }

    private fun create(pelicula: Pelicula): Pelicula {
        logger.debug { "create: $pelicula" }
        val timeStamp = LocalDateTime.now().toString()
        db.transaction {
            db.insert(
                nombre = pelicula.nombre,
                duracion = pelicula.duracion,
                fechaEstreno = pelicula.fechaEstreno.toString(),
                descripcion = pelicula.descripcion,
                categoria = pelicula.categoria.name,
                image = pelicula.imagen,
                created_at = timeStamp,
                updated_at = timeStamp
            )
        }
        return db.selectLastInserted().executeAsOne().toModel()
    }

    private fun update(pelicula: Pelicula): Pelicula {
        logger.debug { "update: $pelicula" }
        val timeStamp = LocalDateTime.now().toString()
        db.update(
            nombre = pelicula.nombre,
            duracion = pelicula.duracion,
            fechaEstreno = pelicula.fechaEstreno.toString(),
            descripcion = pelicula.descripcion,
            categoria = pelicula.categoria.name,
            updated_at = timeStamp,
            id = pelicula.id
        )
        return pelicula
    }

    override fun deleteById(id: Long) {
        logger.debug { "deleteById: $id" }
        db.delete(id)
    }

    override fun deleteAll() {
        logger.debug { "deleteAll" }
        db.deleteAll()
    }

    override fun saveAll(peliculas: List<Pelicula>): List<Pelicula> {
        logger.debug { "saveAll: $peliculas" }
        return peliculas.map { save(it) }
    }
}
