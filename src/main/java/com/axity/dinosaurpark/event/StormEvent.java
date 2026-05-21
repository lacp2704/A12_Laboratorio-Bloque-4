package com.axity.dinosaurpark.event;

import com.axity.dinosaurpark.simulation.ParkState;
import java.util.Random;

public class StormEvent implements SimulationEvent {
    @Override
    public double getProbability() {
        return 0.08; // 8% de probabilidad climática
    }

    @Override
    public void execute(ParkState state, Random rng) {
        System.out.println("[CLIMA] Una tormenta tropical azota la isla. Se reduce la visibilidad.");
        state.setStormActive(true);
        
        state.getDatabaseService().appendEvent("TORMENTA", "Condiciones climáticas adversas activadas.");
    }
}