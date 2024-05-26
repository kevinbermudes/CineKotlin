package org.example.cine.peliculas.errors

sealed class ButacaError(val message: String)  {
    class LoadJson(message: String) : ButacaError(message)
    class SaveJson(message: String) : ButacaError(message)
    class LoadImage(message: String) : ButacaError(message)
    class SaveImage(message: String) : ButacaError(message)
    class DeleteImage
}