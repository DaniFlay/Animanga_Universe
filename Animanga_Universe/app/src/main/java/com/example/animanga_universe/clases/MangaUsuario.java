package com.example.animanga_universe.clases;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

public class MangaUsuario implements Serializable, Parcelable {
    Manga manga;
    String capitulos;
    String estado;
    String nota;

    public MangaUsuario() {

    }

    protected MangaUsuario(Parcel in) {
        manga = in.readParcelable(Manga.class.getClassLoader());
        capitulos = in.readString();
        estado = in.readString();
        nota = in.readString();
    }

    public static final Creator<MangaUsuario> CREATOR = new Creator<MangaUsuario>() {
        @Override
        public MangaUsuario createFromParcel(Parcel in) {
            return new MangaUsuario(in);
        }

        @Override
        public MangaUsuario[] newArray(int size) {
            return new MangaUsuario[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MangaUsuario that = (MangaUsuario) o;
        return Objects.equals(manga, that.manga);
    }

    @Override
    public String toString() {
        return "MangaUsuario{" +
                "manga=" + manga +
                ", capitulos='" + capitulos + '\'' +
                ", estado='" + estado + '\'' +
                ", nota='" + nota + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(manga);
    }

    public Manga getManga() {
        return manga;
    }

    public void setManga(Manga manga) {
        this.manga = manga;
    }

    public String getCapitulos() {
        return capitulos;
    }

    public void setCapitulos(String capitulos) {
        this.capitulos = capitulos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public MangaUsuario(Manga manga, String capitulos, String estado, String nota) {
        this.manga = manga;
        this.capitulos = capitulos;
        this.estado = estado;
        this.nota = nota;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(manga, flags);
        dest.writeString(capitulos);
        dest.writeString(estado);
        dest.writeString(nota);
    }
}
