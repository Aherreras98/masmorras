package com.adrijavi.modelo;

/**
 * Representa los diferentes tipos de celdas que pueden existir en el mapa del juego.
 * <ul>
 *   <li><b>SUELO:</b> Celda transitable por los personajes.</li>
 *   <li><b>PARED:</b> Celda no transitable que actúa como un obstáculo.</li>
 * </ul>
 */
public enum TipoCelda {
    SUELO,
    PARED;
}