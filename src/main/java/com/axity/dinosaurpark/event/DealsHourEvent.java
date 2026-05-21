package com.axity.dinosaurpark.event;

import com.axity.dinosaurpark.simulation.ParkState;
import java.util.Random;

public class DealsHourEvent implements SimulationEvent {
    @Override
    public double getProbability() {
        return 0.12; // 12% de probabilidad
    }

    @Override
    public void execute(ParkState state, Random rng) {
        System.out.println("[OFERTA] ¡Inicia la Hora de Ofertas! Descuentos del 30% en tiendas de souvenirs y boletos");
        state.setDealsHourActive(true);
        state.setCurrentDiscount(0.30); // 30% fijado por regla de negocio
        
        state.getDatabaseService().appendEvent("OFERTAS", "Hora de ofertas activada con 30% de descuento general");
    }
}