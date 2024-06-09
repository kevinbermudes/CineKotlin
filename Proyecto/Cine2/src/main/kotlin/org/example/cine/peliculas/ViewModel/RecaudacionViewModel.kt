package org.example.cine.peliculas.ViewModel

import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import org.example.cine.pago.models.Venta
import org.example.cine.pago.repositories.VentasRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

class RecaudacionViewModel : KoinComponent {
    private val ventasRepository: VentasRepository by inject()

    val ventas: ObservableList<Venta> = FXCollections.observableArrayList()
    val totalRecaudado = SimpleObjectProperty<Double>(0.0)

    init {
        logger.debug { "Inicializando RecaudacionViewModel" }
        loadAllVentas()
    }

    fun loadAllVentas() {
        logger.debug { "Cargando ventas del repositorio" }
        val result = ventasRepository.findAll()
        ventas.setAll(result)
        totalRecaudado.set(result.sumOf { it.total })
        logger.debug { "Ventas recuperadas: ${result.size}" }
    }
}