package com.adrijavi.modelo;

/**
 * Clase base para el protagonista y los enemigos.
 */
public abstract class Personaje {
    protected String nombre;
    protected int salud;
    protected int fuerza;
    protected int defensa;
    protected int velocidad;
    protected int percepcion;
    protected int fila;
    protected int columna;
    
    public Personaje(String nombre, int salud, int fuerza, int defensa, int velocidad, int percepcion, int fila, int columna) {
        this.nombre = nombre;
        this.salud = salud;
        this.fuerza = fuerza;
        this.defensa = defensa;
        this.velocidad = velocidad;
        this.percepcion = percepcion;
        this.fila = fila;
        this.columna = columna;
    }
    
    public String getNombre() {
        return nombre;
    }
    public int getSalud() { return salud; }
    public void setSalud(int s) { salud = s; }
    public int getFuerza() { return fuerza; }
    public int getDefensa() { return defensa; }
    public int getVelocidad() { return velocidad; }
    public int getPercepcion() { return percepcion; }
    
    public int getFila() { return fila; }
    public int getColumna() { return columna; }
    
    public void setPosicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }
    
    /**
     * Ataque sencillo: daño = fuerza atacante – defensa defensor.
     */
    public void atacar(Personaje objetivo) {
        int danio = this.fuerza - objetivo.defensa;
        if (danio < 1) {
            danio = 1;
        }
        objetivo.salud -= danio;
        System.out.println(this.nombre + " ataca a " + objetivo.nombre + " causando " + danio + " puntos de daño.");
    }
    
    public boolean estaVivo() {
        return salud > 0;
    }
}
