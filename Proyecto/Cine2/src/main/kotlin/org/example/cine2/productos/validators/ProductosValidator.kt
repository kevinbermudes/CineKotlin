package org.example.cine2.productos.validators

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.cine2.productos.errors.ProductoError
import org.example.cine2.productos.models.Producto

fun Producto.validate(): Result<Producto, ProductoError> {
    if (this.nombre.isEmpty() || this.nombre.isBlank()) {
        return Err(ProductoError.ValidationProblem("El nombre no puede estar vacío"))
    }
    if (this.precio < 0) {
        return Err(ProductoError.ValidationProblem("El precio no puede ser negativo"))
    }
    if (this.imagen.isEmpty() || this.imagen.isBlank()) {
        return Err(ProductoError.ValidationProblem("La imagen no puede estar vacía"))
    }
    return Ok(this)
}