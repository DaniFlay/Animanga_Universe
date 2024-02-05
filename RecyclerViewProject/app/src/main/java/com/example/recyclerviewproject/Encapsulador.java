package com.example.recyclerviewproject;

public class Encapsulador {
    private int imagen;
    private String titulo, subtitulo;
    private boolean checkbox;

    public Encapsulador(int imagen, String titulo, String subtitulo, boolean checkbox) {
        this.imagen = imagen;
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.checkbox = checkbox;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }
}
