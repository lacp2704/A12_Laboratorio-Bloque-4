package com.axity.dinosaurpark.zone;

import com.axity.dinosaurpark.simulation.ParkState;

public class SouvenirShopZone extends ParkZone {

    public SouvenirShopZone(String zoneId, String name) {
        super(zoneId, name);
    }

    @Override
    public void tick(ParkState state) {
        if (state.isDealsHourActive()) {
            System.out.println("[" + getName() + "] Aplicando un " + (state.getCurrentDiscount() * 100) + "% de descuento en toda la tienda");
            state.getDatabaseService().appendEvent("VENTA_OFERTA", "Ingresos comerciales incrementados por Las Ofertas");
        } else {
            System.out.println("[" + getName() + "] Operando con precios tradicionales");
        }
    }
}