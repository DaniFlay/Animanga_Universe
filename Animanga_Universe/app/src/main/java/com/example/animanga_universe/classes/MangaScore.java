package com.example.animanga_universe.classes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

/**
 * Este objeto se utiliza para llevar la cuenta de las notas de los mangas que hace cada usuario
 * @author Daniel Seregin Kozlov
 * @noinspection ALL
 */
public class MangaScore implements Serializable, Parcelable {
    String nota;
    Manga manga;
    User user;

    public MangaScore() {
    }

    public MangaScore(String nota, Manga manga, User user) {
        this.nota = nota;
        this.manga = manga;
        this.user = user;
    }

    protected MangaScore(Parcel in) {
        nota = in.readString();
        manga = in.readParcelable(Manga.class.getClassLoader());
        user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<MangaScore> CREATOR = new Creator<>() {
        @Override
        public MangaScore createFromParcel(Parcel in) {
            return new MangaScore(in);
        }

        @Override
        public MangaScore[] newArray(int size) {
            return new MangaScore[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MangaScore mangaScore = (MangaScore) o;
        return Objects.equals(manga, mangaScore.manga) && Objects.equals(user, mangaScore.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(manga, user);
    }

    public String getNota() {
        return nota;
    }

    public Manga getManga() {
        return manga;
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

    public void setNota(String nota) {
        this.nota = nota;
    }

    public void setManga(Manga manga) {
        this.manga = manga;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
