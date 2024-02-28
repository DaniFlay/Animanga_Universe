package com.example.animanga_universe.clases;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Review implements Serializable, Parcelable {
    Anime anime;
    Manga manga;
    String resumen;
    String review;
    double score;

    public Review(Anime anime, String resumen, String review, double score) {
        this.anime = anime;
        this.resumen = resumen;
        this.review = review;
        this.score = score;
    }

    public Review(Manga manga, String resumen, String review, double score) {
        this.manga = manga;
        this.resumen = resumen;
        this.review = review;
        this.score = score;
    }

    protected Review(Parcel in) {
        anime = in.readParcelable(Anime.class.getClassLoader());
        manga = in.readParcelable(Manga.class.getClassLoader());
        resumen = in.readString();
        review = in.readString();
        score = in.readDouble();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

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

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(anime, flags);
        dest.writeParcelable(manga, flags);
        dest.writeString(resumen);
        dest.writeString(review);
        dest.writeDouble(score);
    }
}
