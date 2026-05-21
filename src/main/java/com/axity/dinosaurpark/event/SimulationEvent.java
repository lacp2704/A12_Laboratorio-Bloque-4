package com.axity.dinosaurpark.event;

import com.axity.dinosaurpark.simulation.ParkState;
import java.util.Random;

public interface SimulationEvent {
    
    // Retorna la probabilidad configurada para que este evento ocurra     
    double getProbability();

    
    // Ejecuta las consecuencias del evento sobre el estado del parque de forma dinámica     
    void execute(ParkState state, Random rng);
}