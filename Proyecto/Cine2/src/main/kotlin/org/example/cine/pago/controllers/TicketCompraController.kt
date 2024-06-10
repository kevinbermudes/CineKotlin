package org.example.cine.pago.controllers

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import javafx.stage.FileChooser
import org.example.cine.pago.models.Carrito
import org.example.cine.pago.models.Venta
import org.example.cine.pago.repositories.VentasRepository
import org.example.cine.route.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.file.Files
import java.time.Instant
import java.time.LocalDateTime
import java.util.Base64
import javax.imageio.ImageIO

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

    @FXML
    private lateinit var ticketPane: Pane

    private val carrito: Carrito = Carrito.instance
    private val ventasRepository: VentasRepository by inject()

    @FXML
    fun initialize() {
        initEventHandlers()
        configureTextArea()
        loadTicketData()
    }

    private fun configureTextArea() {
        textoComplementos.isWrapText = true
        textoComplementos.prefHeight = 100.0
    }

    private fun initEventHandlers() {
        butonInicio.setOnAction {
            val totalAPagar = textTotalApagar.text.toDouble()
            registrarVenta(totalAPagar)
            volverInicio()
        }
        buttonImprimir.setOnAction {
            val totalAPagar = textTotalApagar.text.toDouble()
            registrarVenta(totalAPagar)
            imprimirTicket()
        }
    }

    private fun loadTicketData() {
        val selectedPelicula = RoutesManager.selectedPelicula
        textNombrePelicula.text = selectedPelicula?.nombre ?: "Nombre de la Película"
        val precioEntradas = carrito.butacas.sumOf { it.precio }
        val precioComplementos = carrito.productos.sumOf { it.precio }

        textoPrecioEntrada.text = precioEntradas.toString()
        textoButacas.text = carrito.butacas.joinToString(", ") { it.id }
        textoComplementos.text = carrito.productos.joinToString("\n") { it.nombre }
        textoPrecioComplementos.text = precioComplementos.toString()

        val totalAPagar = precioEntradas + precioComplementos
        textTotalApagar.text = totalAPagar.toString()

        val selectedImage = RoutesManager.selectedImage
        if (selectedImage != null) {
            imagenCine.image = selectedImage
        } else {
            cargarImagenCine()
        }
    }

    private fun registrarVenta(total: Double) {
        val venta = Venta(
            pelicula = textNombrePelicula.text,
            precioEntrada = textoPrecioEntrada.text.toDouble(),
            butacas = textoButacas.text,
            complementos = textoComplementos.text,
            precioComplementos = textoPrecioComplementos.text.toDouble(),
            total = total,
        )
        logger.debug { "*********************************************Registrando venta: $venta" }
        ventasRepository.save(venta)
    }

    private fun cargarImagenCine() {
        val image = Image(javaClass.getResourceAsStream("/org/example/cine/images/Cine.png"))
        imagenCine.image = image
    }

    private fun volverInicio() {
        RoutesManager.changeScene(view = RoutesManager.View.USUARIOINDEX)
    }

    private fun imprimirTicket() {
        val fileChooser = FileChooser()
        fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("PDF Files", "*.pdf"))
        val file = fileChooser.showSaveDialog(buttonImprimir.scene.window)
        if (file != null) {
            createPdf(file)
            showAlert("Imprimir", "El ticket se ha guardado como PDF.")
        }
    }

    private fun createPdf(file: File) {
        val htmlContent = generateHtmlContent()
        val outputPath = file.toPath()

        try {
            Files.newOutputStream(outputPath).use { os ->
                val builder = PdfRendererBuilder()
                builder.withHtmlContent(htmlContent, "")
                builder.toStream(os)
                builder.run()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun encodeImageToBase64(imagePath: String): String {
        javaClass.getResourceAsStream(imagePath).use { inputStream ->
            val bufferedImage = ImageIO.read(inputStream)
            val outputStream = ByteArrayOutputStream()
            ImageIO.write(bufferedImage, "png", outputStream)
            return Base64.getEncoder().encodeToString(outputStream.toByteArray())
        }
    }

    private fun generateHtmlContent(): String {
        val imagePath = "/org/example/cine/images/Cine.png"
        val encodedImage = encodeImageToBase64(imagePath)
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8"/>
                <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                <style>
                    body { font-family: Arial, sans-serif; }
                    .container { width: 80%; margin: auto; }
                    h1 { text-align: center; }
                    .section { margin: 20px 0; }
                    .section b { display: inline-block; width: 150px; }
                    .section p { display: inline-block; }
                    .image-container { text-align: center; margin: 20px 0; }
                    .center-image { display: block; margin-left: auto; margin-right: auto; width: 50%; }
                </style>
                <title>Ticket de Compra</title>
            </head>
            <body>
                <div class="container">
                    <h1>Ticket de Compra</h1>
                    <div class="image-container">
                        <img src="data:image/png;base64,$encodedImage" alt="Cine" class="center-image"/>
                    </div>
                    <div class="section">
                        <b>Nombre de la Película:</b> <p>${textNombrePelicula.text}</p>
                    </div>
                    <div class="section">
                        <b>Precio de Entrada:</b> <p>${textoPrecioEntrada.text}</p>
                    </div>
                    <div class="section">
                        <b>Butacas:</b> <p>${textoButacas.text}</p>
                    </div>
                    <div class="section">
                        <b>Complementos:</b> <p>${textoComplementos.text.replace("\n", "<br />")}</p>
                    </div>
                    <div class="section">
                        <b>Precio de Complementos:</b> <p>${textoPrecioComplementos.text}</p>
                    </div>
                    <div class="section">
                        <b>Total a Pagar:</b> <p>${textTotalApagar.text}</p>
                    </div>
                </div>
            </body>
            </html>
        """.trimIndent()
    }

    private fun showAlert(titulo: String, mensaje: String) {
        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.title = titulo
        alert.headerText = null
        alert.contentText = mensaje
        alert.showAndWait()
    }
}
