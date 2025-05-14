package com.adrijavi.modelo;

/**
 * Clase que representa una celda del tablero del juego.
 * 
 * <p>Una celda puede ser de diferentes tipos (como suelo o pared) y tiene una posición
 * específica en el tablero, definida por su fila y columna.</p>
 * 
 * @author Adrián Herrera y Javier Villar
 * @version 1.0
 */
public class Celda {
    private TipoCelda tipo; 
    private int fila; 
    private int columna;

    /**
     * Constructor de la clase Celda.
     * 
     * @param tipo El tipo de la celda (suelo, pared, etc.).
     * @param fila La fila en la que se encuentra la celda.
     * @param columna La columna en la que se encuentra la celda.
     */
    public Celda(TipoCelda tipo, int fila, int columna) {
        this.tipo = tipo;
        this.fila = fila;
        this.columna = columna;
    }

    /**
     * Obtiene el tipo de la celda.
     * 
     * @return El tipo de la celda.
     */
    public TipoCelda getTipo() {
        return tipo;
    }

    /**
     * Obtiene la fila en la que se encuentra la celda.
     * 
     * @return La fila de la celda.
     */
    public int getFila() {
        return fila;
    }

    /**
     * Obtiene la columna en la que se encuentra la celda.
     * 
     * @return La columna de la celda.
     */
    public int getColumna() {
        return columna;
    }

    /**
     * Verifica si la celda es caminable.
     * 
     * Una celda es caminable si su tipo es {@code TipoCelda.SUELO}.
     * 
     * @return {@code true} si la celda es caminable, {@code false} en caso contrario.
     */
    public boolean esCaminable() {
        return tipo == TipoCelda.SUELO;
    }
}