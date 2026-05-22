package com.axity.dinosaurpark.event;

import com.axity.dinosaurpark.model.Dinosaur;
import com.axity.dinosaurpark.simulation.ParkState;
import com.axity.dinosaurpark.exception.DinosaurEscapeException;
import java.util.Random;

public class DinosaurEscapeEvent implements SimulationEvent {

    @Override
    public double getProbability() {
        return 0.005; // 0.5% de probabilidad
    }

    @Override
    public void execute(ParkState state, Random rng) {
        if (!state.getDinosaurs().isEmpty()) {
            // Selecciona un dinosaurio al azar para el escape
            int index = rng.nextInt(state.getDinosaurs().size());
            Dinosaur escapedDino = state.getDinosaurs().get(index);
            
            // Registramos el incidente en la base de datos H2 antes de lanzar el fallo
            state.getDatabaseService().appendEvent("ESCAPE_CRITICO", "El dinosaurio " + escapedDino.getName() + " rompió la cerca");

            // Lanzamos una excepción envoltura simulada mediante un flujo controlado o bandera
            System.out.println("[ALERTA MÁXIMA] " + escapedDino.getName() + " ha burlado el perímetro de contención");
            
            // Lo removemos de la lista de control C6 del estado
            state.getDinosaurs().remove(index);
        }
    }
}