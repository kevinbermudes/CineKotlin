package org.example.cinefinal

import javafx.application.Application

import javafx.stage.Stage
import org.example.cinefinal.di.appModule
import org.example.cinefinal.route.RoutesManager
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.inject

class CineApplication : Application() {
    private val routesManager: RoutesManager by inject(RoutesManager::class.java)

    override fun start(primaryStage: Stage) {
        // Inicializar Koin
        startKoin {
            modules(appModule)
        }
        routesManager.app = this
        routesManager.initMainStage(primaryStage)
    }


}

fun main() {
    Application.launch(CineApplication::class.java)


}