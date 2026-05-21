package com.axity.dinosaurpark.simulation;

import com.axity.dinosaurpark.model.Dinosaur;
import com.axity.dinosaurpark.model.Vehicle;
import com.axity.dinosaurpark.model.Worker;
import com.axity.dinosaurpark.persistence.DatabaseService;
import com.axity.dinosaurpark.zone.ParkZone;

import java.util.ArrayList;
import java.util.List;

public class ParkState {
    private int currentStep;
    private boolean dealsHourActive;
    private double currentDiscount;
    private double powerEnergy;
    private boolean blackoutActive;
    private boolean stormActive;
    
    // Listas globales de entidades
    private final List<Vehicle> fleet;
    private final List<Dinosaur> dinosaurs;
    private final List<Worker> workers;
    private final List<String> activeEventsInStep;
    private final List<ParkZone> zones = new ArrayList<>();
    
    private final DatabaseService databaseService;

    public ParkState(DatabaseService databaseService) {
        this.currentStep = 0;
        this.dealsHourActive = false;
        this.currentDiscount = 0.0;
        this.powerEnergy = 100.0;
        this.blackoutActive = false;
        this.stormActive = false;
        this.fleet = new ArrayList<>();
        this.dinosaurs = new ArrayList<>();
        this.workers = new ArrayList<>();
        this.activeEventsInStep = new ArrayList<>();
        this.databaseService = databaseService;
    }

    /**
     * Limpia los eventos del paso anterior y restablece las condiciones base 
     * antes de evaluar el nuevo ciclo de la simulación.
     */
    public void clearActiveEvents() {
        this.activeEventsInStep.clear();
        this.dealsHourActive = false;
        this.currentDiscount = 0.0;
        this.blackoutActive = false;
        this.stormActive = false;
        // Nota: La energía no se resetea a 100 automáticamente porque depende del consumo/reparación
    }

    // Getters y Setters
    public int getCurrentStep() { return currentStep; }
    public void setCurrentStep(int currentStep) { this.currentStep = currentStep; }

    public boolean isDealsHourActive() { return dealsHourActive; }
    public void setDealsHourActive(boolean dealsHourActive) { this.dealsHourActive = dealsHourActive; }
    
    public double getCurrentDiscount() { return currentDiscount; }
    public void setCurrentDiscount(double currentDiscount) { this.currentDiscount = currentDiscount; }

    public double getPowerEnergy() { return powerEnergy; }
    public void setPowerEnergy(double powerEnergy) { this.powerEnergy = powerEnergy; }

    public boolean isBlackoutActive() { return blackoutActive; }
    public void setBlackoutActive(boolean blackoutActive) { this.blackoutActive = blackoutActive; }

    public boolean isStormActive() { return stormActive; }
    public void setStormActive(boolean stormActive) { this.stormActive = stormActive; }

    public List<Vehicle> getFleet() { return fleet; }
    public List<Dinosaur> getDinosaurs() { return dinosaurs; }
    public List<Worker> getWorkers() { return workers; }
    public List<String> getActiveEventsInStep() { return activeEventsInStep; }
    public List<ParkZone> getZones() { return zones; }
    public DatabaseService getDatabaseService() { return databaseService; }
}