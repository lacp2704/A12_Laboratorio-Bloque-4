package com.axity.dinosaurpark.model;

public class Herbivore extends Dinosaur {

    private int plantHunger; // Nivel de hambre vegetal

    public Herbivore(String name, String species, int health) {
        super(name, species, health);
        this.plantHunger = 15; // Inicia con hambre base controlada
    }

    @Override
    public void feed() {
        // Al alimentar al herbívoro, consume plantas y estabiliza sus constantes
        this.plantHunger = 0;
        if (getHealth() < 100) {
            setHealth(Math.min(100, getHealth() + 10));
        }
        System.out.println("[HERBÍVORO] " + getName() + " (" + getSpecies() + ") ha pastado correctamente. Energía y salud estables.");
    }

    @Override
    public void tick() {
        // El hambre de los herbívoros sube de forma ligeramente más lenta que la de los carnívoros
        this.plantHunger += 3;

        // Si llega a un nivel de hambre crítico, BAJA salud de forma paulatina
        if (this.plantHunger >= 80) {
            setHealth(Math.max(0, getHealth() - 5));
            System.out.println("[AVISO HERBÍVORO] " + getName() + " necesita plantas para pastar. Salud actual: " + getHealth() + "%");
        }
    }

    // Getter específico
    public int getPlantHunger() {
        return plantHunger;
    }
}