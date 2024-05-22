package org.example.cine.productos.controllers

import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.stage.FileChooser
import javafx.stage.Stage
import java.io.File

class ProductosAnadirViewController {

    @FXML
    private lateinit var imagenProductoAnadir: ImageView

    @FXML
    private lateinit var textoNombreProducto: TextField

    @FXML
    private lateinit var textoCategoriaProducto: TextField

    @FXML
    private lateinit var textoPrecioProducto: TextField

    @FXML
    private lateinit var butonGuardarProductos: Button

    @FXML
    private lateinit var butonCancelarProductos: Button

    private var imagenSeleccionada: File? = null

    @FXML
    private fun initialize() {
        // Configurar acciones de los botones, falta.
        butonGuardarProductos.setOnAction { guardarProducto() }
        butonCancelarProductos.setOnAction { cancelar() }
        imagenProductoAnadir.setOnMouseClicked { seleccionarImagen() }
    }

    private fun seleccionarImagen() {
        val fileChooser = FileChooser()
        fileChooser.title = "Seleccionar imagen de producto"
        fileChooser.extensionFilters.addAll(
            FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
        )
        val file = fileChooser.showOpenDialog(imagenProductoAnadir.scene.window as Stage)
        if (file != null) {
            imagenSeleccionada = file
            val image = Image(file.toURI().toString())
            imagenProductoAnadir.image = image
        }
    }

    private fun guardarProducto() {
        val nombre = textoNombreProducto.text
        val categoria = textoCategoriaProducto.text
        val precio = textoPrecioProducto.text

        if (nombre.isBlank() || categoria.isBlank() || precio.isBlank()) {
            mostrarAlerta("Error", "Todos los campos deben estar llenos")
            return
        }

        if (imagenSeleccionada == null) {
            mostrarAlerta("Error", "Debe seleccionar una imagen")
            return
        }

        try {
            val precioDouble = precio.toDouble()
            mostrarAlerta("Éxito", "Producto guardado exitosamente")
            limpiarCampos()
        } catch (e: NumberFormatException) {
            mostrarAlerta("Error", "El precio debe ser un número válido")
        }
    }

    private fun cancelar() {
        val stage = butonCancelarProductos.scene.window as Stage
        stage.close()
    }

    private fun mostrarAlerta(titulo: String, mensaje: String) {
        val alert = Alert(AlertType.INFORMATION)
        alert.title = titulo
        alert.contentText = mensaje
        alert.showAndWait()
    }

    private fun limpiarCampos() {
        textoNombreProducto.clear()
        textoCategoriaProducto.clear()
        textoPrecioProducto.clear()
        imagenProductoAnadir.image = Image("@../../images/buscar-imagen.png")
        imagenSeleccionada = null
    }
}
