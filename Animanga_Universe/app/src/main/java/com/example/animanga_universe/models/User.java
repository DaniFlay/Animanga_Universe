package com.example.animanga_universe.models;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

/**
 * El objeto usuario con todos sus datos y listas
 * @author Daniel Seregin Kozlov
 */
public class User implements Serializable, Parcelable {
    String username;
    String password;
    String sexo;
    String correo;
    String fecha_de_nacimiento;
    String fecha_de_registro;
    String rol;
    ArrayList<MangaUser> mangas;
    ArrayList<AnimeUser> animes;

    public User(String username, String password, String sexo, String correo, String fecha_de_nacimiento) {
        this.username = username;
        this.password = password;
        this.sexo = sexo;
        this.correo = correo;
        this.fecha_de_nacimiento = fecha_de_nacimiento;
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.fecha_de_registro = localDate.format(formatter);
        this.rol = "Usuario";
        this.mangas = new ArrayList<>();
        this.animes = new ArrayList<>();

    }

    public User() {
    }



    protected User(Parcel in) {
        username = in.readString();
        password = in.readString();
        sexo = in.readString();
        correo = in.readString();
        fecha_de_nacimiento = in.readString();
        fecha_de_registro = in.readString();
        rol = in.readString();
        mangas = in.createTypedArrayList(MangaUser.CREATOR);
        animes = in.createTypedArrayList(AnimeUser.CREATOR);
    }

    public static final Creator<User> CREATOR = new Creator<>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @NonNull
    @Override
    public String toString() {
        return "Usuario{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", sexo='" + sexo + '\'' +
                ", correo='" + correo + '\'' +
                ", fecha_de_nacimiento='" + fecha_de_nacimiento + '\'' +
                ", fecha_de_registro='" + fecha_de_registro + '\'' +
                ", rol='" + rol + '\'' +
                ", mangas=" + mangas +
                ", animes=" + animes +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    public String getUsername() {
        return username;
    }

    public void setFecha_de_registro(String fecha_de_registro) {
        this.fecha_de_registro = fecha_de_registro;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFecha_de_nacimiento() {
        return fecha_de_nacimiento;
    }

    public void setFecha_de_nacimiento(String fecha_de_nacimiento) {
        this.fecha_de_nacimiento = fecha_de_nacimiento;
    }

    public String getFecha_de_registro() {
        return fecha_de_registro;
    }


    public ArrayList<MangaUser> getMangas() {
        return mangas;
    }

    public void setMangas(ArrayList<MangaUser> mangas) {
        this.mangas = mangas;
    }

    public ArrayList<AnimeUser> getAnimes() {
        return animes;
    }

    public void setAnimes(ArrayList<AnimeUser> animes) {
        this.animes = animes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(sexo);
        dest.writeString(correo);
        dest.writeString(fecha_de_nacimiento);
        dest.writeString(fecha_de_registro);
        dest.writeString(rol);
        dest.writeTypedList(mangas);
        dest.writeTypedList(animes);
    }
}
