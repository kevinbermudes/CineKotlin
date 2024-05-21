package org.example.cinefinal.productos.services.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.cinefinal.productos.dto.json.ProductoDto
import org.example.cinefinal.productos.errors.ProductoError
import org.example.cinefinal.productos.mappers.toProductoDtoList
import org.example.cinefinal.productos.mappers.toProductoModelList
import org.example.cinefinal.productos.models.Producto
import org.lighthousegames.logging.logging
import java.io.File

private val logger = logging()

class ProductosStorageJsonImpl : ProductosStorageJson {
    override fun storeDataJson(file: File, data: List<Producto>): Result<Long, ProductoError> {
        logger.debug { "Guardando datos en fichero $file" }
        return try {
            val json = Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            }
            val jsonString = json.encodeToString<List<ProductoDto>>(data.toProductoDtoList())
            file.writeText(jsonString)
            Ok(data.size.toLong())
        } catch (e: Exception) {
            Err(ProductoError.SaveJson("Error al escribir el JSON: ${e.message}"))
        }
    }

    override fun loadDataJson(file: File): Result<List<Producto>, ProductoError> {
        logger.debug { "Cargando datos en fichero $file" }
        val json = Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        }
        // Debemos decirle el tipo de datos que queremos parsear
        return try {
            val jsonString = file.readText()
            val data = json.decodeFromString<List<ProductoDto>>(jsonString)
            Ok(data.toProductoModelList())
        } catch (e: Exception) {
            Err(ProductoError.LoadJson("Error al parsear el JSON: ${e.message}"))
        }
    }
}
