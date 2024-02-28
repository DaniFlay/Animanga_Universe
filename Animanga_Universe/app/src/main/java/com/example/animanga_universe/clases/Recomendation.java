package com.example.animanga_universe.clases;

public class Recomendation {
    Anime anime_prinicpal;
    Anime anime_recomendacion;
    Usuario usuario;
    String argumento;

    public Recomendation(Anime anime_prinicpal, Anime anime_recomendacion, Usuario usuario, String argumento) {
        this.anime_prinicpal = anime_prinicpal;
        this.anime_recomendacion = anime_recomendacion;
        this.usuario = usuario;
        this.argumento = argumento;
    }

    public Recomendation() {
    }

    public Anime getAnime_prinicpal() {
        return anime_prinicpal;
    }

    public void setAnime_prinicpal(Anime anime_prinicpal) {
        this.anime_prinicpal = anime_prinicpal;
    }

    public Anime getAnime_recomendacion() {
        return anime_recomendacion;
    }

    public void setAnime_recomendacion(Anime anime_recomendacion) {
        this.anime_recomendacion = anime_recomendacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getArgumento() {
        return argumento;
    }

    public void setArgumento(String argumento) {
        this.argumento = argumento;
    }
}
