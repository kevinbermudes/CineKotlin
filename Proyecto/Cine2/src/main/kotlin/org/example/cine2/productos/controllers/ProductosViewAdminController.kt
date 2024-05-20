package org.example.cine2.productos.controllers

import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.image.ImageView
import org.example.cine2.productos.models.Producto

class ProductosViewAdminController {
    @FXML
    private lateinit var butonFile: Button

    @FXML
    private lateinit var butonCerrarSesion: Button

    @FXML
    private lateinit var tableProductos: TableView<Producto>

    @FXML
    private lateinit var tableColumnIdProducto: TableColumn<Producto, Long>

    @FXML
    private lateinit var tableColumnNombreProducto: TableColumn<Producto, String>

    @FXML
    private lateinit var tableColumnPrecio: TableColumn<Producto, Double>

    @FXML
    private lateinit var tableColumnCategoria: TableColumn<Producto, String>

    @FXML
    private lateinit var imagenProductos: ImageView

    @FXML
    private lateinit var butonEditarProducto: Button

    @FXML
    private lateinit var textoNombreProducto: TextField

    @FXML
    private lateinit var textoCategoriaProducto: TextField

    @FXML
    private lateinit var textoPrecioProducto: TextField

    @FXML
    private lateinit var textEstadoLogin: Label

    @FXML
    private lateinit var textBuscadorProductos: TextField

    @FXML
    private lateinit var butonAnadirProductosAdmin: Button

    @FXML
    private lateinit var butonAtras: Button

    @FXML
    private lateinit var butonBorrarProductos: Button

    @FXML
    private lateinit var butonHelp: Button

    // Inicialización del controlador
    @FXML
    private fun initialize() {
        initBindings()
        initEventos()
    }

    private fun initBindings() {
        //Tablas
        // tableProductos.items= viewModel.productos
        //Celdas
        //tableColumnIdProducto.cellValueFactory= PropertyValueFactory("id")
        //tableColumnNombreProducto.cellValueFactory= PropertyValueFactory("nombre")
        //tableColumnPrecio.cellValueFactory= PropertyValueFactory("precio")
        // tableColumnCategoria.cellValueFactory= PropertyValueFactory("categoria")

    }

    private fun initEventos() {
        // Código para inicializar los eventos
    }
    // Métodos de manejo de eventos
    @FXML
    private fun handleFileButtonAction() {
        // Código para manejar la acción del botón File
    }

    @FXML
    private fun handleCerrarSesionButtonAction() {
        // Código para manejar la acción del botón Cerrar Sesión
    }

    @FXML
    private fun handleEditarProductoButtonAction() {
        // Código para manejar la acción del botón Editar
    }

    @FXML
    private fun handleAnadirProductosAdminButtonAction() {
        // Código para manejar la acción del botón Añadir
    }

    @FXML
    private fun handleAtrasButtonAction() {
        // Código para manejar la acción del botón Atrás
    }

    @FXML
    private fun handleBorrarProductosButtonAction() {
        // Código para manejar la acción del botón Borrar
    }

    @FXML
    private fun handleHelpButtonAction() {
        // Código para manejar la acción del botón Help
    }

    @FXML
    private fun handleTableProductosSelection() {
        // Código para manejar la selección de productos en la tabla
    }

    @FXML
    private fun handleTextBuscadorProductosAction() {
        // Código para manejar la acción del campo de texto de búsqueda
    }

    @FXML
    private fun handleTextNombreProductoChange() {
        // Código para manejar cambios en el campo de texto Nombre Producto
    }

    @FXML
    private fun handleTextCategoriaProductoChange() {
        // Código para manejar cambios en el campo de texto Categoría Producto
    }

    @FXML
    private fun handleTextPrecioProductoChange() {
        // Código para manejar cambios en el campo de texto Precio Producto
    }
}