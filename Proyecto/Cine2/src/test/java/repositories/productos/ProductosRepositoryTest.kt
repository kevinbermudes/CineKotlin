package repositories.productos

import org.example.cine.database.SqlDeLightClient
import org.example.cine.productos.models.Producto
import org.example.cine.productos.repositories.ProductosRepositoryImpl
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import java.time.LocalDateTime

class ProductosRepositoryTest {
    private lateinit var repository: ProductosRepositoryImpl
    private val databaseClient = mock(SqlDeLightClient::class.java)

    @Test
    @Order(1)
    fun `test findAll`() {
        // Arrange
        val productos = listOf(
            Producto(
                id = 1,
                nombre = "Producto 1",
                precio = 10.0,
                categoria = Producto.Categoria.BOTANA,
                imagen = "imagen.png",
                stock = 5,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            ),
            Producto(
                id = 2,
                nombre = "Producto 2",
                precio = 15.0,
                categoria = Producto.Categoria.FRUTOS_SECOS,
                imagen = "imagen.png",
                stock = 3,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
        )
    }

    @Test
    @Order(2)
    fun `test findById`() {
        // Arrange
        val id = 1L
        val producto = Producto(
            id = id,
            nombre = "Producto 1",
            precio = 10.0,
            categoria = Producto.Categoria.BOTANA,
            imagen = "imagen.png",
            stock = 5,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

    }

    @Test
    @Order(3)
    fun `test save - create`() {
        // Arrange
        val producto = Producto(
            id = 0, // new producto
            nombre = "Producto 1",
            precio = 10.0,
            categoria = Producto.Categoria.BOTANA,
            imagen = "imagen.png",
            stock = 5,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        val insertedProducto = Producto(
            id = 1, // inserted id
            nombre = "Producto 1",
            precio = 10.0,
            categoria = Producto.Categoria.BOTANA,
            imagen = "imagen.png",
            stock = 5,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    }

    @Test
    @Order(4)
    fun `test save - update`() {
        // Arrange
        val producto = Producto(
            id = 1, // existing producto
            nombre = "Producto 1 Modificado",
            precio = 15.0,
            categoria = Producto.Categoria.BOTANA,
            imagen = "imagen_modificada.png",
            stock = 8,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    }

    @Test
    @Order(5)
    fun `test deleteById`() {
        // Arrange
        val id = 1L

        // Act
//        repository.deleteById(id)
    }

    @Test
    @Order(6)
    fun `test deleteAll`() {
        // Act
//        repository.deleteAll()

        // Assert
        // verify(databaseClient.productQueries, times(1)).deleteAll()
    }

    @Test
    @Order(7)
    fun `test saveAll`() {
        // Arrange
        val productos = listOf(
            Producto(
                id = 1,
                nombre = "Producto 1",
                precio = 10.0,
                categoria = Producto.Categoria.BOTANA,
                imagen = "imagen.png",
                stock = 5,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            ),
            Producto(
                id = 2,
                nombre = "Producto 2",
                precio = 15.0,
                categoria = Producto.Categoria.FRUTOS_SECOS,
                imagen = "imagen.png",
                stock = 3,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
        )
    }


}