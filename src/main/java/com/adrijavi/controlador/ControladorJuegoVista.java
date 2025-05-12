package com.adrijavi.controlador;

import java.io.IOException;

import com.adrijavi.modelo.*;
import com.adrijavi.observador.ObservadorJuego;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyEvent;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;

public class ControladorJuegoVista implements ObservadorJuego {
    @FXML
    private GridPane gridTablero;
    @FXML
    private VBox panelInformacion;
    @FXML
    private Label labelOrdenTurnos;
    @FXML
    private Label labelTurnoActual;
    private Juego juego;

    public void setJuego(Juego juego) {
        this.juego = juego;
        juego.añadirObservador(this);
        actualizarVista();
    }

    // Actualización completa de la vista
    private void actualizarVista() {
        actualizarTablero();
        actualizarPanelInformacion();
    }

    private void actualizarTablero() {
        gridTablero.getChildren().clear();
        Celda[][] celdas = juego.getTablero();
        if (celdas == null)
            return;

        for (int f = 0; f < celdas.length; f++) {
            for (int c = 0; c < celdas[0].length; c++) {
                StackPane panelCelda = new StackPane();
                panelCelda.setPrefSize(40, 40);
                panelCelda.setStyle(celdas[f][c].getTipo() == TipoCelda.PARED
                        ? "-fx-background-color: grey;"
                        : "-fx-background-color: white;");

                // Mostrar personajes
                Personaje personaje = juego.obtenerPersonajeEn(f, c);
                if (personaje != null) {
                    Label etiqueta = new Label(personaje instanceof Protagonista ? "P" : "E");
                    etiqueta.setTextFill(personaje instanceof Protagonista ? Color.BLUE : Color.RED);
                    panelCelda.getChildren().add(etiqueta);
                }
                gridTablero.add(panelCelda, c, f);
            }
        }
    }

    private void actualizarPanelInformacion() {
        panelInformacion.getChildren().clear();
        if (juego.getProtagonista() != null) {
            Protagonista p = juego.getProtagonista();
            Label infoP = new Label(String.format(
                    "Protagonista: %s\nSalud: %d\nFuerza: %d\nDefensa: %d",
                    p.getNombre(), p.getSalud(), p.getFuerza(), p.getDefensa()));
            panelInformacion.getChildren().add(infoP);
        }

        // Orden de turnos
        StringBuilder orden = new StringBuilder("Orden de Turnos:\n");
        for (Personaje p : juego.getOrdenTurnos()) {
            orden.append(p.getNombre()).append(" (").append(p.getVelocidad()).append(")\n");
        }
        labelOrdenTurnos.setText(orden.toString());

        // Turno actual
        labelTurnoActual.setText("Turno actual: " +
                (juego.getPersonajeEnTurno() != null
                        ? juego.getPersonajeEnTurno().getNombre()
                        : "N/A"));
    }

@Override
public void alActualizarJuego() {
    Platform.runLater(() -> {
        actualizarVista();

        // Verificar si el protagonista ha muerto
        if (juego.getProtagonista() == null || !juego.getProtagonista().estaVivo()) {
            mostrarGameOver();
        } else if (juego.esNivelCompleto()) {
            try {
                juego.cargarSiguienteNivel();
                actualizarVista();
            } catch (IOException e) {
                mostrarVictoriaFinal();
            }
        }
    });
}

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
        System.exit(0);
    }

    private void mostrarVictoriaFinal() {
        mostrarAlerta("¡Victoria!", "¡Has completado todos los niveles!");
    }

    private void mostrarGameOver() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("El protagonista ha muerto. Fin del juego.");
        alert.showAndWait();
        Platform.exit(); // Cerrar la aplicación
    }

    public EventHandler<KeyEvent> getManejadorEventoTeclado() {
        return event -> {
            // Si no es el turno del protagonista, avanzar al siguiente turno
            if (!(juego.getPersonajeEnTurno() instanceof Protagonista)) {
                System.out.println("Avanzando al siguiente turno...");
                juego.siguienteTurno();
                return;
            }

            String direccion = null;
            switch (event.getCode()) {
                case UP:
                case W:
                    direccion = "ARRIBA";
                    break;
                case DOWN:
                case S:
                    direccion = "ABAJO";
                    break;
                case LEFT:
                case A:
                    direccion = "IZQUIERDA";
                    break;
                case RIGHT:
                case D:
                    direccion = "DERECHA";
                    break;
                default:
                    System.out.println("Tecla no válida.");
                    return;
            }

            System.out.println("Protagonista se mueve en dirección: " + direccion);
            juego.moverProtagonista(direccion);
            juego.siguienteTurno();
        };
    }
}