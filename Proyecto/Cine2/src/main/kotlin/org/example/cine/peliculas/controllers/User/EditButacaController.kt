package org.example.cine.peliculas.controllers.User

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.RadioButton
import javafx.scene.control.TextField
import javafx.stage.Stage
import org.example.cine.peliculas.service.storage.ButacasStorageJsonImpl
import org.example.cine.route.RoutesManager
import org.lighthousegames.logging.logging
import java.io.File

private val logger = logging()

class EditarButacaController {
    @FXML
    private lateinit var butonEditButacaConfirmar: Button

    @FXML
    private lateinit var butonEditButacaCancelar: Button

    @FXML
    private lateinit var radiusEditVip: RadioButton

    @FXML
    private lateinit var radiusEditMantenimiento: RadioButton

    @FXML
    private lateinit var radiusEditOcupado: RadioButton

    @FXML
    private lateinit var radiusEditLibre: RadioButton

    @FXML
    private lateinit var textPrecioButaca: TextField

    private val butacasStorage = ButacasStorageJsonImpl()
    private var selectedButacaId: String? = null

    @FXML
    fun initialize() {
        initEventos()
        selectedButacaId = RoutesManager.getSelectedButacaId()
        if (selectedButacaId != null) {
            cargarDatosButaca(selectedButacaId!!)
        }
    }


    private fun initEventos() {
        butonEditButacaConfirmar.setOnAction { onConfirm() }
        butonEditButacaCancelar.setOnAction { onCancel() }
    }

    private fun cargarDatosButaca(butacaId: String) {
        val butacaFile = File("src/main/resources/butacas.json")
        if (butacaFile.exists()) {
            val butacasResult = butacasStorage.loadDataJson(butacaFile)
            butacasResult.onSuccess { butacas ->
                val butaca = butacas.find { it.id == butacaId }
                if (butaca != null) {
                    when (butaca.estado) {
                        "vip"           -> radiusEditVip.isSelected = true
                        "mantenimiento" -> radiusEditMantenimiento.isSelected = true
                        "ocupado"       -> radiusEditOcupado.isSelected = true
                        "libre"         -> radiusEditLibre.isSelected = true
                    }
                    textPrecioButaca.text = butaca.precio.toString()
                }
            }.onFailure {
                showAlert("Error", "Error al cargar butacas: ${it.message}")
            }
        }
    }

    private fun onConfirm() {
        val butacaId = selectedButacaId
        if (butacaId != null) {
            val estado = when {
                radiusEditVip.isSelected           -> "vip"
                radiusEditMantenimiento.isSelected -> "mantenimiento"
                radiusEditOcupado.isSelected       -> "ocupado"
                radiusEditLibre.isSelected         -> "libre"
                else                               -> ""
            }

            val precio = textPrecioButaca.text.toDoubleOrNull()
            if (estado.isNotEmpty() && precio != null) {
                actualizarButaca(butacaId, estado, precio)
            } else {
                showAlert("Error", "Por favor, complete todos los campos correctamente.")
            }
        }
    }

    private fun actualizarButaca(id: String, estado: String, precio: Double) {
        val butacasFile = File("src/main/resources/butacas.json")
        if (butacasFile.exists()) {
            val butacasResult = butacasStorage.loadDataJson(butacasFile)
            butacasResult.onSuccess { butacas ->
                val butacaIndex = butacas.indexOfFirst { it.id == id }
                if (butacaIndex != -1) {
                    val nuevaButaca = butacas[butacaIndex].copy(
                        estado = estado,
                        precio = precio,
                        imagen = "$estado.png"
                    )
                    val butacasActualizadas = butacas.toMutableList().apply {
                        this[butacaIndex] = nuevaButaca
                    }
                    butacasStorage.storeDataJson(butacasFile, butacasActualizadas)
                        .onSuccess {
                            showAlert("Éxito", "Butaca actualizada correctamente.")
                            closeCurrentStage()
                        }.onFailure {
                            showAlert("Error", "Error al guardar butacas: ${it.message}")
                        }
                }
            }.onFailure {
                showAlert("Error", "Error al cargar butacas: ${it.message}")
            }
        }
    }

    private fun onCancel() {
        closeCurrentStage()
    }

    private fun closeCurrentStage() {
        val stage = butonEditButacaConfirmar.scene.window as Stage
        stage.close()
    }

    private fun showAlert(title: String, message: String) {
        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.title = title
        alert.headerText = null
        alert.contentText = message
        alert.showAndWait()
    }
}
