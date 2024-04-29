package com.example.animanga_universe.classes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

/**
 * El fin de este objeto es llevar la cuenta de los me gusta y no me gusta de cada comentario
 * @author Daniel Seregin Kozlov
 * @noinspection ALL
 */
public class CommentScore implements Serializable, Parcelable {
    User user;
    Comment comment;
    boolean like;

    public CommentScore(User user, Comment comment, boolean like) {
        this.user = user;
        this.comment = comment;
        this.like = like;
    }

    public CommentScore() {
    }

    protected CommentScore(Parcel in) {
        user = in.readParcelable(User.class.getClassLoader());
        comment = in.readParcelable(Comment.class.getClassLoader());
        like = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(user, flags);
        dest.writeParcelable(comment, flags);
        dest.writeByte((byte) (like ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CommentScore> CREATOR = new Creator<>() {
        @Override
        public CommentScore createFromParcel(Parcel in) {
            return new CommentScore(in);
        }

        @Override
        public CommentScore[] newArray(int size) {
            return new CommentScore[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentScore that = (CommentScore) o;
        return Objects.equals(user, that.user) && Objects.equals(comment, that.comment);
    }

    @NonNull
    @Override
    public String toString() {
        return "CommentScore{" +
                "user=" + user.getUsername() +
                ", comment=" + comment.getComentario() +
                ", like=" + like +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, comment);
    }

    public boolean isLike() {
        return like;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public void setLike(boolean like) {
        this.like = like;
    }
}
