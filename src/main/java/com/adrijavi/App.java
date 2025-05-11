package com.adrijavi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Punto de entrada de la aplicación.
 * Carga la vista de creacion de personaje.
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Cargar el archivo FXML
        Parent root = FXMLLoader.load(getClass().getResource("/com/adrijavi/vista/VistaCrearPersonaje.fxml"));
        
        Scene escena = new Scene(root, 500, 600);
        
        primaryStage.setTitle("Creación de Personaje");
        primaryStage.setScene(escena);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}   