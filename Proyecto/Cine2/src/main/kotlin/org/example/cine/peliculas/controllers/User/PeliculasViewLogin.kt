package org.example.cine.peliculas.controllers.User

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

private val logger = logging()

class PeliculasViewLogin : KoinComponent {

    private val viewModel: CineViewModel by inject()
    private val peliculasStorageImages: PeliculasStorageImages by inject()

    @FXML
    private lateinit var butonHelp: Button

    @FXML
    private lateinit var butonCerrarSecion: Button

    @FXML
    private lateinit var butonComprarLogin: Button

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

    // Método para inicializar
    @FXML
    fun initialize() {
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
        // Configuración de la tabla
        tableColumnId.cellValueFactory = PropertyValueFactory("id")
        tableColumnNombre.cellValueFactory = PropertyValueFactory("nombre")
        tableColumnDuracion.cellValueFactory = PropertyValueFactory("duracion")
        tableColumnFecha.cellValueFactory = PropertyValueFactory("fechaEstreno")
    }

    private fun initBindings() {
        logger.debug { "Inicializando bindings" }

        viewModel.state.addListener { _, _, newValue ->
            logger.debug { "Actualizando datos de la vista" }

            // Actualizamos la tabla
            if (TablaPeliculas.items != newValue.peliculas) {
                TablaPeliculas.items = FXCollections.observableArrayList(newValue.peliculas)
            }

            // Formulario
            textNombrePelicula.text = newValue.pelicula.nombre
            TextDuracionPelicula.text = newValue.pelicula.duracion
            dataFechaDeEstreno.value = newValue.pelicula.fechaEstreno
            textSinopsisPelicula.text = newValue.pelicula.descripcion
            imagenPelicula.image = newValue.pelicula.imagen
        }
    }

    private fun initEventos() {
        // Configuración de eventos para los elementos de la interfaz
        butonHelp.setOnAction { onHelpAction() }
        butonCerrarSecion.setOnAction { onCerrarSecionAction() }
        butonComprarLogin.setOnAction { onComprarLoginAction() }

        // Eventos de la tabla
        TablaPeliculas.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            newValue?.let { onTablaPeliculasSelected(newValue) }
        }

        // Buscador
        TextBuscadorPeliculas.setOnKeyReleased { onBuscadorKeyReleased() }
    }

    private fun onHelpAction() {

    }

    private fun onCerrarSecionAction() {
        logger.debug { "Cerrando sesión desde la vista de películas como usuario" }
        RoutesManager.changeScene(view = RoutesManager.View.MAIN)
    }

    private fun onComprarLoginAction() {
        val selectedPelicula = TablaPeliculas.selectionModel.selectedItem
        if (selectedPelicula != null) {
            logger.debug { "Comprar película -> redirigiendo a escena productos" }
            RoutesManager.selectedPelicula = selectedPelicula
            RoutesManager.selectedImage = imagenPelicula.image
            RoutesManager.changeScene(view = RoutesManager.View.BUTACASUSUARIO)
        } else {
            showAlert("Error", "Por favor, selecciona una película para comprar.")
        }
    }

    private fun onTablaPeliculasSelected(pelicula: Pelicula) {
        viewModel.updatePeliculaSeleccionada(pelicula)
    }

    private fun onBuscadorKeyReleased() {
        val filteredList = viewModel.peliculasFilteredList(TextBuscadorPeliculas.text)
        TablaPeliculas.items = FXCollections.observableArrayList(filteredList)
    }

    private fun showAlert(titulo: String, mensaje: String) {
        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.title = titulo
        alert.headerText = null
        alert.contentText = mensaje
        alert.showAndWait()
    }

}
