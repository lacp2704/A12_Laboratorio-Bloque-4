package com.axity.dinosaurpark.model;

public class Vehicle {
    private final String id;
    private VehicleStatus status;
    private int repairCountdown;

    public Vehicle(String id) {
        this.id = id;
        this.status = VehicleStatus.AVAILABLE;
        this.repairCountdown = 0;
    }

    /**
     * Fuerza al vehículo a entrar en estado de mantenimiento
     * @param duration Pasos necesarios para completar la reparación
     */
    public void breakDown(int duration) {
        this.status = VehicleStatus.BROKEN;
        this.repairCountdown = duration;
    }

    
    //Avanza el tiempo del vehículo. Si está roto, reduce el contador hasta liberarlo automáticamente     
    public void tick() {
        if (this.status == VehicleStatus.BROKEN && this.repairCountdown > 0) {
            this.repairCountdown--;
            if (this.repairCountdown == 0) {
                this.status = VehicleStatus.AVAILABLE;
            }
        }
    }

    // Getters y Setters
    public String getId() { return id; }
    public VehicleStatus getStatus() { return status; }
    public void setStatus(VehicleStatus status) { this.status = status; }
    public int getRepairCountdown() { return repairCountdown; }
}