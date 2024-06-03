package org.example.cine.peliculas.controllers.User

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.image.ImageView
import javafx.scene.layout.GridPane
import org.example.cine.peliculas.ViewModel.CineViewModel
import org.example.cine.peliculas.models.Pelicula
import org.example.cine.peliculas.service.storage.PeliculasStorageImages
import org.example.cine.route.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging
import java.time.LocalDate

private val logger = logging()

class PeliculasViewLoginAdmin : KoinComponent {
    private val viewModel: CineViewModel by inject()
    private val peliculasStorageImages: PeliculasStorageImages by inject()

    @FXML
    private lateinit var butonHelp: Button

    @FXML
    private lateinit var butonCerrarSecion: Button

    @FXML
    private lateinit var butonGestionProductos: Button

    @FXML
    private lateinit var butonGestionButacas: Button

    @FXML
    private lateinit var ButonEditPelicula: Button

    @FXML
    private lateinit var ButonBorrarPelicula: Button

    @FXML
    private lateinit var ButonCrearPelicula: Button

    @FXML
    private lateinit var TablaPeliculas: TableView<Pelicula>

    @FXML
    private lateinit var tableColumnId: TableColumn<Pelicula, Long>

    @FXML
    private lateinit var tableColumnNombre: TableColumn<Pelicula, String>

    @FXML
    private lateinit var tableColumnDuracion: TableColumn<Pelicula, String>

    @FXML
    private lateinit var tableColumnFecha: TableColumn<Pelicula, String>

    @FXML
    private lateinit var imagenPelicula: ImageView

    @FXML
    private lateinit var textSinopsisPelicula: TextArea

    @FXML
    private lateinit var textNombrePelicula: TextField

    @FXML
    private lateinit var TextDuracionPelicula: TextField

    @FXML
    private lateinit var textEstadoLogin: Label

    @FXML
    private lateinit var TextBuscadorPeliculas: TextField

    @FXML
    private lateinit var dataFechaDeEstreno: DatePicker

    @FXML
    private lateinit var gridPane: GridPane

    @FXML
    fun initialize() {
        assert(::dataFechaDeEstreno.isInitialized) { "dataFechaDeEstreno no está inicializada" }
        assert(::textNombrePelicula.isInitialized) { "textNombrePelicula no está inicializada" }

        initDefaultValues()
        initBindings()
        initEventos()
        loadData()
    }

    private fun loadData() {
        logger.debug { "Cargando datos iniciales" }
        viewModel.loadAllPeliculas()
    }

    private fun initDefaultValues() {
        tableColumnId.cellValueFactory = PropertyValueFactory("id")
        tableColumnNombre.cellValueFactory = PropertyValueFactory("nombre")
        tableColumnDuracion.cellValueFactory = PropertyValueFactory("duracion")
        tableColumnFecha.cellValueFactory = PropertyValueFactory("fechaEstreno")
    }

    private fun initBindings() {
        logger.debug { "Inicializando bindings" }

        viewModel.state.addListener { _, _, newValue ->
            logger.debug { "Actualizando datos de la vista" }

            if (TablaPeliculas.items != newValue.peliculas) {
                TablaPeliculas.items = FXCollections.observableArrayList(newValue.peliculas)
            }

            textNombrePelicula.text = newValue.pelicula.nombre
            TextDuracionPelicula.text = newValue.pelicula.duracion
            dataFechaDeEstreno.value = newValue.pelicula.fechaEstreno
            textSinopsisPelicula.text = newValue.pelicula.descripcion
            imagenPelicula.image = newValue.pelicula.imagen
        }
    }

    private fun initEventos() {
        butonHelp.setOnAction { onHelpAction() }
        butonCerrarSecion.setOnAction { onCerrarSecionAction() }
        butonGestionProductos.setOnAction { onGestionProductosAction() }
        butonGestionButacas.setOnAction { onGestionButacasAction() }
        ButonCrearPelicula.setOnAction { onCrearPeliculaAction() }
        ButonEditPelicula.setOnAction { onEditPeliculaAction() }
        ButonBorrarPelicula.setOnAction { onBorrarPeliculaAction() }

        TablaPeliculas.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            newValue?.let { onTablaPeliculasSelected(newValue) }
        }

        TextBuscadorPeliculas.setOnKeyReleased { onBuscadorKeyReleased() }
    }

    private fun onHelpAction() {
        showAlert("Ayuda", "Aquí va la ayuda de la aplicación.")
    }

    private fun onCerrarSecionAction() {
        logger.debug { "cerrando sesión de vista de películas administrador" }
        RoutesManager.changeScene(view = RoutesManager.View.MAIN)
    }

    private fun onGestionProductosAction() {
        logger.debug { "Gestionando productos -> redirigiendo a vista de productos como admin" }
        RoutesManager.changeScene(view = RoutesManager.View.PRODUCTOSADMIN)
    }

    private fun onGestionButacasAction() {
        RoutesManager.changeScene(view = RoutesManager.View.BUTACASADMIN)
    }

    private fun onCrearPeliculaAction() {
        logger.debug { "Creando nueva película" }
        val nuevaPelicula = Pelicula(
            id = Pelicula.NEW_PELICULA,
            nombre = "",
            duracion = "",
            fechaEstreno = LocalDate.now(),
            descripcion = "",
            categoria = Pelicula.Categoria.TERROR,
            imagen = "images/sin-imagen.png"
        )
        RoutesManager.crearPelicula(nuevaPelicula) { loadData() }
    }

    private fun onEditPeliculaAction() {
        val pelicula = TablaPeliculas.selectionModel.selectedItem
        if (pelicula != null) {
            logger.debug { "Editando película: ${pelicula.nombre}" }
            RoutesManager.editarPelicula(pelicula)
        } else {
            showAlert("Error", "Por favor, selecciona una película para editar.")
        }
    }

    private fun onBorrarPeliculaAction() {
        val pelicula = TablaPeliculas.selectionModel.selectedItem
        if (pelicula != null) {
            val alert = Alert(Alert.AlertType.CONFIRMATION)
            alert.title = "Confirmar eliminación"
            alert.headerText = null
            alert.contentText = "¿Estás seguro de que deseas eliminar la película \"${pelicula.nombre}\"?"
            val result = alert.showAndWait()

            if (result.isPresent && result.get() == ButtonType.OK) {
                viewModel.eliminarPelicula(pelicula)
                    .onSuccess {
                        showAlert("Éxito", "Película eliminada correctamente.")
                    }
                    .onFailure {
                        showAlert("Error", "Error al eliminar la película: ${it.message}")
                    }
            }
        } else {
            showAlert("Error", "Por favor, selecciona una película para eliminar.")
        }
    }

    private fun onTablaPeliculasSelected(pelicula: Pelicula) {
        viewModel.updatePeliculaSeleccionada(pelicula)
    }

    private fun onBuscadorKeyReleased() {
        val filteredList = viewModel.peliculasFilteredList(TextBuscadorPeliculas.text)
        TablaPeliculas.items = FXCollections.observableArrayList(filteredList)
    }

    private fun showAlert(title: String, message: String) {
        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.title = title
        alert.headerText = null
        alert.contentText = message
        alert.showAndWait()
    }
}