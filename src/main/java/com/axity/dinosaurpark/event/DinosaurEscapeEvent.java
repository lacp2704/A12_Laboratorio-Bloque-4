package com.axity.dinosaurpark.event;

import com.axity.dinosaurpark.simulation.ParkState;
import java.util.Random;

public class DinosaurEscapeEvent implements SimulationEvent {
    @Override
    public double getProbability() {
        return 0.03; // 3% de probabilidad base
    }

    @Override
    public void execute(ParkState state, Random rng) {
        System.out.println("[CRÍTICO] Se ha reportado la ruptura de una cerca Un dinosaurio ha escapado");
        
        // Persistencia obligatoria 
        state.getDatabaseService().appendEvent("ESCAPE", "Un dinosaurio rompió el perímetro de seguridad.");
        
        // Efecto colateral inmediato Alerta máxima en el sistema
        if(rng.nextBoolean()) {
            System.out.println("[ALERTA] ¡El dinosaurio está persiguiendo un grupo de turistas!");
            state.getDatabaseService().appendEvent("ATAQUE_PROBABLE", "Turistas en zona de riesgo por escape");
        }
    }
}