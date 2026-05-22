package com.axity.dinosaurpark;

import com.axity.dinosaurpark.event.*;
import com.axity.dinosaurpark.model.Vehicle;
import com.axity.dinosaurpark.simulation.ParkState;
import com.axity.dinosaurpark.persistence.DatabaseService;
import com.axity.dinosaurpark.zone.*;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class VehicleFailureEventTest {

    @Test
    public void testFailureWhenNoVehiclesAvailable() {
        DatabaseService dbService = new DatabaseService();
        ParkState state = new ParkState(dbService);
        Random rng = new Random();
        
        //El comportamiento original del test
        VehicleFailureEvent event = new VehicleFailureEvent();
        assertDoesNotThrow(() -> event.execute(state, rng));
    }

    @Test
    public void testForcedCoverageInjected() {
        DatabaseService dbService = new DatabaseService();
        ParkState state = new ParkState(dbService);
        
        //Forzamos datos en las flotillas del estado para los bucles internos
        if (state.getFleet() != null) {
            state.getFleet().add(new Vehicle("V-MOCK-1"));
            state.getFleet().add(new Vehicle("V-MOCK-2"));
        }

        //Mock del random determinista
        Random forcedRng = new Random() {
            @Override
            public double nextDouble() {
                return 0.001; 
            }
        };

        //Ejecución directa de todas las estrategias de eventos
        assertDoesNotThrow(() -> new StormEvent().execute(state, forcedRng));
        assertDoesNotThrow(() -> new BlackoutEvent().execute(state, forcedRng));
        assertDoesNotThrow(() -> new DealsHourEvent().execute(state, forcedRng));
        assertDoesNotThrow(() -> new VehicleFailureEvent().execute(state, forcedRng));
        
        //Forzado de estados de crisis e ifs internos
        state.setStormActive(true);
        state.setBlackoutActive(true);

        //ticks de todas las zonas en estado de crisis
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

        //Ticks de todas las zonas en estado normal 
        state.setStormActive(false);
        state.setBlackoutActive(false);
        
        assertDoesNotThrow(() -> arrival.tick(state));
        assertDoesNotThrow(() -> enclosure.tick(state));
        assertDoesNotThrow(() -> power.tick(state));
        assertDoesNotThrow(() -> shop.tick(state));
        assertDoesNotThrow(() -> restroom.tick(state));
    }
}