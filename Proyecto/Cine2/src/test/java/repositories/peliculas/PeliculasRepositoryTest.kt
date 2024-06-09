package repositories.peliculas

import org.example.cine.database.SqlDeLightClient
import org.example.cine.peliculas.models.Pelicula
import org.example.cine.peliculas.repositories.PeliculasRepositoryImpl
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.Mockito.*
import java.time.LocalDate
import java.time.LocalDateTime

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PeliculasRepositoryTest {

    private lateinit var repository: PeliculasRepositoryImpl
    private val databaseClient = mock(SqlDeLightClient::class.java)

    @BeforeAll
    fun setUp() {
        repository = PeliculasRepositoryImpl(databaseClient)
    }

    @Test
    @Order(1)
    fun `test findAll`() {
        // Arrange
        val peliculas = listOf(
            Pelicula(
                id = 1,
                nombre = "Pelicula 1",
                duracion = "120",
                fechaEstreno = LocalDate.now(),
                descripcion = "Descripción de la Pelicula 1",
                categoria = Pelicula.Categoria.FANTASIA,
                imagen = "imagen1.png"
            ),
            Pelicula(
                id = 2,
                nombre = "Pelicula 2",
                duracion = "110",
                fechaEstreno = LocalDate.now(),
                descripcion = "Descripción de la Pelicula 2",
                categoria = Pelicula.Categoria.ANIMADA,
                imagen = "imagen2.png"
            )
        )
    }

    @Test
    @Order(2)
    fun `test findById`() {
        // Arrange
        val id = 1L
        val pelicula = Pelicula(
            id = id,
            nombre = "Pelicula 1",
            duracion = "120",
            fechaEstreno = LocalDate.now(),
            descripcion = "Descripción de la Pelicula 1",
            categoria = Pelicula.Categoria.FANTASIA,
            imagen = "imagen1.png"
        )
    }

    @Test
    @Order(3)
    fun `test save - create`() {
        // Arrange
        val pelicula = Pelicula(
            id = 0, // new pelicula
            nombre = "Pelicula Nueva",
            duracion = "100",
            fechaEstreno = LocalDate.now(),
            descripcion = "Descripción de la Pelicula Nueva",
            categoria = Pelicula.Categoria.ANIMADA,
            imagen = "imagen_nueva.png"
        )
    }

    @Test
    @Order(4)
    fun `test save - update`() {
        // Arrange
        val pelicula = Pelicula(
            id = 1, // existing pelicula
            nombre = "Pelicula Actualizada",
            duracion = "130",
            fechaEstreno = LocalDate.now(),
            descripcion = "Descripción de la Pelicula Actualizada",
            categoria = Pelicula.Categoria.TERROR,
            imagen = "imagen_actualizada.png"
        )
    }

    @Test
    @Order(5)
    fun `test deleteById`() {
        // Arrange
        val id = 1L
//        repository.deleteById(id)
    }

    @Test
    @Order(6)
    fun `test deleteAll`() {
//        // Act
//        repository.deleteAll()

//        // Assert
//        verify(databaseClient.dbQueries).deleteAll()
    }

    @Test
    @Order(7)
    fun `test saveAll`() {
        // Arrange
        val peliculas = listOf(
            Pelicula(
                id = 1,
                nombre = "Pelicula 1",
                duracion = "120",
                fechaEstreno = LocalDate.now(),
                descripcion = "Descripción de la Pelicula 1",
                categoria = Pelicula.Categoria.TERROR,
                imagen = "imagen1.png"
            ),
            Pelicula(
                id = 2,
                nombre = "Pelicula 2",
                duracion = "110",
                fechaEstreno = LocalDate.now(),
                descripcion = "Descripción de la Pelicula 2",
                categoria = Pelicula.Categoria.FANTASIA,
                imagen = "imagen2.png"
            )
        )

//
//        // Act
//        val result = repository.saveAll(peliculas)
//
//        // Assert
//        assertEquals(peliculas, result)
    }
}
