package com.axity.dinosaurpark.zone;

import com.axity.dinosaurpark.simulation.ParkState;

public class ArrivalZone extends ParkZone {

    public ArrivalZone(String zoneId, String name) {
        super(zoneId, name);
    }

    @Override
    public void tick(ParkState state) {
        // En cada paso del tiempo, llega un flujo constante de turistas por el muelle/helipuerto
        if (!state.isStormActive()) {
            addTourists(15); 
            System.out.println("[" + getName() + "] Han arribado 15 nuevos turistas al parque");
        } else {
            System.out.println("[" + getName() + "] Arribos pausados debido a las condiciones del clima");
        }
    }
}