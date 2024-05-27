package org.example.cine.pago.controllers
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.image.ImageView
import org.example.cine.pago.models.CarritoItem
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
    private lateinit var butonHelp: Button

    @FXML
    private lateinit var butonCerrarSesion: Button

    @FXML
    private lateinit var tableCarrito: TableView<CarritoItem>

    @FXML
    private lateinit var tableColumnId: TableColumn<CarritoItem, Long>

    @FXML
    private lateinit var tableColumnNombre: TableColumn<CarritoItem, String>

    @FXML
    private lateinit var tableColumnPrecio: TableColumn<CarritoItem, Double>

    @FXML
    private lateinit var textoNombreProducto: TextField

    @FXML
    private lateinit var textoCategoriaProducto: TextField

    @FXML
    private lateinit var textoPrecioProducto: TextField

    @FXML
    private lateinit var textEstadoLogin: Label

    @FXML
    private lateinit var textBuscadorProductos: TextField

    @FXML
    private lateinit var butonAnadirProductos: Button

    @FXML
    private lateinit var butonAtras: Button

    private val carritoItems = FXCollections.observableArrayList<CarritoItem>()

    @FXML
    fun initialize() {
        initEventos()
        loadData()
    }

    private fun initEventos() {
        butonHelp.setOnAction { onHelp() }
        butonCerrarSesion.setOnAction { onCerrarSesion() }
        butonAnadirProductos.setOnAction { onAnadirProducto() }
        butonAtras.setOnAction { onAtras() }
    }

    private fun loadData() {
        viewModel.state.addListener { _, _, newValue ->
            tableCarrito.items = FXCollections.observableArrayList(newValue.productos.map { CarritoItem.ProductoItem(it) })
        }
        viewModel.loadAllProductos()
    }

    private fun onHelp() {
        showAlert("Ayuda", "Aquí se muestra la ayuda de la aplicación.")
    }

    private fun onCerrarSesion() {
        logger.debug { "Cerrando sesión" }
        RoutesManager.changeScene(view = RoutesManager.View.LOGIN)
    }

    private fun onAnadirProducto() {
        val selectedProduct = tableCarrito.selectionModel.selectedItem
        selectedProduct?.let {
            carritoItems.add(it)
            tableCarrito.items = carritoItems
        }
    }

    private fun onAtras() {
        logger.debug { "Volviendo a la vista anterior" }
        RoutesManager.changeScene(view = RoutesManager.View.USUARIOINDEX)
    }

    private fun showAlert(title: String, message: String) {
        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.title = title
        alert.headerText = null
        alert.contentText = message
        alert.showAndWait()
    }
}