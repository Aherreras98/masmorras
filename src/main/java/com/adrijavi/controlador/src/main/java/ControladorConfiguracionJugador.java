package com.adrijavi.controlador;

import com.adrijavi.modelo.Protagonista;

import java.io.IOException;

import com.adrijavi.modelo.Juego;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
public class ControladorConfiguracionJugador {

    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfSalud;
    @FXML
    private TextField tfFuerza;
    @FXML
    private TextField tfDefensa;
    @FXML
    private TextField tfVelocidad;
    @FXML
    private TextField tfPercepcion;
    @FXML
    private Button btnIniciar;

    @FXML
    private void iniciarJuego(ActionEvent event) {
        try {
            String nombre = tfNombre.getText().trim();
            if (nombre.isEmpty())
                throw new IllegalArgumentException("El nombre no puede estar vacío.");

            int[] stats = new int[5];
            TextField[] campos = { tfSalud, tfFuerza, tfDefensa, tfVelocidad, tfPercepcion };
            for (int i = 0; i < campos.length; i++) {
                stats[i] = Integer.parseInt(campos[i].getText().trim());
                if (stats[i] <= 0)
                    throw new IllegalArgumentException("Los valores deben ser mayores a 0.");
            }

            Protagonista protagonista = new Protagonista(nombre, stats[0], stats[1], stats[2], stats[3], stats[4], 1,
                    1);

            Juego juego = new Juego();
            juego.setProtagonista(protagonista);
            juego.cargarTablero("mapa.txt");
            juego.cargarEnemigos("enemigos.txt");
            juego.inicializarOrdenTurnos();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/adrijavi/vista/VistaJuego.fxml"));
            if (loader.getLocation() == null) {
                throw new IOException("No se encontró el archivo FXML de la vista del juego");
            }
            Parent rootJuego = loader.load();

            ControladorJuegoVista controladorJuegoVista = loader.getController();
            controladorJuegoVista.setJuego(juego);

            Scene escenaJuego = new Scene(rootJuego, 800, 600);
            escenaJuego.setOnKeyPressed(controladorJuegoVista.getManejadorEventoTeclado());

            Stage stage = (Stage) btnIniciar.getScene().getWindow();
            stage.setScene(escenaJuego);
            stage.setTitle("Juego de Mazmorras");
            stage.show();
        } catch (NumberFormatException e) {
            mostrarAlerta("Valores inválidos. Ingrese números válidos.");
        } catch (IllegalArgumentException | IOException e) {
            mostrarAlerta(e.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            mostrarAlerta("Error al iniciar el juego: " + ex.getMessage());
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR, mensaje);
        alert.showAndWait();
    }
}