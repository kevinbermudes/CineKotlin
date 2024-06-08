package org.example.cine.productos.controllers.Help

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.stage.Stage


class HelpView {
    @FXML
    private lateinit var butonAceptarHelp: Button

    @FXML
    private lateinit var magicLabel: Label

    @FXML
    private lateinit var cineLabel: Label

    @FXML
    private lateinit var precioLabel: Label

    @FXML
    private lateinit var cineImageView: ImageView

    // Este método será llamado cuando se haga clic en el botón
    @FXML
    protected fun handleButtonAction(event: ActionEvent?) {
        // Cerramos la ventana actual
        val stage = butonAceptarHelp.scene.window as Stage
        stage.close()
    }
}