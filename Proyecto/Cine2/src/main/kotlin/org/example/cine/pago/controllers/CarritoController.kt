package org.example.cine.pago.controllers

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import org.example.cine.pago.models.Carrito
import org.example.cine.peliculas.models.Butaca
import org.example.cine.productos.models.Producto
import org.lighthousegames.logging.logging

private val logger = logging()

class CarritoController {

    @FXML
    private lateinit var tablaProductos: TableView<Producto>

    @FXML
    private lateinit var columnaNombreProducto: TableColumn<Producto, String>

    @FXML
    private lateinit var columnaPrecioProducto: TableColumn<Producto, Double>

    @FXML
    private lateinit var botonQuitarProducto: Button

    @FXML
    private lateinit var tablaButacas: TableView<Butaca>

    @FXML
    private lateinit var columnaIdButaca: TableColumn<Butaca, String>

    @FXML
    private lateinit var columnaPrecioButaca: TableColumn<Butaca, Double>

    @FXML
    private lateinit var botonQuitarButaca: Button

    @FXML
    private lateinit var botonConfirmarCompra: Button

    private val carrito = Carrito.instance

    @FXML
    fun initialize() {
        initTableColumns()
        initEventHandlers()
        loadCarritoData()
    }

    private fun initTableColumns() {
        // Productos
        columnaNombreProducto.cellValueFactory = PropertyValueFactory("nombre")
        columnaPrecioProducto.cellValueFactory = PropertyValueFactory("precio")

        // Butacas
        columnaIdButaca.cellValueFactory = PropertyValueFactory("id")
        columnaPrecioButaca.cellValueFactory = PropertyValueFactory("precio")
    }

    private fun initEventHandlers() {
        botonQuitarProducto.setOnAction { quitarProducto() }
        botonQuitarButaca.setOnAction { quitarButaca() }
        botonConfirmarCompra.setOnAction { confirmarCompra() }
    }

    private fun loadCarritoData() {
        // Cargar productos en la tabla
        tablaProductos.items = FXCollections.observableArrayList(carrito.productos)

        // Cargar butacas en la tabla
        tablaButacas.items = FXCollections.observableArrayList(carrito.butacas)
    }

    private fun quitarProducto() {
        val selectedProduct = tablaProductos.selectionModel.selectedItem
        if (selectedProduct != null) {
            carrito.productos.remove(selectedProduct)
            tablaProductos.items.remove(selectedProduct)
            logger.debug { "Producto eliminado del carrito: ${selectedProduct.nombre}" }
        } else {
            showAlert("Selección inválida", "Por favor, seleccione un producto para eliminar.")
        }
    }

    private fun quitarButaca() {
        val selectedButaca = tablaButacas.selectionModel.selectedItem
        if (selectedButaca != null) {
            carrito.butacas.remove(selectedButaca)
            tablaButacas.items.remove(selectedButaca)
            logger.debug { "Butaca eliminada del carrito: ${selectedButaca.id}" }
        } else {
            showAlert("Selección inválida", "Por favor, seleccione una butaca para eliminar.")
        }
    }

    private fun confirmarCompra() {
        if (carrito.productos.isEmpty() && carrito.butacas.isEmpty()) {
            showAlert("Carrito vacío", "No hay productos ni butacas en el carrito para confirmar la compra.")
        } else {
            // Lógica para confirmar la compra
            logger.debug { "Compra confirmada. Total: ${carrito.total}" }
            carrito.productos.clear()
            carrito.butacas.clear()
            loadCarritoData()
            showAlert("Compra exitosa", "La compra ha sido confirmada. Total: ${carrito.total}")
        }
    }

    private fun showAlert(title: String, message: String) {
        val alert = javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION)
        alert.title = title
        alert.headerText = null
        alert.contentText = message
        alert.showAndWait()
    }
}
