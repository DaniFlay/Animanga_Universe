package com.example.animanga_universe.clases;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

public class Anime implements Serializable, Parcelable {
    String aired_from;
    String aired_to;
    String broadcast_day;
    String broadcast_time;
    String demographics;
    String duration;
    String episodes;
    int favourites;
    String genres;
    int id;
    String main_picture;
    String premiered_season;
    String premiered_year;
    String producers;
    double score;
    int scored_by;
    String status;
    String studios;
    String title;
    String title_english;
    String title_japanese;
    String trailer_url;
    String type;

    public Anime(String aired_from, String aired_to, String broadcast_day, String broadcast_time, String demographics, String duration, String episodes, int favourites, String genres, int id, String main_picture, String premiered_season, String premiered_year, String producers, double score, int scored_by, String status, String studios, String title, String title_english, String title_japanese, String trailer_url, String type) {
        this.aired_from = aired_from;
        this.aired_to = aired_to;
        this.broadcast_day = broadcast_day;
        this.broadcast_time = broadcast_time;
        this.demographics = demographics;
        this.duration = duration;
        this.episodes = episodes;
        this.favourites = favourites;
        this.genres = genres;
        this.id = id;
        this.main_picture = main_picture;
        this.premiered_season = premiered_season;
        this.premiered_year = premiered_year;
        this.producers = producers;
        this.score = score;
        this.scored_by = scored_by;
        this.status = status;
        this.studios = studios;
        this.title = title;
        this.title_english = title_english;
        this.title_japanese = title_japanese;
        this.trailer_url = trailer_url;
        this.type = type;
    }

    public Anime() {
    }

    protected Anime(Parcel in) {
        aired_from = in.readString();
        aired_to = in.readString();
        broadcast_day = in.readString();
        broadcast_time = in.readString();
        demographics = in.readString();
        duration = in.readString();
        episodes = in.readString();
        favourites = in.readInt();
        genres = in.readString();
        id = in.readInt();
        main_picture = in.readString();
        premiered_season = in.readString();
        premiered_year = in.readString();
        producers = in.readString();
        score = in.readDouble();
        scored_by = in.readInt();
        status = in.readString();
        studios = in.readString();
        title = in.readString();
        title_english = in.readString();
        title_japanese = in.readString();
        trailer_url = in.readString();
        type = in.readString();
    }

    public static final Creator<Anime> CREATOR = new Creator<Anime>() {
        @Override
        public Anime createFromParcel(Parcel in) {
            return new Anime(in);
        }

        @Override
        public Anime[] newArray(int size) {
            return new Anime[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Anime anime = (Anime) o;
        return id == anime.id && Objects.equals(title, anime.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

    public String getAired_from() {
        return aired_from;
    }

    public void setAired_from(String aired_from) {
        this.aired_from = aired_from;
    }

    public String getAired_to() {
        return aired_to;
    }

    public void setAired_to(String aired_to) {
        this.aired_to = aired_to;
    }

    public String getBroadcast_day() {
        return broadcast_day;
    }

    public void setBroadcast_day(String broadcast_day) {
        this.broadcast_day = broadcast_day;
    }

    public String getBroadcast_time() {
        return broadcast_time;
    }

    public void setBroadcast_time(String broadcast_time) {
        this.broadcast_time = broadcast_time;
    }

    public String getDemographics() {
        return demographics;
    }

    public void setDemographics(String demographics) {
        this.demographics = demographics;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEpisodes() {
        return episodes;
    }

    public void setEpisodes(String episodes) {
        this.episodes = episodes;
    }

    public int getFavourites() {
        return favourites;
    }

    public void setFavourites(int favourites) {
        this.favourites = favourites;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain_picture() {
        return main_picture;
    }

    public void setMain_picture(String main_picture) {
        this.main_picture = main_picture;
    }

    public String getPremiered_season() {
        return premiered_season;
    }

    public void setPremiered_season(String premiered_season) {
        this.premiered_season = premiered_season;
    }

    public String getPremiered_year() {
        return premiered_year;
    }

    public void setPremiered_year(String premiered_year) {
        this.premiered_year = premiered_year;
    }

    public String getProducers() {
        return producers;
    }

    public void setProducers(String producers) {
        this.producers = producers;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getScored_by() {
        return scored_by;
    }

    public void setScored_by(int scored_by) {
        this.scored_by = scored_by;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStudios() {
        return studios;
    }

    public void setStudios(String studios) {
        this.studios = studios;
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

    public String getTrailer_url() {
        return trailer_url;
    }

    public void setTrailer_url(String trailer_url) {
        this.trailer_url = trailer_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(aired_from);
        dest.writeString(aired_to);
        dest.writeString(broadcast_day);
        dest.writeString(broadcast_time);
        dest.writeString(demographics);
        dest.writeString(duration);
        dest.writeString(episodes);
        dest.writeInt(favourites);
        dest.writeString(genres);
        dest.writeInt(id);
        dest.writeString(main_picture);
        dest.writeString(premiered_season);
        dest.writeString(premiered_year);
        dest.writeString(producers);
        dest.writeDouble(score);
        dest.writeInt(scored_by);
        dest.writeString(status);
        dest.writeString(studios);
        dest.writeString(title);
        dest.writeString(title_english);
        dest.writeString(title_japanese);
        dest.writeString(trailer_url);
        dest.writeString(type);
    }
}
