package com.adrijavi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase principal de la aplicación.
 * Extiende la clase {@link Application} de JavaFX y actúa como punto de entrada para la aplicación.
 * Carga la vista de creación de personaje desde un archivo FXML.
 * @author Adrián Herrera y Javier Villar
 * @version 1.0
 */
public class App extends Application {

    /**
     * Método principal de inicio de la aplicación JavaFX.
     * Configura y muestra la ventana principal de la aplicación.
     *
     * @param primaryStage La ventana principal de la aplicación.
     * @throws Exception Si ocurre un error al cargar el archivo FXML.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
        
        // Cargar el archivo FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/adrijavi/vista/VistaCrearPersonaje.fxml"));
        Parent root = loader.load();
        
        // Crear la escena con las dimensiones especificadas
        Scene escena = new Scene(root, 400, 500);
        
        // Configurar el título y la escena de la ventana principal
        primaryStage.setTitle("Creación de Personaje");
        primaryStage.setScene(escena);
        primaryStage.show();
    }

    /**
     * Método principal de la aplicación.
     * Llama al método {@link Application#launch(String...)} para iniciar la aplicación JavaFX.
     *
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        launch(args);
    }
}