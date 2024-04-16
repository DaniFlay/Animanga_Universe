package com.example.animanga_universe.classes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class Forum_Post implements Serializable, Parcelable {
    User user;
    Anime anime;
    Manga manga;
    String topic;
    String message;
    ArrayList<Comment> comments;
    String date;

    public Forum_Post(User user, Anime anime, String topic, String message) {
        this.user = user;
        this.anime = anime;
        this.topic = topic;
        this.message = message;
        this.comments = new ArrayList<>();
        this.date= LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public Forum_Post(User user, Manga manga, String topic, String message ) {
        this.user = user;
        this.manga = manga;
        this.topic = topic;
        this.message = message;
        this.comments = new ArrayList<>();
        this.date= LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public Forum_Post() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Forum_Post forumPost = (Forum_Post) o;
        return Objects.equals(user, forumPost.user) && Objects.equals(anime, forumPost.anime) && Objects.equals(manga, forumPost.manga) && Objects.equals(topic, forumPost.topic) && Objects.equals(message, forumPost.message) && Objects.equals(date, forumPost.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, anime, manga, topic, message, date);
    }

    protected Forum_Post(Parcel in) {
        user = in.readParcelable(User.class.getClassLoader());
        anime = in.readParcelable(Anime.class.getClassLoader());
        manga = in.readParcelable(Manga.class.getClassLoader());
        topic = in.readString();
        message = in.readString();
        comments = in.createTypedArrayList(Comment.CREATOR);
        date = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(user, flags);
        dest.writeParcelable(anime, flags);
        dest.writeParcelable(manga, flags);
        dest.writeString(topic);
        dest.writeString(message);
        dest.writeTypedList(comments);
        dest.writeString(date);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
