package com.axity.dinosaurpark;

import com.axity.dinosaurpark.simulation.SimulationEngine;
import com.axity.dinosaurpark.simulation.ParkState;
import com.axity.dinosaurpark.persistence.DatabaseService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SimulationEngineTest {

    @Test
    public void testEngineRunsSuccessfully() {
        DatabaseService dbService = new DatabaseService();
        ParkState state = new ParkState(dbService);
        SimulationEngine engine = new SimulationEngine(state);
        assertDoesNotThrow(() -> engine.startSimulation());
    }
}