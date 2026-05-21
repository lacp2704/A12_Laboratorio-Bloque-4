package com.axity.dinosaurpark.model;

public abstract class Worker {
    private final String id;
    private final String name;
    private final double salary;

    public Worker(String id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    // Cada rol realiza una acción diferente durante la jornada
    public abstract void performDuty();

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public double getSalary() { return salary; }
}