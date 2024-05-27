package org.example.cine.peliculas.controllers.User

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.image.ImageView
import javafx.scene.layout.GridPane
import org.example.cine.peliculas.ViewModel.CineViewModel
import org.example.cine.peliculas.models.Pelicula
import org.example.cine.route.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

class PeliculasViewLoginAdmin : KoinComponent {
    // Inyectamos nuestro ViewModel
    private val viewModel: CineViewModel by inject()

    // Define las propiedades enlazadas a los elementos del FXML
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
    private lateinit var tableColumnId: TableColumn<Pelicula, String>

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
        // Verificar que todas las propiedades lateinit se inicialicen correctamente
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
        butonHelp.setOnAction { onHelpAction() }
        butonCerrarSecion.setOnAction { onCerrarSecionAction() }
        butonGestionProductos.setOnAction { onGestionProductosAction() }
        butonGestionButacas.setOnAction { onGestionButacasAction() }
        ButonCrearPelicula.setOnAction { onCrearPeliculaAction() }
        ButonEditPelicula.setOnAction { onEditPeliculaAction() }
        ButonBorrarPelicula.setOnAction { onBorrarPeliculaAction() }

        // Eventos de la tabla
        TablaPeliculas.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            newValue?.let { onTablaPeliculasSelected(newValue) }
        }

        // Buscador
        TextBuscadorPeliculas.setOnKeyReleased { onBuscadorKeyReleased() }
    }

    private fun onHelpAction() {
        // Lógica para el botón Help
        showAlert("Ayuda", "Aquí va la ayuda de la aplicación.")
    }

    private fun onCerrarSecionAction() {
        logger.debug { "cerrando cesion de vista de peliculas administrador" }
        RoutesManager.changeScene(view = RoutesManager.View.MAIN)
    }

    private fun onGestionProductosAction() {
        // Lógica para gestionar productos
        logger.debug { "Gestionando productos -> redirigiendo a vista de productos como admin" }
        RoutesManager.changeScene(view = RoutesManager.View.PRODUCTOSADMIN)
    }

    private fun onGestionButacasAction() {
        // Lógica para gestionar butacas
        RoutesManager.changeScene(view = RoutesManager.View.BUTACASADMIN)
    }

    private fun onCrearPeliculaAction() {
        // Lógica para crear una nueva película
        showAlert("Crear Película", "Función para crear una nueva película.")
    }

    private fun onEditPeliculaAction() {
        // Lógica para editar una película
        showAlert("Editar Película", "Función para editar una película existente.")
    }

    private fun onBorrarPeliculaAction() {
        // Lógica para borrar una película
        showAlert("Borrar Película", "Función para borrar una película.")
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

    private fun showAlert(title: String, message: String) {
        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.title = title
        alert.headerText = null
        alert.contentText = message
        alert.showAndWait()
    }
}
