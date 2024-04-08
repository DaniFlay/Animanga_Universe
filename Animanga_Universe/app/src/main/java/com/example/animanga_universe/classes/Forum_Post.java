package com.example.animanga_universe.classes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class Forum_Post implements Serializable, Parcelable {
    User user;
    Anime anime;
    Manga manga;
    String topic;
    String mensaje;
    ArrayList<Comment> comments;

    public Forum_Post(User user, Anime anime, String topic, String mensaje, ArrayList<Comment> comments) {
        this.user = user;
        this.anime = anime;
        this.topic = topic;
        this.mensaje = mensaje;
        this.comments = comments;
    }

    public Forum_Post(User user, Manga manga, String topic, String mensaje, ArrayList<Comment> comments) {
        this.user = user;
        this.manga = manga;
        this.topic = topic;
        this.mensaje = mensaje;
        this.comments = comments;
    }

    public Forum_Post() {
    }

    protected Forum_Post(Parcel in) {
        anime = in.readParcelable(Anime.class.getClassLoader());
        manga = in.readParcelable(Manga.class.getClassLoader());
        topic = in.readString();
        mensaje = in.readString();
        comments = in.createTypedArrayList(Comment.CREATOR);
    }

    public static final Creator<Forum_Post> CREATOR = new Creator<>() {
        @Override
        public Forum_Post createFromParcel(Parcel in) {
            return new Forum_Post(in);
        }

        @Override
        public Forum_Post[] newArray(int size) {
            return new Forum_Post[size];
        }
    };

    public User getUsuario() {
        return user;
    }

    public void setUsuario(User user) {
        this.user = user;
    }

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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public ArrayList<Comment> getComentarios() {
        return comments;
    }

    public void setComentarios(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(anime, flags);
        dest.writeParcelable(manga, flags);
        dest.writeString(topic);
        dest.writeString(mensaje);
        dest.writeTypedList(comments);
    }
}
