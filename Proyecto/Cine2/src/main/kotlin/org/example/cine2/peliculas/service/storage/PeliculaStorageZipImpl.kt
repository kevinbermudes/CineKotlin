package org.example.cine2.peliculas.service.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.cine2.peliculas.errors.PeliculaError
import org.example.cine2.peliculas.models.Pelicula
import org.example.cine2.config.AppConfig
import org.lighthousegames.logging.logging
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream
import kotlin.io.path.name

private val logger = logging()

class PeliculasStorageZipImpl(
    private val appConfig: AppConfig,
    private val storageJson: PeliculasStorageJson,
) : PeliculasStorageZip {

    private val tempDirName = "peliculas"

    override fun exportToZip(fileToZip: File, data: List<Pelicula>): Result<File, PeliculaError> {
        logger.debug { "Exportando a ZIP $fileToZip" }
        // Creamos el directorio temporal
        val tempDir = Files.createTempDirectory(tempDirName)
        // copiamos todas las imágenes al directorio temporal
        return try {
            data.forEach {
                val file = File(appConfig.imagesDirectory + it.imagen)
                if (file.exists()) {
                    Files.copy(
                        file.toPath(),
                        Paths.get(tempDir.toString(), file.name),
                        StandardCopyOption.REPLACE_EXISTING
                    )
                }
            }
            // exportamos un json con el listado al directorio temporal
            storageJson.storeDataJson(File("$tempDir/data.json"), data)
            // Listamos por consola el contenido del directorio temporal
            Files.walk(tempDir).forEach { logger.debug { it } }
            // Eliminamos el directorio temporal al terminar
            // comprimimos
            val archivos = Files.walk(tempDir)
                .filter { Files.isRegularFile(it) }
                .toList()
            ZipOutputStream(Files.newOutputStream(fileToZip.toPath())).use { zip ->
                archivos.forEach { archivo ->
                    val entradaZip = ZipEntry(tempDir.relativize(archivo).toString())
                    zip.putNextEntry(entradaZip)
                    Files.copy(archivo, zip)
                    zip.closeEntry()
                }
            }
            tempDir.toFile().deleteRecursively()
            Ok(fileToZip)
        } catch (e: Exception) {
            logger.error { "Error al exportar a ZIP: ${e.message}" }
            Err(PeliculaError.ExportZip("Error al exportar a ZIP: ${e.message}"))
        }
    }

    override fun loadFromZip(fileToUnzip: File): Result<List<Pelicula>, PeliculaError> {
        logger.debug { "Importando desde ZIP $fileToUnzip" }
        // Creamos el directorio temporal
        val tempDir = Files.createTempDirectory(tempDirName)
        return try {
            // Descomprimimos a un directorio temporal
            ZipFile(fileToUnzip).use { zip ->
                zip.entries().asSequence().forEach { entrada ->
                    zip.getInputStream(entrada).use { input ->
                        Files.copy(
                            input,
                            Paths.get(tempDir.toString(), entrada.name),
                            StandardCopyOption.REPLACE_EXISTING
                        )
                    }
                }
            }
            // Listamos por consola el contenido del directorio temporal
            Files.walk(tempDir).forEach {
                // copiamos todas las imágenes, es decir, todo lo que no es .json al directorio de imágenes
                if (!it.toString().endsWith(".json") && Files.isRegularFile(it)) {
                    Files.copy(
                        it,
                        Paths.get(appConfig.imagesDirectory, it.name),
                        StandardCopyOption.REPLACE_EXISTING
                    )
                }
            }
            // tomamos el fichero data.json y lo parseamos a una lista de películas
            val data = storageJson.loadDataJson(File("$tempDir/data.json"))
            tempDir.toFile().deleteRecursively()
            return data
        } catch (e: Exception) {
            logger.error { "Error al importar desde ZIP: ${e.message}" }
            Err(PeliculaError.ImportZip("Error al importar desde ZIP: ${e.message}"))
        }
    }
}