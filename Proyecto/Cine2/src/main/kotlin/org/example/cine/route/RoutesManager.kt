package org.example.cine.route

import javafx.application.Application
import javafx.application.Platform
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.image.Image
import javafx.scene.layout.Pane
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.stage.WindowEvent
import org.lighthousegames.logging.logging
import java.io.InputStream
import java.net.URL
import java.util.*

private val logger = logging()

/**
 * Clase que gestiona las rutas de la aplicación
 */
object RoutesManager {
    // Necesitamos siempre saber
    private lateinit var mainStage: Stage // La ventana principal
    private lateinit var _activeStage: Stage // La ventana actual
    val activeStage: Stage
        get() = _activeStage
    lateinit var app: Application

    private var scenesMap: HashMap<String, Pane> = HashMap()

    // Todas las vistas
    enum class View(val fxml: String) {
        //vista principal sin login simepre cargamos esta
        MAIN("/org/example/cine/views/peliculas/IndezSinLogin.fxml"),
        //vista Login
        LOGIN("/org/example/cine/views/login/Login.fxml"),
        //vista de usuario
        USUARIOINDEX("/org/example/cine/views/peliculas/IndezLogin.fxml"),
        //vista de administrador
        ADMININDEX("/org/example/cine/views/peliculas/IndezLoginAdmin.fxml"),

  //  ************************************Productos*********************
        //VISTA de produtos con login usuario
        PRODUCTOSUSUARIOS("/org/example/cine/views/produtos/ProductosUsuarios.fxml"),
        //Vista de produtos con login admin
        PRODUCTOSADMIN("/org/example/cine/views/produtos/ProductosAdmin.fxml"),
    }

    init {
        logger.debug { "Inicializando RoutesManager" }
        Locale.setDefault(Locale.forLanguageTag("es-ES"))
    }

    // Inicializamos la scena principal
    fun initMainStage(stage: Stage) {
        logger.debug { "Inicializando MainStage" }

        val fxmlLoader = FXMLLoader(getResource(View.MAIN.fxml))
        val parentRoot = fxmlLoader.load<Pane>()
        val scene = Scene(parentRoot, 900.0, 600.0)
        stage.title = "Cine - Índice sin Login"
        stage.isResizable = false
        stage.icons.add(Image(getResourceAsStream("/org/example/cine/icons/app-icon.png")))
        stage.setOnCloseRequest { onAppExit(event = it) }
        stage.scene = scene
        mainStage = stage
        _activeStage = stage
        mainStage.show()
    }


