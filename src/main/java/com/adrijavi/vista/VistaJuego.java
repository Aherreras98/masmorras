package com.adrijavi.vista;

import com.adrijavi.modelo.Juego;
import com.adrijavi.modelo.Celda;
import com.adrijavi.modelo.TipoCelda;
import com.adrijavi.modelo.Protagonista;
import com.adrijavi.modelo.Enemigo;
import com.adrijavi.modelo.Personaje;
import com.adrijavi.observador.ObservadorJuego;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.io.File;
import java.io.InputStream;

public class VistaJuego extends BorderPane implements ObservadorJuego {
    private Juego juego;
    private GridPane gridTablero;
    private VBox panelInformacion;
    private Label labelOrdenTurnos;

    public VistaJuego(Juego juego) {
        this.juego = juego;
        this.juego.añadirObservador(this);
        configurarLayout();
        actualizarTablero();
        actualizarPanelInformacion();
    }

    private void configurarLayout() {
        gridTablero = new GridPane();
        gridTablero.setHgap(2);
        gridTablero.setVgap(2);
        gridTablero.setPadding(new Insets(10));
        this.setCenter(gridTablero);

        panelInformacion = new VBox(10);
        panelInformacion.setPadding(new Insets(10));
        labelOrdenTurnos = new Label();
        labelOrdenTurnos.setFont(new Font(16));
        panelInformacion.getChildren().add(labelOrdenTurnos);
        this.setRight(panelInformacion);
    }

    public void actualizarTablero() {
        gridTablero.getChildren().clear();
        Celda[][] celdas = juego.getTablero();
        if (celdas == null) return;
        for (int f = 0; f < celdas.length; f++) {
            final int fila = f;
            for (int c = 0; c < celdas[0].length; c++) {
                final int columna = c;
                Celda celda = celdas[fila][columna];
                StackPane panelCelda = new StackPane();
                panelCelda.setPrefSize(60, 60);
                if (celda.getTipo() == TipoCelda.PARED) {
                    panelCelda.setStyle("-fx-background-color: grey; -fx-border-color: black;");
                } else {
                    panelCelda.setStyle("-fx-background-color: white; -fx-border-color: black;");
                }

                // Mostrar protagonista como texto
                if (juego.getProtagonista() != null && juego.getProtagonista().getFila() == fila &&
                        juego.getProtagonista().getColumna() == columna && juego.getProtagonista().estaVivo()) {
                    Label etiqueta = new Label("P");
                    etiqueta.setTextFill(Color.BLUE);
                    panelCelda.getChildren().add(etiqueta);
                }
                gridTablero.add(panelCelda, columna, fila);
            }
        }
    }

    public void actualizarPanelInformacion() {
        panelInformacion.getChildren().clear();
        if (juego.getProtagonista() != null) {
            Protagonista p = juego.getProtagonista();
            Label infoP = new Label("Protagonista: " + p.getNombre() +
                    "\nSalud: " + p.getSalud() +
                    "\nFuerza: " + p.getFuerza() +
                    "\nDefensa: " + p.getDefensa() +
                    "\nVelocidad: " + p.getVelocidad() +
                    "\nPercepción: " + p.getPercepcion());
            panelInformacion.getChildren().add(infoP);
        }
        Label etiquetaEnemigos = new Label("Enemigos:");
        panelInformacion.getChildren().add(etiquetaEnemigos);
        if (juego.getEnemigos() != null && !juego.getEnemigos().isEmpty()) {
            for (Enemigo enemigo : juego.getEnemigos()) {
                Label etiquetaEnemigo = new Label(enemigo.getNombre() + " -- Salud: " + enemigo.getSalud());
                panelInformacion.getChildren().add(etiquetaEnemigo);
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Orden de Turnos:\n");
        for (Personaje p : juego.getOrdenTurnos()) {
            sb.append(p.getNombre()).append(" (").append(p.getVelocidad()).append(")\n");
        }
        labelOrdenTurnos = new Label(sb.toString());
        panelInformacion.getChildren().add(labelOrdenTurnos);
    }

    @Override
    public void alActualizarJuego() {
        actualizarTablero();
        actualizarPanelInformacion();
    }
}
