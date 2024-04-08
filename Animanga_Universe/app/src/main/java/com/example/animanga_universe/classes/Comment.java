package com.example.animanga_universe.classes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Comment implements Serializable, Parcelable {
    User user;
    Forum_Post post;
    String comentario;

    public Comment(User user, Forum_Post post, String comentario) {
        this.user = user;
        this.post = post;
        this.comentario = comentario;
    }

    public Comment() {
    }

    protected Comment(Parcel in) {
        comentario = in.readString();
    }

    public static final Creator<Comment> CREATOR = new Creator<>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    public User getUsuario() {
        return user;
    }

    public void setUsuario(User user) {
        this.user = user;
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
