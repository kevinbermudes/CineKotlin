package org.example.cine.productos.controllers

import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.image.ImageView
import javafx.stage.Stage
import org.example.cine.route.RoutesManager
import org.lighthousegames.logging.logging

private val logger = logging()

class ProductosViewAdminController {

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
    }

    private fun initEventos() {
        butonFile.setOnAction { onFile() }
        butonHelp.setOnAction { onHelp() }
        butonCerrarSesion.setOnAction { onCerrarSesion() }
        butonEditarProducto.setOnAction { onEditarProducto() }
        butonAnadirProductosAdmin.setOnAction { onAnadirProducto() }
        butonAtras.setOnAction { onAtras() }
        butonBorrarProductos.setOnAction { onBorrarProducto() }
    }

    private fun onFile() {
        // Implementar funcionalidad para el botón "File"
        showAlert("File", "Funcionalidad para el botón File.")
    }

    private fun onHelp() {
        showAlert("Ayuda", "Aquí va la información de ayuda.")
    }

    private fun onCerrarSesion() {
        logger.debug { "Cerrando sesión" }
        val stage = butonCerrarSesion.scene.window as Stage
        stage.close()
        RoutesManager.initMainStage(Stage())
    }

    private fun onEditarProducto() {
        val productoSeleccionado = tableProductos.selectionModel.selectedItem
        if (productoSeleccionado != null) {
            textoNombreProducto.text = productoSeleccionado.nombre
            textoCategoriaProducto.text = productoSeleccionado.categoria
            textoPrecioProducto.text = productoSeleccionado.precio.toString()
        } else {
            showAlert("Error", "Selecciona un producto para editar.")
        }
    }

    private fun onAnadirProducto() {
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
        val stage = butonAtras.scene.window as Stage
        stage.close()
        RoutesManager.intiUsuarioIndex() // O la vista que desees cargar
    }

    private fun onBorrarProducto() {
        val productoSeleccionado = tableProductos.selectionModel.selectedItem
        if (productoSeleccionado != null) {
            tableProductos.items.remove(productoSeleccionado)
        } else {
            showAlert("Error", "Selecciona un producto para borrar.")
        }
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

