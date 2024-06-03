package org.example.cine.productos.controllers

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.stage.Stage
import org.example.cine.productos.models.Producto
import org.example.cine.productos.viewmodels.ProductosViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

class ProductosEditarViewController : KoinComponent {
    private val viewModel: ProductosViewModel by inject()

    @FXML
    private lateinit var textoNombreProducto: TextField

    @FXML
    private lateinit var textoCategoriaProducto: TextField

    @FXML
    private lateinit var textoPrecioProducto: TextField

    @FXML
    private lateinit var textoStockProducto: TextField

    @FXML
    private lateinit var imagenProductoEditar: ImageView

    @FXML
    private lateinit var butonGuardarEdicionProducto: Button

    @FXML
    private lateinit var butonCancelarProductos1: Button

    private lateinit var producto: Producto

    fun setProducto(producto: Producto) {
        this.producto = producto
        cargarDatosProducto()
    }

    private fun cargarDatosProducto() {
        textoNombreProducto.text = producto.nombre
        textoCategoriaProducto.text = producto.categoria.name
        textoPrecioProducto.text = producto.precio.toString()
        textoStockProducto.text = producto.stock.toString()

        val imageUrl = if ((producto.imagen as String).startsWith("http")) {
            producto.imagen as String
        } else {
            javaClass.getResource("/org/example/cine/images/${producto.imagen}")?.toString()
        }

        if (imageUrl != null) {
            imagenProductoEditar.image = Image(imageUrl)
        } else {
            logger.error { "Error: No se pudo encontrar la imagen para ${producto.imagen}" }
            imagenProductoEditar.image =
                Image(javaClass.getResource("/org/example/cine/images/sin-imagen.png").toString())
        }
    }

    @FXML
    private fun initialize() {
        butonGuardarEdicionProducto.setOnAction { onConfirmar() }
        butonCancelarProductos1.setOnAction { onCancelar() }
    }

    private fun onConfirmar() {
        logger.debug { "Confirmando edición del producto: ${producto.nombre}" }

        val nombre = textoNombreProducto.text
        val categoria = textoCategoriaProducto.text
        val precio = textoPrecioProducto.text
        val stock = textoStockProducto.text

        if (nombre.isBlank() || categoria.isBlank() || precio.isBlank() || stock.isBlank()) {
            showAlert("Error", "Todos los campos son obligatorios y el precio y stock deben ser números válidos.")
            return
        }

        val categoriaEnum = try {
            Producto.Categoria.valueOf(categoria.uppercase())
        } catch (e: IllegalArgumentException) {
            showAlert("Error", "La categoría no es válida.")
            return
        }

        viewModel.updateDataProducto(
            nombre = nombre,
            precio = precio,
            categoria = categoriaEnum,
            imagen = imagenProductoEditar.image, // Pasamos la imagen directamente
            stock = stock
        )

        viewModel.editarProducto().onSuccess {
            showAlert("Éxito", "Producto actualizado correctamente.")
            val stage = butonGuardarEdicionProducto.scene.window as Stage
            stage.close()
        }.onFailure {
            showAlert("Error", "Error al actualizar el producto: ${it.message}")
        }
    }

    private fun onCancelar() {
        val stage = butonCancelarProductos1.scene.window as Stage
        stage.close()
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
