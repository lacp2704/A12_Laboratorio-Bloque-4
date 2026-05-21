package com.axity.dinosaurpark.persistence; 

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DatabaseService {

    private static final String URL = "jdbc:h2:./data/parkdb";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private Connection connection;

    public DatabaseService() {
        try {
            // 1. Inicializar la conexión JDBC con H2
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            
            // 2. Ejecutar Liquibase para aplicar migraciones pendientes
            runLiquibase();
        } catch (Exception e) {
            throw new RuntimeException("Error al inicializar la base de datos o ejecutar Liquibase", e);
        }
    }

    private void runLiquibase() throws Exception {
        Database database = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(new JdbcConnection(this.connection));
        
        //Instancia Liquibase de forma directa (sin try-with-resources para no cerrar la conexión)
        Liquibase liquibase = new Liquibase("db/changelog/db.changelog-master.xml", 
                new ClassLoaderResourceAccessor(), database);
        
        //Ejecuta la actualización de los changeSets
        liquibase.update("");
    }

    public Connection getConnection() {
        return this.connection;
    }

    
     // Inserta un registro en la tabla de ingresos usando PreparedStatement de manera segura.
     
    public void appendRevenue(BigDecimal amount, String description) {
        String sql = "INSERT INTO revenues (amount, description, created_at) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setBigDecimal(1, amount);
            pstmt.setString(2, description);
            pstmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar ingreso", e);
        }
    }

    
     // Inserta un registro en la tabla de gastos de manera segura.
     
    public void appendExpense(BigDecimal amount, String description) {
        String sql = "INSERT INTO expenses (amount, description, created_at) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setBigDecimal(1, amount);
            pstmt.setString(2, description);
            pstmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar gasto", e);
        }
    }

    
     // Inserta un registro en la tabla de eventos de manera segura.
     
    public void appendEvent(String eventType, String description) {
        String sql = "INSERT INTO events (event_type, description, created_at) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, eventType);
            pstmt.setString(2, description);
            pstmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar evento", e);
        }
    }
}