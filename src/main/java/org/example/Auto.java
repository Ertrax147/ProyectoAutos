package org.example;

public class Auto {
    private String patente;
    private String marca;
    private String modelo;
    private int ano;
    private String color;
    private double precio;
    private boolean vendido;

    public Auto(String patente, String marca, String modelo, int ano, String color, double precio) {
        this.patente = patente;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.color = color;
        this.precio = precio;
        this.vendido = false;
    }

    public void setVendido(boolean vendido) {
        this.vendido = vendido;
    }

    public boolean isVendido() {
        return vendido;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
