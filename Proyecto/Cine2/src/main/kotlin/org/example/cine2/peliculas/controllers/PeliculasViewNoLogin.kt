package org.example.cine2.peliculas.controllers

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.image.ImageView
import javafx.scene.layout.GridPane
import org.example.cine2.peliculas.ViewModel.CineViewModel
import org.example.cine2.peliculas.models.Pelicula
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
    private lateinit var butonCerrarSecion: Button

    @FXML
    private lateinit var butonGestionProductos: Button

    @FXML
    private lateinit var ButonEditPelicula: Button

    @FXML
    private lateinit var ButonBorrarPelicula: Button

    @FXML
    private lateinit var ButonCrearPelicula: Button

    @FXML
    private lateinit var butonGestionButacas: Button

    // Tabla
    @FXML
    private lateinit var TablaPeliculas: TableView<Pelicula>  // Reemplaza 'Any' con tu modelo de datos

    @FXML
    private lateinit var tableColumnId: TableColumn<Pelicula, String>

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

    //Buscadores
    @FXML
    private lateinit var TextBuscadorPeliculas: TextField

    // DatePicker
    @FXML
    private lateinit var datePickerFecha: DatePicker

    // GridPane
    @FXML
    private lateinit var gridPane: GridPane

    // Método para inicializar
    @FXML
    fun initialize() {
        // Inicializa valores por defecto y enlaces
        initDefaultValues()
        initBindings()
        initEventos()
    }

    private fun initDefaultValues() {
        // Inicializa los valores por defecto
//        comboTipo.items = FXCollections.observableArrayList(/* lista de valores predeterminados */)
//        comboTipo.selectionModel.selectFirst()

        // Configuración de la tabla
        tableColumnId.cellValueFactory = PropertyValueFactory("id")
        tableColumnNombre.cellValueFactory = PropertyValueFactory("nombre")
        tableColumnDuracion.cellValueFactory = PropertyValueFactory("duracion")
        tableColumnFecha.cellValueFactory = PropertyValueFactory("fecha")
    }

    private fun initBindings() {
        // Enlaces para actualizar la interfaz en función del estado del modelo de vista
        viewModel.state.addListener { _, _, newValue ->
            // Actualiza los elementos de la interfaz
            TablaPeliculas.items = FXCollections.observableArrayList(newValue.peliculas)
          //  textEstadoLogin.text = newValue.estadoLogin

            // Formulario
            textNombrePelicula.text = newValue.pelicula.nombre
            TextDuracionPelicula.text = newValue.pelicula.duracion
            datePickerFecha.value = newValue.pelicula.fechaEstreno
            textSinopsisPelicula.text = newValue.pelicula.descripcion
            imagenPelicula.image = newValue.pelicula.imagen
        }
    }

    private fun initEventos() {
        // Configuración de eventos para los elementos de la interfaz
        butonHelp.setOnAction { onHelpAction() }
        butonCerrarSecion.setOnAction { onCerrarSecionAction() }
        ButonCrearPelicula.setOnAction { onCrearPeliculaAction() }
        ButonEditPelicula.setOnAction { onEditPeliculaAction() }
        ButonBorrarPelicula.setOnAction { onBorrarPeliculaAction() }
        butonGestionProductos.setOnAction { onGestionProductosAction() }
        butonGestionButacas.setOnAction { onGestionButacasAction() }

        // Eventos de la tabla
        TablaPeliculas.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            newValue?.let { onTablaPeliculasSelected(newValue) }
        }

        // Buscador
        TextBuscadorPeliculas.setOnKeyReleased { onBuscadorKeyReleased() }
    }

    // Métodos de evento
    private fun onHelpAction() {
        // Lógica para el botón Help
    }

    private fun onCerrarSecionAction() {
        // Lógica para el botón Cerrar Sesión
    }

    private fun onCrearPeliculaAction() {
        // Lógica para crear una nueva película
    }

    private fun onEditPeliculaAction() {
        // Lógica para editar una película
    }

    private fun onBorrarPeliculaAction() {
        // Lógica para borrar una película
    }

    private fun onGestionProductosAction() {
        // Lógica para gestionar productos
    }

    private fun onGestionButacasAction() {
        // Lógica para gestionar butacas
    }

    private fun onTablaPeliculasSelected(pelicula: Any) {
        // Lógica para manejar la selección de una película en la tabla
    }

    private fun onBuscadorKeyReleased() {
        // Lógica para manejar la búsqueda de películas
    }
}