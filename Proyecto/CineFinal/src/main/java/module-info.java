module com.example.cinefinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;


    opens com.example.cinefinal to javafx.fxml;
    exports com.example.cinefinal;
}