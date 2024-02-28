package com.example.animanga_universe.clases;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Usuario implements Serializable, Parcelable {
    String username;
    String password;
    String sexo;
    String correo;
    String fecha_de_nacimiento;
    String fecha_de_registro;
    String rol;
    ArrayList<Manga> mangas;
    ArrayList<Anime> animes;
    ArrayList<Character> personajes;

    public Usuario(String username, String password, String sexo, String correo, String fecha_de_nacimiento, String fecha_de_registro, String rol) {
        this.username = username;
        this.password = password;
        this.sexo = sexo;
        this.correo = correo;
        this.fecha_de_nacimiento = fecha_de_nacimiento;
        this.fecha_de_registro = fecha_de_registro;
        this.rol = rol;
        this.mangas= new ArrayList<Manga>();
        this.animes= new ArrayList<Anime>();
        this.personajes= new ArrayList<Character>();
    }

    public Usuario() {
    }

    protected Usuario(Parcel in) {
        username = in.readString();
        password = in.readString();
        sexo = in.readString();
        correo = in.readString();
        fecha_de_nacimiento = in.readString();
        fecha_de_registro = in.readString();
        rol = in.readString();
        mangas = in.createTypedArrayList(Manga.CREATOR);
        animes = in.createTypedArrayList(Anime.CREATOR);
        personajes = in.createTypedArrayList(Character.CREATOR);
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(getUsername(), usuario.getUsername()) && Objects.equals(getCorreo(), usuario.getCorreo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getCorreo());
    }

    public String getUsername() {
        return username;
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

    public void setFecha_de_registro(String fecha_de_registro) {
        this.fecha_de_registro = fecha_de_registro;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public ArrayList<Manga> getMangas() {
        return mangas;
    }

    public void setMangas(ArrayList<Manga> mangas) {
        this.mangas = mangas;
    }

    public ArrayList<Anime> getAnimes() {
        return animes;
    }

    public void setAnimes(ArrayList<Anime> animes) {
        this.animes = animes;
    }

    public ArrayList<Character> getPersonajes() {
        return personajes;
    }

    public void setPersonajes(ArrayList<Character> personajes) {
        this.personajes = personajes;
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
        dest.writeTypedList(personajes);
    }
}
