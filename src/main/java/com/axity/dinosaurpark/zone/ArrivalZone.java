package com.axity.dinosaurpark.zone;

import com.axity.dinosaurpark.simulation.ParkState;
import java.util.Random;

public class ArrivalZone extends ParkZone {

    private final Random random = new Random();

    public ArrivalZone(String id, String name) {
        super(id, name);
    }

    @Override
    public void tick(ParkState state) {
        int actuales = state.getTotalTourists();

        // Escenario de crisis: Si hay un escape o apagón, la prioridad es evacuar
        if (state.getDinosaurs().size() < 2 || state.isBlackoutActive()) {
            if (actuales > 0) { // Solo si hay gente en la isla
                int salidasEmergencia = random.nextInt(15) + 10;
                // Si el random pide sacar más de los que hay, solo sacamos los que quedan
                if (salidasEmergencia > actuales) {
                    salidasEmergencia = actuales;
                }
                state.setTotalTourists(actuales - salidasEmergencia);
                System.out.println("[" + getName() + "] EVACUACIÓN EN CURSO: " + salidasEmergencia
                        + " turistas han abandonado la isla por seguridad.");
            } else {
                // Si ya está en 0, el muelle se queda en silencio avisando que está vacío
                System.out.println("[" + getName()
                        + "] Perímetro en crisis. Isla completamente evacuada. No hay turistas en riesgo.");
            }
            return;
        }

        // Escenario climatico: Si hay tormenta, los arribos se pausan y algunos deciden
        // irse
        if (state.isStormActive()) {
            int salidasPorClima = random.nextInt(10); // Salen entre 0 y 9 personas molestas por la lluvia
            int nuevosActuales = Math.max(0, actuales - salidasPorClima);
            state.setTotalTourists(nuevosActuales);

            System.out.println("[" + getName() + "] Arribos pausados por clima. " + salidasPorClima
                    + " turistas se retiraron a los botes.");
            return;
        }

        // Operación normal: Flujo normal (Entradas y Salidas simultáneas)
        int llegadas = random.nextInt(21); // Entran entre 0 y 20 turistas (Aleatorio)
        int salidas = random.nextInt(16); // Salen entre 0 y 15 turistas hacia el muelle

        // Calculamos el nuevo balance asegurando que no tengamos turistas negativos
        int nuevoBalance = Math.max(0, actuales + llegadas - salidas);
        state.setTotalTourists(nuevoBalance);

        // Mensaje detallado en consola para el desarrollador
        System.out.println(
                String.format("[%s] Flujo turístico ordinario: +%d nuevos arribos / -%d salidas hacia el muelle.",
                        getName(), llegadas, salidas));
    }
}