package org.example.cine.pago.controllers

import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import org.example.cine.pago.models.Carrito
import org.example.cine.route.RoutesManager
import org.koin.core.component.KoinComponent
import org.lighthousegames.logging.logging

private val logger = logging()

class TicketCompraController : KoinComponent {

    @FXML
    private lateinit var butonInicio: Button

    @FXML
    private lateinit var buttonImprimir: Button

    @FXML
    private lateinit var textNombrePelicula: TextField

    @FXML
    private lateinit var textoPrecioEntrada: TextField

    @FXML
    private lateinit var textoButacas: TextField

    @FXML
    private lateinit var textoComplementos: TextArea

    @FXML
    private lateinit var textoPrecioComplementos: TextField

    @FXML
    private lateinit var textTotalApagar: TextField

    @FXML
    private lateinit var imagenCine: ImageView

    private val carrito: Carrito = Carrito.instance

    @FXML
    fun initialize() {
        initEventHandlers()
        configureTextArea()
        loadTicketData()
        cargarImagenCine()
    }

    private fun configureTextArea() {
        textoComplementos.isWrapText = true // Enable line wrapping
        textoComplementos.prefHeight = 100.0 // Set preferred height
    }

    private fun initEventHandlers() {
        butonInicio.setOnAction { volverInicio() }
        buttonImprimir.setOnAction { imprimirTicket() }
    }

    private fun loadTicketData() {
        textNombrePelicula.text =
            "Nombre de la Película"  // Puedes actualizarlo con el nombre real si lo tienes disponible
        val precioEntradas = carrito.butacas.sumOf { it.precio }
        val precioComplementos = carrito.productos.sumOf { it.precio }

        textoPrecioEntrada.text = precioEntradas.toString()
        textoButacas.text = carrito.butacas.joinToString(", ") { it.id }
        textoComplementos.text =
            carrito.productos.joinToString("\n") { it.nombre } // Use "\n" to separate each complement with a new line
        textoPrecioComplementos.text = precioComplementos.toString()

        // Calcular el total a pagar y establecerlo en el campo correspondiente
        val totalAPagar = precioEntradas + precioComplementos
        textTotalApagar.text = totalAPagar.toString()
    }

    private fun cargarImagenCine() {
        val image = Image(javaClass.getResourceAsStream("/org/example/cine/images/Cine.png"))
        imagenCine.image = image
    }

    private fun volverInicio() {
        RoutesManager.changeScene(view = RoutesManager.View.USUARIOINDEX)
    }

    private fun imprimirTicket() {
        // Lógica para imprimir el ticket
        showAlert("Imprimir", "El ticket se ha enviado a la impresora.")
    }

    private fun showAlert(titulo: String, mensaje: String) {
        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.title = titulo
        alert.headerText = null
        alert.contentText = mensaje
        alert.showAndWait()
    }
}
