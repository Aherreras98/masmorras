package com.adrijavi.controlador;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.adrijavi.modelo.*;
import com.adrijavi.observador.ObservadorJuego;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyEvent;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class ControladorJuegoVista implements ObservadorJuego {
    @FXML
    private GridPane gridTablero;
    @FXML
    private VBox panelInformacion;
    @FXML
    private Label labelOrdenTurnos;
    @FXML
    private Label labelTurnoActual;
    @FXML
    private ImageView imagenProtagonista;
    @FXML
    private Label labelInfoProtagonista;
    @FXML
    private VBox panelEnemigos;
    private Juego juego;
    private boolean mostrandoDialogoNivel = false;
    private Map<String, Image> imagenesEnemigos;
    private Image imagenProtagonistaCache;

    public void setJuego(Juego juego) {
        this.juego = juego;
        juego.añadirObservador(this);
        precargarImagenes();
        actualizarVista();
    }

    private void precargarImagenes() {
        imagenesEnemigos = new HashMap<>();
        try {
            // Precargar imagen del protagonista
            imagenProtagonistaCache = new Image(getClass().getResourceAsStream("/com/adrijavi/recursos/protagonista.png"));
            imagenProtagonista.setImage(imagenProtagonistaCache);

            // Precargar imágenes de enemigos
            imagenesEnemigos.put("dragon", new Image(getClass().getResourceAsStream("/com/adrijavi/recursos/dragon.png")));
            imagenesEnemigos.put("esqueleto", new Image(getClass().getResourceAsStream("/com/adrijavi/recursos/esqueleto.png")));
            imagenesEnemigos.put("goblin", new Image(getClass().getResourceAsStream("/com/adrijavi/recursos/goblin.png")));
            imagenesEnemigos.put("enemigo", new Image(getClass().getResourceAsStream("/com/adrijavi/recursos/enemigo.png")));
        } catch (Exception e) {
            System.err.println("Error al precargar imágenes: " + e.getMessage());
        }
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
        
        // Información del protagonista
        if (juego.getProtagonista() != null) {
            Protagonista p = juego.getProtagonista();
            labelInfoProtagonista.setText(
                "Nombre: " + p.getNombre() + "\n" +
                "Salud: " + p.getSalud() + "\n" +
                "Fuerza: " + p.getFuerza() + "\n" +
                "Defensa: " + p.getDefensa() + "\n" +
                "Velocidad: " + p.getVelocidad() + "\n" +
                "Percepción: " + p.getPercepcion()
            );
        }
        
        // Información de los enemigos
        panelEnemigos.getChildren().clear();
        for (Enemigo enemigo : juego.getEnemigos()) {
            if (enemigo.estaVivo()) {
                HBox contenedorEnemigo = new HBox(10);
                contenedorEnemigo.setAlignment(Pos.CENTER_LEFT);
                
                // Obtener la imagen del enemigo desde el caché
                String tipoEnemigo = "";
                if (enemigo.getNombre().toLowerCase().contains("dragon")) {
                    tipoEnemigo = "dragon";
                } else if (enemigo.getNombre().toLowerCase().contains("esqueleto")) {
                    tipoEnemigo = "esqueleto";
                } else if (enemigo.getNombre().toLowerCase().contains("goblin")) {
                    tipoEnemigo = "goblin";
                } else {
                    tipoEnemigo = "enemigo";
                }
                
                ImageView imagenEnemigo = new ImageView(imagenesEnemigos.get(tipoEnemigo));
                imagenEnemigo.setFitHeight(40);
                imagenEnemigo.setFitWidth(40);
                contenedorEnemigo.getChildren().add(imagenEnemigo);
                
                VBox infoEnemigo = new VBox(5);
                Label nombreEnemigo = new Label(enemigo.getNombre());
                nombreEnemigo.setStyle("-fx-font-weight: bold;");
                Label statsEnemigo = new Label(String.format(
                    "Salud: %d\nFuerza: %d\nDefensa: %d\nVelocidad: %d",
                    enemigo.getSalud(),
                    enemigo.getFuerza(),
                    enemigo.getDefensa(),
                    enemigo.getVelocidad()
                ));
                infoEnemigo.getChildren().addAll(nombreEnemigo, statsEnemigo);
                contenedorEnemigo.getChildren().add(infoEnemigo);
                
                panelEnemigos.getChildren().add(contenedorEnemigo);
            }
        }
        
        // Orden de turnos
        StringBuilder orden = new StringBuilder();
        for (Personaje p : juego.getOrdenTurnos()) {
            orden.append(String.format("%s (Vel: %d, Salud: %d)\n",
                p.getNombre(),
                p.getVelocidad(),
                p.getSalud()));
        }
        labelOrdenTurnos.setText(orden.toString());
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