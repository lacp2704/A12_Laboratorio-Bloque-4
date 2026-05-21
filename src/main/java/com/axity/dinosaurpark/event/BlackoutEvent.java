package com.axity.dinosaurpark.event;

import com.axity.dinosaurpark.simulation.ParkState;
import java.util.Random;

public class BlackoutEvent implements SimulationEvent {
    @Override
    public double getProbability() {
        return 0.05; // En base a powerplant.failureProbability=0.05 del park.properties
    }

    @Override
    public void execute(ParkState state, Random rng) {
        System.out.println("[CORTE] ¡Falla en los generadores principales! El parque entra en apagón");
        state.setBlackoutActive(true);
        state.setPowerEnergy(0.0);
        
        state.getDatabaseService().appendEvent("APAGÓN", "Planta de energía fuera de servicio por falla aleatoria");
    }
}