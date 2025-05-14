package com.adrijavi.modelo;

/**
 * Clase base para representar a los personajes del juego, incluyendo al protagonista y los enemigos.
 * Contiene atributos y métodos comunes para gestionar las características y acciones de los personajes.
 */
public abstract class Personaje {
    /**
     * Nombre del personaje.
     */
    protected String nombre;

    /**
     * Salud actual del personaje.
     */
    protected int salud;

    /**
     * Salud máxima del personaje.
     */
    protected int saludMaxima;

    /**
     * Fuerza del personaje, utilizada para calcular el daño en los ataques.
     */
    protected int fuerza;

    /**
     * Defensa del personaje, utilizada para reducir el daño recibido.
     */
    protected int defensa;

    /**
     * Velocidad del personaje, utilizada para determinar el orden de las acciones.
     */
    protected int velocidad;

    /**
     * Percepción del personaje, utilizada para detectar elementos o enemigos en el entorno.
     */
    protected int percepcion;

    /**
     * Fila en la que se encuentra el personaje en el mapa.
     */
    protected int fila;

    /**
     * Columna en la que se encuentra el personaje en el mapa.
     */
    protected int columna;

    /**
     * Constructor de la clase `Personaje`.
     * 
     * @param nombre Nombre del personaje.
     * @param salud Salud inicial del personaje.
     * @param fuerza Fuerza del personaje.
     * @param defensa Defensa del personaje.
     * @param velocidad Velocidad del personaje.
     * @param percepcion Percepción del personaje.
     * @param fila Fila inicial del personaje en el mapa.
     * @param columna Columna inicial del personaje en el mapa.
     */
    public Personaje(String nombre, int salud, int fuerza, int defensa, int velocidad, int percepcion, int fila, int columna) {
        this.nombre = nombre;
        this.salud = salud;
        this.saludMaxima = salud;
        this.fuerza = fuerza;
        this.defensa = defensa;
        this.velocidad = velocidad;
        this.percepcion = percepcion;
        this.fila = fila;
        this.columna = columna;
    }

    /**
     * Obtiene el nombre del personaje.
     * 
     * @return Nombre del personaje.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene la salud actual del personaje.
     * 
     * @return Salud actual del personaje.
     */
    public int getSalud() {
        return salud;
    }

    /**
     * Establece la salud actual del personaje.
     * 
     * @param s Nueva salud del personaje.
     */
    public void setSalud(int s) {
        salud = s;
    }

    /**
     * Obtiene la salud máxima del personaje.
     * 
     * @return Salud máxima del personaje.
     */
    public int getSaludMaxima() {
        return saludMaxima;
    }

    /**
     * Establece la salud máxima del personaje.
     * 
     * @param s Nueva salud máxima del personaje.
     */
    public void setSaludMaxima(int s) {
        saludMaxima = s;
    }

    /**
     * Obtiene la fuerza del personaje.
     * 
     * @return Fuerza del personaje.
     */
    public int getFuerza() {
        return fuerza;
    }

    /**
     * Establece la fuerza del personaje.
     * 
     * @param f Nueva fuerza del personaje.
     */
    public void setFuerza(int f) {
        fuerza = f;
    }

    /**
     * Obtiene la defensa del personaje.
     * 
     * @return Defensa del personaje.
     */
    public int getDefensa() {
        return defensa;
    }

    /**
     * Establece la defensa del personaje.
     * 
     * @param d Nueva defensa del personaje.
     */
    public void setDefensa(int d) {
        defensa = d;
    }

    /**
     * Obtiene la velocidad del personaje.
     * 
     * @return Velocidad del personaje.
     */
    public int getVelocidad() {
        return velocidad;
    }

    /**
     * Establece la velocidad del personaje.
     * 
     * @param v Nueva velocidad del personaje.
     */
    public void setVelocidad(int v) {
        velocidad = v;
    }

    /**
     * Obtiene la percepción del personaje.
     * 
     * @return Percepción del personaje.
     */
    public int getPercepcion() {
        return percepcion;
    }

    /**
     * Establece la percepción del personaje.
     * 
     * @param p Nueva percepción del personaje.
     */
    public void setPercepcion(int p) {
        percepcion = p;
    }

    /**
     * Obtiene la fila en la que se encuentra el personaje en el mapa.
     * 
     * @return Fila en la que se encuentra el personaje en el mapa.
     */
    public int getFila() {
        return fila;
    }

    /**
     * Obtiene la columna en la que se encuentra el personaje en el mapa.
     * 
     * @return Columna en la que se encuentra el personaje en el mapa.
     */
    public int getColumna() {
        return columna;
    }

    /**
     * Establece la posición del personaje en el mapa.
     * 
     * @param fila Nueva fila del personaje en el mapa.
     * @param columna Nueva columna del personaje en el mapa.
     */
    public void setPosicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    /**
     * Recibe daño y actualiza la salud del personaje.
     * 
     * @param dano Cantidad de daño a recibir.
     */
    public void recibirDano(int dano) {
        int danoReal = Math.max(1, dano - defensa); // El daño mínimo es 1
        salud = Math.max(0, salud - danoReal);
    }

    /**
     * Verifica si el personaje está vivo.
     * 
     * @return true si el personaje está vivo, false si está muerto.
     */
    public boolean estaVivo() {
        return salud > 0;
    }

    /**
     * Restaura la salud del personaje a su valor máximo original.
     */
    public void restaurarSalud() {
        this.salud = this.saludMaxima;
    }

    /**
     * Ataque sencillo: daño = fuerza atacante – defensa defensor.
     * 
     * @param objetivo Personaje objetivo del ataque.
     */
    public void atacar(Personaje objetivo) {
        int danio = this.fuerza - objetivo.defensa;
        if (danio < 1) {
            danio = 1;
        }
        objetivo.salud -= danio;
        System.out.println(this.nombre + " ataca a " + objetivo.nombre + " causando " + danio + " puntos de daño.");
    }
}
