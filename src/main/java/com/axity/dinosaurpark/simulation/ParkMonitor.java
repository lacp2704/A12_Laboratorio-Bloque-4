package com.axity.dinosaurpark.simulation;

import com.axity.dinosaurpark.model.Vehicle;
import com.axity.dinosaurpark.model.VehicleStatus;

public class ParkMonitor {

    private final int monitoringInterval;

    public ParkMonitor(int monitoringInterval) {
        this.monitoringInterval = monitoringInterval;
    }

   
     // Muestra el estado del parque de forma condicional si el paso actual coincide con el intervalo
    public void displaySnapshot(ParkState state) {
        if (state.getCurrentStep() % monitoringInterval == 0) {
            
            // Calcular vehículos en uso activo
            long vehiclesInUse = state.getFleet().stream()
                    .filter(v -> v.getStatus() == VehicleStatus.IN_USE)
                    .count();

            System.out.println("\n==================================================");
            System.out.println("MONITOR DEL PARQUE - PASO DEL TIEMPO: " + state.getCurrentStep());
            System.out.println("==================================================");
            System.out.println("Turistas Activos en el Ecosistema : 125 (Flujo Estimado)");
            System.out.println("Dinosaurios bajo Control C6         : " + state.getDinosaurs().size());
            System.out.println("Nivel de Energía Eléctrica          : " + state.getPowerEnergy() + "%");
            System.out.println("Vehículos en uso en Campo            : " + vehiclesInUse + " de " + state.getFleet().size());
            
            String eventos = state.getActiveEventsInStep().isEmpty() 
                    ? "Ninguno (Operación Normal)" 
                    : String.join(", ", state.getActiveEventsInStep());
            System.out.println("Eventos Activos en este Ciclo       : [" + eventos + "]");
            System.out.println("==================================================\n");
        }
    }
}