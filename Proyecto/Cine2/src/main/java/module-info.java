module org.example.cine2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;


    opens org.example.cine2 to javafx.fxml;
    exports org.example.cine2;
}