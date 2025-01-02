package org.example;

public abstract class Usuario {
    protected String nombre;
    protected String email;
    protected String contrasena;

    public Usuario(String nombre, String email, String contrasena) {
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
    }

    public void iniciarSesion(String email, String contrasena) {
        if (this.email.equals(email) && this.contrasena.equals(contrasena)) {
            System.out.println("Inicio de sesión exitoso.");
        } else {
            System.out.println("Credenciales incorrectas.");
        }
    }
    public String getNombre() {
        return nombre;  // Accede al nombre del usuario (Cliente)
    }
    public void cerrarSesion() {
        System.out.println("Sesión cerrada.");
    }
}
