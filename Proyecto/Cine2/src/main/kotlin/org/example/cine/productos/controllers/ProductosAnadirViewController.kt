package org.example.cine.productos.controllers

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.image.ImageView
import javafx.stage.Stage
import org.example.cine.productos.models.Producto
import org.example.cine.productos.viewmodels.ProductosViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

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
    }

    private fun onGuardar() {
        val nombre = textoNombreProducto.text
        val categoria = textoCategoriaProducto.text
        val precioText = textoPrecioProducto.text
        val stockText = textoStockProducto.text

        // Manejo de valores predeterminados y conversión segura
        val precio = precioText.toDoubleOrNull() ?: 0.0
        val stock = stockText.toIntOrNull() ?: 0

        val productoFormState = ProductosViewModel.ProductoFormState(
            nombre = nombre,
            categoria = Producto.Categoria.valueOf(categoria),
            precio = precio.toString(),
            stock = stock.toString()
        )

        viewModel.state.value = viewModel.state.value.copy(
            producto = productoFormState
        )

        viewModel.crearProducto(
            nombre = nombre,
            precio = precio.toString(),
            categoria = Producto.Categoria.valueOf(categoria),
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
