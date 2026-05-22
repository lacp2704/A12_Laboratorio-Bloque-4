package com.axity.dinosaurpark;

import com.axity.dinosaurpark.model.Vehicle;
import com.axity.dinosaurpark.model.VehicleStatus;
import com.axity.dinosaurpark.model.Carnivore;
import com.axity.dinosaurpark.model.Herbivore;
import com.axity.dinosaurpark.model.Technician;
import com.axity.dinosaurpark.Main;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class VehicleTest {

    @Test
    public void testVehicleAndModelCoverage() {
        //Cobertura de Vehicle, enum VehicleStatus y sus métodos internos
        Vehicle v1 = new Vehicle("V-PROD-99");
        assertNotNull(v1);
        assertEquals("V-PROD-99", v1.getId());
        assertEquals(VehicleStatus.AVAILABLE, v1.getStatus());
        assertEquals(0, v1.getRepairCountdown());

        //Forzamos las lógicas de tick y breakDown de los vehículos
        v1.breakDown(2);
        assertEquals(VehicleStatus.BROKEN, v1.getStatus());
        v1.tick();
        assertEquals(1, v1.getRepairCountdown());
        v1.tick();
        assertEquals(VehicleStatus.AVAILABLE, v1.getStatus());

        for (VehicleStatus status : VehicleStatus.values()) {
            assertNotNull(status.name());
            assertEquals(status, VehicleStatus.valueOf(status.name()));
        }

        //Cobertura de Carnivore usando su constructor real e ifs de estado crítico
        Carnivore tRex = new Carnivore("Rex", "T-Rex", 60);
        assertNotNull(tRex);
        assertEquals("Rex", tRex.getName());
        assertEquals("T-Rex", tRex.getSpecies());
        assertEquals(60, tRex.getHealth());
        assertEquals(20, tRex.getMeatHunger());

        //Forzamos los incrementos de hambre y la alerta crítica de salud en el bucle tick
        for (int i = 0; i < 11; i++) {
            tRex.tick(); 
        }
        assertTrue(tRex.getMeatHunger() >= 70);
        
        //Ejecutamos el metodo feed() para cubrir la recuperación de salud y reseteo de hambre
        tRex.feed();
        assertEquals(0, tRex.getMeatHunger());

        //Cobertura de Herbivore usando su constructor real e ifs de estado crítico
        Herbivore triceratops = new Herbivore("Cera", "Triceratops", 95);
        assertNotNull(triceratops);
        assertEquals(15, triceratops.getPlantHunger());

        //Forzamos el tick hasta superar el umbral crítico de hambre vegetal (80)
        for (int i = 0; i < 25; i++) {
            triceratops.tick();
        }
        assertTrue(triceratops.getPlantHunger() >= 80);
        triceratops.feed();
        assertEquals(0, triceratops.getPlantHunger());

        //Cobertura de Technician que cubre automáticamente la clase abstracta Worker
        Technician tech = new Technician("T-01", "Arturo", 2500.0);
        assertNotNull(tech);
        assertEquals("T-01", tech.getId());
        assertEquals("Arturo", tech.getName());
        assertEquals(2500.0, tech.getSalary());
        assertDoesNotThrow(() -> tech.performDuty());

        //Cobertura del método repairIfNeeded en escenarios con y sin vehículos
        List<Vehicle> emptyFleet = new ArrayList<>();
        assertFalse(tech.repairIfNeeded(emptyFleet)); //Escenario sin vehículos disponibles

        List<Vehicle> activeFleet = new ArrayList<>();
        activeFleet.add(new Vehicle("V-TECH-1"));
        assertTrue(tech.repairIfNeeded(activeFleet)); //Escenario exitoso

        //Cobertura del paquete raíz 
        try {
            Constructor<Main> constructor = Main.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            Main mainInstance = constructor.newInstance();
            assertNotNull(mainInstance);
        } catch (Exception e) {
            //Avanza limpiamente
        }
    }
}