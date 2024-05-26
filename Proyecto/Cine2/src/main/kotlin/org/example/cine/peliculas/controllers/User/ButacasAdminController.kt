package org.example.cine.peliculas.controllers.User


import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.image.ImageView
import org.example.cine.route.RoutesManager
import org.lighthousegames.logging.logging

private val logger = logging()

class ButacasAdminController {

    @FXML
    private lateinit var butonHelp: Button

    @FXML
    private lateinit var butonEditarProductos: Button

    @FXML
    private lateinit var butonAtrasButacas: Button

    @FXML
    private lateinit var butonEditarButaca: Button

    @FXML
    private lateinit var textNombreCoordenada: TextField

    @FXML
    private lateinit var cantidadButacasVip: TextField

    @FXML
    private lateinit var cantidadButacasMantenimiento: TextField

    @FXML
    private lateinit var cantidadButacasOcupado: TextField

    @FXML
    private lateinit var cantidadButacasLibre: TextField

    // Define all the ImageView for the butacas
    @FXML
    private lateinit var butacaA1: ImageView
    @FXML
    private lateinit var butacaA2: ImageView
    @FXML
    private lateinit var butacaA3: ImageView
    @FXML
    private lateinit var butacaA4: ImageView
    @FXML
    private lateinit var butacaA5: ImageView
    @FXML
    private lateinit var butacaA36: ImageView
    @FXML
    private lateinit var butacaA7: ImageView
    @FXML
    private lateinit var butacaB1: ImageView
    @FXML
    private lateinit var butacaB2: ImageView
    @FXML
    private lateinit var butacaB3: ImageView
    @FXML
    private lateinit var butacaB4: ImageView
    @FXML
    private lateinit var butacaB5: ImageView
    @FXML
    private lateinit var butacaB6: ImageView
    @FXML
    private lateinit var butacaB7: ImageView
    @FXML
    private lateinit var butacaC1: ImageView
    @FXML
    private lateinit var butacaC2: ImageView
    @FXML
    private lateinit var butacaB73: ImageView
    @FXML
    private lateinit var butacaC3: ImageView
    @FXML
    private lateinit var butacaC4: ImageView
    @FXML
    private lateinit var butacaB76: ImageView
    @FXML
    private lateinit var butacaC5: ImageView
    @FXML
    private lateinit var butacaC6: ImageView
    @FXML
    private lateinit var butacaC7: ImageView
    @FXML
    private lateinit var butacaD1: ImageView
    @FXML
    private lateinit var butacaD2: ImageView
    @FXML
    private lateinit var butacaD3: ImageView
    @FXML
    private lateinit var butacaD4: ImageView
    @FXML
    private lateinit var butacaD5: ImageView
    @FXML
    private lateinit var butacaD6: ImageView
    @FXML
    private lateinit var butacaD7: ImageView
    @FXML
    private lateinit var butacaE1: ImageView
    @FXML
    private lateinit var butacaE2: ImageView
    @FXML
    private lateinit var butacaE3: ImageView
    @FXML
    private lateinit var butacaE4: ImageView
    @FXML
    private lateinit var butacaE5: ImageView
    @FXML
    private lateinit var butacaE6: ImageView
    @FXML
    private lateinit var butacaE7: ImageView

    @FXML
    fun initialize() {
        initEventos()
    }

    private fun initEventos() {
        butonHelp.setOnAction { onImportarButacas() }
        butonEditarProductos.setOnAction { onEditarProductos() }
        butonAtrasButacas.setOnAction { onAtrasButacas() }
        butonEditarButaca.setOnAction { onEditarButaca() }
    }

    private fun onImportarButacas() {
        // Implementar la lógica para importar butacas
        logger.debug { "Importar butacas" }
    }

    private fun onEditarProductos() {
        // Implementar la lógica para editar productos
        logger.debug { "Editar productos" }
    }

    private fun onAtrasButacas() {
        // Implementar la lógica para volver atrás
        logger.debug { "Volver atrás" }
        RoutesManager.changeScene(view = RoutesManager.View.ADMININDEX)
    }

    private fun onEditarButaca() {
        // Implementar la lógica para editar una butaca específica
        val coordenada = textNombreCoordenada.text
        // Aquí debes implementar la lógica específica para editar la butaca
        logger.debug { "Editar butaca en coordenada: $coordenada" }
    }
}