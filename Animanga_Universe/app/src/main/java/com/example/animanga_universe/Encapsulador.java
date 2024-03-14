package com.example.animanga_universe;

import android.graphics.drawable.Drawable;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Encapsulador that = (Encapsulador) o;
        return Objects.equals(titulo, that.titulo) && Objects.equals(info, that.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo);
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

    @Override
    public String toString() {
        return "Encapsulador{" +
                "imagen=" + imagen +
                ", color=" + color +
                ", titulo='" + titulo + '\'' +
                ", info='" + info + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }
}
