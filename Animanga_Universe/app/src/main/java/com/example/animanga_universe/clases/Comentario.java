package com.example.animanga_universe.clases;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Comentario implements Serializable, Parcelable {
    Usuario usuario;
    Forum_Post post;
    String comentario;

    public Comentario(Usuario usuario, Forum_Post post, String comentario) {
        this.usuario = usuario;
        this.post = post;
        this.comentario = comentario;
    }

    public Comentario() {
    }

    protected Comentario(Parcel in) {
        comentario = in.readString();
    }

    public static final Creator<Comentario> CREATOR = new Creator<Comentario>() {
        @Override
        public Comentario createFromParcel(Parcel in) {
            return new Comentario(in);
        }

        @Override
        public Comentario[] newArray(int size) {
            return new Comentario[size];
        }
    };

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Forum_Post getPost() {
        return post;
    }

    public void setPost(Forum_Post post) {
        this.post = post;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(comentario);
    }
}
