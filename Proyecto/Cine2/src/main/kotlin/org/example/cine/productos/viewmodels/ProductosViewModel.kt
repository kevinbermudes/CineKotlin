package org.example.cine.productos.viewmodels

import com.github.michaelbull.result.*
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.scene.image.Image
import org.example.cine.productos.errors.ProductoError
import org.example.cine.productos.models.Producto
import org.example.cine.productos.services.database.ProductosService
import org.example.cine.productos.services.storage.ProductosStorage
import org.example.cine.productos.validators.validate
import org.example.cine.route.RoutesManager
import org.lighthousegames.logging.logging
import java.io.File
import java.time.LocalDateTime

private val logger = logging()

class ProductosViewModel(
    private val service: ProductosService,
    private val storage: ProductosStorage
) {

    // Estado del ViewModel
    val state: SimpleObjectProperty<ProductoState> = SimpleObjectProperty(ProductoState())

    init {
        logger.debug { "Inicializando ProductosViewModel" }
        loadAllProductos() // Cargamos los datos de los productos
    }

    fun loadAllProductos() {
        logger.debug { "Cargando productos del repositorio" }
        service.findAll().onSuccess {
            logger.debug { "Productos recuperados: ${it.size}" }
            it.forEach { producto -> logger.debug { "Producto: $producto" } }
            state.value = state.value.copy(productos = FXCollections.observableArrayList(it))
            updateActualState()
        }.onFailure {
            logger.error { "Error cargando productos: ${it.message}" }
        }
    }

    // Actualiza el estado de la aplicación con los datos de ese instante en el estado
    private fun updateActualState() {
        logger.debug { "Actualizando estado de Aplicacion" }
        val numProductos = state.value.productos.size.toString()
        // Solo toca el estado una vez para evitar problemas de concurrencia
        state.value = state.value.copy(
            numProductos = numProductos,
            producto = ProductoFormState()
        )
    }

    fun clearState() {
        logger.debug { "Limpiando estado de Producto" }
        state.set(ProductoState())
    }


    // Filtra la lista de productos en el estado en función de la categoría y el nombre
    fun productosFilteredList(categoria: String, nombre: String): List<Producto> {
        logger.debug { "Filtrando lista de Productos: $categoria, $nombre" }
        return state.value.productos.filter { producto ->
            (categoria == "TODAS" || producto.categoria.name == categoria) && producto.nombre.contains(nombre, true)
        }
    }

    fun saveProductosToJson(file: File): Result<Long, ProductoError> {
        logger.debug { "Guardando Productos en JSON" }
        return storage.storeDataJson(file, state.value.productos)
    }


    fun saveProduct(producto: Producto) {
        service.save(producto).onSuccess {
            loadAllProductos() // Recargar todos los productos para reflejar los cambios
        }.onFailure {
            logger.error { "Error guardando el producto: ${it.message}" }
        }
    }


    fun loadProductosFromJson(file: File, withImages: Boolean = false): Result<List<Producto>, ProductoError> {
        logger.debug { "Cargando Productos en JSON" }
        return storage.loadDataJson(file).onSuccess {
            service.deleteAll() // Borramos todos los datos de la BD
            service.saveAll(it.map { p ->
                p.copy(
                    id = Producto.NEW_PRODUCTO,
                    imagen = if (withImages) p.imagen else ""
                )
            })
            loadAllProductos() // Actualizamos la lista
        }
    }

    // Carga en el estado el producto seleccionado
    fun updateProductoSeleccionado(producto: Producto) {
        logger.debug { "Actualizando estado de Producto: $producto" }

        // Datos de la imagen
        var imagen = Image(RoutesManager.getResourceAsStream("images/sin-imagen.png"))
        var fileImage = File(RoutesManager.getResource("images/sin-imagen.png").toURI())

        // Si tiene imagen, la cargamos
storage.loadImage(producto.imagen).onSuccess {
            imagen=Image(it.absoluteFile.toURI().toString())
            fileImage = it
        }

        // Convertir ID a Long para la comparación
        val productoId = producto.id.toString()
        if (state.value.producto.id != productoId) {
            state.value = state.value.copy(
                producto = ProductoFormState(
                    id = producto.id.toString(),
                    nombre = producto.nombre,
                    precio = producto.precio.toString(),
                    stock = producto.stock.toString(),
                    categoria = producto.categoria,
                    imagen = imagen,
                    fileImage = fileImage
                )
            )
        }
    }


    // Crea un nuevo producto en el estado y repositorio
    fun crearProducto(
        nombre: String,
        precio: String,
        categoria: Producto.Categoria,
        stock: Long
    ): Result<Producto, ProductoError> {
        logger.debug { "Creando Producto" }
        val newProductoTemp = ProductoFormState(
            nombre = nombre,
            precio = precio,
            categoria = categoria,
            stock = stock.toString()
        ).copy()
        var newProducto = newProductoTemp.toModel().copy(id = Producto.NEW_PRODUCTO)
        return newProducto.validate().andThen {
            newProductoTemp.fileImage?.let { newFileImage ->
                storage.saveImage(newFileImage).onSuccess {
                    newProducto = newProducto.copy(imagen = it.name)
                }
            }
            service.save(newProducto).andThen {
                state.value = state.value.copy(
                    productos = state.value.productos + it
                )
                updateActualState()
                Ok(it)
            }
        }
    }

    // Edita un producto en el estado y repositorio
    fun editarProducto(): Result<Producto, ProductoError> {
        logger.debug { "Editando Producto" }
        val updatedProductoTemp = state.value.producto.copy()
        var updatedProducto = state.value.producto.toModel()
        return updatedProducto.validate().andThen {
            updatedProductoTemp.fileImage?.let { newFileImage ->
                if (updatedProducto.imagen == TipoImagen.SIN_IMAGEN.value || updatedProducto.imagen == TipoImagen.EMPTY.value) {
                    storage.saveImage(newFileImage).onSuccess {
                        updatedProducto = updatedProducto.copy(imagen = it.name)
                    }
                }
            }
            service.save(updatedProducto).onSuccess {
                val index = state.value.productos.indexOfFirst { p -> p.id == it.id }
                state.value = state.value.copy(
                    productos = state.value.productos.toMutableList().apply { this[index] = it }
                )
                updateActualState()
                Ok(it)
            }
        }
    }

    // Elimina un producto en el estado y repositorio
    fun eliminarProducto(producto: Producto): Result<Unit, ProductoError> {
        logger.debug { "Eliminando Producto" }
        val producto = state.value.producto.copy()
        val myId = producto.id.toLong()

        producto.fileImage?.let {
            if (it.name != TipoImagen.SIN_IMAGEN.value) {
                storage.deleteImage(it)
            }
        }

        service.deleteById(myId)
        state.value = state.value.copy(
            productos = state.value.productos.toMutableList().apply { this.removeIf { it.id == myId } }
        )
        updateActualState()
        return Ok(Unit)
    }

    // Actualiza la imagen del producto en el estado
    fun updateImageProducto(fileImage: File) {
        logger.debug { "Actualizando imagen: $fileImage" }
        state.value = state.value.copy(
            producto = state.value.producto.copy(
                imagen = Image(fileImage.toURI().toString()),
                fileImage = fileImage
            )
        )
    }

    fun exportToZip(fileToZip: File): Result<Unit, ProductoError> {
        logger.debug { "Exportando a ZIP: $fileToZip" }
        service.findAll().andThen {
            storage.exportToZip(fileToZip, it)
        }.onFailure {
            logger.error { "Error al exportar a ZIP: ${it.message}" }
            return Err(it)
        }
        return Ok(Unit)
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

    fun changeProductoOperacion(newValue: TipoOperacion) {
        logger.debug { "Cambiando tipo de operacion: $newValue" }
        if (newValue == TipoOperacion.EDITAR) {
            logger.debug { "Copiando estado de Producto Seleccionado a Operacion" }
            state.value = state.value.copy(
                producto = state.value.producto.copy(),
                tipoOperacion = newValue
            )
        } else {
            logger.debug { "Limpiando estado de Producto Operacion" }
            state.value = state.value.copy(
                producto = ProductoFormState(),
                tipoOperacion = newValue
            )
        }
    }

    fun updateDataProducto(
        nombre: String,
        precio: String,
        categoria: Producto.Categoria,
        imagen: Image,
        stock: String
    ) {
        logger.debug { "Actualizando estado de Producto Operacion" }
        state.value = state.value.copy(
            producto = state.value.producto.copy(
                nombre = nombre,
                precio = precio,
                categoria = categoria,
                imagen = imagen,
                stock = stock
            )
        )
    }


    enum class TipoImagen(val value: String) {
        SIN_IMAGEN("sin-imagen.png"), EMPTY("")
    }

    enum class TipoOperacion(val value: String) {
        NUEVO("Nuevo"), EDITAR("Editar")
    }

    // Clases que representan el estado
    data class ProductoState(
        val productos: List<Producto> = FXCollections.observableArrayList(),
        val numProductos: String = "",
        val producto: ProductoFormState = ProductoFormState(),
        val tipoOperacion: TipoOperacion = TipoOperacion.NUEVO
    )

    data class ProductoFormState(
        val id: String = "0",
        val nombre: String = "",
        val precio: String = "0.0",
        val stock: String = "0",
        val categoria: Producto.Categoria = Producto.Categoria.BOTANA,
        val imagen: Image = Image(RoutesManager.getResourceAsStream("images/sin-imagen.png")),
        val fileImage: File? = null
    ) {
        fun copy(): ProductoFormState {
            return ProductoFormState(
                id = this.id,
                nombre = this.nombre,
                precio = this.precio,
                stock = this.stock,
                categoria = this.categoria,
                imagen = this.imagen,
                fileImage = this.fileImage
            )
        }

        fun toModel(): Producto {
            return Producto(
                id = id.toLongOrNull() ?: 0,
                nombre = nombre,
                precio = precio.toDoubleOrNull() ?: 0.0,
                categoria = categoria,
                imagen = imagen.url,
                stock = stock.toIntOrNull() ?: 0,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),

                )
        }
    }
}