package com.example.animanga_universe.classes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/** @noinspection deprecation*/
public class Comment implements Serializable, Parcelable {
    User user;
    String date;
    String comentario;
    int likes;
    int dislikes;

    public User getUser() {
        return user;
    }

    public String getComentario() {
        return comentario;
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

    public String getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(user, comment.user) && Objects.equals(date, comment.date) && Objects.equals(comentario, comment.comentario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, date, comentario);
    }

    public Comment(User user, String comentario, int likes, int dislikes) {
        this.user = user;
        this.comentario = comentario;
        this.likes = likes;
        this.dislikes = dislikes;
        this.date= LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    protected Comment(Parcel in) {
        user = in.readParcelable(User.class.getClassLoader());
        comentario = in.readString();
        likes = in.readInt();
        dislikes = in.readInt();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
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
        dest.writeString(date);
    }
}
