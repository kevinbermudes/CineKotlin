package org.example.cine.productos.repositories

import org.example.cine.database.SqlDeLightClient
import org.example.cine.productos.mappers.toModel
import org.example.cine.productos.models.Producto
import org.lighthousegames.logging.logging
import java.time.LocalDateTime

private val logger = logging()

class ProductosRepositoryImpl(
    private val databaseClient: SqlDeLightClient
) : ProductosRepository {

    val db = databaseClient.productQueries

    override fun findAll(): List<Producto> {
        logger.debug { "findAll" }
        return db.selectAll().executeAsList().map { it.toModel() }
    }

    override fun findById(id: Long): Producto? {
        logger.debug { "findById: $id" }
        return db.selectById(id).executeAsOneOrNull()?.toModel()
    }

    override fun save(producto: Producto): Producto {
        logger.debug { "save: $producto" }
        return if (producto.isNewProducto) {
            create(producto)
        } else {
            update(producto)
        }
    }

    ///proba de comit

    private fun create(producto: Producto): Producto {
        logger.debug { "create: $producto" }
        val timeStamp = LocalDateTime.now().toString()
        db.transaction {
            db.insert(
                nombre = producto.nombre,
                precio = producto.precio,
                imagen = producto.imagen,
                categoria = producto.categoria.name,
                stock = producto.stock.toLong(), // Asegúrate de que el stock se maneje correctamente
                created_at = timeStamp,
                updated_at = timeStamp
            )
        }
        return db.selectLastInserted().executeAsOne().toModel()
    }

    private fun update(producto: Producto): Producto {
        logger.debug { "update: $producto" }
        val timeStamp = LocalDateTime.now().toString()
        db.update(
            id = producto.id,
            nombre = producto.nombre,
            precio = producto.precio,
            imagen = producto.imagen,
            stock = producto.stock.toLong(), // Asegúrate de que el stock se maneje correctamente
            categoria = producto.categoria.name,
            updated_at = timeStamp
        )
        return producto
    }

    override fun deleteById(id: Long) {
        logger.debug { "deleteById: $id" }
        return db.delete(id)
    }

    override fun deleteAll() {
        logger.debug { "deleteAll" }
        return db.deleteAll()
    }

    override fun saveAll(productos: List<Producto>): List<Producto> {
        logger.debug { "saveAll: $productos" }
        return productos.map { save(it) }
    }
}
