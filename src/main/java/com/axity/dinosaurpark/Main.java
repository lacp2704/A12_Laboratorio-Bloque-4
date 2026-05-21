package com.axity.dinosaurpark;

import com.axity.dinosaurpark.model.Carnivore;
import com.axity.dinosaurpark.model.Herbivore;
import com.axity.dinosaurpark.persistence.DatabaseService;
import com.axity.dinosaurpark.simulation.ParkState;
import com.axity.dinosaurpark.simulation.SimulationEngine;

public class Main {
    public static void main(String[] args) {
        System.out.println("*** SISTEMA DE MONITOREO DE DINOSAURIOS ***");
        
        //Inicializa la persistencia
        DatabaseService dbService = new DatabaseService();
        
        //Inicializa el contenedor de estado global
        ParkState state = new ParkState(dbService);
        
        // Añade especímenes iniciales de prueba para validar el tick()
        state.getDinosaurs().add(new Carnivore("Rexy", "T-Rex", 100));
        state.getDinosaurs().add(new Herbivore("Lucy", "Parasaurolophus", 100));

        // Construye y arranca el motor de la simulación
        SimulationEngine engine = new SimulationEngine(state);
        engine.startSimulation();
    }
}