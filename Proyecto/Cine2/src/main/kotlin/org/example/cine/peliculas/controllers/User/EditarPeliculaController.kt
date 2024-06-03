package org.example.cine.peliculas.controllers.User

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.stage.Stage
import org.example.cine.peliculas.ViewModel.CineViewModel
import org.example.cine.peliculas.models.Pelicula
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging
import java.time.LocalDate

private val logger = logging()

class EditarPeliculaController : KoinComponent {
    private val viewModel: CineViewModel by inject()

    @FXML
    private lateinit var textEdidNombrePelicula: TextField

    @FXML
    private lateinit var textEdidDuracionPelicula: TextField

    @FXML
    private lateinit var dataEditPelicula: DatePicker

    @FXML
    private lateinit var textEditSinopsisPelicula: TextArea

    @FXML
    private lateinit var imagenEditPelicula: ImageView

    @FXML
    private lateinit var butonEditConfirmar: Button

    @FXML
    private lateinit var butonCancelarEditPelicula: Button

    private lateinit var pelicula: Pelicula

    fun setPelicula(pelicula: Pelicula) {
        this.pelicula = pelicula
        cargarDatosPelicula()
    }

    private fun cargarDatosPelicula() {
        textEdidNombrePelicula.text = pelicula.nombre
        textEdidDuracionPelicula.text = pelicula.duracion
        dataEditPelicula.value = pelicula.fechaEstreno
        textEditSinopsisPelicula.text = pelicula.descripcion

        val imageUrl = if (pelicula.imagen.startsWith("http")) {
            pelicula.imagen
        } else {
            javaClass.getResource("/org/example/cine/images/${pelicula.imagen}")?.toString()
        }

        if (imageUrl != null) {
            imagenEditPelicula.image = Image(imageUrl)
        } else {
            logger.error { "Error: No se pudo encontrar la imagen para ${pelicula.imagen}" }
            imagenEditPelicula.image =
                Image(javaClass.getResource("/org/example/cine/images/sin-imagen.png").toString())
        }
    }

    @FXML
    private fun initialize() {
        butonEditConfirmar.setOnAction { onConfirmar() }
        butonCancelarEditPelicula.setOnAction { onCancelar() }
    }

    private fun onConfirmar() {
        logger.debug { "Confirmando edición de la película: ${pelicula.nombre}" }

        val nombre = textEdidNombrePelicula.text
        val duracion = textEdidDuracionPelicula.text
        val fechaEstreno = dataEditPelicula.value ?: LocalDate.now()
        val descripcion = textEditSinopsisPelicula.text

        viewModel.updateDataPelicula(
            nombre = nombre,
            duracion = duracion,
            fechaEstreno = fechaEstreno,
            descripcion = descripcion,
            categoria = pelicula.categoria,
            imagePelicula = imagenEditPelicula.image
        )

        viewModel.editarPelicula().onSuccess {
            showAlert("Éxito", "Película actualizada correctamente.")
            val stage = butonEditConfirmar.scene.window as Stage
            stage.close()
        }.onFailure {
            showAlert("Error", "Error al actualizar la película: ${it.message}")
        }
    }

    private fun onCancelar() {
        val stage = butonCancelarEditPelicula.scene.window as Stage
        stage.close()
    }

    private fun showAlert(title: String, message: String) {
        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.title = title
        alert.headerText = null
        alert.contentText = message
        alert.showAndWait()
    }
}
