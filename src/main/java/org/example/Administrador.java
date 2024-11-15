package org.example;

import java.util.List;

public class Administrador extends Usuario implements GestionAutos {
    public Administrador(String nombre, String email, String contrasena) {
        super(nombre, email, contrasena);
    }

    @Override
    public void agregarAuto(Auto auto) {
        Inventario.getInstance().agregarAuto(auto);
    }

    @Override
    public void modificarAuto(Auto auto, String atributo, String nuevoValor) {
        Inventario.getInstance().modificarAuto(auto, atributo, nuevoValor);
    }

    @Override
    public void eliminarAuto(Auto auto) {
        Inventario.getInstance().eliminarAuto(auto);
    }

    public List<Venta> verHistorialVentas() {
        return Venta.getHistorialVentas();
    }
}
