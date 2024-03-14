package com.example.animanga_universe.clases;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Manga implements Serializable, Parcelable {
    String authors;
    Long chapters;
    String demographics;
    Long favorites;
    String genres;
    String malId;
    String mainPicture;
    String publishedFrom;
    String publishedTo;
    Double score;
    String scoredBy;
    String status;
    String synopsis;
    String title;
    String titleEnglish;
    String titleJapanese;
    String type;
    Long volumes;

    public Manga() {
    }

    @Override
    public String toString() {
        return "Manga{" +
                "authors='" + authors + '\'' +
                ", chapters=" + chapters +
                ", demographics='" + demographics + '\'' +
                ", favorites=" + favorites +
                ", genres='" + genres + '\'' +
                ", malId='" + malId + '\'' +
                ", mainPicture='" + mainPicture + '\'' +
                ", publishedFrom='" + publishedFrom + '\'' +
                ", publishedTo='" + publishedTo + '\'' +
                ", score=" + score +
                ", scoredBy='" + scoredBy + '\'' +
                ", status='" + status + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", title='" + title + '\'' +
                ", titleEnglish='" + titleEnglish + '\'' +
                ", titleJapanese='" + titleJapanese + '\'' +
                ", type='" + type + '\'' +
                ", volumes=" + volumes +
                '}';
    }

    protected Manga(Parcel in) {
        authors = in.readString();
        if (in.readByte() == 0) {
            chapters = null;
        } else {
            chapters = in.readLong();
        }
        demographics = in.readString();
        if (in.readByte() == 0) {
            favorites = null;
        } else {
            favorites = in.readLong();
        }
        genres = in.readString();
        malId = in.readString();
        mainPicture = in.readString();
        publishedFrom = in.readString();
        publishedTo = in.readString();
        if (in.readByte() == 0) {
            score = null;
        } else {
            score = in.readDouble();
        }
        scoredBy = in.readString();
        status = in.readString();
        synopsis = in.readString();
        title = in.readString();
        titleEnglish = in.readString();
        titleJapanese = in.readString();
        type = in.readString();
        if (in.readByte() == 0) {
            volumes = null;
        } else {
            volumes = in.readLong();
        }
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
        return Objects.equals(malId, manga.malId) && Objects.equals(title, manga.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(malId, title);
    }

    public Manga(String authors, Long chapters, String demographics, Long favorites, String genres, String malId, String mainPicture, String publishedFrom, String publishedTo, Double score, String scoredBy, String status, String synopsis, String title, String titleEnglish, String titleJapanese, String type, Long volumes) {
        this.authors = authors;
        this.chapters = chapters;
        this.demographics = demographics;
        this.favorites = favorites;
        this.genres = genres;
        this.malId = malId;
        this.mainPicture = mainPicture;
        this.publishedFrom = publishedFrom;
        this.publishedTo = publishedTo;
        this.score = score;
        this.scoredBy = scoredBy;
        this.status = status;
        this.synopsis = synopsis;
        this.title = title;
        this.titleEnglish = titleEnglish;
        this.titleJapanese = titleJapanese;
        this.type = type;
        this.volumes = volumes;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public Long getChapters() {
        return chapters;
    }

    public void setChapters(Long chapters) {
        this.chapters = chapters;
    }

    public String getDemographics() {
        return demographics;
    }

    public void setDemographics(String demographics) {
        this.demographics = demographics;
    }

    public Long getFavorites() {
        return favorites;
    }

    public void setFavorites(Long favorites) {
        this.favorites = favorites;
    }

    public String getGenres() {
        return genres;
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

    public String getMainPicture() {
        return mainPicture;
    }

    public void setMainPicture(String mainPicture) {
        this.mainPicture = mainPicture;
    }

    public String getPublishedFrom() {
        return publishedFrom;
    }

    public void setPublishedFrom(String publishedFrom) {
        this.publishedFrom = publishedFrom;
    }

    public String getPublishedTo() {
        return publishedTo;
    }

    public void setPublishedTo(String publishedTo) {
        this.publishedTo = publishedTo;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
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

    public String getTitleEnglish() {
        return titleEnglish;
    }

    public void setTitleEnglish(String titleEnglish) {
        this.titleEnglish = titleEnglish;
    }

    public String getTitleJapanese() {
        return titleJapanese;
    }

    public void setTitleJapanese(String titleJapanese) {
        this.titleJapanese = titleJapanese;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getVolumes() {
        return volumes;
    }

    public void setVolumes(Long volumes) {
        this.volumes = volumes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(authors);
        if (chapters == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(chapters);
        }
        dest.writeString(demographics);
        if (favorites == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(favorites);
        }
        dest.writeString(genres);
        dest.writeString(malId);
        dest.writeString(mainPicture);
        dest.writeString(publishedFrom);
        dest.writeString(publishedTo);
        if (score == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(score);
        }
        dest.writeString(scoredBy);
        dest.writeString(status);
        dest.writeString(synopsis);
        dest.writeString(title);
        dest.writeString(titleEnglish);
        dest.writeString(titleJapanese);
        dest.writeString(type);
        if (volumes == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(volumes);
        }
    }
}
