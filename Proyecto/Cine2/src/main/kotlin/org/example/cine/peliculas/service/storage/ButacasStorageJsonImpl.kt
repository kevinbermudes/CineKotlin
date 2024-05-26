package org.example.cine.peliculas.service.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.cine.peliculas.errors.ButacaError
import org.example.cine.peliculas.models.Butaca
import org.example.cine.peliculas.models.ButacasContainer
import org.lighthousegames.logging.logging
import java.io.File

private val logger = logging()

class ButacasStorageJsonImpl : ButacasStorageJson {
    override fun storeDataJson(file: File, data: List<Butaca>): Result<Long, ButacaError> {
        logger.debug { "Guardando datos en fichero $file" }
        return try {
            val json = Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            }
            val container = ButacasContainer(data)
            val jsonString = json.encodeToString(container)
            file.writeText(jsonString)
            Ok(data.size.toLong())
        } catch (e: Exception) {
            Err(ButacaError.SaveJson("Error al escribir el JSON: ${e.message}"))
        }
    }

    override fun loadDataJson(file: File): Result<List<Butaca>, ButacaError> {
        logger.debug { "Cargando datos en fichero $file" }
        val json = Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        }
        return try {
            val jsonString = file.readText()
            val container = json.decodeFromString<ButacasContainer>(jsonString)
            Ok(container.butacas)
        } catch (e: Exception) {
            Err(ButacaError.LoadJson("Error al parsear el JSON: ${e.message}"))
        }
    }
}
