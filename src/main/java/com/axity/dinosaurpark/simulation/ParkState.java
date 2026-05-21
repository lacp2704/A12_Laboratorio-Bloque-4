package com.axity.dinosaurpark.simulation;

import com.axity.dinosaurpark.model.Vehicle;
import com.axity.dinosaurpark.persistence.DatabaseService;
import java.util.ArrayList;
import java.util.List;

public class ParkState {
    private boolean dealsHourActive;
    private double currentDiscount;
    private double powerEnergy;
    private boolean blackoutActive;
    private boolean stormActive;
    private final List<Vehicle> fleet;
    private final DatabaseService databaseService;

    public ParkState(DatabaseService databaseService) {
        this.dealsHourActive = false;
        this.currentDiscount = 0.0;
        this.powerEnergy = 100.0;
        this.blackoutActive = false;
        this.stormActive = false;
        this.fleet = new ArrayList<>();
        this.databaseService = databaseService;
    }

    // Getters y Setters necesarios para las estrategias
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
    public DatabaseService getDatabaseService() { return databaseService; }
}