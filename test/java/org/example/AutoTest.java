package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AutoTest {

    @Test
    public void testConstructor() {
        Auto auto = new Auto("ABC123", "Toyota", "Corolla", 2020, "Rojo", 20000);
        assertEquals("ABC123", auto.getPatente());
        assertEquals("Toyota", auto.getMarca());
        assertEquals("Corolla", auto.getModelo());
        assertEquals(2020, auto.getAno());
        assertEquals("Rojo", auto.getColor());
        assertEquals(20000, auto.getPrecio());
        assertFalse(auto.isVendido());
    }

    @Test
    public void testSettersAndGetters() {
        Auto auto = new Auto();
        auto.setPatente("XYZ456");
        auto.setMarca("Honda");
        auto.setModelo("Civic");
        auto.setAno(2022);
        auto.setColor("Azul");
        auto.setPrecio(22000);
        auto.setVendido(true);

        assertEquals("XYZ456", auto.getPatente());
        assertEquals("Honda", auto.getMarca());
        assertEquals("Civic", auto.getModelo());
        assertEquals(2022, auto.getAno());
        assertEquals("Azul", auto.getColor());
        assertEquals(22000, auto.getPrecio());
        assertTrue(auto.isVendido());
    }
}
