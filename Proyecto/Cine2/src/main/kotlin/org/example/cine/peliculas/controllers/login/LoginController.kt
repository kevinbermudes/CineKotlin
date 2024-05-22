package org.example.cine.peliculas.controllers.login

import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.stage.Stage
import org.example.cine.route.RoutesManager
import org.lighthousegames.logging.logging

private val logger = logging()

class LoginController {

    @FXML
    private lateinit var textoNombreUsuario: TextField

    @FXML
    private lateinit var textoContrasena: TextField

    @FXML
    private lateinit var buttonNoIniciarSesion: Button

    @FXML
    private lateinit var buttonInicarSesion: Button

    @FXML
    fun initialize() {
        initEventos()
    }

    private fun initEventos() {
        buttonInicarSesion.setOnAction { onIniciarSesion() }
        buttonNoIniciarSesion.setOnAction { onNoIniciarSesion() }
    }

    private fun onIniciarSesion() {
        val usuario = textoNombreUsuario.text
        val contrasena = textoContrasena.text

        if (usuario == "admin" && contrasena == "admin") {
            // Cargar vista de administrador
            logger.debug { "Accediendo como admin" }
            RoutesManager.initIndezLoginAdminStage()

        } else if (usuario == "user" && contrasena == "user") {
            // Cargar vista de usuario
            logger.debug { "Accediendo como usuario" }
            RoutesManager.intiUsuarioIndex()
        } else {
            // Mostrar alerta de error
            showAlert("Credenciales incorrectas", "Por favor, inténtalo de nuevo.")
        }

        // Cerrar la ventana de login
        val stage = buttonInicarSesion.scene.window as Stage
        stage.close()
    }

    private fun onNoIniciarSesion() {
        // Acción para no iniciar sesión, si se requiere alguna lógica aquí
        showAlert("Información", "Decidiste no iniciar sesión.")
    }

    private fun showAlert(title: String, message: String) {
        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.title = title
        alert.headerText = null
        alert.contentText = message
        alert.showAndWait()
    }
}
