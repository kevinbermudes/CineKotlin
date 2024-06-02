package org.example.cine.pago.controllers

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.TextField
import org.example.cine.pago.models.Carrito
import org.example.cine.peliculas.service.storage.ButacasStorageJsonImpl
import org.example.cine.productos.viewmodels.ProductosViewModel
import org.example.cine.route.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging
import java.io.File

private val logger = logging()

class PagoController : KoinComponent {

    @FXML
    private lateinit var butonTerminarPago: Button

    @FXML
    private lateinit var butonAtrasPago: Button

    @FXML
    private lateinit var buttonCancelar: Button

    @FXML
    private lateinit var textoNumeroTarjeta: TextField

    @FXML
    private lateinit var textoTitular: TextField

    @FXML
    private lateinit var textoCodigo: TextField

    @FXML
    private lateinit var textoAno: TextField

    @FXML
    private lateinit var textoMes: TextField

    @FXML
    private lateinit var textoprecioComplementos: TextField

    @FXML
    private lateinit var textoprecioEntrada: TextField

    @FXML
    private lateinit var textoprecioTotal: TextField

    private val carrito: Carrito = Carrito.instance
    private val viewModel: ProductosViewModel by inject()
    private val butacasStorage: ButacasStorageJsonImpl by inject()

    @FXML
    fun initialize() {
        initEventHandlers()
        calcularPrecios()
    }

    private fun initEventHandlers() {
        butonTerminarPago.setOnAction { terminarPago() }
        butonAtrasPago.setOnAction { volverAProductos() }
        buttonCancelar.setOnAction { cancelarCompra() }
    }

    private fun calcularPrecios() {
        val precioComplementos = carrito.productos.sumOf { it.precio }
        val precioEntradas = carrito.butacas.sumOf { it.precio }
        val precioTotal = precioComplementos + precioEntradas

        textoprecioComplementos.text = precioComplementos.toString()
        textoprecioEntrada.text = precioEntradas.toString()
        textoprecioTotal.text = precioTotal.toString()
    }

    private fun terminarPago() {
        if (validarTarjeta()) {
            // Guardar productos en la base de datos
            carrito.productos.forEach { producto ->
                producto.reduceStock()
                viewModel.saveProduct(producto)
            }

            // Actualizar JSON de butacas
            actualizarButacasEnJson()

            showAlert("Compra realizada", "Tu compra ha sido realizada con éxito.")
            RoutesManager.changeScene(view = RoutesManager.View.TICKET)
        }
    }

    private fun validarTarjeta(): Boolean {
        val numeroTarjeta = textoNumeroTarjeta.text
        val titular = textoTitular.text
        val codigo = textoCodigo.text
        val ano = textoAno.text
        val mes = textoMes.text

        if (numeroTarjeta.length != 16 || !numeroTarjeta.all { it.isDigit() }) {
            showAlert("Error de validación", "El número de la tarjeta no es válido.")
            return false
        }
        if (titular.isBlank()) {
            showAlert("Error de validación", "El nombre del titular no puede estar vacío.")
            return false
        }
        if (codigo.length != 3 || !codigo.all { it.isDigit() }) {
            showAlert("Error de validación", "El código de seguridad no es válido.")
            return false
        }
        if (ano.length != 4 || !ano.all { it.isDigit() }) {
            showAlert("Error de validación", "El año no es válido.")
            return false
        }
        if (mes.toIntOrNull() == null || mes.toInt() !in 1..12) {
            showAlert("Error de validación", "El mes no es válido.")
            return false
        }

        return true
    }

    private fun volverAProductos() {
        RoutesManager.changeScene(view = RoutesManager.View.PRODUCTOSUSUARIOS)
    }

    private fun cancelarCompra() {
        // Devolver productos al stock
        carrito.productos.forEach { producto ->
            producto.increaseStock()
            viewModel.saveProduct(producto)
        }

        // Limpiar el carrito
        carrito.butacas.clear()
        carrito.productos.clear()

        showAlert("Compra cancelada", "Tu compra ha sido cancelada.")
        RoutesManager.changeScene(view = RoutesManager.View.USUARIOINDEX)
    }

    private fun showAlert(title: String, message: String) {
        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.title = title
        alert.headerText = null
        alert.contentText = message
        alert.showAndWait()
    }

    private fun actualizarButacasEnJson() {
        val butacasFile = File("src/main/resources/butacas.json")

        if (butacasFile.exists()) {
            butacasStorage.loadDataJson(butacasFile).onSuccess { butacas ->
                val butacasActualizadas = butacas.map { butaca ->
                    if (carrito.butacas.any { it.id == butaca.id }) {
                        butaca.copy(estado = "ocupado", imagen = "ocupado.png")
                    } else {
                        butaca
                    }
                }

                butacasStorage.storeDataJson(butacasFile, butacasActualizadas).onFailure {
                    logger.error { "Error al guardar las butacas actualizadas: ${it.message}" }
                }
            }.onFailure {
                logger.error { "Error al cargar las butacas: ${it.message}" }
            }
        } else {
            logger.debug { "El archivo butacas.json no existe." }
        }
    }
}