package com.adrijavi.controlador;

import com.adrijavi.modelo.Protagonista;

import java.io.IOException;

import com.adrijavi.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class ControladorCrearPersonaje {

    @FXML
    private TextField tfNombre;
    @FXML
    private Label lblPuntosRestantes, lblSalud, lblFuerza, lblDefensa, lblVelocidad, lblPercepcion;
    @FXML
    private Button btnSaludMas, btnSaludMenos, btnFuerzaMas, btnFuerzaMenos, btnDefensaMas, btnDefensaMenos,
            btnVelocidadMas, btnVelocidadMenos, btnPercepcionMas, btnPercepcionMenos, btnIniciar;

    private int puntosRestantes = 15; 
    private int salud = 1, fuerza = 1, defensa = 1, velocidad = 1, percepcion = 1;

    @FXML
    public void initialize() {
        
        lblSalud.setText(String.valueOf(salud));
        lblFuerza.setText(String.valueOf(fuerza));
        lblDefensa.setText(String.valueOf(defensa));
        lblVelocidad.setText(String.valueOf(velocidad));
        lblPercepcion.setText(String.valueOf(percepcion));
        lblPuntosRestantes.setText("Puntos restantes: " + puntosRestantes);

        
        btnSaludMas.setOnAction(e -> incrementarEstadistica("salud"));
        btnSaludMenos.setOnAction(e -> decrementarEstadistica("salud"));

        
        btnFuerzaMas.setOnAction(e -> incrementarEstadistica("fuerza"));
        btnFuerzaMenos.setOnAction(e -> decrementarEstadistica("fuerza"));

        
        btnDefensaMas.setOnAction(e -> incrementarEstadistica("defensa"));
        btnDefensaMenos.setOnAction(e -> decrementarEstadistica("defensa"));

        
        btnVelocidadMas.setOnAction(e -> incrementarEstadistica("velocidad"));
        btnVelocidadMenos.setOnAction(e -> decrementarEstadistica("velocidad"));

        btnPercepcionMas.setOnAction(e -> incrementarEstadistica("percepcion"));
        btnPercepcionMenos.setOnAction(e -> decrementarEstadistica("percepcion"));
    }

    private void incrementarEstadistica(String estadistica) {
        if (puntosRestantes > 0) {
            switch (estadistica) {
                case "salud":
                    salud++;
                    lblSalud.setText(String.valueOf(salud));
                    break;
                case "fuerza":
                    fuerza++;
                    lblFuerza.setText(String.valueOf(fuerza));
                    break;
                case "defensa":
                    defensa++;
                    lblDefensa.setText(String.valueOf(defensa));
                    break;
                case "velocidad":
                    velocidad++;
                    lblVelocidad.setText(String.valueOf(velocidad));
                    break;
                case "percepcion":
                    percepcion++;
                    lblPercepcion.setText(String.valueOf(percepcion));
                    break;
            }
            puntosRestantes--;
            lblPuntosRestantes.setText("Puntos restantes: " + puntosRestantes);
        }
    }

    private void decrementarEstadistica(String estadistica) {
        switch (estadistica) {
            case "salud":
                if (salud > 1) { 
                    salud--;
                    lblSalud.setText(String.valueOf(salud));
                    puntosRestantes++;
                }
                break;
            case "fuerza":
                if (fuerza > 1) {
                    fuerza--;
                    lblFuerza.setText(String.valueOf(fuerza));
                    puntosRestantes++;
                }
                break;
            case "defensa":
                if (defensa > 1) {
                    defensa--;
                    lblDefensa.setText(String.valueOf(defensa));
                    puntosRestantes++;
                }
                break;
            case "velocidad":
                if (velocidad > 1) {
                    velocidad--;
                    lblVelocidad.setText(String.valueOf(velocidad));
                    puntosRestantes++;
                }
                break;
            case "percepcion":
                if (percepcion > 1) {
                    percepcion--;
                    lblPercepcion.setText(String.valueOf(percepcion));
                    puntosRestantes++;
                }
                break;
        }
        lblPuntosRestantes.setText("Puntos restantes: " + puntosRestantes);
    }

    @FXML
    private void iniciarJuego(ActionEvent event) {
        if (puntosRestantes == 0) {
            try {
                String nombre = tfNombre.getText().trim();
                if (nombre.isEmpty())
                    throw new IllegalArgumentException("El nombre no puede estar vacío.");

                Protagonista protagonista = new Protagonista(nombre, salud, fuerza, defensa, velocidad, percepcion, 1,
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
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Debes asignar todos los puntos antes de continuar.");
            alert.showAndWait();
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR, mensaje);
        alert.showAndWait();
    }
}