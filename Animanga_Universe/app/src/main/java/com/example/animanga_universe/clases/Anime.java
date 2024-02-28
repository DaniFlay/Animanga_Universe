package com.example.animanga_universe.clases;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

public class Anime implements Serializable, Parcelable {
    int id;
    String title;
    String type;
    double score;
    double scored_by;
    String status;
    String episodes;
    String aired_from;
    String aired_to;
    String source;
    int favourites;
    String duration;
    String rating;
    String premiered_season;
    String premiered_year;
    String broadcast_day;
    String broadcast_time;
    String genres;
    String themes;
    String demographics;
    String studios;
    String producers;
    String licensors;
    String synopsis;
    String background;
    String picture;
    String trailer;
    String title_j;
    String estado;
    boolean favorito;
    double score_usuario;
    String fecha_inicio;
    String fecha_fin;

    public Anime(int id,String title, String type, double score, double scored_by, String status, String episodes, String aired_from, String aired_to, String source, int favourites, String duration, String rating, String premiered_season, String premiered_year, String broadcast_day, String broadcast_time, String genres, String themes, String demographics, String studios, String producers, String licensors, String synopsis, String background, String picture, String trailer, String title_j, String estado, boolean favorito, double score_usuario, String fecha_inicio, String fecha_fin) {
        this.id = id;
        this.title= title;
        this.type = type;
        this.score = score;
        this.scored_by = scored_by;
        this.status = status;
        this.episodes = episodes;
        this.aired_from = aired_from;
        this.aired_to = aired_to;
        this.source = source;
        this.favourites = favourites;
        this.duration = duration;
        this.rating = rating;
        this.premiered_season = premiered_season;
        this.premiered_year = premiered_year;
        this.broadcast_day = broadcast_day;
        this.broadcast_time = broadcast_time;
        this.genres = genres;
        this.themes = themes;
        this.demographics = demographics;
        this.studios = studios;
        this.producers = producers;
        this.licensors = licensors;
        this.synopsis = synopsis;
        this.background = background;
        this.picture = picture;
        this.trailer = trailer;
        this.title_j = title_j;
        this.estado = estado;
        this.favorito = favorito;
        this.score_usuario = score_usuario;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
    }

    public Anime() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Anime)) return false;
        Anime anime = (Anime) o;
        return getId() == anime.getId() && Objects.equals(getTitle(), anime.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle());
    }

    protected Anime(Parcel in) {
        id = in.readInt();
        title= in.readString();
        type = in.readString();
        score = in.readDouble();
        scored_by = in.readDouble();
        status = in.readString();
        episodes = in.readString();
        aired_from = in.readString();
        aired_to = in.readString();
        source = in.readString();
        favourites = in.readInt();
        duration = in.readString();
        rating = in.readString();
        premiered_season = in.readString();
        premiered_year = in.readString();
        broadcast_day = in.readString();
        broadcast_time = in.readString();
        genres = in.readString();
        themes = in.readString();
        demographics = in.readString();
        studios = in.readString();
        producers = in.readString();
        licensors = in.readString();
        synopsis = in.readString();
        background = in.readString();
        picture = in.readString();
        trailer = in.readString();
        title_j = in.readString();
        estado = in.readString();
        favorito = in.readByte() != 0;
        score_usuario = in.readDouble();
        fecha_inicio = in.readString();
        fecha_fin = in.readString();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getScored_by() {
        return scored_by;
    }

    public void setScored_by(double scored_by) {
        this.scored_by = scored_by;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEpisodes() {
        return episodes;
    }

    public void setEpisodes(String episodes) {
        this.episodes = episodes;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getFavourites() {
        return favourites;
    }

    public void setFavourites(int favourites) {
        this.favourites = favourites;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
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

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getThemes() {
        return themes;
    }

    public void setThemes(String themes) {
        this.themes = themes;
    }

    public String getDemographics() {
        return demographics;
    }

    public void setDemographics(String demographics) {
        this.demographics = demographics;
    }

    public String getStudios() {
        return studios;
    }

    public void setStudios(String studios) {
        this.studios = studios;
    }

    public String getProducers() {
        return producers;
    }

    public void setProducers(String producers) {
        this.producers = producers;
    }

    public String getLicensors() {
        return licensors;
    }

    public void setLicensors(String licensors) {
        this.licensors = licensors;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getTitle_j() {
        return title_j;
    }

    public void setTitle_j(String title_j) {
        this.title_j = title_j;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    public double getScore_usuario() {
        return score_usuario;
    }

    public void setScore_usuario(double score_usuario) {
        this.score_usuario = score_usuario;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(type);
        dest.writeDouble(score);
        dest.writeDouble(scored_by);
        dest.writeString(status);
        dest.writeString(episodes);
        dest.writeString(aired_from);
        dest.writeString(aired_to);
        dest.writeString(source);
        dest.writeInt(favourites);
        dest.writeString(duration);
        dest.writeString(rating);
        dest.writeString(premiered_season);
        dest.writeString(premiered_year);
        dest.writeString(broadcast_day);
        dest.writeString(broadcast_time);
        dest.writeString(genres);
        dest.writeString(themes);
        dest.writeString(demographics);
        dest.writeString(studios);
        dest.writeString(producers);
        dest.writeString(licensors);
        dest.writeString(synopsis);
        dest.writeString(background);
        dest.writeString(picture);
        dest.writeString(trailer);
        dest.writeString(title_j);
        dest.writeString(estado);
        dest.writeByte((byte) (favorito ? 1 : 0));
        dest.writeDouble(score_usuario);
        dest.writeString(fecha_inicio);
        dest.writeString(fecha_fin);
    }
}
