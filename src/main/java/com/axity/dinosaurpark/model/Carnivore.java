package com.axity.dinosaurpark.model;

public class Carnivore extends Dinosaur {

    private int meatHunger; // Nivel de hambre (0 = Satisfecho, 100 = Hambriento/Peligro)

    public Carnivore(String name, String species, int health) {
        super(name, species, health);
        this.meatHunger = 20; // Inicia con un nivel de hambre base bajo
    }

    @Override
    public void feed() {
        // Al alimentar al carnívoro, su hambre disminuye a cero y recupera salud si estaba herido
        this.meatHunger = 0;
        if (getHealth() < 100) {
            setHealth(Math.min(100, getHealth() + 15));
        }
        System.out.println("[CARNÍVORO] " + getName() + " (" + getSpecies() + ") ha sido alimentado con carne fresca. Hambre restablecida.");
    }

    @Override
    public void tick() {
        // En cada paso de la simulación, el hambre aumenta progresivamente
        this.meatHunger += 5;

        // Si el hambre supera un umbral crítico (ej. 70), empieza a perder salud 
        if (this.meatHunger >= 70) {
            setHealth(Math.max(0, getHealth() - 10));
            System.out.println("[ALERTA CARNÍVORO] " + getName() + " tiene demasiada hambre (" + meatHunger + "%). Su salud está bajando: " + getHealth() + "%");
        }
    }

    // Getter específico
    public int getMeatHunger() {
        return meatHunger;
    }
}