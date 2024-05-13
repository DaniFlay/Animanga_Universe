package com.example.animanga_universe.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

/**
 * Se guardan en los listados de los animes de un usuasrio, contiene el anime con toda la infromación, y los datos
 * del progreso individual del usuario en este anime
 * @author Daniel Seregin Kozlov
 * @noinspection ALL
 */
public class AnimeUser implements Serializable, Parcelable {
    Anime anime;
    String episodios;
    String nota;
    String estado;

    public AnimeUser() {
    }

    public AnimeUser(Anime anime, String episodios, String nota, String estado) {
        this.anime = anime;
        this.episodios = episodios;
        this.nota = nota;
        this.estado = estado;
    }

    protected AnimeUser(Parcel in) {
        //noinspection deprecation
        anime = in.readParcelable(Anime.class.getClassLoader());
        episodios = in.readString();
        nota = in.readString();
        estado = in.readString();
    }

    public static final Creator<AnimeUser> CREATOR = new Creator<>() {
        @Override
        public AnimeUser createFromParcel(Parcel in) {
            return new AnimeUser(in);
        }

        @Override
        public AnimeUser[] newArray(int size) {
            return new AnimeUser[size];
        }
    };

    @NonNull
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
        AnimeUser that = (AnimeUser) o;
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
