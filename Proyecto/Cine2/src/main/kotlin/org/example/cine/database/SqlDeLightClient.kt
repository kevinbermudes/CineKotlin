package org.example.cine.database

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import database.CineQueries
import database.ProdutosQueries
import dev.cine2.database.AppDatabase
import org.example.cine.config.AppConfig
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
    val pruductodbQueries: ProdutosQueries by lazy {
        JdbcSqliteDriver(appConfig.databaseUrl).let { driver ->
            // Creamos la base de datos
            logger.debug { "SqlDeLightClient.init() - Create Schemas" }
            AppDatabase.Schema.create(driver)
            AppDatabase(driver)
        }.produtosQueries
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

        pruductodbQueries.transaction {
            pruductodbQueries.deleteAll()
        }
    }
}