package com.example.animanga_universe.clases;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Manga implements Serializable, Parcelable {
    String authors;
    String chapters;
    String demographics;
    String favourites;
    String genres;
    String id;
    String main_picture;
    String published_from;
    String published_to;
    String score;
    String scored_by;
    String status;
    String synopsis;
    String title;
    String title_english;
    String title_japanese;
    String type;
    String volumes;

    public Manga(String authors, String chapters, String demographics, String favourites, String genres, String id, String main_picture, String published_from, String published_to, String score, String scored_by, String status, String synopsis, String title, String title_english, String title_japanese, String type, String volumes) {
        this.authors = authors;
        this.chapters = chapters;
        this.demographics = demographics;
        this.favourites = favourites;
        this.genres = genres;
        this.id = id;
        this.main_picture = main_picture;
        this.published_from = published_from;
        this.published_to = published_to;
        this.score = score;
        this.scored_by = scored_by;
        this.status = status;
        this.synopsis = synopsis;
        this.title = title;
        this.title_english = title_english;
        this.title_japanese = title_japanese;
        this.type = type;
        this.volumes = volumes;
    }

    public Manga() {
    }

    @Override
    public String toString() {
        return "Manga{" +
                "authors='" + authors + '\'' +
                ", chapters='" + chapters + '\'' +
                ", demographics='" + demographics + '\'' +
                ", favourites=" + favourites +
                ", genres='" + genres + '\'' +
                ", id=" + id +
                ", main_picture='" + main_picture + '\'' +
                ", published_from='" + published_from + '\'' +
                ", published_to='" + published_to + '\'' +
                ", score=" + score +
                ", scored_by=" + scored_by +
                ", status='" + status + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", title='" + title + '\'' +
                ", title_english='" + title_english + '\'' +
                ", title_japanese='" + title_japanese + '\'' +
                ", type='" + type + '\'' +
                ", volumes='" + volumes + '\'' +
                '}';
    }

    protected Manga(Parcel in) {
        authors = in.readString();
        chapters = in.readString();
        demographics = in.readString();
        favourites = in.readString();
        genres = in.readString();
        id = in.readString();
        main_picture = in.readString();
        published_from = in.readString();
        published_to = in.readString();
        score = in.readString();
        scored_by = in.readString();
        status = in.readString();
        synopsis = in.readString();
        title = in.readString();
        title_english = in.readString();
        title_japanese = in.readString();
        type = in.readString();
        volumes = in.readString();
    }

    public static final Creator<Manga> CREATOR = new Creator<Manga>() {
        @Override
        public Manga createFromParcel(Parcel in) {
            return new Manga(in);
        }

        @Override
        public Manga[] newArray(int size) {
            return new Manga[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manga manga = (Manga) o;
        return Objects.equals(title, manga.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getChapters() {
        return chapters;
    }

    public void setChapters(String chapters) {
        this.chapters = chapters;
    }

    public String getDemographics() {
        return demographics;
    }

    public void setDemographics(String demographics) {
        this.demographics = demographics;
    }

    public String getFavourites() {
        return favourites;
    }

    public void setFavourites(String favourites) {
        this.favourites = favourites;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMain_picture() {
        return main_picture;
    }

    public void setMain_picture(String main_picture) {
        this.main_picture = main_picture;
    }

    public String getPublished_from() {
        return published_from;
    }

    public void setPublished_from(String published_from) {
        this.published_from = published_from;
    }

    public String getPublished_to() {
        return published_to;
    }

    public void setPublished_to(String published_to) {
        this.published_to = published_to;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getScored_by() {
        return scored_by;
    }

    public void setScored_by(String scored_by) {
        this.scored_by = scored_by;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle_english() {
        return title_english;
    }

    public void setTitle_english(String title_english) {
        this.title_english = title_english;
    }

    public String getTitle_japanese() {
        return title_japanese;
    }

    public void setTitle_japanese(String title_japanese) {
        this.title_japanese = title_japanese;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVolumes() {
        return volumes;
    }

    public void setVolumes(String volumes) {
        this.volumes = volumes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(authors);
        dest.writeString(chapters);
        dest.writeString(demographics);
        dest.writeString(favourites);
        dest.writeString(genres);
        dest.writeString(id);
        dest.writeString(main_picture);
        dest.writeString(published_from);
        dest.writeString(published_to);
        dest.writeString(score);
        dest.writeString(scored_by);
        dest.writeString(status);
        dest.writeString(synopsis);
        dest.writeString(title);
        dest.writeString(title_english);
        dest.writeString(title_japanese);
        dest.writeString(type);
        dest.writeString(volumes);
    }
}
