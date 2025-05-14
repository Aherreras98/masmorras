package com.adrijavi.modelo;

import java.util.Random;

/**
 * Clase que representa a un enemigo en el juego.
 * 
 * <p>Un enemigo es un personaje que puede ser un jefe final y tiene la capacidad
 * de interactuar con el protagonista y otros elementos del juego.</p>
 * 
 * <p>Si el enemigo es el jefe final, tiene habilidades especiales, como
 * la posibilidad de resucitar tras ser derrotado.</p>
 * 
 * @see Personaje
 * @see Protagonista
 * @see Juego
 * 
 * @author Adrián Herrera y Javier Villar
 * @version 1.0
 */
public class Enemigo extends Personaje {
    private boolean esJefeFinal = false; 
    private Random random = new Random();

    /**
     * Constructor de la clase Enemigo.
     * 
     * @param nombre El nombre del enemigo.
     * @param salud La cantidad de salud del enemigo.
     * @param fuerza La fuerza del enemigo.
     * @param defensa La defensa del enemigo.
     * @param velocidad La velocidad del enemigo.
     * @param percepcion La percepción del enemigo.
     * @param fila La fila en la que se encuentra el enemigo en el tablero.
     * @param columna La columna en la que se encuentra el enemigo en el tablero.
     */
    public Enemigo(String nombre, int salud, int fuerza, int defensa, int velocidad, int percepcion, int fila, int columna) {
        super(nombre, salud, fuerza, defensa, velocidad, percepcion, fila, columna);
        // Si el enemigo es el "Rey Mono", se marca como jefe final
        if (nombre.equals("Rey Mono")) {
            esJefeFinal = true;
        }
    }

    /**
     * Método que permite al enemigo recibir daño.
     * 
     * <p>Si el enemigo es un jefe final y muere, tiene un 70% de probabilidad
     * de resucitar con 100 puntos de salud.</p>
     * 
     * @param dano La cantidad de daño que recibe el enemigo.
     */
    @Override
    public void recibirDano(int dano) {
        super.recibirDano(dano);
        
        if (esJefeFinal && !estaVivo()) {
            if (random.nextDouble() < 0.7) { 
                setSalud(100);
            }
        }
    }
}