package org.example.cine.Usuario.controlador

import javafx.fxml.FXML
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import javafx.scene.control.Alert
import javafx.scene.control.Button
import org.example.cine.Usuario.models.Usuario
import org.example.cine.Usuario.repositories.UsuarioRepository
import org.example.cine.route.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

class NuevoUsuarioController : KoinComponent {

    private val usuarioRepository: UsuarioRepository by inject()

    @FXML
    private lateinit var textoNombreUsuario: TextField

    @FXML
    private lateinit var textoContrasena: PasswordField

    @FXML
    private lateinit var textoEmail: TextField

    @FXML
    private lateinit var buttonCrearUsuario: Button

    @FXML
    private lateinit var buttonCancelar: Button

    @FXML
    fun initialize() {
        buttonCrearUsuario.setOnAction { onCrearUsuario() }
        buttonCancelar.setOnAction { onCancelar() }
    }

    private fun onCancelar() {
        logger.debug { "Cancelando creación de usuario" }
        RoutesManager.changeScene(view = RoutesManager.View.LOGIN)
    }

    private fun onCrearUsuario() {
        val nombreUsuario = textoNombreUsuario.text
        val contrasena = textoContrasena.text
        val email = textoEmail.text

        if (nombreUsuario.isNotEmpty() && contrasena.isNotEmpty() && email.isNotEmpty()) {
            val usuario = Usuario(
                nombre = nombreUsuario,
                contrasena = contrasena,
                email = email,
                rol = Usuario.Rol.USER
            )
            usuarioRepository.save(usuario)
            showAlert(title = "Éxito", message = "Usuario creado correctamente.")
            RoutesManager.changeScene(view = RoutesManager.View.LOGIN)
        } else {
            showAlert(title = "Error", message = "Por favor, complete todos los campos.")
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
