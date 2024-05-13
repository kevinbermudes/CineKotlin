module org.example.cine {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.cine to javafx.fxml;
    exports org.example.cine;
}