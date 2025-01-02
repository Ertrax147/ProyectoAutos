package org.example;

public class Auto {
    private String patente;
    private String marca;
    private String modelo;
    private int ano;
    private String color;
    private double precio;
    private boolean vendido;

    // Constructor vacío necesario para Firebase
    public Auto() {
    }

    // Constructor con parámetros
    public Auto(String patente, String marca, String modelo, int ano, String color, double precio) {
        this.patente = patente;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.color = color;
        this.precio = precio;
        this.vendido = false; // Inicialmente el auto no está vendido
    }

    // Getters y Setters
    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isVendido() {
        return vendido;
    }

    public void setVendido(boolean vendido) {
        this.vendido = vendido;
    }
}