package org.example.cine.productos.controllers

import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import org.example.cine.productos.viewmodels.ProductosViewModel
import org.example.cine.route.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()
class ProductosEditarViewController : KoinComponent {
    private val viewModel: ProductosViewModel by inject()
    @FXML
    private lateinit var textoStockProducto: TextField

    @FXML
    private lateinit var imagenProductoEditar: ImageView

    @FXML
    private lateinit var textoNombreProducto: TextField

    @FXML
    private lateinit var textoCategoriaProducto: TextField

    @FXML
    private lateinit var textoPrecioProducto: TextField

    @FXML
    private lateinit var butonGuardarEdicionProducto: Button

    @FXML
    private lateinit var butonLimpiarProductos: Button

    @FXML
    private lateinit var butonCancelarProductos1: Button

    @FXML
    private lateinit var mensajeEstado: Label

    @FXML
    private fun initialize() {
        logger.debug { "Iniciando edición de productos..." }

        butonGuardarEdicionProducto.setOnAction { guardarEdicionProducto() }
        butonLimpiarProductos.setOnAction { limpiarCampos() }
        butonCancelarProductos1.setOnAction { cancelarEdicion() }
    }

    @FXML
    private fun guardarEdicionProducto() {
        val nombre: String = textoNombreProducto.getText()
        val categoria: String = textoCategoriaProducto.getText()
        val precio: String = textoPrecioProducto.getText()
        val stock: String = textoStockProducto.getText()
        val imagen: Image = imagenProductoEditar.image

        if (nombre.isEmpty() || categoria.isEmpty() || precio.isEmpty() ||stock.isEmpty()) {
            showAlert("Error", "Por favor, complete todos los campos.")
        } else {
            logger.debug { "Guardando producto: Nombre=$nombre, Categoría=$categoria, Precio=$precio, Stock=$stock" }
            showAlert("Edición guardada", "La edición se ha guardado correctamente.")
        }
    }

    @FXML
    private fun limpiarCampos() {
        textoNombreProducto.clear()
        textoCategoriaProducto.clear()
        textoPrecioProducto.clear()
        textoStockProducto.clear()

        logger.debug { "Limpiando campos de edición de productos..." }
        showAlert("Campos limpiados", "Los campos se han limpiado correctamente.")

    }

    @FXML
    private fun cancelarEdicion() {
        logger.debug { "Cancelando edición de productos..." }
        clearForm()
        viewModel.clearState()
        RoutesManager.changeScene(view = RoutesManager.View.PRODUCTOSADMIN)
    }

    private fun clearForm() {
        textoNombreProducto.clear()
        textoCategoriaProducto.clear()
        textoPrecioProducto.clear()
        textoStockProducto.clear()
        imagenProductoEditar.image = null
    }

    private fun showAlert(title: String, message: String) {
        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.title = title
        alert.headerText = null
        alert.contentText = message
        alert.showAndWait()
    }
}