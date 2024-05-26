package org.example.cine.peliculas.controllers

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.image.ImageView
import javafx.scene.layout.GridPane
import javafx.scene.layout.Pane
import javafx.stage.Stage
import org.example.cine.peliculas.ViewModel.CineViewModel
import org.example.cine.peliculas.models.Pelicula
import org.example.cine.route.RoutesManager
import org.koin.core.component.KoinComponent
import org.lighthousegames.logging.logging
import org.koin.core.component.inject

private val logger = logging()

class PeliculasViewNoLogin : KoinComponent {

    // Inyectamos nuestro ViewModel
    private val viewModel: CineViewModel by inject()

    // Define las propiedades enlazadas a los elementos del FXML
    // Botones
    @FXML
    private lateinit var butonHelp: Button

    @FXML
    private lateinit var butonLogin: Button

    @FXML
    private lateinit var butonComprarNoLogin: Button

    // Tabla
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

    // Imagen
    @FXML
    private lateinit var imagenPelicula: ImageView

    // TextAreas
    @FXML
    private lateinit var textSinopsisPelicula: TextArea

    // TextFields
    @FXML
    private lateinit var textNombrePelicula: TextField

    @FXML
    private lateinit var TextDuracionPelicula: TextField

    @FXML
    private lateinit var textEstadoLogin: Label

    // Buscadores
    @FXML
    private lateinit var TextBuscadorPeliculas: TextField

    // DatePicker
    @FXML
    private lateinit var dataFechaDeEstreno: DatePicker

    // GridPane
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

    private fun initDefaultValues() {
        logger.debug { "Inicializando valores por defecto de la tabla" }
        // Configuración de la tabla
        tableColumnId.cellValueFactory = PropertyValueFactory("id")
        tableColumnNombre.cellValueFactory = PropertyValueFactory("nombre")
        tableColumnDuracion.cellValueFactory = PropertyValueFactory("duracion")
        tableColumnFecha.cellValueFactory = PropertyValueFactory("fechaEstreno")
    }
    private fun loadData() {
        logger.debug { "Cargando datos iniciales" }
        viewModel.loadAllPeliculas()
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
        butonLogin.setOnAction { onLoginAction() }
        butonComprarNoLogin.setOnAction { onComprarNoLoginAction() }

        // Eventos de la tabla
        TablaPeliculas.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            newValue?.let { onTablaPeliculasSelected(newValue) }
        }

        // Buscador
        TextBuscadorPeliculas.setOnKeyReleased { onBuscadorKeyReleased() }
    }

    private fun onHelpAction() {
        // Lógica para el botón Help
    }

    private fun onLoginAction() {
       logger.debug { "Iniciando sesión" }
        // Lógica para el botón Login
//RoutesManager.initLoginStage()
        RoutesManager.changeScene(view =RoutesManager.View.LOGIN)
    }
    private fun onComprarNoLoginAction() {
        // Lógica para el botón Comprar No Login
        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.title = "Información"
        alert.headerText = "Acción no permitida"
        alert.contentText = "Necesitas estar logado para poder comprar una entrada."
        alert.showAndWait()
    }

    private fun onTablaPeliculasSelected(pelicula: Pelicula) {
        // Lógica para manejar la selección de una película en la tabla
        viewModel.updatePeliculaSeleccionada(pelicula)

    }

    private fun onBuscadorKeyReleased() {
        // Lógica para manejar la búsqueda de películas
        val filteredList = viewModel.peliculasFilteredList(TextBuscadorPeliculas.text)
        TablaPeliculas.items = FXCollections.observableArrayList(filteredList)
    }
}
