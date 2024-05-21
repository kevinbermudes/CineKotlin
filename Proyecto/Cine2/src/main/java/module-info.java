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
    requires kotlinx.serialization.json;


    opens org.example.cine to javafx.fxml;
    exports org.example.cine;
}