package org.example.cine2.productos.services.storage

import com.github.michaelbull.result.Result
import org.example.cine2.config.AppConfig
import org.example.cine2.productos.errors.ProductoError
import org.example.cine2.productos.models.Producto
import org.lighthousegames.logging.logging
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
private val logger = logging()
class ProductosStorageImpl(
        private val appConfig: AppConfig,
    private val storageJson: ProductosStorageJson,
    private val storageZip: ProductosStorageZip,
    private val storageImage: ProductosStorageImages): ProductosStorage{
    init {
        logger.debug { "Creando directorio de imagenes si no existe" }
        Files.createDirectories(Paths.get(appConfig.imagesDirectory))
    }

   override fun storeDataJson(file: File, data: List<Producto>): Result<Long, ProductoError> {
        logger.debug { "Guardando datos en fichero $file" }
        return storageJson.storeDataJson(file, data)
    }

    override fun loadDataJson(file: File): Result<List<Producto>, ProductoError> {
        logger.debug { "Cargando datos en fichero $file" }
        return storageJson.loadDataJson(file)

    }


    override fun saveImage(fileName: File): Result<File, ProductoError> {
        logger.debug { "Guardando imagen $fileName" }
        return storageImage.saveImage(fileName)
    }

    override fun loadImage(fileName: String): Result<File, ProductoError> {
        logger.debug { "Cargando imagen $fileName" }
        return storageImage.loadImage(fileName)
    }

    override fun deleteImage(fileImage: File): Result<Unit, ProductoError> {
        logger.debug { "Borrando imagen $fileImage" }
        return storageImage.deleteImage(fileImage)
    }

    override fun deleteAllImages(): Result<Long, ProductoError> {
        logger.debug { "Borrando todas las imagenes" }
        return storageImage.deleteAllImages()
    }

    override fun updateImage(imagenName: String, newFileImage: File): Result<File, ProductoError> {
        logger.debug { "Actualizando imagen $imagenName" }
        return storageImage.updateImage(imagenName, newFileImage)
    }

    override fun exportToZip(fileToZip: File, data: List<Producto>): Result<File, ProductoError> {
        logger.debug { "Exportando a ZIP $fileToZip" }
        return storageZip.exportToZip(fileToZip, data)
    }

    override fun loadFromZip(fileToUnzip: File): Result<List<Producto>, ProductoError> {
        logger.debug { "Importando desde ZIP $fileToUnzip" }
        return storageZip.loadFromZip(fileToUnzip)
    }
}