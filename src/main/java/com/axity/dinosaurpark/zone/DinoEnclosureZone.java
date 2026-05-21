package com.axity.dinosaurpark.zone;

import com.axity.dinosaurpark.model.Dinosaur;
import com.axity.dinosaurpark.simulation.ParkState;

public class DinoEnclosureZone extends ParkZone {

    public DinoEnclosureZone(String zoneId, String name) {
        super(zoneId, name);
    }

    @Override
    public void tick(ParkState state) {
        // Si no hay un apagón y el nivel de energía eléctrica es óptimo, se activan los sistemas de soporte
        if (!state.isBlackoutActive() && state.getPowerEnergy() > 10) {
            System.out.println("[" + getName() + "] Automatización C6 activa. Alimentando especímenes en cautiverio...");
            
            // Alimentamos a todos los dinosaurios mapeados en el estado
            for (Dinosaur dino : state.getDinosaurs()) {
                dino.feed(); 
            }
        } else {
            System.out.println("[ALERTA " + getName() + "] Comederos eléctricos apagados. ¡Riesgo de alimentación y estrés!");
        }
    }
}