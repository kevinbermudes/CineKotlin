// src/main/kotlin/org/example/cine2/di/AppModule.kt

package org.example.cinefinal.di

import org.example.cinefinal.config.AppConfig
import org.example.cinefinal.database.SqlDeLightClient
import org.example.cinefinal.peliculas.ViewModel.CineViewModel
import org.example.cinefinal.peliculas.repositories.PeliculasRepository
import org.example.cinefinal.peliculas.repositories.PeliculasRepositoryImpl
import org.example.cinefinal.peliculas.service.cache.PeliculasCache
import org.example.cinefinal.peliculas.service.cache.PeliculasCacheImpl
import org.example.cinefinal.peliculas.service.database.PeliculasService
import org.example.cinefinal.peliculas.service.database.PeliculasServiceImpl
import org.example.cinefinal.peliculas.service.storage.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::AppConfig)

    singleOf(::SqlDeLightClient)

    singleOf(::PeliculasRepositoryImpl) {
        bind<PeliculasRepository>()
    }

//    singleOf(::PeliculasStorageJsonImpl) {
//        bind<PeliculasStorageJson>()
//    }
//    singleOf(::PeliculasStorageImagesImpl) {
//        bind<PeliculasStorageImages>()
//    }

    singleOf(::PeliculasStorageZipImpl) {
        bind<PeliculasStorageZip>()
    }



    singleOf(::PeliculasStorageImpl) {
        bind<PeliculasStorage>()
    }

    singleOf(::PeliculasCacheImpl) {
        bind<PeliculasCache>()
    }

    singleOf(::PeliculasServiceImpl) {
        bind<PeliculasService>()
    }

    singleOf(::CineViewModel)
}
