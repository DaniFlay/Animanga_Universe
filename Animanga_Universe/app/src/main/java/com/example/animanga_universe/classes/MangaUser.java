package com.example.animanga_universe.classes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

/** @noinspection deprecation*/
public class MangaUser implements Serializable, Parcelable {
    Manga manga;
    String capitulos;
    String estado;
    String nota;

    public MangaUser() {

    }

    protected MangaUser(Parcel in) {
        manga = in.readParcelable(Manga.class.getClassLoader());
        capitulos = in.readString();
        estado = in.readString();
        nota = in.readString();
    }

    public static final Creator<MangaUser> CREATOR = new Creator<>() {
        @Override
        public MangaUser createFromParcel(Parcel in) {
            return new MangaUser(in);
        }

        @Override
        public MangaUser[] newArray(int size) {
            return new MangaUser[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MangaUser that = (MangaUser) o;
        return Objects.equals(manga, that.manga);
    }

    @NonNull
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

    public MangaUser(Manga manga, String capitulos, String estado, String nota) {
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
