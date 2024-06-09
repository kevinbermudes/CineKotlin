package org.example.cine.pago.repositories
import org.example.cine.pago.models.Venta
import org.example.cine.database.SqlDeLightClient
import org.example.cine.pago.mapper.toModel
import org.lighthousegames.logging.logging

private val logger = logging()

class VentasRepositoryImpl(
    private val databaseClient: SqlDeLightClient
) : VentasRepository {

    private val db = databaseClient.ventasQueries

    override fun findAll(): List<Venta> {
        logger.debug { "findAll" }
        return db.selectAllVentas().executeAsList().map { it.toModel() }
    }

    override fun findById(id: Long): Venta? {
        TODO("Not yet implemented")
    }

    override fun save(venta: Venta): Venta {
        logger.debug { "save: $venta" }
        return if (venta.isNewVenta) {
            create(venta)
        } else {
            update(venta)
        }
    }

    override fun deleteById(id: Long) {
        TODO("Not yet implemented")
    }

    override fun deleteAll(venta: List<Venta>): List<Venta> {
        TODO("Not yet implemented")
    }

    private fun create(venta: Venta): Venta {
        logger.debug { "create: $venta" }
        db.transaction {
            db.insertVenta(
                pelicula = venta.pelicula,
                precioEntrada = venta.precioEntrada,
                butacas = venta.butacas,
                complementos = venta.complementos,
                precioComplementos = venta.precioComplementos,
                total = venta.total,
            )
        }
        return db.selectLastInsertedVenta().executeAsOne().toModel()
    }

    private fun update(venta: Venta): Venta {
        logger.debug { "update: $venta" }
        db.updateVenta(
            pelicula = venta.pelicula,
            precioEntrada = venta.precioEntrada,
            butacas = venta.butacas,
            complementos = venta.complementos,
            precioComplementos = venta.precioComplementos,
            total = venta.total,
            id = venta.id
        )
        return venta
    }


}