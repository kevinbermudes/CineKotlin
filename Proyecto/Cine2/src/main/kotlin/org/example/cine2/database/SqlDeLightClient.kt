package org.example.cine2.database

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import database.CineQueries
import dev.cine2.database.AppDatabase
import dev.joseluisgs.expedientesacademicos.config.AppConfig
import org.lighthousegames.logging.logging
import java.io.File
import java.nio.file.Files

private val logger = logging()

class SqlDeLightClient(
    private val appConfig: AppConfig
) {
    val dbQueries: CineQueries by lazy {
        JdbcSqliteDriver(appConfig.databaseUrl).let { driver ->
            // Creamos la base de datos
            logger.debug { "SqlDeLightClient.init() - Create Schemas" }
            AppDatabase.Schema.create(driver)
            AppDatabase(driver)
        }.cineQueries
    }

    init {
        logger.debug { "Inicializando el gestor de Bases de Datos" }
        // Borramos la base de datos
        if (appConfig.databaseInit) {
            logger.debug { "Borrando la base de datos" }
            Files.deleteIfExists(File(appConfig.databaseUrl.removePrefix("jdbc:sqlite:")).toPath())
        }

        if (appConfig.databaseRemoveData) {
            clearData()
        }
    }

    private fun clearData() {
        logger.debug { "Borrando datos de la base de datos" }
        dbQueries.transaction {
            dbQueries.deleteAll()
        }
    }
}