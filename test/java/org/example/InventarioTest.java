package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class InventarioTest {

    private Inventario inventario;

    @BeforeEach
    public void setUp() {
        // Limpiar los autos disponibles antes de cada prueba
        inventario = Inventario.getInstance();
        inventario.buscarAutos().clear(); // Limpiar la lista de autos
    }

    @Test
    public void testAgregarAuto() {
        Auto auto = new Auto("JKL456", "BMW", "X5", 2023, "Gris", 40000);

        // Agregar auto al inventario
        inventario.agregarAuto(auto);

        // Verificar que el auto esté en la lista
        List<Auto> autosDisponibles = inventario.buscarAutos();
        assertEquals(1, autosDisponibles.size());
        assertEquals(auto, autosDisponibles.get(0));
    }

    @Test
    public void testEliminarAuto() {
        Auto auto = new Auto("JKL456", "BMW", "X5", 2023, "Gris", 40000);

        // Agregar y luego eliminar el auto
        inventario.agregarAuto(auto);
        inventario.eliminarAuto(auto);

        // Verificar que la lista esté vacía
        List<Auto> autosDisponibles = inventario.buscarAutos();
        assertEquals(0, autosDisponibles.size());
    }

    @Test
    public void testModificarAuto() {
        Auto auto = new Auto("JKL456", "BMW", "X5", 2023, "Gris", 40000);

        // Agregar auto y modificar su precio
        inventario.agregarAuto(auto);
        inventario.modificarAuto(auto, "precio", "45000");

        // Verificar que el precio se haya actualizado
        assertEquals(45000, auto.getPrecio());
    }
}
