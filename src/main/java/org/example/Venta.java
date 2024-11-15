package org.example;

import java.util.ArrayList;
import java.util.List;

public class Venta {
    private static List<Venta> historialVentas = new ArrayList<>();
    private Auto auto;
    private Cliente cliente;
    private String fecha;
    private double monto;

    public Venta(Auto auto, Cliente cliente, String fecha, double monto) {
        this.auto = auto;
        this.cliente = cliente;
        this.fecha = fecha;
        this.monto = monto;
        historialVentas.add(this);
    }

    public static void registrarVenta(Cliente cliente, Auto auto, double monto) {
        historialVentas.add(new Venta(auto, cliente, "2024-11-14", monto));
    }

    public static List<Venta> getHistorialVentas() {
        return historialVentas;
    }
}
