package org.example.cine.peliculas.controllers.User

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import org.example.cine.peliculas.models.Butaca
import org.example.cine.peliculas.service.storage.ButacasStorageJsonImpl
import org.example.cine.route.RoutesManager
import org.lighthousegames.logging.logging
import java.io.File

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
    private lateinit var butacaA6: ImageView

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
    private lateinit var butacaC3: ImageView

    @FXML
    private lateinit var butacaC4: ImageView

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

    private val butacasStorage = ButacasStorageJsonImpl()
    private var selectedButacaId: String? = null

    @FXML
    fun initialize() {
        initEventos()
        loadButacasFromJson()
    }

    private fun initEventos() {
        butonHelp.setOnAction { onImportarButacas() }
        butonEditarProductos.setOnAction { onEditarProductos() }
        butonAtrasButacas.setOnAction { onAtrasButacas() }
        butonEditarButaca.setOnAction { onEditarButaca() }
    }

    private fun loadButacasFromJson() {
        val butacasFile = File("src/main/resources/butacas.json")
        if (butacasFile.exists()) {
            val butacasResult = butacasStorage.loadDataJson(butacasFile)
            butacasResult.onSuccess { butacas ->
                butacas.forEach { butaca ->
                    val imageView = getImageViewById(butaca.id)
                    val imageUrl = javaClass.getResource("/org/example/cine/images/${butaca.imagen}")
                    if (imageUrl != null) {
                        imageView.image = Image(imageUrl.toString())
                    } else {
                        println("Error: No se pudo encontrar la imagen para ${butaca.imagen}")
                    }
                    imageView.setOnMouseClicked {
                        seleccionarButaca(butaca.id)
                    }
                }
                actualizarContadores(butacas)
            }.onFailure {
                println("Error al cargar butacas: ${it.message}")
            }
        } else {
            println("El archivo butacas.json no existe.")
        }
    }

    private fun getImageViewById(id: String): ImageView {
        return when (id) {
            "A1" -> butacaA1
            "A2" -> butacaA2
            "A3" -> butacaA3
            "A4" -> butacaA4
            "A5" -> butacaA5
            "A6" -> butacaA6
            "A7" -> butacaA7
            "B1" -> butacaB1
            "B2" -> butacaB2
            "B3" -> butacaB3
            "B4" -> butacaB4
            "B5" -> butacaB5
            "B6" -> butacaB6
            "B7" -> butacaB7
            "C1" -> butacaC1
            "C2" -> butacaC2
            "C3" -> butacaC3
            "C4" -> butacaC4
            "C5" -> butacaC5
            "C6" -> butacaC6
            "C7" -> butacaC7
            "D1" -> butacaD1
            "D2" -> butacaD2
            "D3" -> butacaD3
            "D4" -> butacaD4
            "D5" -> butacaD5
            "D6" -> butacaD6
            "D7" -> butacaD7
            "E1" -> butacaE1
            "E2" -> butacaE2
            "E3" -> butacaE3
            "E4" -> butacaE4
            "E5" -> butacaE5
            "E6" -> butacaE6
            "E7" -> butacaE7
            else -> throw IllegalArgumentException("Id de butaca desconocido: $id")
        }
    }

    private fun seleccionarButaca(id: String) {
        textNombreCoordenada.text = id
        selectedButacaId = id
    }

    private fun actualizarContadores(butacas: List<Butaca>) {
        val vipCount = butacas.count { it.estado == "vip" }
        val mantenimientoCount = butacas.count { it.estado == "mantenimiento" }
        val ocupadoCount = butacas.count { it.estado == "ocupado" }
        val libreCount = butacas.count { it.estado == "libre" }

        cantidadButacasVip.text = vipCount.toString()
        cantidadButacasMantenimiento.text = mantenimientoCount.toString()
        cantidadButacasOcupado.text = ocupadoCount.toString()
        cantidadButacasLibre.text = libreCount.toString()
    }

    private fun onImportarButacas() {
        logger.debug { "Importar butacas" }
    }

    private fun onEditarProductos() {
        logger.debug { "Editar productos" }
        RoutesManager.changeScene(view = RoutesManager.View.PRODUCTOSADMIN)
    }

    private fun onAtrasButacas() {
        logger.debug { "Volviendo atras a ADMININDEX" }
        RoutesManager.changeScene(view = RoutesManager.View.ADMININDEX)
    }

    private fun onEditarButaca() {
        val coordenada = selectedButacaId
        if (!coordenada.isNullOrEmpty()) {
            logger.debug { "Editar butaca en coordenada: $coordenada" }
            RoutesManager.editarButaca(coordenada)
        } else {
            showAlert("Error", "Seleccione una butaca para editar.")
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