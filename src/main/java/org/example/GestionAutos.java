package org.example;

public interface GestionAutos {
    void agregarAuto(Auto auto);
    void modificarAuto(Auto auto, String atributo, String nuevoValor);
    void eliminarAuto(Auto auto);
}
