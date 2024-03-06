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
    ArrayList<Manga> mangasLeidos;
    ArrayList<Manga> mangasEspera;
    ArrayList<Manga> mangasLeyendo;
    ArrayList<Manga> mangasDejados;
    ArrayList<Manga> mangasPlaneados;
    ArrayList<Anime> animesVistos;
    ArrayList<Anime> animesViendo;
    ArrayList<Anime> animesDejados;
    ArrayList<Anime> animesEspera;
    ArrayList<Anime> animesPlaneados;
    ArrayList<Character> personajes;

    public Usuario(String username, String password, String sexo, String correo, String fecha_de_nacimiento, String fecha_de_registro, String rol) {
        this.username = username;
        this.password = password;
        this.sexo = sexo;
        this.correo = correo;
        this.fecha_de_nacimiento = fecha_de_nacimiento;
        this.fecha_de_registro = fecha_de_registro;
        this.rol = rol;
        this.mangasDejados= new ArrayList<>();
        this.mangasLeidos= new ArrayList<>();
        this.mangasLeyendo= new ArrayList<>();
        this.mangasPlaneados= new ArrayList<>();
        this.mangasEspera= new ArrayList<>();
        this.animesDejados= new ArrayList<>();
        this.animesEspera= new ArrayList<>();
        this.animesPlaneados= new ArrayList<>();
        this.animesViendo= new ArrayList<>();
        this.animesVistos= new ArrayList<>();
        this.personajes= new ArrayList<>();
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
        mangasLeidos = in.createTypedArrayList(Manga.CREATOR);
        mangasEspera = in.createTypedArrayList(Manga.CREATOR);
        mangasLeyendo = in.createTypedArrayList(Manga.CREATOR);
        mangasDejados = in.createTypedArrayList(Manga.CREATOR);
        mangasPlaneados = in.createTypedArrayList(Manga.CREATOR);
        animesVistos = in.createTypedArrayList(Anime.CREATOR);
        animesViendo = in.createTypedArrayList(Anime.CREATOR);
        animesDejados = in.createTypedArrayList(Anime.CREATOR);
        animesEspera = in.createTypedArrayList(Anime.CREATOR);
        animesPlaneados = in.createTypedArrayList(Anime.CREATOR);
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
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(username, usuario.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
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

    public ArrayList<Manga> getMangasLeidos() {
        return mangasLeidos;
    }

    public void setMangasLeidos(ArrayList<Manga> mangasLeidos) {
        this.mangasLeidos = mangasLeidos;
    }

    public ArrayList<Manga> getMangasEspera() {
        return mangasEspera;
    }

    public void setMangasEspera(ArrayList<Manga> mangasEspera) {
        this.mangasEspera = mangasEspera;
    }

    public ArrayList<Manga> getMangasLeyendo() {
        return mangasLeyendo;
    }

    public void setMangasLeyendo(ArrayList<Manga> mangasLeyendo) {
        this.mangasLeyendo = mangasLeyendo;
    }

    public ArrayList<Manga> getMangasDejados() {
        return mangasDejados;
    }

    public void setMangasDejados(ArrayList<Manga> mangasDejados) {
        this.mangasDejados = mangasDejados;
    }

    public ArrayList<Manga> getMangasPlaneados() {
        return mangasPlaneados;
    }

    public void setMangasPlaneados(ArrayList<Manga> mangasPlaneados) {
        this.mangasPlaneados = mangasPlaneados;
    }

    public ArrayList<Anime> getAnimesVistos() {
        return animesVistos;
    }

    public void setAnimesVistos(ArrayList<Anime> animesVistos) {
        this.animesVistos = animesVistos;
    }

    public ArrayList<Anime> getAnimesViendo() {
        return animesViendo;
    }

    public void setAnimesViendo(ArrayList<Anime> animesViendo) {
        this.animesViendo = animesViendo;
    }

    public ArrayList<Anime> getAnimesDejados() {
        return animesDejados;
    }

    public void setAnimesDejados(ArrayList<Anime> animesDejados) {
        this.animesDejados = animesDejados;
    }

    public ArrayList<Anime> getAnimesEspera() {
        return animesEspera;
    }

    public void setAnimesEspera(ArrayList<Anime> animesEspera) {
        this.animesEspera = animesEspera;
    }

    public ArrayList<Anime> getAnimesPlaneados() {
        return animesPlaneados;
    }

    public void setAnimesPlaneados(ArrayList<Anime> animesPlaneados) {
        this.animesPlaneados = animesPlaneados;
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
        dest.writeTypedList(mangasLeidos);
        dest.writeTypedList(mangasEspera);
        dest.writeTypedList(mangasLeyendo);
        dest.writeTypedList(mangasDejados);
        dest.writeTypedList(mangasPlaneados);
        dest.writeTypedList(animesVistos);
        dest.writeTypedList(animesViendo);
        dest.writeTypedList(animesDejados);
        dest.writeTypedList(animesEspera);
        dest.writeTypedList(animesPlaneados);
        dest.writeTypedList(personajes);
    }
}
