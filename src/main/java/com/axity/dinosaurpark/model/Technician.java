package com.axity.dinosaurpark.model;

import java.util.List;

public class Technician extends Worker {

    public Technician(String id, String name, double salary) {
        super(id, name, salary);
    }

    @Override
    public void performDuty() {
        // Lógica general que realiza el técnico en un turno normal
    }

    /**
     * Intenta reparar un elemento averiado,condicionado a la disponibilidad de transporte.
     * @param fleet Lista de vehículos del parque disponibles en el ecosistema.
     * @return true si se encontró vehículo y se ejecutó la reparación; false en caso contrario.
     */
    public boolean repairIfNeeded(List<Vehicle> fleet) {
        // Regla: Buscar el primer vehículo que esté completamente libre
        Vehicle assignedVehicle = null;
        for (Vehicle vehicle : fleet) {
            if (vehicle.getStatus() == VehicleStatus.AVAILABLE) {
                assignedVehicle = vehicle;
                break; // Rompemos el ciclo al encontrar el primero disponible
            }
        }

        // Si no hay vehículos utilizables, la operación se cancela de inmediato
        if (assignedVehicle == null) {
            System.out.println("[TÉCNICO] No se puede realizar la reparación: No hay vehículos DISPONIBLE");
            return false;
        }

        // Simulación del uso temporal del transporte durante el viaje
        assignedVehicle.setStatus(VehicleStatus.IN_USE);
        System.out.println("[TÉCNICO] Vehículo [" + assignedVehicle.getId() + "] asignado. Reparación ejecutada con éxito.");
        
        // Al terminar la acción inmediata, se libera el vehículo para el siguiente ciclo
        assignedVehicle.setStatus(VehicleStatus.AVAILABLE);
        return true;
    }
}