package com.axity.dinosaurpark.zone;

import com.axity.dinosaurpark.simulation.ParkState;

public class RestroomZone extends ParkZone {

    public RestroomZone(String zoneId, String name) {
        super(zoneId, name);
    }

    @Override
    public void tick(ParkState state) {
        // Monitorea flujos sanitarios 
        if (state.isStormActive()) {
            System.out.println("[" + getName() + "] Alta ocupación: los turistas hacen uso del espacio de baño");
        } else {
            System.out.println("[" + getName() + "] Servicios funcionando con normalidad");
        }
    }
}