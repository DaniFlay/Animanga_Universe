package com.example.animanga_universe.encapsulators;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.animanga_universe.classes.Anime;
import com.example.animanga_universe.classes.Manga;

import java.io.Serializable;
import java.util.Objects;

/**
 * Encapsulador para representar los animes o mangas en los RecyclerView
 * @author Daniel Seregin Kozlov
 * @noinspection ALL
 */
public class Encapsulator implements Serializable, Parcelable {
    Anime anime;
    Manga manga;
    Drawable imagen;
    int color;
    String titulo, info, rating;
    int progreso;

    public int getProgreso() {
        return progreso;
    }

    public Encapsulator(Anime anime, Drawable imagen, int color, String titulo, String info, int progreso) {
        this.anime = anime;
        this.imagen = imagen;
        this.color = color;
        this.titulo = titulo;
        this.info = info;
        this.progreso= progreso;
    }

    public Encapsulator(Manga manga, Drawable imagen, int color, String titulo, String info, int progreso) {
        this.manga = manga;
        this.imagen = imagen;
        this.color = color;
        this.titulo = titulo;
        this.info = info;
        this.progreso= progreso;
    }

    public Encapsulator(Anime anime, Drawable imagen, int color, String titulo, String info, String rating) {
        this.anime = anime;
        this.imagen = imagen;
        this.color = color;
        this.titulo = titulo;
        this.info = info;
        this.rating = rating;
    }

    public Encapsulator(Manga manga, Drawable imagen, int color, String titulo, String info, String rating) {
        this.manga = manga;
        this.imagen = imagen;
        this.color = color;
        this.titulo = titulo;
        this.info = info;
        this.rating = rating;
    }

    public Encapsulator() {
    }

    protected Encapsulator(Parcel in) {
        anime = in.readParcelable(Anime.class.getClassLoader());
        manga = in.readParcelable(Manga.class.getClassLoader());
        color = in.readInt();
        titulo = in.readString();
        info = in.readString();
        rating = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(anime, flags);
        dest.writeParcelable(manga, flags);
        dest.writeInt(color);
        dest.writeString(titulo);
        dest.writeString(info);
        dest.writeString(rating);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Encapsulator> CREATOR = new Creator<>() {
        @Override
        public Encapsulator createFromParcel(Parcel in) {
            return new Encapsulator(in);
        }

        @Override
        public Encapsulator[] newArray(int size) {
            return new Encapsulator[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Encapsulator that = (Encapsulator) o;
        return Objects.equals(titulo, that.titulo) && Objects.equals(info, that.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, info);
    }

    @NonNull
    @Override
    public String toString() {
        return "Encapsulador{" +
                "anime=" + anime +
                ", manga=" + manga +
                ", imagen=" + imagen +
                ", color=" + color +
                ", titulo='" + titulo + '\'' +
                ", info='" + info + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }

    public Anime getAnime() {
        return anime;
    }

    public Manga getManga() {
        return manga;
    }

    public Drawable getImagen() {
        return imagen;
    }

    public int getColor() {
        return color;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getInfo() {
        return info;
    }

    public String getRating() {
        return rating;
    }

}
