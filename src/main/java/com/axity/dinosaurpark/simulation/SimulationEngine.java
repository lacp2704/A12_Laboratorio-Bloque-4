package com.axity.dinosaurpark.simulation;

import com.axity.dinosaurpark.config.ParkConfig;
import com.axity.dinosaurpark.event.*;
import com.axity.dinosaurpark.model.Dinosaur;
import com.axity.dinosaurpark.model.Vehicle;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulationEngine {

    private final ParkState state;
    private final ParkMonitor monitor;
    private final List<SimulationEvent> eventStrategies;
    private final Random rng;
    private final int totalSteps;

    public SimulationEngine(ParkState state) {
        this.state = state;
        ParkConfig config = ParkConfig.getInstance();
        
        //Configura intervalos a través del Singleton
        this.totalSteps = config.getInt("simulation.totalSteps");
        this.monitor = new ParkMonitor(config.getInt("monitoring.intervalSteps"));
        
        //Inicializa generador aleatorio No Determinista
        this.rng = new Random();
        
        //Registrar los 5 eventos del Patrón Strategy
        this.eventStrategies = new ArrayList<>();
        this.eventStrategies.add(new DinosaurEscapeEvent());
        this.eventStrategies.add(new BlackoutEvent());
        this.eventStrategies.add(new StormEvent());
        this.eventStrategies.add(new DealsHourEvent());
        this.eventStrategies.add(new VehicleFailureEvent());

        //Inicialización de la flotilla de vehículos configurada
        int vehicleCount = config.getInt("vehicles.count");
        for (int i = 1; i <= vehicleCount; i++) {
            state.getFleet().add(new Vehicle("V-" + i));
        }
    }

    
    // Aqui se lanza el ciclo principal de la simulación del parque
    public void startSimulation() {
        System.out.println("Iniciando Motor de Simulación No Determinista...");

        for (int step = 1; step <= totalSteps; step++) {
            state.setCurrentStep(step);
            
            //Limpia el estado de los eventos del paso anterior
            state.clearActiveEvents();
            
            //Consumo eléctrico base por cada paso
            double consumption = ParkConfig.getInstance().getDouble("powerplant.consumptionPerStep");
            state.setPowerEnergy(Math.max(0, state.getPowerEnergy() - consumption));

            //Evalua y dispara eventos aleatorios por probabilidad (Strategy)
            checkAndFireEvents();

            //Avanza el tiempo autónomo de los vehículos y dinosaurios
            for (Vehicle vehicle : state.getFleet()) {
                vehicle.tick();
            }
            for (Dinosaur dinosaur : state.getDinosaurs()) {
                dinosaur.tick();
            }

            //Desplega instantánea en el monitor si corresponde al ciclo
            monitor.displaySnapshot(state);

            //Pequeña pausa opcional para poder leer la terminal de forma fluida
            try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }

        System.out.println("Simulación finalizada con éxito.");
        
        //Operación de cierre obligatoria al terminar el motor
        try {
            if (state.getDatabaseService().getConnection() != null && !state.getDatabaseService().getConnection().isClosed()) {
                state.getDatabaseService().getConnection().close();
                System.out.println("Conexión física con H2 liberada correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar el servicio de base de datos: " + e.getMessage());
        }
    }

    
    //Compara de manera probabilística el factor aleatorio contra cada estrategia de evento.
    private void checkAndFireEvents() {
        for (SimulationEvent event : eventStrategies) {
            if (this.rng.nextDouble() < event.getProbability()) {
                event.execute(state, this.rng);
                // Registra el nombre de la clase para el Monitor visual
                state.getActiveEventsInStep().add(event.getClass().getSimpleName());
            }
        }
    }
}