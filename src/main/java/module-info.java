module com.adrijavi {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.adrijavi to javafx.graphics;
    opens com.adrijavi.controlador to javafx.fxml;
    exports com.adrijavi;
    exports com.adrijavi.controlador;
}