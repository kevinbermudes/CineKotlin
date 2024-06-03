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
import org.example.cine.route.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging
import java.time.LocalDate

private val logger = logging()

class CrearPeliculaController : KoinComponent {
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
    private lateinit var butonCrearConfirmar: Button

    @FXML
    private lateinit var butonCancelarCrearPelicula: Button

    private lateinit var pelicula: Pelicula

    @FXML
    fun initialize() {
        butonCrearConfirmar.setOnAction { onConfirmar() }
        butonCancelarCrearPelicula.setOnAction { onCancel() }
    }

    fun setPelicula(pelicula: Pelicula) {
        this.pelicula = pelicula
        cargarDatosPelicula()
    }

    private fun cargarDatosPelicula() {
        textEdidNombrePelicula.text = pelicula.nombre
        textEdidDuracionPelicula.text = pelicula.duracion
        dataEditPelicula.value = pelicula.fechaEstreno
        textEditSinopsisPelicula.text = pelicula.descripcion
        imagenEditPelicula.image = Image(RoutesManager.getResourceAsStream(pelicula.imagen))
    }

    @FXML
    private fun onConfirmar() {
        val fechaEstreno = dataEditPelicula.value
        if (fechaEstreno != null && fechaEstreno.isAfter(LocalDate.now())) {
            val peliculaFormState = CineViewModel.PeliculaFormState(
                nombre = textEdidNombrePelicula.text,
                duracion = textEdidDuracionPelicula.text,
                fechaEstreno = fechaEstreno,
                descripcion = textEditSinopsisPelicula.text,

                )
            viewModel.state.value = viewModel.state.value.copy(
                pelicula = peliculaFormState
            )
            viewModel.crearPelicula()
                .onSuccess {
                    showAlert("Éxito", "Película creada correctamente.")
                    closeCurrentStage()
                }
                .onFailure {
                    showAlert("Error", "Error al crear película: ${it.message}")
                }
        } else {
            showAlert("Error", "La fecha de estreno debe ser posterior a la fecha actual.")
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
        val stage = butonCrearConfirmar.scene.window as Stage
        stage.close()
    }
}
