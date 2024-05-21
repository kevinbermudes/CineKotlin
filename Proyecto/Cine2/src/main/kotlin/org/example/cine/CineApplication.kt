package org.example.cine

import javafx.application.Application
import javafx.stage.Stage
import org.example.cine.di.appModule
import org.example.cine.route.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import java.time.LocalDateTime

class CineApplication : Application(), KoinComponent {

    init {
        println(LocalDateTime.now().toString())
        // Inicializar Koin
        startKoin {
            //printLogger() // Logger de Koin
            modules(appModule) // Módulos de Koin
        }
    }

    override fun start(stage: Stage) {
        // Le pasamos la aplicación a la clase RoutesManager
        RoutesManager.apply {
            app = this@CineApplication
        }.run {
            // Iniciamos la aplicación
            initMainStage(stage)
        }
    }

    // Cuando se para la aplicación (puedes descomentar si es necesario)
    /*
    override fun stop() {
        // No hacemos nada
    }
    */
}

fun main() {
    // No hagas nada aquí porque abre un hilo de ejecución
    Application.launch(CineApplication::class.java)
}
