package org.example.cine2.peliculas.service.storage

import com.github.michaelbull.result.Result
import org.example.cine2.config.AppConfig
import org.example.cine2.peliculas.errors.PeliculaError
import org.example.cine2.peliculas.models.Pelicula
import org.lighthousegames.logging.logging
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths


private val logger = logging()

class PeliculasStorageImpl(
    private val appConfig: AppConfig,
    private val storageJson: PeliculasStorageJson,
    private val storageZip: PeliculasStorageZip,
    private val storageImage: PeliculasStorageImages,
) : PeliculasStorage {

    init {
        // Creamos el directorio de imágenes si no existe
        logger.debug { "Creando directorio de imágenes si no existe" }
        Files.createDirectories(Paths.get(appConfig.imagesDirectory))
    }

    override fun storeDataJson(file: File, data: List<Pelicula>): Result<Long, PeliculaError> {
        logger.debug { "Guardando datos en fichero $file" }
        return storageJson.storeDataJson(file, data)
    }

    override fun loadDataJson(file: File): Result<List<Pelicula>, PeliculaError> {
        logger.debug { "Cargando datos en fichero $file" }
        return storageJson.loadDataJson(file)
    }

    override fun saveImage(fileName: File): Result<File, PeliculaError> {
        logger.debug { "Guardando imagen $fileName" }
        return storageImage.saveImage(fileName)
    }

    override fun loadImage(fileName: String): Result<File, PeliculaError> {
        logger.debug { "Cargando imagen $fileName" }
        return storageImage.loadImage(fileName)
    }

    override fun deleteImage(fileImage: File): Result<Unit, PeliculaError> {
        logger.debug { "Borrando imagen $fileImage" }
        return storageImage.deleteImage(fileImage)
    }

    override fun deleteAllImages(): Result<Long, PeliculaError> {
        logger.debug { "Borrando todas las imágenes" }
        return storageImage.deleteAllImages()
    }

    override fun updateImage(imageName: String, newFileImage: File): Result<File, PeliculaError> {
        logger.debug { "Actualizando imagen $imageName" }
        return storageImage.updateImage(imageName, newFileImage)
    }

    override fun exportToZip(fileToZip: File, data: List<Pelicula>): Result<File, PeliculaError> {
        logger.debug { "Exportando a ZIP $fileToZip" }
        return storageZip.exportToZip(fileToZip, data)
    }

    override fun loadFromZip(fileToUnzip: File): Result<List<Pelicula>, PeliculaError> {
        logger.debug { "Importando desde ZIP $fileToUnzip" }
        return storageZip.loadFromZip(fileToUnzip)
    }
}