    // Abrimos una nueva ventana para "Acerca De"
    fun initLoginStage() {
        logger.debug { "Inicializando Login" }

        val fxmlLoader = FXMLLoader(getResource(View.LOGIN.fxml))
        val parentRoot = fxmlLoader.load<Pane>()
        val scene = Scene(parentRoot, 900.0, 600.0)
        val stage = Stage()
        stage.icons.add(Image(getResourceAsStream("/org/example/cine/icons/app-icon.png")))
        stage.setOnCloseRequest { onAppExit(event = it) }
        stage.title = "Login"
        stage.scene = scene
        stage.initOwner(mainStage)
        stage.initModality(Modality.WINDOW_MODAL)
        stage.isResizable = false
        closeActiveStage()
        _activeStage = stage
        stage.show()
    }
    fun intiUsuarioIndex(){
        logger.debug { "Inicializando UsuarioIndex" }

        val fxmlLoader = FXMLLoader(getResource(View.USUARIOINDEX.fxml))
        val parentRoot = fxmlLoader.load<Pane>()
        val scene = Scene(parentRoot, 900.0, 600.0)
        val stage = Stage()
        stage.icons.add(Image(getResourceAsStream("/org/example/cine/icons/app-icon.png")))
        stage.setOnCloseRequest { onAppExit(event = it) }
        stage.title = "Cine - Índice Login"
        stage.scene = scene
        stage.initOwner(mainStage)
        stage.initModality(Modality.WINDOW_MODAL)
        stage.isResizable = false
        closeActiveStage()
        _activeStage = stage
        stage.show()
    }
    fun initIndezLoginAdminStage(){
        logger.debug { "Inicializando AdminIndex" }

        val fxmlLoader = FXMLLoader(getResource(View.ADMININDEX.fxml))
        val parentRoot = fxmlLoader.load<Pane>()
        val scene = Scene(parentRoot, 900.0, 600.0)
        val stage = Stage()
        stage.icons.add(Image(getResourceAsStream("/org/example/cine/icons/app-icon.png")))
        stage.setOnCloseRequest { onAppExit(event = it) }
        stage.title = "Cine - Índice Admin"
        stage.scene = scene
        stage.initOwner(mainStage)
        stage.initModality(Modality.WINDOW_MODAL)
        stage.isResizable = false
        closeActiveStage()
        _activeStage = stage
        stage.show()
    }
    //***********************************PRODUTOS***************************************
    //index con user
    fun initProductosUsuarios(){
        logger.debug { "Inicializando ProductosUsuarios" }

        val fxmlLoader = FXMLLoader(getResource(View.PRODUCTOSUSUARIOS.fxml))
        val parentRoot = fxmlLoader.load<Pane>()
        val scene = Scene(parentRoot, 900.0, 600.0)
        val stage = Stage()
        stage.icons.add(Image(getResourceAsStream("/org/example/cine/icons/app-icon.png")))
        stage.setOnCloseRequest { onAppExit(event = it) }
        stage.title = "Cine - Productos Usuarios"
        stage.scene = scene
        stage.initOwner(mainStage)
        stage.initModality(Modality.WINDOW_MODAL)
        stage.isResizable = false
        closeActiveStage()
        _activeStage = stage
        stage.show()
    }
    //index con admin
    fun initProductosAdmin(){
        logger.debug { "Inicializando ProductosAdmin" }

        val fxmlLoader = FXMLLoader(getResource(View.PRODUCTOSADMIN.fxml))
        val parentRoot = fxmlLoader.load<Pane>()
        val scene = Scene(parentRoot, 900.0, 600.0)
        val stage = Stage()
        stage.icons.add(Image(getResourceAsStream("/org/example/cine/icons/app-icon.png")))
        stage.setOnCloseRequest { onAppExit(event = it) }
        stage.title = "Cine - Productos Admin"
        stage.scene = scene
        stage.initOwner(mainStage)
        stage.initModality(Modality.WINDOW_MODAL)
        stage.isResizable = false
        closeActiveStage()
        _activeStage = stage
        stage.show()
    }

    private fun closeActiveStage() {
        if (::mainStage.isInitialized && _activeStage != mainStage) {
            _activeStage.close()
        }
    }

    // O podemos hacer uno genérico, añade las opciones que necesites
    fun getResource(resource: String): URL {
        return app::class.java.getResource(resource)
            ?: throw RuntimeException("No se ha encontrado el recurso: $resource")
    }

    fun getResourceAsStream(resource: String): InputStream {
        return app::class.java.getResourceAsStream(resource)
            ?: throw RuntimeException("No se ha encontrado el recurso como stream: $resource")
    }

    fun onAppExit(
        title: String = "Salir de ${mainStage.title}?",
        headerText: String = "¿Estás seguro de que quieres salir de ${mainStage.title}?",
        contentText: String = "Si sales, se cerrará la aplicación y perderás todos los datos no guardados",
        event: WindowEvent? = null
    ) {
        logger.debug { "Cerrando aplicación" }
        Alert(Alert.AlertType.CONFIRMATION).apply {
            this.title = title
            this.headerText = headerText
            this.contentText = contentText
        }.showAndWait().ifPresent { opcion ->
            if (opcion == ButtonType.OK) {
                Platform.exit()
            } else {
                event?.consume()
            }
        }
    }
}
