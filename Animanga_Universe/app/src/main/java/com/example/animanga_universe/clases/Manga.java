package com.example.animanga_universe.clases;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Manga implements Serializable, Parcelable {
    int id;
    String title;
    String type;
    double score;
    double scored_by;
    String status;
    String volumes;
    String chapters;
    String published_from;
    String published_to;
    int favourites;
    String genres;
    String themes;
    String demographics;
    String authors;
    String synopsis;
    String picture;
    String title_j;
    String estado;
    String fecha_inicio;
    String fecha_fin;
    String score_usuario;
    boolean favorito;

    public Manga(int id, String title, String type, double score, double scored_by, String status, String volumes, String chapters, String published_from, String published_to, int favourites, String genres, String themes, String demographics, String authors, String synopsis, String picture, String title_j, String estado, String fecha_inicio, String fecha_fin, String score_usuario, boolean favorito) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.score = score;
        this.scored_by = scored_by;
        this.status = status;
        this.volumes = volumes;
        this.chapters = chapters;
        this.published_from = published_from;
        this.published_to = published_to;
        this.favourites = favourites;
        this.genres = genres;
        this.themes = themes;
        this.demographics = demographics;
        this.authors = authors;
        this.synopsis = synopsis;
        this.picture = picture;
        this.title_j = title_j;
        this.estado = estado;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.score_usuario = score_usuario;
        this.favorito = favorito;
    }

    public Manga() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Manga)) return false;
        Manga manga = (Manga) o;
        return getId() == manga.getId() && Objects.equals(getTitle(), manga.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle());
    }

    protected Manga(Parcel in) {
        id = in.readInt();
        title = in.readString();
        type = in.readString();
        score = in.readDouble();
        scored_by = in.readDouble();
        status = in.readString();
        volumes = in.readString();
        chapters = in.readString();
        published_from = in.readString();
        published_to = in.readString();
        favourites = in.readInt();
        genres = in.readString();
        themes = in.readString();
        demographics = in.readString();
        authors = in.readString();
        synopsis = in.readString();
        picture = in.readString();
        title_j = in.readString();
        estado = in.readString();
        fecha_inicio = in.readString();
        fecha_fin = in.readString();
        score_usuario = in.readString();
        favorito = in.readByte() != 0;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getVolumes() {
        return volumes;
    }

    public void setVolumes(String volumes) {
        this.volumes = volumes;
    }

    public String getChapters() {
        return chapters;
    }

    public void setChapters(String chapters) {
        this.chapters = chapters;
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

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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

    public String getScore_usuario() {
        return score_usuario;
    }

    public void setScore_usuario(String score_usuario) {
        this.score_usuario = score_usuario;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
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
        dest.writeString(volumes);
        dest.writeString(chapters);
        dest.writeString(published_from);
        dest.writeString(published_to);
        dest.writeInt(favourites);
        dest.writeString(genres);
        dest.writeString(themes);
        dest.writeString(demographics);
        dest.writeString(authors);
        dest.writeString(synopsis);
        dest.writeString(picture);
        dest.writeString(title_j);
        dest.writeString(estado);
        dest.writeString(fecha_inicio);
        dest.writeString(fecha_fin);
        dest.writeString(score_usuario);
        dest.writeByte((byte) (favorito ? 1 : 0));
    }
}
