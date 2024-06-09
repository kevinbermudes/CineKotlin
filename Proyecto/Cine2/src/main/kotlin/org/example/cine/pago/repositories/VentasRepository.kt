package org.example.cine.pago.repositories

import org.example.cine.pago.models.Venta

interface VentasRepository {
    fun findAll(): List<Venta>
    fun findById(id: Long): Venta?
    fun save(venta: Venta): Venta
    fun deleteById(id: Long)
    fun deleteAll(venta: List<Venta>):List<Venta>
}