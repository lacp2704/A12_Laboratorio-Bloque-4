package com.axity.dinosaurpark.simulation;

import com.axity.dinosaurpark.config.ParkConfig;
import com.axity.dinosaurpark.event.*;
import com.axity.dinosaurpark.model.Dinosaur;
import com.axity.dinosaurpark.model.Vehicle;
import com.axity.dinosaurpark.zone.ArrivalZone;
import com.axity.dinosaurpark.zone.DinoEnclosureZone;
import com.axity.dinosaurpark.zone.ParkZone;
import com.axity.dinosaurpark.zone.PowerPlantZone;
import com.axity.dinosaurpark.zone.RestroomZone;
import com.axity.dinosaurpark.zone.SouvenirShopZone;

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
        state.getZones().add(new ArrivalZone("Z-01", "Muelle de arribo Internacional"));
        state.getZones().add(new DinoEnclosureZone("Z-02", "Paddock Central de Depredadores"));
        state.getZones().add(new PowerPlantZone("Z-03", "Planta geotérmica principal"));
        state.getZones().add(new SouvenirShopZone("Z-04", "Tienda de souvenirs T-Rex"));
        state.getZones().add(new RestroomZone("Z-05", "Estación de servicios y descanso"));
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
                // Si el vehículo está operativo, simulamos su uso basado en las condiciones del parque
                if (vehicle.getStatus() != com.axity.dinosaurpark.model.VehicleStatus.BROKEN) {
                    if (state.isStormActive() || state.isBlackoutActive()) {
                        // Ante clima hostil o apagón, los vehículos se resguardan en la estación
                        vehicle.setStatus(com.axity.dinosaurpark.model.VehicleStatus.AVAILABLE);
                    } else {
                        // En operación normal, los vehículos salen a campo a pasear turistas de manera aleatoria
                        if (this.rng.nextDouble() < 0.6) { // 60% de probabilidad de estar en ruta
                            vehicle.setStatus(com.axity.dinosaurpark.model.VehicleStatus.IN_USE);
                        } else {
                            vehicle.setStatus(com.axity.dinosaurpark.model.VehicleStatus.AVAILABLE);
                        }
                    }
                }                
                // Ejecuta su desgaste interno ordinario (tick)
                vehicle.tick();
            }
            for (Dinosaur dinosaur : state.getDinosaurs()) {
                dinosaur.tick();
            }

            //Las zonas procesan su estado 
            for (ParkZone zone : state.getZones()) {
                zone.tick(state);
            }

            //Desplega instantánea en el monitor si corresponde al ciclo
            monitor.displaySnapshot(state);

            // Pequeña pausa para poder leer la terminal de forma fluida
            try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }

        System.out.println("Simulación finalizada con éxito");
        
        //Operación de cierre obligatoria al terminar el motor
        try {
            if (state.getDatabaseService().getConnection() != null && !state.getDatabaseService().getConnection().isClosed()) {
                state.getDatabaseService().getConnection().close();
                System.out.println("Conexión física con H2 liberada correctamente");
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