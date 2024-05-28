package org.example.cine.productos.controllers

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.image.ImageView
import org.example.cine.productos.models.Producto
import org.example.cine.productos.viewmodels.ProductosViewModel
import org.example.cine.route.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

class ProductosViewUsuariosController : KoinComponent {
    private val viewModel: ProductosViewModel by inject()
    @FXML
    private lateinit var textProductosDisponibles: TextField

    @FXML
    private lateinit var butonHelp: Button

    @FXML
    private lateinit var butonCerrarSesion: Button

    @FXML
    private lateinit var tableProductos: TableView<Producto>

    @FXML
    private lateinit var tableColumnIdProducto: TableColumn<Producto, Long>

    @FXML
    private lateinit var tableColumnNombreProducto: TableColumn<Producto, String>

    @FXML
    private lateinit var tableColumnPrecio: TableColumn<Producto, Double>

    @FXML
    private lateinit var tableColumnCategoria: TableColumn<Producto, String>

    @FXML
    private lateinit var tableColumnStock: TableColumn<Producto, Double>

    @FXML
    private lateinit var imagenProductos: ImageView

    @FXML
    private lateinit var butonComprarProductosLogin: Button

    @FXML
    private lateinit var textoNombreProducto: TextField

    @FXML
    private lateinit var textoCategoriaProducto: TextField

    @FXML
    private lateinit var textoPrecioProducto: TextField

    @FXML
    private lateinit var textProductosStock:TextField

    @FXML
    private lateinit var textEstadoLogin: Label

    @FXML
    private lateinit var textBuscadorProductos: TextField

    @FXML
    private lateinit var butonAnadirProductos: Button

    @FXML
    private lateinit var butonAtras: Button

    @FXML
    fun initialize() {
        initEventos()
        initDefaultValues()
        initBindings()
        loadData()
        ProductosDisponibles()
    }

    private fun initEventos() {
        butonHelp.setOnAction { onHelp() }
        butonCerrarSesion.setOnAction { onCerrarSesion() }
        butonComprarProductosLogin.setOnAction { onComprarProductosLogin() }
        butonAnadirProductos.setOnAction { onAnadirProductos() }
        butonAtras.setOnAction { onAtras() }

        tableProductos.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            newValue?.let { onTableProductosSelected(it) }
        }

        textBuscadorProductos.setOnKeyReleased { onBuscadorKeyReleased() }
    }

    private fun initDefaultValues() {
        tableColumnIdProducto.cellValueFactory = PropertyValueFactory("id")
        tableColumnNombreProducto.cellValueFactory = PropertyValueFactory("nombre")
        tableColumnPrecio.cellValueFactory = PropertyValueFactory("precio")
        tableColumnCategoria.cellValueFactory = PropertyValueFactory("categoria")
        tableColumnStock.cellValueFactory = PropertyValueFactory("stock")
    }

    private fun initBindings() {
        viewModel.state.addListener { _, _, newValue ->
            logger.debug { "Actualizando bindings productos" }
            tableProductos.items = FXCollections.observableArrayList(newValue.productos)
            textEstadoLogin.text = "Productos cargados: ${newValue.numProductos}"

            // Actualizar formulario
            val producto = newValue.producto
            textoNombreProducto.text = producto.nombre
            textoCategoriaProducto.text = producto.categoria.name
            textoPrecioProducto.text = producto.precio
            imagenProductos.image = producto.imagen
            textProductosStock.text = producto.stock
        }
    }

    private fun loadData() {
        logger.debug { "Cargando datos iniciales de productos" }
        viewModel.loadAllProductos()
    }

    private fun onHelp() {
        showAlert("Ayuda", "Aquí va la información de ayuda.")
    }

    private fun onCerrarSesion() {
        logger.debug { "Cerrando sesión" }
        RoutesManager.changeScene(view = RoutesManager.View.MAIN)
    }

    private fun onComprarProductosLogin() {
        showAlert("Compra", "Esto te redigirá a la vista de compra.")
    }

    private fun onAnadirProductos() {
        showAlert("Anadir", "Esto añadira un producto a la pasarela de pago.")
    }

    private fun onAtras() {
        logger.debug { "Volviendo a la vista..." }
        clearForm()
        viewModel.clearState()
        RoutesManager.changeScene(view = RoutesManager.View.BUTACASUSUARIO)
    }

    private fun onTableProductosSelected(producto: Producto) {
        viewModel.updateProductoSeleccionado(producto)
    }

    private fun onBuscadorKeyReleased() {
        val filteredList = viewModel.productosFilteredList("TODAS", textBuscadorProductos.text)
        tableProductos.items = FXCollections.observableArrayList(filteredList)
    }

    private fun clearForm() {
        textoNombreProducto.clear()
        textoCategoriaProducto.clear()
        textoPrecioProducto.clear()
        textProductosStock.clear()
    }

    private fun ProductosDisponibles() {
        textProductosDisponibles.text = tableProductos.items.size.toString()
    }

    private fun showAlert(title: String, message: String) {
        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.title = title
        alert.headerText = null
        alert.contentText = message
        alert.showAndWait()
    }
}
