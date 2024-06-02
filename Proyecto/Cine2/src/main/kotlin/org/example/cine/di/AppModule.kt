package org.example.cine.di

import org.example.cine.Usuario.repositories.UsuarioRepository
import org.example.cine.config.AppConfig
import org.example.cine.database.SqlDeLightClient
import org.example.cine.pago.models.Carrito
import org.example.cine.peliculas.ViewModel.CineViewModel
import org.example.cine.peliculas.repositories.PeliculasRepository
import org.example.cine.peliculas.repositories.PeliculasRepositoryImpl
import org.example.cine.peliculas.service.cache.PeliculasCache
import org.example.cine.peliculas.service.cache.PeliculasCacheImpl
import org.example.cine.peliculas.service.database.PeliculasService
import org.example.cine.peliculas.service.database.PeliculasServiceImpl
import org.example.cine.peliculas.service.storage.*
import org.example.cine.productos.repositories.ProductosRepository
import org.example.cine.productos.repositories.ProductosRepositoryImpl
import org.example.cine.productos.services.cache.ProductosCache
import org.example.cine.productos.services.cache.ProductosCacheImpl
import org.example.cine.productos.services.database.ProductosService
import org.example.cine.productos.services.database.ProductosServiceImpl
import org.example.cine.productos.services.storage.*
import org.example.cine.productos.viewmodels.ProductosViewModel
import org.example.cine.repositories.UsuarioRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::AppConfig)

    singleOf(::SqlDeLightClient)

    // Películas
    singleOf(::PeliculasRepositoryImpl) {
        bind<PeliculasRepository>()
    }

    singleOf(::PeliculasStorageJsonImpl) {
        bind<PeliculasStorageJson>()
    }

    singleOf(::PeliculasStorageZipImpl) {
        bind<PeliculasStorageZip>()
    }

    singleOf(::PeliculasStorageImagesImpl) {
        bind<PeliculasStorageImages>()
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

    singleOf(::CineViewModel) {
        bind<CineViewModel>()
    }

    // Productos
    singleOf(::ProductosRepositoryImpl) {
        bind<ProductosRepository>()
    }

    singleOf(::ProductosStorageJsonImpl) {
        bind<ProductosStorageJson>()
    }

    singleOf(::ProductosStorageZipImpl) {
        bind<ProductosStorageZip>()
    }

    singleOf(::ProductosStorageImagesImpl) {
        bind<ProductosStorageImages>()
    }

    singleOf(::ProductosStorageImpl) {
        bind<ProductosStorage>()
    }

    singleOf(::ProductosCacheImpl) {
        bind<ProductosCache>()
    }

    singleOf(::ProductosServiceImpl) {
        bind<ProductosService>()
    }

    singleOf(::ProductosViewModel) {
        bind<ProductosViewModel>()
    }

    // Usuarios
    singleOf(::UsuarioRepositoryImpl) {
        bind<UsuarioRepository>()
    }
    single { Carrito.instance }

    // Butacas
    singleOf(::ButacasStorageJsonImpl) {
        bind<ButacasStorageJson>()
    }
}
