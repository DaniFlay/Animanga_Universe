package com.example.animanga_universe.clases;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

public class AnimeUsuario implements Serializable, Parcelable {
    Anime anime;
    String episodios;
    String nota;
    String estado;

    public AnimeUsuario() {
    }

    public AnimeUsuario(Anime anime, String episodios, String nota, String estado) {
        this.anime = anime;
        this.episodios = episodios;
        this.nota = nota;
        this.estado = estado;
    }

    protected AnimeUsuario(Parcel in) {
        anime = in.readParcelable(Anime.class.getClassLoader());
        episodios = in.readString();
        nota = in.readString();
        estado = in.readString();
    }

    public static final Creator<AnimeUsuario> CREATOR = new Creator<AnimeUsuario>() {
        @Override
        public AnimeUsuario createFromParcel(Parcel in) {
            return new AnimeUsuario(in);
        }

        @Override
        public AnimeUsuario[] newArray(int size) {
            return new AnimeUsuario[size];
        }
    };

    @Override
    public String toString() {
        return "AnimeUsuario{" +
                "anime=" + anime +
                ", episodios='" + episodios + '\'' +
                ", nota='" + nota + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }

    public Anime getAnime() {
        return anime;
    }

    public void setAnime(Anime anime) {
        this.anime = anime;
    }

    public String getEpisodios() {
        return episodios;
    }

    public void setEpisodios(String episodios) {
        this.episodios = episodios;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimeUsuario that = (AnimeUsuario) o;
        return Objects.equals(anime, that.anime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(anime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(anime, flags);
        dest.writeString(episodios);
        dest.writeString(nota);
        dest.writeString(estado);
    }
}
