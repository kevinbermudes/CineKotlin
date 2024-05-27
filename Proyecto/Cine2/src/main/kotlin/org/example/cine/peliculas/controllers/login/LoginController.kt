package org.example.cine.peliculas.controllers.login

import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.stage.Stage
import org.example.cine.Usuario.models.Usuario
import org.example.cine.Usuario.repositories.UsuarioRepository
import org.example.cine.route.RoutesManager
import org.koin.core.component.KoinComponent
import org.lighthousegames.logging.logging
import org.koin.core.component.inject

private val logger = logging()

class LoginController : KoinComponent {
   // private val usuarioRepository: UsuarioRepository by inject()


    @FXML
    private lateinit var textoNombreUsuario: TextField

    @FXML
    private lateinit var textoContrasena: TextField

    @FXML
    private lateinit var buttonNoIniciarSesion: Button

    @FXML
    private lateinit var buttonInicarSesion: Button

    @FXML
    private lateinit var buttonNuevoUsuario: Button

    @FXML
    private lateinit var buttonCambiarContrasena: Button

    private val usuarioRepository: UsuarioRepository by inject()
    @FXML
    fun initialize() {
        initEventos()
    }

    private fun initEventos() {
        buttonInicarSesion.setOnAction { onIniciarSesion() }
        buttonNoIniciarSesion.setOnAction { onNoIniciarSesion() }
        buttonNuevoUsuario.setOnAction { onNuevoUsuario() }
        buttonCambiarContrasena.setOnAction { onCambiarContrasena() }
    }

    private fun onIniciarSesion() {
        val nombreUsuario = textoNombreUsuario.text
        val contrasena = textoContrasena.text

        if (nombreUsuario.isNotEmpty() && contrasena.isNotEmpty()) {
            val usuario = usuarioRepository.findByNombreUsuario(nombreUsuario)
            if (usuario != null && usuario.contrasena == contrasena) {
                when (usuario.rol) {
                    Usuario.Rol.ADMIN -> {
                        logger.debug { "Accediendo como admin" }
                        RoutesManager.changeScene(view = RoutesManager.View.ADMININDEX)
                    }
                    Usuario.Rol.USER -> {
                        logger.debug { "Accediendo como usuario" }
                        RoutesManager.changeScene(view = RoutesManager.View.USUARIOINDEX)
                    }
                }
                closeWindow()
            } else {
                showAlert("Credenciales incorrectas", "Por favor, inténtalo de nuevo.")
            }
        } else {
            showAlert("Error", "Por favor, complete todos los campos.")
        }
    }

    private fun onNoIniciarSesion() {
    logger.debug { "No iniciar sesión" }
    RoutesManager.changeScene(view = RoutesManager.View.MAIN)
    }

    private fun onNuevoUsuario() {
        RoutesManager.changeScene(view = RoutesManager.View.NUEVO_USUARIO)
    }

    private fun onCambiarContrasena() {
        RoutesManager.changeScene(view = RoutesManager.View.CAMBIAR_CONTRASENA)
    }

    private fun closeWindow() {
        val window = buttonInicarSesion.scene?.window
        if (window is Stage) {
            window.close()
        } else {
            logger.error { "La ventana actual no es una instancia de Stage o es null" }
        }
    }

    private fun showAlert(title: String, message: String) {
        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.title = title
        alert.headerText = null
        alert.contentText = message
        alert.showAndWait()
    }
}
