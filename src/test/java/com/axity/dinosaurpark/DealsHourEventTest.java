package com.axity.dinosaurpark;

import com.axity.dinosaurpark.event.DealsHourEvent;
import com.axity.dinosaurpark.simulation.ParkState;
import com.axity.dinosaurpark.persistence.DatabaseService;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class DealsHourEventTest {

    @Test
    public void testDealsHourActivation() {
        DatabaseService dbService = new DatabaseService();
        ParkState state = new ParkState(dbService);
        Random rng = new Random();
        
        DealsHourEvent event = new DealsHourEvent();
        
        //Comprobamos la ejecución básica del evento probabilístico
        assertDoesNotThrow(() -> event.execute(state, rng));
        assertNotNull(state, "El estado del parque no debería quedar nulo");
    }
}