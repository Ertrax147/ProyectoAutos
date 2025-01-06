package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class VentaTest {

    @BeforeEach
    public void setUp() {
        // Limpiar el historial de ventas antes de cada prueba
        Venta.getHistorialVentas().clear();
    }

    @Test
    public void testVentaMonto() {
        // Crear objetos Auto y Cliente
        Auto auto = new Auto("GHI123", "Chevrolet", "Malibu", 2021, "Blanco", 25000);
        Cliente cliente = new Cliente("Ana", "ana@mail.com", "abc123");

        // Registramos la venta
        Venta.registrarVenta(cliente, auto, 25000);

        // Verificamos el monto de la venta
        List<Venta> historial = Venta.getHistorialVentas();
        assertEquals(25000, historial.get(0).getMonto(), "El monto de la venta debería ser 25000.");
    }
}
