package org.example.cine2.peliculas.repositories

import org.example.cine2.peliculas.models.Pelicula

interface PeliculasRepository {
    fun findAll(): List<Pelicula>
    fun findById(id: Long): Pelicula?
    fun save(pelicula: Pelicula): Pelicula
    fun deleteById(id: Long)
    fun deleteAll()
    fun saveAll(peliculas: List<Pelicula>): List<Pelicula>
}
