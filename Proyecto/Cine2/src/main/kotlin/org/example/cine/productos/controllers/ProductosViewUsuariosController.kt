package org.example.cine.productos.controllers

import javafx.beans.value.ChangeListener
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import org.example.cine.pago.models.Carrito
import org.example.cine.productos.models.Producto
import org.example.cine.productos.viewmodels.ProductosViewModel
import org.example.cine.route.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

class ProductosViewUsuariosController : KoinComponent {
    private val viewModel: ProductosViewModel by inject()
    private val carrito: Carrito = Carrito.instance
    private val defaultImage = Image(javaClass.getResourceAsStream("/org/example/cine/images/sin-imagen.png"))

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

    private val productosObservableList: ObservableList<Producto> = FXCollections.observableArrayList()

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

        tableProductos.selectionModel.selectedItemProperty().addListener(tableSelectionListener)
        textBuscadorProductos.setOnKeyReleased { onBuscadorKeyReleased() }
    }

    private fun initDefaultValues() {
        tableColumnIdProducto.cellValueFactory = PropertyValueFactory("id")
        tableColumnNombreProducto.cellValueFactory = PropertyValueFactory("nombre")
        tableColumnPrecio.cellValueFactory = PropertyValueFactory("precio")
        tableColumnCategoria.cellValueFactory = PropertyValueFactory("categoria")
        tableProductos.items = productosObservableList
    }

    private val stateListener = ChangeListener<ProductosViewModel.ProductoState> { _, _, newValue ->
        logger.debug { "Actualizando datos de la vista" }

        // Actualizamos la tabla
        if (tableProductos.items != newValue.productos) {
            tableProductos.items = FXCollections.observableArrayList(newValue.productos)
        }

        // Formulario
        textoNombreProducto.text = newValue.producto.nombre
        textoCategoriaProducto.text = newValue.producto.categoria.name
        textoPrecioProducto.text = newValue.producto.precio.toString()
        textProductosDisponibles.text = newValue.producto.stock.toString()
        imagenProductos.image = newValue.producto.imagen
    }

    private fun initBindings() {
        logger.debug { "Inicializando bindings" }
        viewModel.state.addListener(stateListener)
    }

    private val tableSelectionListener = ChangeListener<Producto?> { _, _, newValue ->
        newValue?.let { onTableProductosSelected(it) }
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
        logger.debug { "redirigiendo a carrito de compra" }
        RoutesManager.changeScene(view = RoutesManager.View.CARRITO)
    }

    private fun onAnadirProductos() {
        val selectedProduct = tableProductos.selectionModel.selectedItem
        if (selectedProduct != null) {
            if (selectedProduct.stock > 0) {
                selectedProduct.reduceStock()
                viewModel.saveProduct(selectedProduct)
                carrito.productos.add(selectedProduct)
                updateProductoDetalles(selectedProduct)
            } else {
                showAlert("Stock agotado", "No hay suficiente stock de este producto.")
            }
        } else {
            showAlert("Selección inválida", "Por favor, selecciona un producto para añadir.")
        }
    }

    private fun onAtras() {
        logger.debug { "Volviendo a la vista..." }
        clearForm()
        viewModel.clearState()
        clearCarrito()
        RoutesManager.changeScene(view = RoutesManager.View.BUTACASUSUARIO)
    }

    private fun onTableProductosSelected(producto: Producto) {
        // Desactivar el listener de cambios de estado para evitar recursión infinita
        viewModel.state.removeListener(stateListener)
        try {
            viewModel.updateProductoSeleccionado(producto)
            // Actualizar los campos del formulario manualmente
            textoNombreProducto.text = producto.nombre
            textoCategoriaProducto.text = producto.categoria.name
            textoPrecioProducto.text = producto.precio.toString()
            textProductosDisponibles.text = producto.stock.toString()
            imagenProductos.image = loadImageOrDefault(producto.imagen)
        } finally {
            // Volver a activar el listener después de la actualización
            viewModel.state.addListener(stateListener)
        }
    }

    private fun onBuscadorKeyReleased() {
        val filteredList = viewModel.productosFilteredList("TODAS", textBuscadorProductos.text)
        productosObservableList.setAll(filteredList)
    }

    private fun clearForm() {
        textoNombreProducto.clear()
        textoCategoriaProducto.clear()
        textoPrecioProducto.clear()
        textProductosStock.clear()
    }

    private fun ProductosDisponibles() {
        textProductosDisponibles.text = productosObservableList.size.toString()
    }

    private fun clearCarrito() {
        carrito.butacas.clear()
        carrito.productos.clear()
    }

    private fun loadImageOrDefault(imagePath: String?): Image {
        return try {
            val url = if (imagePath != null) javaClass.getResource("/org/example/cine/images/$imagePath") else null
            if (url != null) Image(url.toString()) else defaultImage
        } catch (e: Exception) {
            defaultImage
        }
    }

    private fun updateProductoDetalles(producto: Producto) {
        textoNombreProducto.text = producto.nombre
        textoCategoriaProducto.text = producto.categoria.name
        textoPrecioProducto.text = producto.precio.toString()
        textProductosDisponibles.text = producto.stock.toString()
        imagenProductos.image = loadImageOrDefault(producto.imagen)
    }

    private fun showAlert(title: String, message: String) {
        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.title = title
        alert.headerText = null
        alert.contentText = message
        alert.showAndWait()
    }
}
