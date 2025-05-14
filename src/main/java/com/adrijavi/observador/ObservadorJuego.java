package com.adrijavi.observador;

/**
 * Interfaz que define un observador para los eventos del juego.
 * 
 * Las clases que implementen esta interfaz deben proporcionar
 * una implementación para manejar actualizaciones del estado del juego.
 */
public interface ObservadorJuego {

    /**
     * Método llamado cuando el estado del juego se actualiza.
     * 
     * Este método debe ser implementado por las clases que deseen
     * reaccionar a los cambios en el estado del juego.
     */
    void alActualizarJuego();
}

