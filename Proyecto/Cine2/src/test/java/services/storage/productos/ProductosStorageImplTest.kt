package services.storage.productos

import org.example.cine.productos.models.Producto
import org.example.cine.productos.services.storage.ProductosStorageJsonImpl
import org.junit.jupiter.api.*
import java.io.File

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class ProductosStorageImplTest {
    private lateinit var productosStorageJson: ProductosStorageJsonImpl
    private lateinit var myFile: File

    @BeforeEach
    fun setup() {
        productosStorageJson = ProductosStorageJsonImpl()
//        myFile = File("test.json")
    }

    @AfterEach
    fun tearDown() {
//        Files.deleteIfExists(myFile.toPath())
    }

    @Test
    @Order(1)
    fun `storeDataJson returns Ok when data is successfully written to file`() {
        val data = listOf(
            Producto(
                id = 1,
                nombre = "Producto 1",
                precio = 10.0,
                categoria = Producto.Categoria.BOTANA,
                imagen = "imagen.png",
                stock = 5
            )
        )
    }

    @Test
    fun `loadDataJson returns Ok when data is successfully read from file`() {
        val data = listOf(
            Producto(
                id = 1,
                nombre = "Producto 1",
                precio = 10.0,
                categoria = Producto.Categoria.BOTANA,
                imagen = "imagen.png",
                stock = 5
            )
        )
    }
}