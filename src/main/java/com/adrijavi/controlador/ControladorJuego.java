package com.adrijavi.controlador;

import com.adrijavi.modelo.Juego;
import com.adrijavi.modelo.Protagonista;
import com.adrijavi.observador.ObservadorJuego;

import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

/**
 * Clase que controla la lógica del juego y maneja los eventos de teclado.
 * Implementa la interfaz {@link ObservadorJuego} para observar los cambios en el juego.
 */
public class ControladorJuego implements ObservadorJuego {
    private Juego juego;

    /**
     * Constructor de la clase ControladorJuego.
     * 
     * @param juego Instancia del juego que será controlada por este controlador.
     */
    public ControladorJuego(Juego juego) {
        this.juego = juego;
        this.juego.añadirObservador(this);
    }
    
    /**
     * Devuelve un manejador de eventos de teclado que procesa las teclas presionadas
     * para mover al protagonista en el juego.
     * 
     * @return Un {@link EventHandler} que maneja los eventos de teclado.
     */
    public EventHandler<KeyEvent> getManejadorEventoTeclado() {
        return event -> {
            if (!(juego.getPersonajeEnTurno() instanceof Protagonista)) return;
            
            String direccion = null;
            switch(event.getCode()){
                case UP:
                case W: direccion = "ARRIBA"; break;
                case DOWN:
                case S: direccion = "ABAJO"; break;
                case LEFT:
                case A: direccion = "IZQUIERDA"; break;
                case RIGHT:
                case D: direccion = "DERECHA"; break;
                default: break;
            }
            
            if(direccion != null){
                juego.moverProtagonista(direccion);
                juego.turnoEnemigos();
                juego.siguienteTurno();
            }
        };
    }

    /**
     * Método llamado cuando el estado del juego se actualiza.
     * Este método debe ser implementado para manejar las actualizaciones del juego.
     * 
     * @throws UnsupportedOperationException Si el método no está implementado.
     */
    @Override
    public void alActualizarJuego() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'alActualizarJuego'");
    }
}