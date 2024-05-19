package org.example.cine2.peliculas.service.cache

import org.example.cine2.config.AppConfig
import org.example.cine2.peliculas.models.Pelicula
import org.lighthousegames.logging.logging

private val logger = logging()

class PeliculasCacheImpl(
    private val appConfig: AppConfig
) : PeliculasCache {
    private val cache = mutableMapOf<Long, Pelicula>()

    override fun put(key: Long, value: Pelicula) {
        logger.debug { "put $key" }
        if (cache.size >= appConfig.cacheSize) {
            logger.debug { "Cache completa, eliminando el primer elemento" }
            val oldestKey = cache.keys.first()
            cache.remove(oldestKey)
        }
        cache[key] = value
    }

    override fun get(key: Long): Pelicula? {
        logger.debug { "get $key" }
        return cache[key]
    }

    override fun remove(key: Long): Pelicula? {
        logger.debug { "remove $key" }
        return cache.remove(key)
    }

    override fun clear() {
        logger.debug { "clear" }
        cache.clear()
    }
}

