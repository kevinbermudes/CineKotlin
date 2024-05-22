module org.example.cine {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires koin.core.jvm;

    requires logging.jvm;
    requires org.slf4j;


    requires kotlinx.serialization.core;
    requires kotlin.result.jvm;
    requires runtime.jvm;
    requires sqlite.driver;
    requires java.sql;
    requires kotlinx.serialization.json;


    opens org.example.cine to javafx.fxml;
    exports org.example.cine;
    opens org.example.cine.route to javafx.fxml;
    exports org.example.cine.route;

    // Configuración
    opens org.example.cine.config to javafx.fxml;
    exports org.example.cine.config;

    // Base de datos
    opens org.example.cine.database to javafx.fxml;
    exports org.example.cine.database;

    // DI
    opens org.example.cine.di to javafx.fxml;
    exports org.example.cine.di;

    // Películas
    // Controladores
    opens org.example.cine.peliculas.controllers to javafx.fxml;
    exports org.example.cine.peliculas.controllers;

    // DTOs
    opens org.example.cine.peliculas.dto to javafx.fxml;
    exports org.example.cine.peliculas.dto;

    // Modelos
    opens org.example.cine.peliculas.models to javafx.fxml;
    exports org.example.cine.peliculas.models;

    // Productos
    // Controladores
    opens org.example.cine.productos.controllers to javafx.fxml;
    exports org.example.cine.productos.controllers;

    // DTOs
    opens org.example.cine.productos.dto.json to javafx.fxml;
    exports org.example.cine.productos.dto.json;

    // Modelos
    opens org.example.cine.productos.models to javafx.fxml;
    exports org.example.cine.productos.models;

    opens org.example.cine.peliculas.controllers.login to javafx.fxml;
    exports org.example.cine.peliculas.controllers.login;

    opens org.example.cine.peliculas.controllers.User to javafx.fxml;
    exports org.example.cine.peliculas.controllers.User;




}