package org.example.cine2.peliculas.service.cache

import org.example.cine2.peliculas.models.Pelicula

interface PeliculasCache {
    fun put(key: Long, value: Pelicula)
    fun get(key: Long): Pelicula?
    fun remove(key: Long): Pelicula?
    fun clear()
}
