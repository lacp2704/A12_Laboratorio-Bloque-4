package com.axity.dinosaurpark.zone;

import com.axity.dinosaurpark.simulation.ParkState;

public abstract class ParkZone {
    private final String zoneId;
    private final String name;
    private int touristCount;

    public ParkZone(String zoneId, String name) {
        this.zoneId = zoneId;
        this.name = name;
        this.touristCount = 0;
    }
    
    //Ciclo de simulación específico para el comportamiento interno de cada zona
    public abstract void tick(ParkState state);

    //Métodos comunes para la gestión de turistas dentro de la infraestructura
    public void addTourists(int count) {
        this.touristCount += count;
    }

    public void removeTourists(int count) {
        this.touristCount = Math.max(0, this.touristCount - count);
    }

    // Getters
    public String getZoneId() { return zoneId; }
    public String getName() { return name; }
    public int getTouristCount() { return touristCount; }
}