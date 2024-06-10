package services.database.productos


import org.example.cine.productos.models.Producto
import org.example.cine.productos.repositories.ProductosRepository
import org.example.cine.productos.services.cache.ProductosCache
import org.example.cine.productos.services.database.ProductosServiceImpl
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.Mockito.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductosServiceImplTest {

    private lateinit var repository: ProductosRepository
    private lateinit var cache: ProductosCache
    private lateinit var service: ProductosServiceImpl

    @BeforeAll
    fun setUp() {
        repository = mock(ProductosRepository::class.java)
        cache = mock(ProductosCache::class.java)
        service = ProductosServiceImpl(repository, cache)
    }

    @Test
    @Order(1)
    fun `test findAll`() {
        val productos = listOf(
            Producto(
                id = 1,
                nombre = "Producto 1",
                precio = 10.0,
                categoria = Producto.Categoria.BEBIDAS,
                imagen = "imagen.png",
                stock = 5
            ),
            Producto(
                id = 2,
                nombre = "Producto 2",
                precio = 15.0,
                categoria = Producto.Categoria.FRUTOS_SECOS,
                imagen = "imagen.png",
                stock = 3
            )
        )

        `when`(repository.findAll()).thenReturn(productos)
        val result = service.findAll()
        assertEquals(productos, result.value)
    }


    @Test
    @Order(2)
    fun `test save`() {
        val producto = Producto(
            id = 1,
            nombre = "Producto 1",
            precio = 10.0,
            categoria = Producto.Categoria.BEBIDAS,
            imagen = "imagen.png",
            stock = 5
        )

        `when`(repository.save(producto)).thenReturn(producto)
    }


    @Test
    @Order(3)
    fun `test deleteById`() {
        // Arrange
        val id = 1L
        val producto = Producto(
            id = id,
            nombre = "Producto 1",
            precio = 10.0,
            categoria = Producto.Categoria.BEBIDAS,
            imagen = "imagen.png",
            stock = 5
        )
        // Act
        service.deleteById(id)
        // Assert
        verify(repository).deleteById(id)
    }

    @Test
    @Order(4)
    fun `test findById from cache`() {
        // Arrange
        val id = 1L
        val producto = Producto(
            id = id,
            nombre = "Producto 1",
            precio = 10.0,
            categoria = Producto.Categoria.FRUTOS_SECOS,
            imagen = "imagen.png",
            stock = 5
        )

        `when`(cache.get(id)).thenReturn(producto)
        // Act
        val result = service.findById(id)
        // Assert
        assertEquals(producto, result.value)
    }

    @Test
    @Order(5)
    fun `test findById not found`() {
        val id = 1L
        `when`(cache.get(id)).thenReturn(null)
        `when`(repository.findById(id)).thenReturn(null) // Simulamos que no se encontró en el repositorio
        val result = service.findById(id)
    }

    @Test
    @Order(6)
    fun `test saveAll`() {
        // Arrange
        val productos = listOf(
            Producto(
                id = 1,
                nombre = "Producto 1",
                precio = 10.0,
                categoria = Producto.Categoria.BOTANA,
                imagen = "imagen.png",
                stock = 5
            ),
            Producto(
                id = 2,
                nombre = "Producto 2",
                precio = 15.0,
                categoria = Producto.Categoria.FRUTOS_SECOS,
                imagen = "imagen.png",
                stock = 3
            )
        )
        // Act
        val result = service.saveAll(productos)

        // Assert
        verify(cache, times(1)).clear() // Verificamos que se limpie la cache
    }

    @Test
    @Order(7)
    fun `test deleteAll`() {
        val result = service.deleteAll()

        // Assert
        verify(repository).deleteAll()
    }

    @AfterAll
    fun tearDown() {
        reset(repository, cache)
    }
}
