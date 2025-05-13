package com.adrijavi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;

/**
 * Punto de entrada de la aplicación.
 * Carga la vista de creacion de personaje.
 */
public class App extends Application {

    @Override
    public void init() throws Exception {
        // Verificar que los recursos están disponibles
        URL resourceUrl = getClass().getResource("/com/adrijavi/recursos/protagonista.png");
        if (resourceUrl == null) {
            System.err.println("Error: No se encontró el recurso protagonista.png");
        } else {
            System.out.println("Recurso encontrado: " + resourceUrl);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Configurar el ClassLoader para asegurar que los recursos se cargan correctamente
        Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
        
        // Cargar el archivo FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/adrijavi/vista/VistaCrearPersonaje.fxml"));
        Parent root = loader.load();
        
        Scene escena = new Scene(root, 500, 600);
        
        primaryStage.setTitle("Creación de Personaje");
        primaryStage.setScene(escena);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}   