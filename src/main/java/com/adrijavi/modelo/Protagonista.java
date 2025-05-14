package com.adrijavi.modelo;

/**
 * Representa al protagonista del juego, que hereda de la clase {@link Personaje}.
 * El protagonista tiene atributos como nombre, salud, fuerza, defensa, velocidad,
 * percepción, y su posición en el mapa (fila y columna).
 */
public class Protagonista extends Personaje {

    /**
     * Constructor de la clase Protagonista.
     *
     * @param nombre     El nombre del protagonista.
     * @param salud      La cantidad de salud inicial del protagonista.
     * @param fuerza     La fuerza del protagonista, que afecta el daño que puede infligir.
     * @param defensa    La defensa del protagonista, que reduce el daño recibido.
     * @param velocidad  La velocidad del protagonista, que puede influir en el orden de turnos.
     * @param percepcion La percepción del protagonista, que puede afectar su capacidad para detectar cosas.
     * @param fila       La posición inicial del protagonista en el eje de filas del mapa.
     * @param columna    La posición inicial del protagonista en el eje de columnas del mapa.
     */
    public Protagonista(String nombre, int salud, int fuerza, int defensa, int velocidad, int percepcion, int fila, int columna) {
        super(nombre, salud, fuerza, defensa, velocidad, percepcion, fila, columna);
    }
}