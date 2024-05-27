package org.example.cine.Usuario.controlador

import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.TextField
import org.example.cine.Usuario.repositories.UsuarioRepository
import org.example.cine.route.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.time.LocalDateTime
import org.lighthousegames.logging.logging
private val logger = logging()
class CambiarContrasenaController : KoinComponent {

    private val usuarioRepository: UsuarioRepository by inject()

    @FXML
    private lateinit var textoNombreUsuario: TextField

    @FXML
    private lateinit var textoContrasenaNueva: TextField

    @FXML
    private lateinit var buttonGuardarContrasena: Button

    @FXML
    private lateinit var buttonCanelar: Button
    @FXML
    private fun initialize() {
        buttonGuardarContrasena.setOnAction { onGuardarContrasena() }
        buttonCanelar.setOnAction { RoutesManager.changeScene(view = RoutesManager.View.LOGIN) }
    }

    private fun onGuardarContrasena() {
        val nombreUsuario = textoNombreUsuario.text
        val contrasenaNueva = textoContrasenaNueva.text

        if (nombreUsuario.isNotEmpty() && contrasenaNueva.isNotEmpty()) {
            val usuario = usuarioRepository.findByNombreUsuario(nombreUsuario)
            if (usuario != null) {
                val updatedUsuario = usuario.copy(contrasena = contrasenaNueva, updatedAt = LocalDateTime.now())
                usuarioRepository.update(updatedUsuario)
                showAlert(title = "Éxito", message = "Contraseña actualizada correctamente.")
            } else {
                showAlert(title = "Error", message = "Usuario no encontrado.")
            }
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
