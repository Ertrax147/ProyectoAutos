package org.example;

import java.util.ArrayList;
import java.util.List;

public class Inventario implements GestionAutos {
    private static Inventario instance;
    private List<Auto> autosDisponibles = new ArrayList<>();

    private Inventario() {}

    public static Inventario getInstance() {
        if (instance == null) {
            instance = new Inventario();
        }
        return instance;
    }

    @Override
    public void agregarAuto(Auto auto) {
        autosDisponibles.add(auto);
        System.out.println("Auto agregado al inventario.");
    }

    @Override
    public void modificarAuto(Auto auto, String atributo, String nuevoValor) {
        switch (atributo.toLowerCase()) {
            case "precio":
                auto.setPrecio(Double.parseDouble(nuevoValor));
                break;
            case "vendido":
                auto.setVendido(Boolean.parseBoolean(nuevoValor));
                break;
            default:
                System.out.println("Atributo no válido.");
        }
    }

    @Override
    public void eliminarAuto(Auto auto) {
        autosDisponibles.remove(auto);
        System.out.println("Auto eliminado del inventario.");
    }

    public List<Auto> buscarAutos() {
        return autosDisponibles;
    }
}
