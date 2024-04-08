package com.example.animanga_universe.clases;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

public class NotaManga implements Serializable, Parcelable {
    String nota;
    Manga manga;
    Usuario user;

    public NotaManga() {
    }

    public NotaManga(String nota, Manga manga, Usuario user) {
        this.nota = nota;
        this.manga = manga;
        this.user = user;
    }

    protected NotaManga(Parcel in) {
        nota = in.readString();
        manga = in.readParcelable(Manga.class.getClassLoader());
        user = in.readParcelable(Usuario.class.getClassLoader());
    }

    public static final Creator<NotaManga> CREATOR = new Creator<NotaManga>() {
        @Override
        public NotaManga createFromParcel(Parcel in) {
            return new NotaManga(in);
        }

        @Override
        public NotaManga[] newArray(int size) {
            return new NotaManga[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotaManga notaManga = (NotaManga) o;
        return Objects.equals(manga, notaManga.manga) && Objects.equals(user, notaManga.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(manga, user);
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Manga getManga() {
        return manga;
    }

    public void setManga(Manga manga) {
        this.manga = manga;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(nota);
        dest.writeParcelable(manga, flags);
        dest.writeParcelable(user, flags);
    }
}
