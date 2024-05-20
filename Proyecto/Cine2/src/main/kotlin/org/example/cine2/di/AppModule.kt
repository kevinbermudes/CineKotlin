package org.example.cine2.di

import org.example.cine2.config.AppConfig
import org.example.cine2.database.SqlDeLightClient
import org.example.cine2.peliculas.repositories.PeliculasRepository
import org.example.cine2.peliculas.repositories.PeliculasRepositoryImpl
import org.example.cine2.peliculas.service.cache.PeliculasCache
import org.example.cine2.peliculas.service.cache.PeliculasCacheImpl
import org.example.cine2.peliculas.service.database.PeliculasService
import org.example.cine2.peliculas.service.database.PeliculasServiceImpl
import org.example.cine2.peliculas.service.storage.*
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

    singleOf(::PeliculasStorageZipImpl) {
        bind<PeliculasStorageZip>()
    }

//    singleOf(::PeliculasStorageImagesImpl) {
//        bind<PeliculasStorageImages>()
//    }

    singleOf(::PeliculasStorageImpl) {
        bind<PeliculasStorage>()
    }

    singleOf(::PeliculasCacheImpl) {
        bind<PeliculasCache>()
    }

    singleOf(::PeliculasServiceImpl) {
        bind<PeliculasService>()
    }

    //singleOf(::CineViewModel)
}