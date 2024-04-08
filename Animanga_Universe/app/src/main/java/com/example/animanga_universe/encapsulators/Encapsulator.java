package com.example.animanga_universe.encapsulators;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.animanga_universe.classes.Anime;
import com.example.animanga_universe.classes.Manga;

import java.io.Serializable;
import java.util.Objects;

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

    public void setProgreso(int progreso) {
        this.progreso = progreso;
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

    public void setAnime(Anime anime) {
        this.anime = anime;
    }

    public Manga getManga() {
        return manga;
    }

    public void setManga(Manga manga) {
        this.manga = manga;
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
