package org.example.cine.productos.controllers

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.image.ImageView
import javafx.stage.Stage
import org.example.cine.productos.models.Producto
import org.example.cine.productos.viewmodels.ProductosViewModel
import org.example.cine.route.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

class ProductosViewAdminController : KoinComponent {
    private val viewModel: ProductosViewModel by inject()

    @FXML
    private lateinit var butonFile: Button

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
    private lateinit var butonEditarProducto: Button

    @FXML
    private lateinit var textoNombreProducto: TextField

    @FXML
    private lateinit var textoCategoriaProducto: TextField

    @FXML
    private lateinit var textoPrecioProducto: TextField

    @FXML
    private lateinit var textoStockProducto: TextField

    @FXML
    private lateinit var textEstadoLogin: Label

    @FXML
    private lateinit var textBuscadorProductos: TextField

    @FXML
    private lateinit var butonAnadirProductosAdmin: Button

    @FXML
    private lateinit var butonAtras: Button

    @FXML
    private lateinit var butonBorrarProductos: Button

    @FXML
    fun initialize() {
        initEventos()
        initDefaultValues()
        initBindings()
        loadData()
    }

    private fun initEventos() {
        butonFile.setOnAction { onFile() }
        butonHelp.setOnAction { onHelp() }
        butonCerrarSesion.setOnAction { onCerrarSesion() }
        butonEditarProducto.setOnAction { onEditarProducto() }
        butonAnadirProductosAdmin.setOnAction { onAnadirProducto() }
        butonAtras.setOnAction { onAtras() }
        butonBorrarProductos.setOnAction { onBorrarProducto() }

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
            textoStockProducto.text = producto.stock
            logger.debug { " cargando imaegn con nombre Imagen: ${newValue.producto.imagen}" }
            imagenProductos.image = newValue.producto.imagen
        }
    }

    private fun loadData() {
        logger.debug { "Cargando datos iniciales de productos" }
        viewModel.loadAllProductos()
    }

    private fun onFile() {
        showAlert("File", "Funcionalidad para el botón File.")
    }

    private fun onHelp() {
        logger.debug { "Abriendo ventana de ayuda" }
        // RoutesManager.changeScene(view = RoutesManager.View.HELP)
        showAlert("Ayuda", "Aquí va la información de ayuda.")
    }

    private fun onCerrarSesion() {
        logger.debug { "Cerrando sesión" }
        RoutesManager.changeScene(view = RoutesManager.View.MAIN)
    }

    private fun onEditarProducto() {
        val producto = tableProductos.selectionModel.selectedItem
        if (producto != null) {
            logger.debug { "Editando producto: ${producto.nombre}" }
            RoutesManager.editarProducto(producto)
        } else {
            showAlert("Error", "Por favor, selecciona un producto para editar.")
        }
    }

    private fun onAnadirProducto() {
        logger.debug { "Añadiendo nuevo producto" }
        val stage = butonAnadirProductosAdmin.scene.window as Stage
        RoutesManager.initAnadirViewController()
    }

    private fun onAtras() {
        logger.debug { "Volviendo al índice de administrador" }
        clearForm()
        viewModel.clearState()
        RoutesManager.changeScene(view = RoutesManager.View.ADMININDEX)
    }

    private fun onBorrarProducto() {
        val producto = tableProductos.selectionModel.selectedItem
        if (producto != null) {
            val alert = Alert(Alert.AlertType.CONFIRMATION)
            alert.title = "Confirmar eliminación"
            alert.headerText = null
            alert.contentText = "¿Estás seguro de que deseas eliminar el producto \"${producto.nombre}\"?"
            val result = alert.showAndWait()

            if (result.isPresent && result.get() == ButtonType.OK) {
                viewModel.eliminarProducto(producto)
                    .onSuccess {
                        showAlert("Éxito", "Producto eliminado correctamente.")
                        loadData()  // Volver a cargar los datos después de eliminar el producto
                    }
                    .onFailure {
                        showAlert("Error", "Error al eliminar el producto: ${it.message}")
                    }
            }
        } else {
            showAlert("Error", "Por favor, selecciona un producto para eliminar.")
        }
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
        textoStockProducto.clear()
    }

    private fun showAlert(title: String, message: String) {
        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.title = title
        alert.headerText = null
        alert.contentText = message
        alert.showAndWait()
    }
}
