package org.example.cinefinal.peliculas.service.database

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import org.example.cinefinal.peliculas.errors.PeliculaError
import org.example.cinefinal.peliculas.models.Pelicula
import org.example.cinefinal.peliculas.repositories.PeliculasRepository
import org.example.cinefinal.peliculas.service.cache.PeliculasCache
import org.lighthousegames.logging.logging

private val logger = logging()

class PeliculasServiceImpl(
    private val repository: PeliculasRepository,
    private val cache: PeliculasCache
) : PeliculasService {
    override fun findAll(): Result<List<Pelicula>, PeliculaError> {
        logger.debug { "findAll" }
        return Ok(repository.findAll())
    }

    override fun deleteAll(): Result<Unit, PeliculaError> {
        logger.debug { "deleteAll" }
        repository.deleteAll().also {
            cache.clear()
            return Ok(it)
        }
    }

    override fun saveAll(peliculas: List<Pelicula>): Result<List<Pelicula>, PeliculaError> {
        logger.debug { "saveAll" }
        repository.saveAll(peliculas).also {
            cache.clear()
            return Ok(it)
        }
    }

    override fun save(pelicula: Pelicula): Result<Pelicula, PeliculaError> {
        logger.debug { "save" }
        repository.save(pelicula).also {
            cache.put(pelicula.id, pelicula)
            return Ok(it)
        }
    }

    override fun deleteById(id: Long): Result<Unit, PeliculaError> {
        logger.debug { "deleteById" }
        repository.deleteById(id).also {
            cache.remove(id)
            return Ok(it)
        }
    }

    override fun findById(id: Long): Result<Pelicula, PeliculaError> {
        logger.debug { "findById" }
        return cache.get(id)?.let {
            Ok(it)
        } ?: repository.findById(id)?.also {
            cache.put(id, it)
        }?.let {
            Ok(it)
        } ?: Err(PeliculaError.NotFound("Película con ID $id no encontrada"))
    }
}