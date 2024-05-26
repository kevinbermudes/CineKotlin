package org.example.cine.peliculas.service.storage

import org.example.cine.peliculas.errors.ButacaError
import org.example.cine.peliculas.models.Butaca
import java.io.File
import com.github.michaelbull.result.Result

interface ButacasStorageJson {
    fun storeDataJson(file: File, data: List<Butaca>): Result<Long, ButacaError>
    fun loadDataJson(file: File): Result<List<Butaca>, ButacaError>
}