package com.axity.dinosaurpark.event;

import com.axity.dinosaurpark.model.Vehicle;
import com.axity.dinosaurpark.model.VehicleStatus;
import com.axity.dinosaurpark.simulation.ParkState;
import java.util.Random;

public class VehicleFailureEvent implements SimulationEvent {
    @Override
    public double getProbability() {
        return 0.10; // 10% de probabilidad de avería mecánica
    }

    @Override
    public void execute(ParkState state, Random rng) {
        System.out.println("[MECÁNICA] Buscando vehículos operativos para simular desgaste...");
        
        Vehicle brokenVehicle = null;
        // Búsqueda del primer vehículo disponiblepara marcarlo como BROKEN
        for (Vehicle vehicle : state.getFleet()) {
            if (vehicle.getStatus() == VehicleStatus.AVAILABLE) {
                brokenVehicle = vehicle;
                break;
            }
        }

        if (brokenVehicle != null) {
            // Se rompe el vehículo. Tomará 3 pasos repararse (según vehicles.repairSteps)
            brokenVehicle.breakDown(3);
            System.out.println("[AVERÍA] El Vehículo [" + brokenVehicle.getId() + "] ha sufrido un desperfecto eléctrico. Estado cambiado a BROKEN");
            
            state.getDatabaseService().appendEvent("FALLA_VEHÍCULO", "Vehículo " + brokenVehicle.getId() + " averiado en campo.");
        } else {
            System.out.println("[INFO] Ocurrió el evento de falla, pero no hay vehículos disponibles actualmente.");
        }
    }
}