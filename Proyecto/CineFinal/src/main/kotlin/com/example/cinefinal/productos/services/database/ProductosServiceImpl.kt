package org.example.cinefinal.productos.services.database

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.cinefinal.productos.errors.ProductoError
import org.example.cinefinal.productos.models.Producto
import org.example.cinefinal.productos.repositories.ProductosRepository
import org.example.cinefinal.productos.services.cache.ProductosCache
import org.lighthousegames.logging.logging

private val logger = logging()

class ProductosServiceImpl(
    private val repository: ProductosRepository,
    private val cache: ProductosCache
) : ProductosService {
    override fun findAll(): Result<List<Producto>, ProductoError> {
        logger.debug { "findAll" }
        return Ok(repository.findAll())
    }

    override fun deleteAll(): Result<Unit, ProductoError> {
        logger.debug { "deleteAll" }
        repository.deleteAll().also {
            cache.clear()
            return Ok(it)
        }
    }

    override fun saveAll(productos: List<Producto>): Result<List<Producto>, ProductoError> {
        logger.debug { "saveAll" }
        repository.saveAll(productos).also {
            cache.clear()
            return Ok(it)
        }
    }

    override fun save(producto: Producto): Result<Producto, ProductoError> {
        logger.debug { "save" }
        repository.save(producto).also {
            cache.put(producto.id, producto)
            return Ok(it)
        }
    }

    override fun deleteById(id: Long): Result<Unit, ProductoError> {
        logger.debug { "deleteById" }
        repository.deleteById(id).also {
            cache.remove(id)
            return Ok(it)
        }
    }

    override fun findById(id: Long): Result<Producto, ProductoError> {
        logger.debug { "findById" }
        return cache.get(id)?.let {
            Ok(it)
        } ?: repository.findById(id)?.also {
            cache.put(id, it)
        }?.let {
            Ok(it)
        } ?: Err(ProductoError.NotFound("Producto con ID $id no encontrado"))
    }
}