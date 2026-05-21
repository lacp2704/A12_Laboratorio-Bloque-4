package com.axity.dinosaurpark.zone;

import com.axity.dinosaurpark.simulation.ParkState;

public class PowerPlantZone extends ParkZone {

    public PowerPlantZone(String zoneId, String name) {
        super(zoneId, name);
    }

    @Override
    public void tick(ParkState state) {
        if (state.isBlackoutActive()) {
            System.out.println("[" + getName() + "] Se han apagado los interruptores principales. Se requiere intervención técnica.");
        } else {
            // Recarga las baterías del parque de forma progresiva hasta el tope del 100%
            double currentPower = state.getPowerEnergy();
            state.setPowerEnergy(Math.min(100.0, currentPower + 8.0));
            System.out.println("[" + getName() + "] Generadores estables. Energía actual: " + state.getPowerEnergy() + "%");
        }
    }
}