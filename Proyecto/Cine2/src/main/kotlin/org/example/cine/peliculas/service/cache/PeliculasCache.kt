package org.example.cine.peliculas.service.cache

import org.example.cine.peliculas.models.Pelicula

interface PeliculasCache {
    fun put(key: Long, value: Pelicula)
    fun get(key: Long): Pelicula?
    fun remove(key: Long): Pelicula?
    fun clear()
}
