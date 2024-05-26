package org.example.cine.productos.controllers

import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.image.ImageView
import org.example.cine.productos.viewmodels.ProductosViewModel
import org.example.cine.route.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging


private val logger = logging()

class ProductosViewUsuariosController  : KoinComponent {
    private val viewModel: ProductosViewModel by inject()


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
    }


    private fun initEventos() {
        butonHelp.setOnAction { onHelp() }
        butonCerrarSesion.setOnAction { onCerrarSesion() }
        butonComprarProductosLogin.setOnAction { onComprarProductosLogin() }
        butonAnadirProductos.setOnAction { onAnadirProductos() }
        butonAtras.setOnAction { onAtras() }
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
        showAlert("Compra", "Esto te redigira a la vista de compra.")
    }

    private fun onAnadirProductos() {
        val nombre = textoNombreProducto.text
        val categoria = textoCategoriaProducto.text
        val precio = textoPrecioProducto.text.toDoubleOrNull()

        if (nombre.isNotBlank() && categoria.isNotBlank() && precio != null) {
            val producto = Producto(nombre, categoria, precio)
            tableProductos.items.add(producto)
            clearForm()
        } else {
            showAlert("Error", "Por favor, completa todos los campos correctamente.")
        }
    }

    private fun onAtras() {
        logger.debug { "Volviendo a la vista de peliculas admin..." }
        RoutesManager.changeScene(view = RoutesManager.View.USUARIOINDEX)
    }

    private fun clearForm() {
        textoNombreProducto.clear()
        textoCategoriaProducto.clear()
        textoPrecioProducto.clear()
    }

    private fun showAlert(title: String, message: String) {
        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.title = title
        alert.headerText = null
        alert.contentText = message
        alert.showAndWait()
    }
}

data class Producto(val nombre: String, val categoria: String, val precio: Double)
