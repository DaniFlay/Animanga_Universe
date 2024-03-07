package com.example.animanga_universe;

import android.graphics.drawable.Drawable;

public class Encapsulador {
    Drawable imagen;
    int color;
    String titulo, info, rating;

    public Encapsulador(Drawable imagen, int color, String titulo, String info, String rating) {
        this.imagen = imagen;
        this.color = color;
        this.titulo = titulo;
        this.info = info;
        this.rating = rating;
    }

    public Drawable getImagen() {
        return imagen;
    }

    public void setImagen(Drawable imagen) {
        this.imagen = imagen;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
