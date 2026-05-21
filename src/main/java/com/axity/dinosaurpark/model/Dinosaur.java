package com.axity.dinosaurpark.model;

public abstract class Dinosaur {
    private final String name;
    private final String species;
    private int health;

    public Dinosaur(String name, String species, int health) {
        this.name = name;
        this.species = species;
        this.health = health;
    }

    // Cada tipo de dinosaurio (Carnívoro/Herbívoro) procesará su alimentación de forma distinta
    public abstract void feed();

    // Comportamiento del dinosaurio en cada ciclo o paso de la simulación
    public abstract void tick();

    // Getters y Setters
    public String getName() { return name; }
    public String getSpecies() { return species; }
    public int getHealth() { return health; }
    protected void setHealth(int health) { this.health = health; }
}