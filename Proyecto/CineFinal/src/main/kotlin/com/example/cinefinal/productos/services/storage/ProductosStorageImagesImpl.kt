package org.example.cinefinal.productos.services.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.cinefinal.config.AppConfig
import org.example.cinefinal.productos.errors.ProductoError
import org.lighthousegames.logging.logging
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.time.Instant

private val logger = logging()

class ProductosStorageImagesImpl(private val appConfig: AppConfig) : ProductosStorageImages {
    private fun getImagenName(newFileImage: File): String {
        val name = newFileImage.name
        val extension = name.substring(name.lastIndexOf(".") + 1)
        return "${Instant.now().toEpochMilli()}.$extension"
    }

    override fun saveImage(fileName: File): Result<File, ProductoError> {
        logger.debug { "Guardando imagen $fileName" }
        return try {
            val newFileImage = File(appConfig.imagesDirectory + getImagenName(fileName))
            Files.copy(fileName.toPath(), newFileImage.toPath(), StandardCopyOption.REPLACE_EXISTING)
            Ok(newFileImage)
        } catch (e: Exception) {
            Err(ProductoError.SaveImage("Error al guardar la imagen: ${e.message}"))
        }
    }

    override fun loadImage(fileName: String): Result<File, ProductoError> {
        logger.debug { "Cargando imagen $fileName" }
        val file = File(appConfig.imagesDirectory + fileName)
        return if (file.exists()) {
            Ok(file)
        } else {
            Err(ProductoError.LoadImage("La imagen no existe: ${file.name}"))
        }
    }

    override fun deleteImage(fileName: File): Result<Unit, ProductoError> {
        Files.deleteIfExists(fileName.toPath())
        return Ok(Unit)
    }

    override fun deleteAllImages(): Result<Long, ProductoError> {
        logger.debug { "Borrando todas las imagenes" }
        return try {
            Ok(
                Files.walk(Paths.get(appConfig.imagesDirectory))
                    .filter { Files.isRegularFile(it) }
                    .map { Files.deleteIfExists(it) }
                    .count())
        } catch (e: Exception) {
            Err(ProductoError.deleteImage("Error al borrar todas las imagenes: ${e.message}"))
        }
    }

    override fun updateImage(imagenName: String, newFileImage: File): Result<File, ProductoError> {
        logger.debug { "Actualizando imagen $imagenName" }
        return try {
            val newUpdateImage = File(appConfig.imagesDirectory + imagenName)
            Files.copy(newFileImage.toPath(), newUpdateImage.toPath(), StandardCopyOption.REPLACE_EXISTING)
            Ok(newUpdateImage)
        } catch (e: Exception) {
            Err(ProductoError.SaveImage("Error al guardar la imagen: ${e.message}"))
        }
    }
}