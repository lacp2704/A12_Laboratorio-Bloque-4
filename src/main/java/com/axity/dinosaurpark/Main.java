package com.axity.dinosaurpark;

import java.math.BigDecimal;

import com.axity.dinosaurpark.persistence.DatabaseService;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando aplicación...");
        
        // Al instanciarse, creará la carpeta /data y ejecutará los changelogs
        DatabaseService dbService = new DatabaseService();
        
        // Prueba de inserción segura
        dbService.appendEvent("INFO", "Sistema base inicializado con éxito.");
        dbService.appendRevenue(new BigDecimal("150.50"), "Pago de entrada estacionamiento");
        
        System.out.println("Proceso completo");
    }
}