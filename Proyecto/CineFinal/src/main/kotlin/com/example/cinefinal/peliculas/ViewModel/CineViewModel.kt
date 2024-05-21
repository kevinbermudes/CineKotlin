package org.example.cinefinal.peliculas.ViewModel

import com.github.michaelbull.result.onSuccess
import javafx.beans.property.SimpleObjectProperty
import org.example.cinefinal.peliculas.errors.PeliculaError
import org.example.cinefinal.peliculas.models.Pelicula
import org.example.cinefinal.peliculas.service.database.PeliculasService
import org.example.cinefinal.peliculas.service.storage.PeliculasStorage
import org.lighthousegames.logging.logging
import java.io.File
import com.github.michaelbull.result.*
import javafx.scene.image.Image
import org.example.cinefinal.peliculas.mapers.toModel
import org.example.cinefinal.peliculas.validadores.validate
import org.example.cinefinal.route.RoutesManager
import java.time.LocalDate


private val logger = logging()

class CineViewModel(
    private val service: PeliculasService,
    private val storage: PeliculasStorage
) {

    // Estado del ViewModel
    val state: SimpleObjectProperty<PeliculaState> = SimpleObjectProperty(PeliculaState())

    init {
        logger.debug { "Inicializando PeliculasViewModel" }
        loadAllPeliculas() // Cargamos los datos de las películas
    }

    private fun loadAllPeliculas() {
        logger.debug { "Cargando películas del repositorio" }
        service.findAll().onSuccess {
            logger.debug { "Cargando películas del repositorio: ${it.size}" }
            state.value = state.value.copy(peliculas = it)
            updateActualState()
        }
    }


    // Actualiza el estado de la aplicación con los datos de ese instante en el estado
    private fun updateActualState() {
        logger.debug { "Actualizando estado de Aplicacion" }
        val numPeliculas = state.value.peliculas.size.toString()
        // Solo toca el estado una vez para evitar problemas de concurrencia
        state.value = state.value.copy(
            numPeliculas = numPeliculas,
            pelicula = PeliculaFormState()
        )
    }

    // Filtra la lista de películas en el estado en función del nombre
    fun peliculasFilteredList(nombre: String): List<Pelicula> {
        logger.debug { "Filtrando lista de Películas: $nombre" }

        return state.value.peliculas.filter { pelicula ->
            pelicula.nombre.contains(nombre, true)
        }
    }

    fun savePeliculasToJson(file: File): Result<Long, PeliculaError> {
        logger.debug { "Guardando Películas en JSON" }
        return storage.storeDataJson(file, state.value.peliculas)
    }

    fun loadPeliculasFromJson(file: File): Result<List<Pelicula>, PeliculaError> {
        logger.debug { "Cargando Películas en JSON" }
        return storage.loadDataJson(file).onSuccess {
            service.deleteAll() // Borramos todos los datos de la BD
            service.saveAll(it)
            loadAllPeliculas() // Actualizamos la lista
        }
    }

    // carga en el estado la película seleccionada
    fun updatePeliculaSeleccionada(pelicula: Pelicula) {
        logger.debug { "Actualizando estado de Película: $pelicula" }

        // Datos de la imagen
        var imagen = Image(RoutesManager.getResourceAsStream("images/sin-imagen.png"))
        var fileImage = File(RoutesManager.getResource("images/sin-imagen.png").toURI())

        storage.loadImage(pelicula.imagen).onSuccess {
            imagen = Image(it.absoluteFile.toURI().toString())
            fileImage = it
        }

        state.value = state.value.copy(
            pelicula = PeliculaFormState(
                id = pelicula.id.toString(),
                nombre = pelicula.nombre,
                duracion = pelicula.duracion,
                fechaEstreno = pelicula.fechaEstreno,
                descripcion = pelicula.descripcion,
                categoria = pelicula.categoria,
                imagen = imagen,
                fileImage = fileImage
            )
        )
    }

    // Crea una nueva película en el estado y repositorio
    fun crearPelicula(): Result<Pelicula, PeliculaError> {
        logger.debug { "Creando Película" }
        val newPeliculaTemp = state.value.pelicula.copy()
        var newPelicula = newPeliculaTemp.toModel().copy(id = Pelicula.NEW_PELICULA)
        return newPelicula.validate().andThen {
            newPeliculaTemp.fileImage?.let { newFileImage ->
                storage.saveImage(newFileImage).onSuccess {
                    newPelicula = newPelicula.copy(imagen = it.name)
                }
            }
            service.save(newPelicula).andThen {
                state.value = state.value.copy(
                    peliculas = state.value.peliculas + it
                )
                updateActualState()
                Ok(it)
            }
        }
    }

    enum class TipoImagen(val value: String) {
        SIN_IMAGEN("sin-imagen.png"), EMPTY("")
    }

    // Edita una película en el estado y repositorio
    fun editarPelicula(): Result<Pelicula, PeliculaError> {
        logger.debug { "Editando Película" }
        val updatedPeliculaTemp = state.value.pelicula.copy()
        var updatedPelicula = state.value.pelicula.toModel()
        return updatedPelicula.validate().andThen {
            updatedPeliculaTemp.fileImage?.let { newFileImage ->
                if (updatedPelicula.imagen == TipoImagen.SIN_IMAGEN.value || updatedPelicula.imagen == TipoImagen.EMPTY.value) {
                    storage.saveImage(newFileImage).onSuccess {
                        updatedPelicula = updatedPelicula.copy(imagen = it.name)
                    }
                } else {
                    storage.updateImage(updatedPelicula.imagen, newFileImage)
                }
            }
            service.save(updatedPelicula).onSuccess {
                val index = state.value.peliculas.indexOfFirst { p -> p.id == it.id }
                state.value = state.value.copy(
                    peliculas = state.value.peliculas.toMutableList().apply { this[index] = it }
                )
                updateActualState()
                Ok(it)
            }
        }
    }

    // Elimina una película en el estado y repositorio
    fun eliminarPelicula(): Result<Unit, PeliculaError> {
        logger.debug { "Eliminando Película" }
        val pelicula = state.value.pelicula.copy()
        val myId = pelicula.id.toLong()

        pelicula.fileImage?.let {
            if (it.name != TipoImagen.SIN_IMAGEN.value) {
                storage.deleteImage(it)
            }
        }

        service.deleteById(myId)
        state.value = state.value.copy(
            peliculas = state.value.peliculas.toMutableList().apply { this.removeIf { it.id == myId } }
        )
        updateActualState()
        return Ok(Unit)
    }

    // Actualiza la imagen de la película en el estado
    fun updateImagePelicula(fileImage: File) {
        logger.debug { "Actualizando imagen: $fileImage" }
        state.value = state.value.copy(
            pelicula = state.value.pelicula.copy(
                imagen = Image(fileImage.toURI().toString()),
                fileImage = fileImage,
                oldFileImage = state.value.pelicula.fileImage
            )
        )
    }

    fun exportToZip(fileToZip: File): Result<Unit, PeliculaError> {
        logger.debug { "Exportando a ZIP: $fileToZip" }
        service.findAll().andThen {
            storage.exportToZip(fileToZip, it)
        }.onFailure {
            logger.error { "Error al exportar a ZIP: ${it.message}" }
            return Err(it)
        }
        return Ok(Unit)
    }

    fun loadPeliculasFromZip(fileToUnzip: File): Result<List<Pelicula>, PeliculaError> {
        logger.debug { "Importando de ZIP: $fileToUnzip" }
        return storage.loadFromZip(fileToUnzip).onSuccess { lista ->
            service.deleteAll().andThen {
                service.saveAll(lista.map { p -> p.copy(id = Pelicula.NEW_PELICULA) })
            }.onSuccess {
                loadAllPeliculas()
            }
        }
    }

    fun changePeliculaOperacion(newValue: TipoOperacion) {
        logger.debug { "Cambiando tipo de operacion: $newValue" }
        if (newValue == TipoOperacion.EDITAR) {
            logger.debug { "Copiando estado de Película Seleccionada a Operacion" }
            state.value = state.value.copy(
                pelicula = state.value.pelicula.copy(),
                tipoOperacion = newValue
            )

        } else {
            logger.debug { "Limpiando estado de Película Operacion" }
            state.value = state.value.copy(
                pelicula = PeliculaFormState(),
                tipoOperacion = newValue
            )
        }
    }

    fun updateDataPelicula(
        nombre: String,
        duracion: String,
        fechaEstreno: LocalDate,
        descripcion: String,
        categoria: Pelicula.Categoria,
        imagePelicula: Image
    ) {
        logger.debug { "Actualizando estado de Película Operacion" }
        state.value = state.value.copy(
            pelicula = state.value.pelicula.copy(
                nombre = nombre,
                duracion = duracion,
                fechaEstreno = fechaEstreno,
                descripcion = descripcion,
                categoria = categoria,
                imagen = imagePelicula
            )
        )
    }

    // Mi estado
    enum class TipoOperacion(val value: String) {
        NUEVO("Nuevo"), EDITAR("Editar")
    }

    // Clases que representan el estado
    data class PeliculaState(
        val peliculas: List<Pelicula> = emptyList(),
        val numPeliculas: String = "0",
        val pelicula: PeliculaFormState = PeliculaFormState(),
        val tipoOperacion: TipoOperacion = TipoOperacion.NUEVO
    )

    data class PeliculaFormState(
        val id: String = "",
        val nombre: String = "",
        val duracion: String = "",
        val fechaEstreno: LocalDate = LocalDate.now(),
        val descripcion: String = "",
        val categoria: Pelicula.Categoria = Pelicula.Categoria.TERROR,
        val imagen: Image = Image(RoutesManager.getResourceAsStream("images/sin-imagen.png")),
        val fileImage: File? = null,
        val oldFileImage: File? = null
    )

}