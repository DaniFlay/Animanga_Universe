package com.example.animanga_universe.clases;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

public class NotaAnime implements Serializable, Parcelable {
    Anime anime;
    String nota;
    Usuario user;

    public NotaAnime() {
    }

    public NotaAnime(Anime anime, String nota, Usuario user) {
        this.anime = anime;
        this.nota = nota;
        this.user = user;
    }

    protected NotaAnime(Parcel in) {
        anime = in.readParcelable(Anime.class.getClassLoader());
        nota = in.readString();
        user = in.readParcelable(Usuario.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(anime, flags);
        dest.writeString(nota);
        dest.writeParcelable(user, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NotaAnime> CREATOR = new Creator<NotaAnime>() {
        @Override
        public NotaAnime createFromParcel(Parcel in) {
            return new NotaAnime(in);
        }

        @Override
        public NotaAnime[] newArray(int size) {
            return new NotaAnime[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotaAnime notaAnime = (NotaAnime) o;
        return Objects.equals(anime, notaAnime.anime) && Objects.equals(user, notaAnime.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(anime, user);
    }

    public Anime getAnime() {
        return anime;
    }

    public void setAnime(Anime anime) {
        this.anime = anime;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
}
