package org.example.cine.productos.viewmodels


import com.github.michaelbull.result.*
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.collections.transformation.FilteredList
import javafx.scene.image.Image
import org.example.cine.productos.errors.ProductoError
import org.example.cine.productos.models.Producto
import org.example.cine.productos.models.Producto.Categoria
import org.example.cine.productos.services.database.ProductosService
import org.example.cine.productos.services.storage.ProductosStorage
import org.example.cine.productos.validators.validate
import org.example.cine.route.RoutesManager
import org.lighthousegames.logging.logging
import java.io.File
import java.time.LocalDateTime
import kotlin.properties.Delegates

private val logger = logging()

class ProductosViewModel(
    private val service: ProductosService,
    private val storage: ProductosStorage
) {

    // Estado del ViewModel
    val state = ProductoState()

    init {
        logger.debug { "Inicializando ProductosViewModel" }
        loadAllProductos() // Cargamos los datos de los productos
        loadCategorias() // Cargamos las categorías de productos
    }

    private fun loadCategorias() {
        logger.debug { "Cargando categorías de productos" }
        state.categorias.clear()
        state.categorias.addAll(Categoria.values().map { it.name })
    }

    private fun loadAllProductos() {
        logger.debug { "Cargando productos del repositorio" }
        service.findAll().onSuccess {
            logger.debug { "Cargando productos del repositorio: ${state.productos.size}" }
            state.productos.clear()
            state.productos.addAll(it)
            updateActualState()
        }
    }

    // Actualiza el estado de la aplicación con los datos de ese instante en el estado
    private fun updateActualState() {
        logger.debug { "Actualizando estado de Aplicacion" }
        state.productoSeleccionado.limpiar()
        state.productoOperacion.limpiar()
    }

    // Filtra la lista de productos en el estado en función de la categoría y el nombre
    fun productosFilteredList(categoria: String, nombre: String): FilteredList<Producto> {
        logger.debug { "Filtrando lista de Productos: $categoria, $nombre" }

        return state.productos
            .filtered { producto ->
                categoria == "TODAS" || producto.categoria.name == categoria
            }
            .filtered { producto ->
                producto.nombre.contains(nombre, true)
            }
    }

    fun saveProductosToJson(file: File): Result<Long, ProductoError> {
        logger.debug { "Guardando Productos en JSON" }
        return storage.storeDataJson(file, state.productos)
    }

    fun loadProductosFromJson(file: File, withImages: Boolean = false): Result<List<Producto>, ProductoError> {
        logger.debug { "Cargando Productos en JSON" }
        return storage.deleteAllImages().andThen {
            storage.loadDataJson(file).onSuccess {
                service.deleteAll() // Borramos todos los datos de la BD
                service.saveAll(it.map { p -> p.copy(id = Producto.NEW_PRODUCTO, imagen = if (withImages) p.imagen else "") })
                loadAllProductos() // Actualizamos la lista
            }
        }
    }

    // carga en el estado el producto seleccionado
    fun updateProductoSeleccionado(producto: Producto) {
        logger.debug { "Actualizando estado de Producto: $producto" }

        state.productoSeleccionado.apply {
            id.value = producto.id.toString()
            nombre.value = producto.nombre
            precio.value = producto.precio.toString()
            categoria.value = producto.categoria.name
            createdAt.value = producto.createdAt.toString()
            updatedAt.value = producto.updatedAt.toString()
            storage.loadImage(producto.imagen).onSuccess {
                imagen.value = Image(it.absoluteFile.toURI().toString())
                fileImage = it
            }.onFailure {
                imagen.value = Image(RoutesManager.getResourceAsStream("images/sin-imagen.png"))
                fileImage = File(RoutesManager.getResource("images/sin-imagen.png").toURI())
            }
        }
    }

    // Crea un nuevo producto en el estado y repositorio
    fun crearProducto(): Result<Producto, ProductoError> {
        logger.debug { "Creando Producto" }
        val newProductoTemp = state.productoOperacion.copy()
        var newProducto = newProductoTemp.toModel().copy(id = Producto.NEW_PRODUCTO)
        return newProducto.validate().andThen {
            newProductoTemp.fileImage?.let { newFileImage ->
                storage.saveImage(newFileImage).onSuccess {
                    newProducto = newProducto.copy(imagen = it.name)
                }
            }
            service.save(newProducto).andThen {
                state.productos.add(it)
                updateActualState()
                Ok(it)
            }
        }
    }

    // Edita un producto en el estado y repositorio
    fun editarProducto(): Result<Producto, ProductoError> {
        logger.debug { "Editando Producto" }
        val updatedProductoTemp = state.productoOperacion.copy()
        val fileNameTemp = state.productoSeleccionado.fileImage!!.name
        var updatedProducto = state.productoOperacion.toModel().copy(imagen = fileNameTemp)
        return updatedProducto.validate().andThen {
            updatedProductoTemp.fileImage?.let { newFileImage ->
                if (updatedProducto.imagen.isEmpty()) {
                    storage.saveImage(newFileImage).onSuccess {
                        updatedProducto = updatedProducto.copy(imagen = it.name)
                    }
                } else {
                    storage.updateImage(fileNameTemp, newFileImage)
                }
            }
            service.save(updatedProducto).onSuccess {
                val index = state.productos.indexOfFirst { p -> p.id == it.id }
                if (index != -1) state.productos[index] = it
                updateActualState()
                Ok(it)
            }
        }
    }

    // Elimina un producto en el estado y repositorio
    fun eliminarProducto(): Result<Unit, ProductoError> {
        logger.debug { "Eliminando Producto" }
        val producto = state.productoSeleccionado.copy()
        val myId = producto.id.value.toLong()

        producto.fileImage?.let {
            if (it.name != "sin-imagen.png") {
                storage.deleteImage(it)
            }
        }

        service.deleteById(myId)
        state.productos.removeIf { it.id == myId }
        updateActualState()
        return Ok(Unit)
    }

    // Actualiza la imagen del producto en el estado
    fun updateImageProductoOperacion(fileImage: File) {
        logger.debug { "Actualizando imagen: $fileImage" }
        state.productoOperacion.imagen.value = Image(fileImage.toURI().toString())
        state.productoOperacion.fileImage = fileImage
    }

    fun exportToZip(fileToZip: File): Result<File, ProductoError> {
        logger.debug { "Exportando a ZIP: $fileToZip" }
        return service.findAll().andThen {
            storage.exportToZip(fileToZip, it)
        }.onFailure {
            logger.error { "Error al exportar a ZIP: ${it.message}" }
            Err(it)
        }
    }

    fun loadProductosFromZip(fileToUnzip: File): Result<List<Producto>, ProductoError> {
        logger.debug { "Importando de ZIP: $fileToUnzip" }
        return storage.loadFromZip(fileToUnzip).onSuccess { lista ->
            service.deleteAll().andThen {
                service.saveAll(lista.map { p -> p.copy(id = Producto.NEW_PRODUCTO) })
            }.onSuccess {
                loadAllProductos()
            }
        }
    }

    // Mi estado
    // Enums
    enum class TipoFiltro(val value: String) {
        TODAS("Todas"), BOTANA("Botana"), BEBIDA("Bebida"), BEBIDAS("Bebidas"), FRUTOS_SECOS("Frutos Secos")
    }
    enum class TipoOperacion(val value: String) {
        NUEVO("Nuevo"), EDITAR("Editar")
    }

    // Clases que representan el estado
    // Estado del ViewModel y caso de uso de Gestión de Productos
    data class ProductoState(
        val categorias: ObservableList<String> = FXCollections.observableArrayList<String>(),
        val productos: ObservableList<Producto> = FXCollections.observableArrayList<Producto>(),

        // Estado para estadísticas o detalles
        val productoSeleccionado: ProductoDetalleState = ProductoDetalleState(),
        val productoOperacion: ProductoDetalleState = ProductoDetalleState(),
    ) {
        var tipoOperacion: TipoOperacion by Delegates.observable(TipoOperacion.NUEVO) { _, _, newValue ->
            if (newValue == TipoOperacion.EDITAR) {
                logger.debug { "Copiando estado de Producto Seleccionado a Operacion" }
                productoOperacion.copyFrom(productoSeleccionado)
            } else {
                logger.debug { "Limpiando estado de Producto Operacion" }
                productoOperacion.limpiar()
            }
        }
    }

    // Estado para formularios de Producto (seleccionado y de operaciones)
    data class ProductoDetalleState(
        val id: SimpleStringProperty = SimpleStringProperty(""),
        val nombre: SimpleStringProperty = SimpleStringProperty(""),
        val precio: SimpleStringProperty = SimpleStringProperty(""),
        val categoria: SimpleStringProperty = SimpleStringProperty(""),
        val createdAt: SimpleStringProperty = SimpleStringProperty(LocalDateTime.now().toString()),
        val updatedAt: SimpleStringProperty = SimpleStringProperty(LocalDateTime.now().toString()),
        val imagen: SimpleObjectProperty<Image> = SimpleObjectProperty(Image(RoutesManager.getResourceAsStream("images/sin-imagen.png"))),
        var fileImage: File? = null
    ) {
        fun limpiar() {
            id.value = ""
            nombre.value = ""
            precio.value = ""
            categoria.value = ""
            createdAt.value = LocalDateTime.now().toString()
            updatedAt.value = LocalDateTime.now().toString()
            imagen.value = Image(RoutesManager.getResourceAsStream("images/sin-imagen.png"))
            fileImage = null
        }

        fun copyFrom(other: ProductoDetalleState) {
            id.value = other.id.value
            nombre.value = other.nombre.value
            precio.value = other.precio.value
            categoria.value = other.categoria.value
            createdAt.value = other.createdAt.value
            updatedAt.value = other.updatedAt.value
            imagen.value = other.imagen.value
            fileImage = other.fileImage
        }

        fun toModel():Producto{
            return Producto(
                id = id.value.toLong(),
                nombre = nombre.value,
                precio = precio.value.toDouble(),
                categoria = Producto.Categoria.valueOf(categoria.value.uppercase()),
                imagen = imagen.value.url,
                createdAt = LocalDateTime.parse(createdAt.value),
                updatedAt = LocalDateTime.parse(updatedAt.value)
            )
        }
    }
}
