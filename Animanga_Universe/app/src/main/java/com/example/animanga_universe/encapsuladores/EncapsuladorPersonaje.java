package com.example.animanga_universe.encapsuladores;

public class EncapsuladorPersonaje {
    String nombre, favs;
    int imagen, color;

    public EncapsuladorPersonaje(String nombre, String favs, int imagen, int color) {
        this.nombre = nombre;
        this.favs = favs;
        this.imagen = imagen;
        this.color = color;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFavs() {
        return favs;
    }

    public void setFavs(String favs) {
        this.favs = favs;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
