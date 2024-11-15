package org.example;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario {
    private List<Auto> favoritos = new ArrayList<>();
    private List<Venta> historialCompras = new ArrayList<>();

    public Cliente(String nombre, String email, String contrasena) {
        super(nombre, email, contrasena);
    }

    public void agregarAFavoritos(Auto auto) {
        favoritos.add(auto);
    }

    public void eliminarDeFavoritos(Auto auto) {
        favoritos.remove(auto);
    }

    public List<Auto> verAutosFavoritos() {
        return favoritos;
    }

    public List<Venta> verHistorialDeCompras() {
        return historialCompras;
    }

    public void comprarAuto(Auto auto) {
        if (!auto.isVendido()) {
            auto.setVendido(true);
            Venta.registrarVenta(this, auto, auto.getPrecio());
            historialCompras.add(new Venta(auto, this, "10-11-2024", auto.getPrecio()));
            System.out.println("Compra realizada con éxito.");
        } else {
            System.out.println("El auto ya ha sido vendido.");
        }
    }
}
