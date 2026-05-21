package com.axity.dinosaurpark.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ParkConfig {

    private static ParkConfig instance;
    private final Properties properties;

    // Constructor privado para evitar instanciación externa (Singleton)
    private ParkConfig() {
        this.properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("park.properties")) {
            if (input == null) {
                throw new RuntimeException("Error: No se pudo encontrar el archivo park.properties");
            }
            // Carga las propiedades en memoria una única vez
            this.properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar la configuración del parque", e);
        }
    }

    // Método de acceso global  (Lazy Initialization)
    public static synchronized ParkConfig getInstance() {
        if (instance == null) {
            instance = new ParkConfig();
        }
        return instance;
    }

    
    //obtiene una propiedad de tipo String.
    public String getString(String key) {
        return properties.getProperty(key);
    }

    
    //Obtiene una propiedad convertida a Integer    
    public int getInt(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("La propiedad [" + key + "] no existe en park.properties");
        }
        return Integer.parseInt(value.trim());
    }

    
    //Obtiene una propiedad convertida a Double de forma segura.     
    public double getDouble(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("La propiedad [" + key + "] no existe en park.properties");
        }
        return Double.parseDouble(value.trim());
    }
}