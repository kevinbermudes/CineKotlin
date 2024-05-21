package org.example.cine.peliculas.service.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.cine.peliculas.dto.PeliculaDto
import org.example.cine.peliculas.errors.PeliculaError
import org.example.cine.peliculas.mapers.toDtoList
import org.example.cine.peliculas.mapers.toModelFromDtoList
import org.example.cine.peliculas.models.Pelicula
import org.lighthousegames.logging.logging
import java.io.File

private val logger = logging()

class PeliculasStorageJsonImpl : PeliculasStorageJson {
    override fun storeDataJson(file: File, data: List<Pelicula>): Result<Long, PeliculaError> {
        logger.debug { "Guardando datos en fichero $file" }
        return try {
            val json = Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            }
            val jsonString = json.encodeToString<List<PeliculaDto>>(data.toDtoList())
            file.writeText(jsonString)
            Ok(data.size.toLong())
        } catch (e: Exception) {
            Err(PeliculaError.SaveJson("Error al escribir el JSON: ${e.message}"))
        }
    }

    override fun loadDataJson(file: File): Result<List<Pelicula>, PeliculaError> {
        logger.debug { "Cargando datos en fichero $file" }
        val json = Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        }
        return try {
            val jsonString = file.readText()
            val data = json.decodeFromString<List<PeliculaDto>>(jsonString)
            Ok(data.toModelFromDtoList())
        } catch (e: Exception) {
            Err(PeliculaError.LoadJson("Error al parsear el JSON: ${e.message}"))
        }
    }
}
