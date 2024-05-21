package org.example.cine.peliculas.errors


sealed class PeliculaError(val message: String) {
    class LoadJson(message: String) : PeliculaError(message)
    class SaveJson(message: String) : PeliculaError(message)
    class LoadImage(message: String) : PeliculaError(message)
    class SaveImage(message: String) : PeliculaError(message)
    class DeleteImage(message: String) : PeliculaError(message)
    class DeleteById(message: String) : PeliculaError(message)
    class ValidationProblem(message: String) : PeliculaError(message)
    class NotFound(message: String) : PeliculaError(message)
    class ExportZip(message: String) : PeliculaError(message)
    class ImportZip(message: String) : PeliculaError(message)
}