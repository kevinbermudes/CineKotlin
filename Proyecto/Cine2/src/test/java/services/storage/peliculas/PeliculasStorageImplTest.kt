package services.storage.peliculas

import org.example.cine.peliculas.models.Pelicula
import org.example.cine.peliculas.service.storage.PeliculasStorageJsonImpl
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import java.io.File
import java.time.LocalDate

class PeliculasStorageImplTest {
    private lateinit var peliculasStorage: PeliculasStorageJsonImpl
    private lateinit var myFile: File

    @BeforeEach
    fun setup() {
        peliculasStorage = PeliculasStorageJsonImpl()
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
           Pelicula(
               id = 1,
               nombre = "Pelicula 1",
               duracion = "2h",
               fechaEstreno = LocalDate.now(),
               imagen = "imagen.png",
               descripcion = "una descripción",
               categoria = Pelicula.Categoria.TERROR
           )
        )

//        val result = peliculasStorage.storeDataJson(myFile, data)
//
//        assertTrue(result.isOk)
//        assertEquals(data.size.toLong(), result.value)
    }

    @Test
    fun `loadDataJson returns Ok when data is successfully read from file`() {
        val data = listOf(
            Pelicula(
                id = 1,
                nombre = "Pelicula 1",
                duracion = "2h",
                fechaEstreno = LocalDate.now(),
                imagen = "imagen.png",
                descripcion = "una descripción",
                categoria = Pelicula.Categoria.TERROR
            )
        )

//          peliculasStorage.storeDataJson(myFile, data)
//
//         val result = peliculasStorage.loadDataJson(myFile)
//
//        assertTrue(result.isOk)
//        assertEquals(data, result.value)
    }
}
