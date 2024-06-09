package org.example.cine.productos.controllers

import com.github.michaelbull.result.*
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.image.ImageView
import javafx.stage.FileChooser
import javafx.stage.Stage
import org.example.cine.productos.models.Producto
import org.example.cine.productos.viewmodels.ProductosViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging
import java.io.File

private val logger = logging()

class ProductosAnadirViewController : KoinComponent {
    private val viewModel: ProductosViewModel by inject()

    @FXML
    private lateinit var imagenProductoAnadir: ImageView

    @FXML
    private lateinit var textoNombreProducto: TextField

    @FXML
    private lateinit var textoCategoriaProducto: TextField

    @FXML
    private lateinit var textoPrecioProducto: TextField

    @FXML
    private lateinit var textoStockProducto: TextField

    @FXML
    private lateinit var butonGuardarProductos: Button

    @FXML
    private lateinit var butonCancelarProductos: Button

    @FXML
    fun initialize() {
        butonGuardarProductos.setOnAction { onGuardar() }
        butonCancelarProductos.setOnAction { onCancel() }
        imagenProductoAnadir.setOnMouseClicked { onImageAction() }
    }

    private fun onGuardar() {
        val nombre = textoNombreProducto.text
        val categoriaText = textoCategoriaProducto.text
        val precioText = textoPrecioProducto.text
        val stockText = textoStockProducto.text

        // Validar que los campos no sean nulos o vacíos
        if (nombre.isNullOrEmpty() || categoriaText.isNullOrEmpty() || precioText.isNullOrEmpty() || stockText.isNullOrEmpty()) {
            showAlert("Error", "Todos los campos deben ser llenados.")
            return
        }

        val precio = precioText.toDoubleOrNull() ?: 0.0
        val stock = stockText.toIntOrNull() ?: 0

        val categoria = try {
            Producto.Categoria.valueOf(categoriaText)
        } catch (e: IllegalArgumentException) {
            showAlert("Error", "Categoría inválida.")
            return
        }

        val productoFormState = ProductosViewModel.ProductoFormState(
            nombre = nombre,
            categoria = categoria,
            precio = precio.toString(),
            stock = stock.toString()
        )

        viewModel.state.value = viewModel.state.value.copy(
            producto = productoFormState
        )

        viewModel.crearProducto(
            nombre = nombre,
            precio = precio.toString(),
            categoria = categoria,
            stock = stock.toLong(),
        ).onSuccess {
            showAlert("Éxito", "Producto añadido correctamente.")
            closeCurrentStage()
        }.onFailure {
            showAlert("Error", "Error al añadir producto: ${it.message}")
        }
    }

    private fun onCancel() {
        closeCurrentStage()
    }

    private fun onImageAction() {
        FileChooser().run {
            title = "Selecciona una imagen"
            extensionFilters.addAll(FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"))
            showOpenDialog(null)
        }?.let {
            viewModel.updateImageProducto(it)
        }
    }

    private fun showAlert(title: String, message: String) {
        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.title = title
        alert.headerText = null
        alert.contentText = message
        alert.showAndWait()
    }

    private fun closeCurrentStage() {
        val stage = butonGuardarProductos.scene.window as Stage
        stage.close()
    }
}
