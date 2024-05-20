package org.example.cine2.productos.controllers

import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.image.ImageView
import org.example.cine2.productos.models.Producto
import org.example.cine2.productos.viewmodels.ProductosViewModel
import org.lighthousegames.logging.logging

private val logger = logging()

class ProductosViewUsuariosController {
    //Inyectamos nuestro ViewModel
    val viewModel: ProductosViewModel by inject()
    //Botones
    @FXML
    private lateinit var butonHelp: Button
    @FXML
    private lateinit var butonAnadirProductos: Button
    @FXML
    private lateinit var butonAtras: Button
    @FXML
    private lateinit var butonComprarProductosLogin: Button
    @FXML
    private lateinit var butonCerrarSesion: Button

    //Tablas

    @FXML
    private lateinit var tableProductos: TableView<Producto>

    @FXML
    private lateinit var tableColumnIdProducto: TableColumn<Producto, Long>

    @FXML
    private lateinit var tableColumnNombreProducto: TableColumn<Producto, String>

    @FXML
    private lateinit var tableColumnPrecio: TableColumn<Producto, Double>

    @FXML
    private lateinit var tableColumnCategoria: TableColumn<Producto, Producto.Categoria>

    //Formularios
    @FXML
    private lateinit var imagenProductos: ImageView

    @FXML
    private lateinit var textoNombreProducto: TextField

    @FXML
    private lateinit var textoCategoriaProducto: TextField

    @FXML
    private lateinit var textoPrecioProducto: TextField
    //Estado si logeado o no
    @FXML
    private lateinit var textEstadoLogin: Label
    //Buscador
    @FXML
    private lateinit var textBuscadorProductos: TextField



    // Inicialización del controlador
    @FXML
    private fun initialize() {
       logger.debug { "Inicializando ProductosViewModel" }

        //Iniciamos los datos de los productos
        loadBindings()

        //Iniciamos los eventos
        loadEventos()
    }

    private fun loadBindings() {
        logger.debug { "Cargando bindings/datos" }

        //Tablas
        tableProductos.items= viewModel.state.productos
        //Celdas
        tableColumnIdProducto.cellValueFactory= PropertyValueFactory("id")
        tableColumnNombreProducto.cellValueFactory= PropertyValueFactory("nombre")
        tableColumnPrecio.cellValueFactory= PropertyValueFactory("precio")
        tableColumnCategoria.cellValueFactory= PropertyValueFactory("categoria")

        //Formularios
        imagenProductos.imageProperty().bind(viewModel.state.productoSeleccionado.imagen)
        textoNombreProducto.textProperty().bind(viewModel.state.productoSeleccionado.nombre)
        textoCategoriaProducto.textProperty().bind(viewModel.state.productoSeleccionado.categoria)
        textoPrecioProducto.textProperty().bind(viewModel.state.productoSeleccionado.precio)
    }

    private fun loadEventos() {
        logger.debug { "Cargando eventos" }
        //Botones
        butonAnadirProductos.setOnAction { onNuevoAction () }
        butonAtras.setOnAction { onAtrasAction () }
        butonComprarProductosLogin.setOnAction { onComprarAction () }
        butonHelp.setOnAction { onHelpAction () }
        butonCerrarSesion.setOnAction { onCerrarSesionAction () }

        //Tablas
        tableProductos.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            newValue?.let { onTablaSeleccionado(it) }
        }
        //Buscador
        textBuscadorProductos.setOnKeyReleased { newValue ->
            newValue?.let { onBuscaadorSeleccionado() }
        }
    }  private fun onBuscaadorSeleccionado() {
        logger.debug { "onBuscaadorSeleccionado" }
        filterDataTable()
    }
    private fun filterDataTable() {
        logger.debug { "filterDataTable" }
        // filtramos por el tipo seleccionado en la tabla
        tableProductos.items = viewModel.productosFilteredList(textBuscadorProductos.text.trim())
    }

    private fun onTablaSeleccionado(new: Producto) {
        logger.debug { "onTablaSeleccionado" }
        viewModel.updateProductoSeleccionado(new)
    }

    private fun onAtrasAction() {
        logger.debug { "onAtrasAction" }
    }

    private fun onComprarAction() {
        logger.debug { "onComprarAction" }
    }

    private fun onHelpAction() {
        logger.debug { "onHelpAction" }
    }

    private fun onNuevoAction() {
        logger.debug { "onNuevoAction" }
    }

    private fun onCerrarSesionAction() {
        logger.debug { "onCerrarSesionAction" }
    }

}
