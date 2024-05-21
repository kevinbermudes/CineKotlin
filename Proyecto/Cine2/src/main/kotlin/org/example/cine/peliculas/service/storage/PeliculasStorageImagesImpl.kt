package org.example.cine.peliculas.service.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.cine.config.AppConfig
import org.example.cine.peliculas.errors.PeliculaError
import org.lighthousegames.logging.logging
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.time.Instant

private val logger = logging()

class PeliculasStorageImagesImpl(
    private val appConfig: AppConfig
) : PeliculasStorageImages {

    private fun getImagenName(newFileImage: File): String {
        val name = newFileImage.name
        val extension = name.substring(name.lastIndexOf(".") + 1)
        return "${Instant.now().toEpochMilli()}.$extension"
    }

    override fun saveImage(fileName: File): Result<File, PeliculaError> {
        logger.debug { "Guardando imagen $fileName" }
        return try {
            val newFileImage = File(appConfig.imagesDirectory + getImagenName(fileName))
            Files.copy(fileName.toPath(), newFileImage.toPath(), StandardCopyOption.REPLACE_EXISTING)
            Ok(newFileImage)
        } catch (e: Exception) {
            Err(PeliculaError.SaveImage("Error al guardar la imagen: ${e.message}"))
        }
    }

    override fun loadImage(fileName: String): Result<File, PeliculaError> {
        logger.debug { "Cargando imagen $fileName" }
        val file = File(appConfig.imagesDirectory + fileName)
        return if (file.exists()) {
            Ok(file)
        } else {
            Err(PeliculaError.LoadImage("La imagen no existe: ${file.name}"))
        }
    }

    override fun deleteImage(fileName: File): Result<Unit, PeliculaError> {
        Files.deleteIfExists(fileName.toPath())
        return Ok(Unit)
    }

    override fun deleteAllImages(): Result<Long, PeliculaError> {
        logger.debug { "Borrando todas las imágenes" }
        return try {
            Ok(Files.walk(Paths.get(appConfig.imagesDirectory))
                .filter { Files.isRegularFile(it) }
                .map { Files.deleteIfExists(it) }
                .count())
        } catch (e: Exception) {
            Err(PeliculaError.DeleteImage("Error al borrar todas las imágenes: ${e.message}"))
        }
    }

    override fun updateImage(imagenName: String, newFileImage: File): Result<File, PeliculaError> {
        logger.debug { "Actualizando imagen $imagenName" }
        return try {
            val newUpdateImage = File(appConfig.imagesDirectory + imagenName)
            Files.copy(newFileImage.toPath(), newUpdateImage.toPath(), StandardCopyOption.REPLACE_EXISTING)
            Ok(newUpdateImage)
        } catch (e: Exception) {
            Err(PeliculaError.SaveImage("Error al guardar la imagen: ${e.message}"))
        }
    }
}
