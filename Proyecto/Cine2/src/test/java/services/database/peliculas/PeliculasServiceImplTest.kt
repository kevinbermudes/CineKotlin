package services.database.peliculas

import com.github.michaelbull.result.Ok
import org.example.cine.peliculas.models.Pelicula
import org.example.cine.peliculas.repositories.PeliculasRepository
import org.example.cine.peliculas.service.cache.PeliculasCache
import org.example.cine.peliculas.service.database.PeliculasServiceImpl
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.mockito.Mockito.*
import java.time.LocalDate

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PeliculasServiceImplTest {

    private lateinit var repository: PeliculasRepository
    private lateinit var cache: PeliculasCache
    private lateinit var service: PeliculasServiceImpl

    @BeforeAll
    fun setUp() {
        repository = mock(PeliculasRepository::class.java)
        cache = mock(PeliculasCache::class.java)
        service = PeliculasServiceImpl(repository, cache)
    }

    @Test
    @Order(1)
    fun `test findAll`() {
        val peliculas = listOf(
            Pelicula(id = 1, nombre = "Pelicula 1", duracion = "120", fechaEstreno = LocalDate.now(), imagen = "imagen.png", descripcion = "una descripción", categoria = Pelicula.Categoria.TERROR,),
            Pelicula(id = 2, nombre = "Pelicula 2", duracion = "110", fechaEstreno = LocalDate.now(), imagen = "imagen.png", descripcion = "una descripción", categoria = Pelicula.Categoria.TERROR,),
        )

        `when`(repository.findAll()).thenReturn(peliculas)
        val result = service.findAll()
        assertEquals(Ok(peliculas), result)
    }

    @Test
    @Order(2)
    fun `test save`() {
        val pelicula = Pelicula(id = 1, nombre = "Pelicula 1", duracion = "120", fechaEstreno = LocalDate.now(), imagen = "imagen.png", descripcion = "una descripción", categoria = Pelicula.Categoria.TERROR,)

        `when`(repository.save(pelicula)).thenReturn(pelicula)
        val result = service.save(pelicula)
        assertEquals(Ok(pelicula), result)
    }

    @Test
    @Order(3)
    fun `test deleteById`() {
        // Arrange
        val id = 1L

        // Act
        val result = service.deleteById(id)

        // Assert
        verify(repository).deleteById(id)
        assertEquals(Ok(Unit), result)
    }

    @Test
    @Order(4)
    fun `test findById from cache`() {
        // Arrange
        val id = 1L
        val pelicula = Pelicula(id = id, nombre = "Pelicula 1", duracion = "120", fechaEstreno = LocalDate.now(), imagen = "imagen.png", descripcion = "una descripción", categoria = Pelicula.Categoria.TERROR,)

        `when`(cache.get(id)).thenReturn(pelicula)

        // Act
        val result = service.findById(id)

        // Assert
        assertEquals(Ok(pelicula), result)
    }

    @Test
    @Order(5)
    fun `test findById not found`() {
        // Arrange
        val id = 1L

        `when`(cache.get(id)).thenReturn(null)
        `when`(repository.findById(id)).thenReturn(null)

        // Act
        val result = service.findById(id)

        // Assert
        assertTrue(result.isErr)
    }

    @Test
    @Order(6)
    fun `test saveAll`() {
        // Arrange
        val peliculas = listOf(
            Pelicula(id = 1, nombre = "Pelicula 1", duracion = "120", fechaEstreno = LocalDate.now(), imagen = "imagen.png", descripcion = "una descripción", categoria = Pelicula.Categoria.TERROR,),
            Pelicula(id = 2, nombre = "Pelicula 2", duracion = "110", fechaEstreno = LocalDate.now(), imagen = "imagen.png", descripcion = "una descripción", categoria = Pelicula.Categoria.TERROR,),
        )

        // Act
        val result = service.saveAll(peliculas)

        // Assert
        verify(repository).saveAll(peliculas)
        verify(cache).clear()
    }

    @Test
    @Order(7)
    fun `test deleteAll`() {
        // Act
        val result = service.deleteAll()

        // Assert
        verify(repository).deleteAll()
        assertEquals(Ok(Unit), result)
    }

    @AfterAll
    fun tearDown() {
        reset(repository, cache)
    }
}
