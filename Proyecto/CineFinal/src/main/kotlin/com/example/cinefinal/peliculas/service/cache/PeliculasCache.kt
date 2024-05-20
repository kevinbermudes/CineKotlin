package org.example.cinefinal.peliculas.service.cache

import org.example.cinefinal.peliculas.models.Pelicula

interface PeliculasCache {
    fun put(key: Long, value: Pelicula)
    fun get(key: Long): Pelicula?
    fun remove(key: Long): Pelicula?
    fun clear()
}
