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
import javafx.scene.control.ButtonType;

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
    private boolean mostrandoDialogoNivel = false;

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
        
        // Información del protagonista
        if (juego.getProtagonista() != null) {
            Protagonista p = juego.getProtagonista();
            Label infoP = new Label(String.format(
                    "Protagonista: %s\nSalud: %d\nFuerza: %d\nDefensa: %d\nVelocidad: %d\nPercepción: %d",
                    p.getNombre(), p.getSalud(), p.getFuerza(), p.getDefensa(), p.getVelocidad(), p.getPercepcion()));
            infoP.setStyle("-fx-font-weight: bold;");
            panelInformacion.getChildren().add(infoP);
        }

        // Separador
        panelInformacion.getChildren().add(new Label("\nEnemigos:"));
        
        // Información de los enemigos
        for (Enemigo enemigo : juego.getEnemigos()) {
            if (enemigo.estaVivo()) {
                Label infoE = new Label(String.format(
                    "%s\nSalud: %d\nFuerza: %d\nDefensa: %d\nVelocidad: %d\nPercepción: %d",
                    enemigo.getNombre(),
                    enemigo.getSalud(),
                    enemigo.getFuerza(),
                    enemigo.getDefensa(),
                    enemigo.getVelocidad(),
                    enemigo.getPercepcion()
                ));
                infoE.setStyle("-fx-text-fill: red;");
                panelInformacion.getChildren().add(infoE);
            }
        }

        // Separador
        panelInformacion.getChildren().add(new Label("\nOrden de Turnos:"));
        
        // Orden de turnos con más detalles
        StringBuilder orden = new StringBuilder();
        for (Personaje p : juego.getOrdenTurnos()) {
            orden.append(String.format("%s (Vel: %d, Salud: %d)\n",
                p.getNombre(),
                p.getVelocidad(),
                p.getSalud()));
        }
        labelOrdenTurnos.setText(orden.toString());
        panelInformacion.getChildren().add(labelOrdenTurnos);

        // Turno actual
        Personaje personajeActual = juego.getPersonajeEnTurno();
        if (personajeActual != null) {
            labelTurnoActual.setText(String.format("Turno actual: %s (Salud: %d)",
                personajeActual.getNombre(),
                personajeActual.getSalud()));
            labelTurnoActual.setStyle("-fx-font-weight: bold;");
        } else {
            labelTurnoActual.setText("Turno actual: N/A");
        }
        panelInformacion.getChildren().add(labelTurnoActual);
    }

    @Override
    public void alActualizarJuego() {
        Platform.runLater(() -> {
            actualizarVista();

            // Verificar si el protagonista ha muerto
            if (juego.getProtagonista() == null || !juego.getProtagonista().estaVivo()) {
                mostrarGameOver();
            } else if (juego.esNivelCompleto() && !mostrandoDialogoNivel) {
                mostrandoDialogoNivel = true;
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Nivel Completado");
                alert.setHeaderText("¡Has completado el nivel " + juego.getNivelActual() + "!");
                alert.setContentText("¿Quieres continuar al siguiente nivel?");

                ButtonType buttonTypeContinuar = new ButtonType("Continuar");
                ButtonType buttonTypeTerminar = new ButtonType("Terminar Partida");
                alert.getButtonTypes().setAll(buttonTypeContinuar, buttonTypeTerminar);

                alert.showAndWait().ifPresent(response -> {
                    if (response == buttonTypeContinuar) {
                        if (!juego.cargarSiguienteNivel()) {
                            mostrarVictoriaFinal();
                        } else {
                            actualizarVista();
                        }
                    } else {
                        mostrarVictoriaFinal();
                    }
                    mostrandoDialogoNivel = false;
                });
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
        mostrarAlerta("¡Victoria!", "¡Has completado el juego!");
    }

    private void mostrarGameOver() {
        mostrarAlerta("Game Over", "¡Has sido derrotado!");
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