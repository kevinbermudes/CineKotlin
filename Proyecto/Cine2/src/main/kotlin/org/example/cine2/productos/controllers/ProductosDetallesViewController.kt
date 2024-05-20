//package org.example.cine2.productos.controllers
//import com.github.michaelbull.result.*
//import javafx.fxml.FXML
//import javafx.scene.control.Alert
//import javafx.scene.control.Alert.AlertType
//import org.example.cine2.productos.viewmodels.ProductosViewModel.TipoOperacion.NUEVO
//import org.example.cine2.productos.viewmodels.ProductosViewModel.TipoOperacion.EDITAR
//import javafx.scene.control.Button
//import javafx.scene.control.TextField
//import javafx.scene.image.ImageView
//import javafx.stage.FileChooser
//import org.example.cine2.productos.errors.ProductoError
//import org.example.cine2.productos.viewmodels.ProductosViewModel
//import org.example.cine2.route.RoutesManager
//import org.koin.core.component.inject
//import org.lighthousegames.logging.logging
//
//
//
//private val logger = logging()
//
//class ProductosDetallesViewController {
//    val viewModel : ProductosViewModel by inject()
//
//    //Imagenes
//    @FXML
//    private lateinit var imagenProductoEditar: ImageView
//    //Formulario/Textos
//    @FXML
//    private lateinit var textoNombreProducto: TextField
//
//    @FXML
//    private lateinit var textoCategoriaProducto: TextField
//
//    @FXML
//    private lateinit var textoPrecioProducto: TextField
//
//    //Botones
//
//    @FXML
//    private lateinit var butonGuardarEdicionProducto: Button
//
//    @FXML
//    private lateinit var butonLimpiarProductos: Button
//
//    @FXML
//    private lateinit var butonCancelarProductos1: Button
//
//    // Inicialización del controlador
//    @FXML
//    private fun initialize() {
//       logger.debug { "Iniciando el controlador DetalleProductos" }
//        //Iniciamos los bindings
//        initBindings()
//        //Iniciamos los eventos
//        initEvents()
//    }
//    private fun initEvents() {
//      butonGuardarEdicionProducto.setOnAction {
//          onGuardarAction()
//      }
//        butonLimpiarProductos.setOnAction {
//            onLimpiarAction()
//        }
//        butonCancelarProductos1.setOnAction {
//            onCancelarAction()
//        }
//        imagenProductoEditar.setOnMouseClicked {
//            onImagenClick()
//        }
//    }
//
//    private fun onImagenClick() {
//        logger.debug { "onImagenClick" }
//        FileChooser().run {
//            title = "Elige una imagen para el producto"
//            extensionFilters.addAll(FileChooser.ExtensionFilter("Imagenes", "*.png", "*.jpg", "*.jpeg"))
//            showOpenDialog(RoutesManager.activeStage)
//        }?.let {
//            viewModel.updateImageProductoOperacion(it)
//        }
//    }
//    private fun initBindings() {
//       //Formulario/Textos
//       textoNombreProducto.textProperty().bindBidirectional(viewModel.state.productoOperacion.nombre)
//       textoCategoriaProducto.textProperty().bindBidirectional(viewModel.state.productoOperacion.categoria)
//        imagenProductoEditar.imageProperty().bindBidirectional(viewModel.state.productoOperacion.imagen)
//       textoPrecioProducto.textProperty().bindBidirectional(viewModel.state.productoOperacion.precio)
//    }
//
//    private fun onGuardarAction(){
//        logger.debug { "onGuardarActionProductos" }
//        validateForm().andThen {
//            when (viewModel.state.productoOperacion){
//                NUEVO -> {
//                    viewModel.crearProducto()
//                }
//                EDITAR -> {
//                    viewModel.editarProducto()
//                }
//            }
//        }.onSuccess {
//            logger.debug { "Producto guardado" }
//            showAlertOperacion(
//                AlertType.INFORMATION,
//                "Operación exitosa",
//                "Producto guardado correctamente"
//            )
//            cerrarVentana()
//        }.onFailure {
//            logger.error { "Error al guardar el producto: ${it.message}" }
//            showAlertOperacion(
//                AlertType.ERROR,
//                "Operación fallida",
//                "Error al guardar el producto:\n ${it.message}"
//            )
//        }
//    }
//    private fun cerrarVentana() {
//        butonCancelarProductos1.scene.window.hide()
//    }
//    private fun onCancelarAction() {
//        logger.debug { "onCancelarAction" }
//        viewModel.state.productoOperacion.limpiar()
//        cerrarVentana()
//    }
//
//    private fun onLimpiarAction() {
//        logger.debug { "onLimpiarAction" }
//        // Limpiamos el estado actual
//        viewModel.state.productoOperacion.limpiar()
//    }
//
//    private fun validateForm(): Result<ProductosDetallesViewController, ProductoError.ValidationProblem> {
//        logger.debug { "Validando formulario" }
//
//        if (textoNombreProducto.text.isNullOrEmpty()) {
//            return Err(ProductoError.ValidationProblem("El nombre no puede estar vacío"))
//        }
//        if (textoPrecioProducto.text.isNullOrEmpty().or(textoPrecioProducto.text == "0.0")) {
//            return Err(ProductoError.ValidationProblem("El precio no puede ser negativo ni vacio"))
//        }
//        return Ok(this)
//    }
//    private fun showAlertOperacion(
//        alerta: AlertType = AlertType.CONFIRMATION,
//        title: String = "",
//        mensaje: String = ""
//    ) {
//        val alert = Alert(alerta)
//        alert.title = title
//        alert.contentText = mensaje
//        alert.showAndWait()
//    }
//}
