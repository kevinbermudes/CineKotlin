package org.example.cine.pago.controllers

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.scene.control.cell.PropertyValueFactory
import org.example.cine.pago.models.Venta
import org.example.cine.peliculas.ViewModel.RecaudacionViewModel
import org.example.cine.route.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private val logger = logging()

class RecaudacionViewController : KoinComponent {

    @FXML
    private lateinit var tablaVentas: TableView<Venta>

    @FXML
    private lateinit var columnaPelicula: TableColumn<Venta, String>

    @FXML
    private lateinit var columnaPrecioEntrada: TableColumn<Venta, Double>

    @FXML
    private lateinit var columnaButacas: TableColumn<Venta, String>

    @FXML
    private lateinit var columnaComplementos: TableColumn<Venta, String>

    @FXML
    private lateinit var columnaPrecioComplementos: TableColumn<Venta, Double>

    @FXML
    private lateinit var columnaTotal: TableColumn<Venta, Double>


    @FXML
    private lateinit var textTotalRecaudado: TextField

    @FXML
    private lateinit var botonCerrar: Button

    private val viewModel: RecaudacionViewModel by inject()

    @FXML
    private fun initialize() {
        initDefaultValues()
        bindToViewModel()
        loadData()
        botonCerrar.setOnAction { onClose() }
    }

    private fun loadData() {
        logger.debug { "Cargando datos iniciales" }
        viewModel.loadAllVentas()
    }

    private fun initDefaultValues() {
        columnaPelicula.cellValueFactory = PropertyValueFactory("pelicula")
        columnaPrecioEntrada.cellValueFactory = PropertyValueFactory("precioEntrada")
        columnaButacas.cellValueFactory = PropertyValueFactory("butacas")
        columnaComplementos.cellValueFactory = PropertyValueFactory("complementos")
        columnaPrecioComplementos.cellValueFactory = PropertyValueFactory("precioComplementos")
        columnaTotal.cellValueFactory = PropertyValueFactory("total")
    }

    private fun bindToViewModel() {
        tablaVentas.items = viewModel.ventas
        textTotalRecaudado.textProperty().bind(viewModel.totalRecaudado.asString())
    }

    private fun onClose() {
        RoutesManager.changeScene(view = RoutesManager.View.ADMININDEX)
    }
}
