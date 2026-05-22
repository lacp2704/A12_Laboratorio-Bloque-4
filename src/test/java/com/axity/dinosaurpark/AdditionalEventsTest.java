package com.axity.dinosaurpark;

import com.axity.dinosaurpark.event.*;
import com.axity.dinosaurpark.model.Vehicle;
import com.axity.dinosaurpark.simulation.ParkState;
import com.axity.dinosaurpark.persistence.DatabaseService;
import com.axity.dinosaurpark.zone.*;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class AdditionalEventsTest {

    @Test
    public void testComprehensiveParkElements() {
        DatabaseService dbService = new DatabaseService();
        ParkState state = new ParkState(dbService);
        
        // listas internas
        if (state.getFleet() != null) {
            state.getFleet().add(new Vehicle("V-MOCK-1"));
            state.getFleet().add(new Vehicle("V-MOCK-2"));
        }

        Random forcedRng = new Random() {
            @Override
            public double nextDouble() {
                return 0.001; 
            }
        };

        // nstanciamos y ejecutamos las estrategias de eventos
        SimulationEvent storm = new StormEvent();
        SimulationEvent blackout = new BlackoutEvent();
        SimulationEvent deals = new DealsHourEvent();
        SimulationEvent failure = new VehicleFailureEvent();

        assertDoesNotThrow(() -> storm.execute(state, forcedRng));
        assertDoesNotThrow(() -> blackout.execute(state, forcedRng));
        assertDoesNotThrow(() -> deals.execute(state, forcedRng));
        assertDoesNotThrow(() -> failure.execute(state, forcedRng));
        
        //Forzamos flags en True para cubrir protocolos de crisis en las zonas
        state.setStormActive(true);
        state.setBlackoutActive(true);

        //Instanciamos y ejecutamos los ticks de todas las zonas del parque
        ParkZone arrival = new ArrivalZone("Z-MOCK-01", "Arrival");
        ParkZone enclosure = new DinoEnclosureZone("Z-MOCK-02", "Enclosure");
        ParkZone power = new PowerPlantZone("Z-MOCK-03", "Power");
        ParkZone shop = new SouvenirShopZone("Z-MOCK-04", "Shop");
        ParkZone restroom = new RestroomZone("Z-MOCK-05", "Restroom");

        assertDoesNotThrow(() -> arrival.tick(state));
        assertDoesNotThrow(() -> enclosure.tick(state));
        assertDoesNotThrow(() -> power.tick(state));
        assertDoesNotThrow(() -> shop.tick(state));
        assertDoesNotThrow(() -> restroom.tick(state));

        //False para cubrir las ramas normales de las zonas
        state.setStormActive(false);
        state.setBlackoutActive(false);
        
        assertDoesNotThrow(() -> arrival.tick(state));
        assertDoesNotThrow(() -> enclosure.tick(state));
        assertDoesNotThrow(() -> power.tick(state));
        assertDoesNotThrow(() -> shop.tick(state));
        assertDoesNotThrow(() -> restroom.tick(state));
    }
}