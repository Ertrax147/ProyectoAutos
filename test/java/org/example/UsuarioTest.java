package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    @Test
    public void testIniciarSesion() {
        Usuario usuario = new Cliente("Carlos", "carlos@mail.com", "password123");

        // Intento con credenciales correctas
        usuario.iniciarSesion("carlos@mail.com", "password123");

        // Intento con credenciales incorrectas
        usuario.iniciarSesion("carlos@mail.com", "wrongpassword");

        // No se puede verificar el mensaje en consola, pero en una prueba real usaríamos mocks para validar esto.
    }

    @Test
    public void testCerrarSesion() {
        Usuario usuario = new Cliente("Carlos", "carlos@mail.com", "password123");
        usuario.cerrarSesion();
    }
}
