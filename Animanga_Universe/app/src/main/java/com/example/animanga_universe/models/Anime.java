package com.example.animanga_universe.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

/**
 * Objeto anime que se utiliza para Firestore con sus respectivos metodos
 * @author Daniel Seregin Kozlov
 */
public class Anime implements Serializable, Parcelable {
    String airedFrom;
    String airedTo;
    String broadcastDay;
    String demographics;
    String duration;
    String episodes;
    String favorites;
    String genres;
    String malId;
    String mainPicture;
    String premieredSeason;
    String premieredYear;
    String producers;
    String score;
    String scoredBy;
    String status;
    String studios;
    String source;
    String synopsis;
    String title;
    String titleEnglish;
    String titleJapanese;
    String trailerUrl;
    String type;

    public Anime() {
    }

    public Anime(String airedFrom, String airedTo, String broadcastDay, String demographics, String duration, String episodes, String favorites, String genres, String malId, String mainPicture, String premieredSeason, String premieredYear, String producers, String score, String scoredBy, String status, String studios, String source, String synopsis, String title, String titleEnglish, String titleJapanese, String trailerUrl, String type) {
        this.airedFrom = airedFrom;
        this.airedTo = airedTo;
        this.broadcastDay = broadcastDay;
        this.demographics = demographics;
        this.duration = duration;
        this.episodes = episodes;
        this.favorites = favorites;
        this.genres = genres;
        this.malId = malId;
        this.mainPicture = mainPicture;
        this.premieredSeason = premieredSeason;
        this.premieredYear = premieredYear;
        this.producers = producers;
        this.score = score;
        this.scoredBy = scoredBy;
        this.status = status;
        this.studios = studios;
        this.source = source;
        this.synopsis = synopsis;
        this.title = title;
        this.titleEnglish = titleEnglish;
        this.titleJapanese = titleJapanese;
        this.trailerUrl = trailerUrl;
        this.type = type;
    }

    protected Anime(Parcel in) {
        airedFrom = in.readString();
        airedTo = in.readString();
        broadcastDay = in.readString();
        demographics = in.readString();
        duration = in.readString();
        episodes = in.readString();
        favorites = in.readString();
        genres = in.readString();
        malId = in.readString();
        mainPicture = in.readString();
        premieredSeason = in.readString();
        premieredYear = in.readString();
        producers = in.readString();
        score = in.readString();
        scoredBy = in.readString();
        status = in.readString();
        studios = in.readString();
        source = in.readString();
        synopsis = in.readString();
        title = in.readString();
        titleEnglish = in.readString();
        titleJapanese = in.readString();
        trailerUrl = in.readString();
        type = in.readString();
    }

    public static final Creator<Anime> CREATOR = new Creator<>() {
        @Override
        public Anime createFromParcel(Parcel in) {
            return new Anime(in);
        }

        @Override
        public Anime[] newArray(int size) {
            return new Anime[size];
        }
    };

    public String getAiredFrom() {
        return airedFrom;
    }

    public void setAiredFrom(String airedFrom) {
        this.airedFrom = airedFrom;
    }

    public String getAiredTo() {
        return airedTo;
    }

    public void setAiredTo(String airedTo) {
        this.airedTo = airedTo;
    }

    public String getBroadcastDay() {
        return broadcastDay;
    }

    public void setBroadcastDay(String broadcastDay) {
        this.broadcastDay = broadcastDay;
    }

    public void setDemographics(String demographics) {
        this.demographics = demographics;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setEpisodes(String episodes) {
        this.episodes = episodes;
    }

    public String getFavorites() {
        return favorites;
    }

    public void setFavorites(String favorites) {
        this.favorites = favorites;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getMalId() {
        return malId;
    }

    public void setMalId(String malId) {
        this.malId = malId;
    }

    public void setMainPicture(String mainPicture) {
        this.mainPicture = mainPicture;
    }

    public void setPremieredSeason(String premieredSeason) {
        this.premieredSeason = premieredSeason;
    }

    public void setPremieredYear(String premieredYear) {
        this.premieredYear = premieredYear;
    }

    public String getProducers() {
        return producers;
    }

    public void setProducers(String producers) {
        this.producers = producers;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStudios(String studios) {
        this.studios = studios;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleEnglish() {
        return titleEnglish;
    }

    public void setTitleEnglish(String titleEnglish) {
        this.titleEnglish = titleEnglish;
    }

    public void setTitleJapanese(String titleJapanese) {
        this.titleJapanese = titleJapanese;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Anime anime = (Anime) o;
        return Objects.equals(malId, anime.malId) && Objects.equals(title, anime.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(malId, title);
    }

    public String getDemographics() {
        return demographics;
    }

    public String getDuration() {
        return duration;
    }

    public String getEpisodes() {
        return episodes;
    }

    public String getGenres() {
        return genres;
    }

    public String getMainPicture() {
        return mainPicture;
    }

    public String getPremieredSeason() {
        return premieredSeason;
    }

    public String getPremieredYear() {
        return premieredYear;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getScoredBy() {
        return scoredBy;
    }

    public void setScoredBy(String scoredBy) {
        this.scoredBy = scoredBy;
    }

    public String getStatus() {
        return status;
    }

    public String getStudios() {
        return studios;
    }

    public String getSource() {
        return source;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleJapanese() {
        return titleJapanese;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    @NonNull
    @Override
    public String toString() {
        return "Anime{" +
                "airedFrom='" + airedFrom + '\'' +
                ", airedTo='" + airedTo + '\'' +
                ", broadcastDay='" + broadcastDay + '\'' +
                ", demographics='" + demographics + '\'' +
                ", duration='" + duration + '\'' +
                ", episodes='" + episodes + '\'' +
                ", favorites='" + favorites + '\'' +
                ", genres='" + genres + '\'' +
                ", malId='" + malId + '\'' +
                ", mainPicture='" + mainPicture + '\'' +
                ", premieredSeason='" + premieredSeason + '\'' +
                ", premieredYear='" + premieredYear + '\'' +
                ", producers='" + producers + '\'' +
                ", score='" + score + '\'' +
                ", scoredBy='" + scoredBy + '\'' +
                ", status='" + status + '\'' +
                ", studios='" + studios + '\'' +
                ", source='" + source + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", title='" + title + '\'' +
                ", titleEnglish='" + titleEnglish + '\'' +
                ", titleJapanese='" + titleJapanese + '\'' +
                ", trailerUrl='" + trailerUrl + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(airedFrom);
        dest.writeString(airedTo);
        dest.writeString(broadcastDay);
        dest.writeString(demographics);
        dest.writeString(duration);
        dest.writeString(episodes);
        dest.writeString(favorites);
        dest.writeString(genres);
        dest.writeString(malId);
        dest.writeString(mainPicture);
        dest.writeString(premieredSeason);
        dest.writeString(premieredYear);
        dest.writeString(producers);
        dest.writeString(score);
        dest.writeString(scoredBy);
        dest.writeString(status);
        dest.writeString(studios);
        dest.writeString(source);
        dest.writeString(synopsis);
        dest.writeString(title);
        dest.writeString(titleEnglish);
        dest.writeString(titleJapanese);
        dest.writeString(trailerUrl);
        dest.writeString(type);
    }
}
