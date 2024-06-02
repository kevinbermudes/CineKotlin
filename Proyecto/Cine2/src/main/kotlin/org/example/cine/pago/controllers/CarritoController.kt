package org.example.cine.pago.controllers

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import org.example.cine.pago.models.Carrito
import org.example.cine.peliculas.models.Butaca
import org.example.cine.productos.models.Producto
import org.example.cine.productos.viewmodels.ProductosViewModel
import org.example.cine.route.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

class CarritoController : KoinComponent {

    @FXML
    private lateinit var tableButacas: TableView<Butaca>

    @FXML
    private lateinit var tableColumnIdButaca: TableColumn<Butaca, String>

    @FXML
    private lateinit var tableColumnPrecioButaca: TableColumn<Butaca, Double>

    @FXML
    private lateinit var tableProductos: TableView<Producto>

    @FXML
    private lateinit var tableColumnNombreProducto: TableColumn<Producto, String>

    @FXML
    private lateinit var tableColumnPrecioProducto: TableColumn<Producto, Double>

    @FXML
    private lateinit var botonQuitarButaca: Button

    @FXML
    private lateinit var botonQuitarProducto: Button

    @FXML
    private lateinit var botonConfirmarCompra: Button

    @FXML
    private lateinit var butonAtrasProduto: Button

    private val carrito: Carrito by inject()
    private val viewModel: ProductosViewModel by inject()

    @FXML
    fun initialize() {
        initTableColumns()
        initEventHandlers()
        loadCarritoData()
    }

    private fun initTableColumns() {
        tableColumnIdButaca.cellValueFactory = PropertyValueFactory("id")
        tableColumnPrecioButaca.cellValueFactory = PropertyValueFactory("precio")

        tableColumnNombreProducto.cellValueFactory = PropertyValueFactory("nombre")
        tableColumnPrecioProducto.cellValueFactory = PropertyValueFactory("precio")
    }

    private fun initEventHandlers() {
        botonQuitarButaca.setOnAction { eliminarButaca() }
        botonQuitarProducto.setOnAction { eliminarProducto() }
        botonConfirmarCompra.setOnAction { confirmarCompra() }
        butonAtrasProduto.setOnAction { volverAProductos() }
    }

    private fun loadCarritoData() {
        tableButacas.items = FXCollections.observableArrayList(Carrito.instance.butacas)
        tableProductos.items = FXCollections.observableArrayList(Carrito.instance.productos)
    }

    private fun eliminarButaca() {
        val selectedButaca = tableButacas.selectionModel.selectedItem
        if (selectedButaca != null) {
            if (Carrito.instance.butacas.size == 1) {
                showAlert("Selección inválida", "Debes tener al menos 1 butaca en el carrito.")
                return
            }
            Carrito.instance.butacas.remove(selectedButaca)
            tableButacas.items.remove(selectedButaca)
        } else {
            showAlert("Selección inválida", "Por favor, selecciona una butaca para eliminar.")
        }
    }

    private fun eliminarProducto() {
        val selectedProducto = tableProductos.selectionModel.selectedItem
        if (selectedProducto != null) {
            // Añadir el stock del producto de nuevo
            selectedProducto.increaseStock()
            viewModel.saveProduct(selectedProducto)

            Carrito.instance.productos.remove(selectedProducto)
            tableProductos.items.remove(selectedProducto)
        } else {
            showAlert("Selección inválida", "Por favor, selecciona un producto para eliminar.")
        }
    }

    private fun confirmarCompra() {
        // Lógica para confirmar la compra
        logger.debug { "redirigiendo a carrito de compra" }
        RoutesManager.changeScene(view = RoutesManager.View.PAGO)
    }

    private fun volverAProductos() {
        RoutesManager.changeScene(view = RoutesManager.View.PRODUCTOSUSUARIOS)
    }

    private fun showAlert(titulo: String, mensaje: String) {
        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.title = titulo
        alert.headerText = null
        alert.contentText = mensaje
        alert.showAndWait()
    }
}