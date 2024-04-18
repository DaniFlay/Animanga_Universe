package com.example.animanga_universe.classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Objects;

/** @noinspection deprecation */
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

}
