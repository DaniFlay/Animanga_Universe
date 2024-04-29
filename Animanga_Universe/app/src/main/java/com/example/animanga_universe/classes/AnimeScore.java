package com.example.animanga_universe.classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Objects;

/**
 * Objeto para Realtime Database, se ha creado para saber si se ha valorado un anime, y el usuario que lo ha valorado
 * El fin es poder llevar la cuenta de la valoración media de los animes
 * @noinspection ALL
 */
public class AnimeScore implements Serializable, Parcelable {
    Anime anime;
    String nota;
    User user;

    public AnimeScore() {
    }

    public AnimeScore(Anime anime, String nota, User user) {
        this.anime = anime;
        this.nota = nota;
        this.user = user;
    }

    /** @noinspection deprecation, deprecation */
    protected AnimeScore(Parcel in) {
        anime = in.readParcelable(Anime.class.getClassLoader());
        nota = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
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

    public static final Creator<AnimeScore> CREATOR = new Creator<>() {
        @Override
        public AnimeScore createFromParcel(Parcel in) {
            return new AnimeScore(in);
        }

        @Override
        public AnimeScore[] newArray(int size) {
            return new AnimeScore[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimeScore animeScore = (AnimeScore) o;
        return Objects.equals(anime, animeScore.anime) && Objects.equals(user, animeScore.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(anime, user);
    }

    public Anime getAnime() {
        return anime;
    }

    public String getNota() {
        return nota;
    }

    public void setAnime(Anime anime) {
        this.anime = anime;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
