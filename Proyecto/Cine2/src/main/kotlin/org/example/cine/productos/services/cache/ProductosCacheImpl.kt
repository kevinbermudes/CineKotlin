package org.example.cine.productos.services.cache

import org.example.cine.config.AppConfig
import org.example.cine.productos.models.Producto
import org.lighthousegames.logging.logging

private val logger = logging()

class ProductosCacheImpl (private val appConfig: AppConfig) : ProductosCache {
    private val cache = mutableMapOf<Long, Producto>()
    override fun put(key: Long, value: Producto) {
        logger.debug { "put $key" }
        if (cache.size >= appConfig.cacheSize) {
            logger.debug { "Cache completa, eliminando el primer elemento" }
            val oldestKey = cache.keys.first()
            cache.remove(oldestKey)
        }
        cache[key] = value
    }

    override fun get(key: Long): Producto? {
        logger.debug { "get $key" }
        return cache[key]
    }

    override fun remove(key: Long): Producto? {
        logger.debug { "remove $key" }
        return cache.remove(key)
    }

    override fun clear() {
        logger.debug { "clear" }
        cache.clear()
    }
}