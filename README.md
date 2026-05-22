# Dinosaur Park Simulator - Laboratorio Bloque 4

## Descripcion General del Sistema
Este proyecto es un sistema de simulacion interactivo disenado en Java para gestionar y supervisar las operaciones complejas de un Parque Tematico de Dinosaurios. El nucleo del sistema ejecuta ciclos de tiempo continuos (ticks) donde se simula el comportamiento biologico de los dinosaurios, la gestion de la flotilla de vehiculos del parque y las actividades del personal operativo ante eventos de crisis.

---

## Herramientas y Tecnologias Utilizadas
* Lenguaje: Java 17 / 21
* Gestor de Dependencias: Maven (v3.8+)
* Base de Datos: Motor de persistencia relacional para el almacenamiento de metricas e historicos.
* Pruebas Unitarias: JUnit 5
* Reportes de Cobertura: JaCoCo Plugin (v0.8.11)

---

## Patrones de Diseno Aplicados

### 1. Patron Strategy (Estrategia)
Utilizado para el procesamiento dinamico de eventos aleatorios dentro de la simulacion. Permite que las crisis del parque (como StormEvent, BlackoutEvent, DealsHourEvent y VehicleFailureEvent) compartan una interfaz comun y ejecuten comportamientos completamente diferentes de manera desacoplada segun el ciclo.

### 2. Template Method / Heredancia Polimorfica
* Entidades Biologicas: La clase abstracta Dinosaur define las firmas esenciales (feed y tick). Clases concretas como Carnivore y Herbivore extienden esta base para aplicar logicas de negocio independientes (comportamiento del hambre de carne frente a plantas y tasas de desgaste de salud).
* Personal Operativo: La clase abstracta Worker sirve como plantilla base para los roles del parque, permitiendo que la clase Congreso Technician implemente su deber especifico e interactue dinamicamente con el estado de los vehiculos.

---

## Instrucciones de Configuracion y Ejecucion

### Prerrequisitos
Asegurate de contar con el Java Development Kit (JDK) instalado y configurado en tus variables de entorno, junto con Apache Maven.

### 1. Clonar el repositorio
git clone https://github.com/lacp2704/A12_Laboratorio-Bloque-4.git
cd A12_Laboratorio-Bloque-4

### 2. Limpiar y Compilar el Proyecto
Para descargar las dependencias y compilar los archivos fuentes, ejecuta:
mvn clean compile

### 3. Ejecutar las Pruebas Unitarias y Validar Cobertura (JaCoCo)
El proyecto cuenta con reglas estrictas de verificacion de calidad. Para correr la suite completa de pruebas unitarias y verificar que se cumpla el minimo del 65% de cobertura de código mandatorio, ejecuta:
mvn verify

Si la cobertura cumple con el umbral, la consola mostrara el mensaje: All coverage checks have been met. BUILD SUCCESS.

### 4. Ejecucion del Sistema
Para levantar la aplicacion principal y comenzar la simulacion del parque:
mvn exec:java -Dexec.mainClass="com.axity.dinosaurpark.Main"

---

## Persistencia de Datos
El sistema cumple estrictamente con el registro persistente en Base de Datos de los siguientes modulos:
1. Gastos Operativos: Registro detallado de costos de mantenimiento, consumo de energia y reparaciones de infraestructura.
2. Historico de Incidentes: Almacenamiento auditable de todas las crisis detonadas (apagones, tormentas y fallas mecanicas de la flota).

## Autor
* **Desarrollador:** Luis Armando Cruz Prospero
* **GitHub:** [@lacp2704](https://github.com/lacp2704)
* **Proyecto:** Laboratorio Bloque 4 - Axity
