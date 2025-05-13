module com.adrijavi {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.adrijavi to javafx.graphics;
    opens com.adrijavi.controlador to javafx.fxml;
    opens com.adrijavi.vista to javafx.fxml;
    
    exports com.adrijavi;
    exports com.adrijavi.controlador;
    exports com.adrijavi.vista;
}