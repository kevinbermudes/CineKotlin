package org.example.cine2.productos.errors

sealed class ProductoError(val message: String) {
    class NotFound(message: String) : ProductoError(message)
    class LoadJson(message: String) : ProductoError(message)
    class SaveJson(message: String) : ProductoError(message)
    class LoadImage(message: String) : ProductoError(message)
    class SaveImage(message: String) : ProductoError(message)
    class deleteImage(message: String) : ProductoError(message)
    class deletebyId(message: String) : ProductoError(message)
    class ExportZip(message: String) : ProductoError(message)
    class ImportZip(message: String) : ProductoError(message)
    class ValidationProblem(message: String) : ProductoError(message)
}