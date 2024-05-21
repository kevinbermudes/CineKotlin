package org.example.cine.di

import org.example.cine.config.AppConfig
import org.example.cine.database.SqlDeLightClient
import org.example.cine.peliculas.repositories.PeliculasRepository
import org.example.cine.peliculas.repositories.PeliculasRepositoryImpl
import org.example.cine.peliculas.service.cache.PeliculasCache
import org.example.cine.peliculas.service.cache.PeliculasCacheImpl
import org.example.cine.peliculas.service.database.PeliculasService
import org.example.cine.peliculas.service.database.PeliculasServiceImpl
import org.example.cine.peliculas.service.storage.*
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