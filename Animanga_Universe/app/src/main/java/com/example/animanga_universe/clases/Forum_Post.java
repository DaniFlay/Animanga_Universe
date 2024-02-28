package com.example.animanga_universe.clases;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class Forum_Post implements Serializable, Parcelable {
    Usuario usuario;
    Anime anime;
    Manga manga;
    String topic;
    String mensaje;
    ArrayList<Comentario> comentarios;

    public Forum_Post(Usuario usuario, Anime anime, String topic, String mensaje, ArrayList<Comentario> comentarios) {
        this.usuario = usuario;
        this.anime = anime;
        this.topic = topic;
        this.mensaje = mensaje;
        this.comentarios = comentarios;
    }

    public Forum_Post(Usuario usuario, Manga manga, String topic, String mensaje, ArrayList<Comentario> comentarios) {
        this.usuario = usuario;
        this.manga = manga;
        this.topic = topic;
        this.mensaje = mensaje;
        this.comentarios = comentarios;
    }

    public Forum_Post() {
    }

    protected Forum_Post(Parcel in) {
        anime = in.readParcelable(Anime.class.getClassLoader());
        manga = in.readParcelable(Manga.class.getClassLoader());
        topic = in.readString();
        mensaje = in.readString();
        comentarios = in.createTypedArrayList(Comentario.CREATOR);
    }

    public static final Creator<Forum_Post> CREATOR = new Creator<Forum_Post>() {
        @Override
        public Forum_Post createFromParcel(Parcel in) {
            return new Forum_Post(in);
        }

        @Override
        public Forum_Post[] newArray(int size) {
            return new Forum_Post[size];
        }
    };

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

    public ArrayList<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(ArrayList<Comentario> comentarios) {
        this.comentarios = comentarios;
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
        dest.writeTypedList(comentarios);
    }
}
