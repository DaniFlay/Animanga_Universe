package com.example.animanga_universe.classes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Comment implements Serializable, Parcelable {
    User user;
    String comentario;
    int likes;
    int dislikes;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public Comment() {
    }

    public Comment(User user, String comentario, int likes, int dislikes) {
        this.user = user;
        this.comentario = comentario;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    protected Comment(Parcel in) {
        user = in.readParcelable(User.class.getClassLoader());
        comentario = in.readString();
        likes = in.readInt();
        dislikes = in.readInt();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(user, flags);
        dest.writeString(comentario);
        dest.writeInt(likes);
        dest.writeInt(dislikes);
    }
}