package com.example.animanga_universe.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.animanga_universe.R;
import com.example.animanga_universe.clases.AnimeUsuario;
import com.example.animanga_universe.clases.MangaUsuario;
import com.example.animanga_universe.clases.Usuario;
import com.example.animanga_universe.encapsuladores.Encapsulador;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EditarItem extends AppCompatActivity implements View.OnClickListener, ChipGroup.OnCheckedStateChangeListener {
    Usuario usuario;
    Encapsulador e;
    String busqueda, estado, estadoAnime;
    ChipGroup cg;
    Chip completado, enProceso, enEspera, dejado, enLista, porDefecto;
    RatingBar ratingBar;
    ProgressBar progressBar;
    TextInputLayout progreso;
    AppCompatImageButton botonPlus;
    Button botonGuardar;
    AnimeUsuario animeUsuario;
    MangaUsuario mangaUsuario;
    DatabaseReference ref;
    TextView totales;
    ArrayList<AnimeUsuario> animes;
    ArrayList<MangaUsuario> mangas;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_item);
        animes = new ArrayList<>();
        mangas = new ArrayList<>();
        estadoAnime = "";
        usuario = getIntent().getParcelableExtra("usuario");
        e = getIntent().getParcelableExtra("encapsulador");
        Log.d("encapsulador", e.toString());
        busqueda = getIntent().getStringExtra("busqueda");
        ref = FirebaseDatabase.getInstance().getReference("Usuario");
        totales = findViewById(R.id.episodiosTotales);
        estado = "";

        progreso = findViewById(R.id.progreso);
        animeUsuario = new AnimeUsuario();
        mangaUsuario = new MangaUsuario();
        botonPlus = findViewById(R.id.botonPlus);
        botonPlus.setOnClickListener(this);
        botonGuardar = findViewById(R.id.botonGuardar);
        botonGuardar.setOnClickListener(this);
        ratingBar = findViewById(R.id.ratingBar);
        progressBar = findViewById(R.id.progressBar);
        cg = findViewById(R.id.chipGroup);
        cg.setOnCheckedStateChangeListener(this);
        completado = findViewById(R.id.completado);
        enProceso = findViewById(R.id.enProceso);
        enEspera = findViewById(R.id.enEspera);
        dejado = findViewById(R.id.dejado);
        enLista = findViewById(R.id.planeado);
        porDefecto = findViewById(R.id.sinAsignar);
        assert progreso.getEditText() != null;
        if (e.getAnime()!=null && usuario.getAnimes() != null) {
            for (AnimeUsuario a : usuario.getAnimes()) {
                if (a.getAnime().equals(e.getAnime())) {
                    estadoAnime = a.getEstado();
                    progreso.getEditText().setText(a.getEpisodios());
                }
            }
        } else if (e.getManga()!=null && usuario.getMangas() != null) {
            for (MangaUsuario m : usuario.getMangas()) {
                if (m.getManga().equals(e.getManga())) {
                    estadoAnime = m.getEstado();
                    progreso.getEditText().setText(m.getCapitulos());
                }
            }
        }
        if(usuario.getAnimes()!=null){
            if (e.getAnime()!=null) {
                if(estadoAnime.equals(getString(R.string.enespera))){
                    enEspera.setChecked(true);
                    for(AnimeUsuario a: usuario.getAnimes()){
                        if(e.getTitulo().equals(a.getAnime().getTitle())){
                            ratingBar.setRating(Float.parseFloat(a.getNota()));
                            if(e.getInfo().split(" ")[0].equals("?")){
                                progressBar.setMax(10);
                                progressBar.setProgress(5);
                            }else{
                                progressBar.setMax(Integer.parseInt(e.getInfo().split(" ")[0]));
                                progressBar.setProgress(Integer.parseInt(a.getEpisodios()));
                                progressBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.espera)));

                            }
                        }
                    }
                } else if (estadoAnime.equals(getString(R.string.viendo))) {
                    enProceso.setChecked(true);
                    for(AnimeUsuario a: usuario.getAnimes()){
                        if(e.getTitulo().equals(a.getAnime().getTitle())){
                            ratingBar.setRating(Float.parseFloat(a.getNota()));
                            if(e.getInfo().split(" ")[0].equals("?")){
                                progressBar.setMax(10);
                                progressBar.setProgress(5);
                            }else{
                                progressBar.setMax(Integer.parseInt(e.getInfo().split(" ")[0]));
                                progressBar.setProgress(Integer.parseInt(a.getEpisodios()));
                                progressBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.enProceso)));
                            }
                        }
                    }
                } else if (estadoAnime.equals(getString(R.string.completado))) {
                    completado.setChecked(true);
                    for(AnimeUsuario a: usuario.getAnimes()){
                        if(e.getTitulo().equals(a.getAnime().getTitle())){
                            ratingBar.setRating(Float.parseFloat(a.getNota()));
                            if(e.getInfo().split(" ")[0].equals("?")){
                                progressBar.setMax(10);
                                progressBar.setProgress(5);
                            }else{
                                progressBar.setMax(Integer.parseInt(e.getInfo().split(" ")[0]));
                                progressBar.setProgress(Integer.parseInt(a.getEpisodios()));
                                progressBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.completado)));
                            }
                        }
                    }

                }else if(estadoAnime.equals(getString(R.string.planeado))){
                    enLista.setChecked(true);
                    for(AnimeUsuario a: usuario.getAnimes()){
                        if(e.getTitulo().equals(a.getAnime().getTitle())){
                            ratingBar.setRating(Float.parseFloat(a.getNota()));
                            if(e.getInfo().split(" ")[0].equals("?")){
                                progressBar.setMax(10);
                                progressBar.setProgress(5);
                            }else{
                                progressBar.setMax(Integer.parseInt(e.getInfo().split(" ")[0]));
                                progressBar.setProgress(Integer.parseInt(a.getEpisodios()));
                                progressBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.enlista)));
                            }
                        }
                    }
                } else if (estadoAnime.equals(getString(R.string.dejado))) {
                    dejado.setChecked(true);
                    for(AnimeUsuario a: usuario.getAnimes()){
                        if(e.getTitulo().equals(a.getAnime().getTitle())){
                            ratingBar.setRating(Float.parseFloat(a.getNota()));
                            if(e.getInfo().split(" ")[0].equals("?")){
                                progressBar.setMax(10);
                                progressBar.setProgress(5);
                            }else{
                                progressBar.setMax(Integer.parseInt(e.getInfo().split(" ")[0]));
                                progressBar.setProgress(Integer.parseInt(a.getEpisodios()));
                                progressBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.dejado)));
                            }
                        }
                    }
                }
                animeUsuario.setAnime(e.getAnime());

            } else if(e.getManga()!=null) {
                enProceso.setText("Leyendo");
                enLista.setText("Planeado para leer");
                if(estadoAnime.equals(getString(R.string.enespera))){
                    enEspera.setChecked(true);
                    for(MangaUsuario m: usuario.getMangas()){
                        if(e.getTitulo().equals(m.getManga().getTitle())){
                            ratingBar.setRating(Float.parseFloat(String.valueOf(m.getNota())));
                            if(e.getInfo().split(" ")[0].equals("?")){
                                progressBar.setMax(10);
                                progressBar.setProgress(5);
                            }else{
                                progressBar.setMax(Integer.parseInt(e.getInfo().split(" ")[0]));
                                progressBar.setProgress(Integer.parseInt(String.valueOf(m.getCapitulos())));
                                progressBar.setBackgroundColor(getResources().getColor(R.color.espera));
                            }
                        }
                    }
                } else if (estadoAnime.equals(getString(R.string.leyendo))) {
                    enProceso.setChecked(true);
                    for(MangaUsuario m: usuario.getMangas()){
                        if(e.getTitulo().equals(m.getManga().getTitle())){
                            ratingBar.setRating(Float.parseFloat(String.valueOf(m.getNota())));
                            if(e.getInfo().split(" ")[0].equals("?")){
                                progressBar.setMax(10);
                                progressBar.setProgress(5);
                            }else{
                                progressBar.setMax(Integer.parseInt(e.getInfo().split(" ")[0]));
                                progressBar.setProgress(Integer.parseInt(String.valueOf(m.getCapitulos())));
                                progressBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.enProceso)));
                            }
                        }
                    }
                } else if (estadoAnime.equals(getString(R.string.completado))) {
                    completado.setChecked(true);
                    for(MangaUsuario m: usuario.getMangas()){
                        if(e.getTitulo().equals(m.getManga().getTitle())){
                            ratingBar.setRating(Float.parseFloat(String.valueOf(m.getNota())));
                            if(e.getInfo().split(" ")[0].equals("?")){
                                progressBar.setMax(10);
                                progressBar.setProgress(5);
                            }else{
                                progressBar.setMax(Integer.parseInt(e.getInfo().split(" ")[0]));
                                progressBar.setProgress(Integer.parseInt(String.valueOf(m.getCapitulos())));
                                progressBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.completado)));
                            }
                        }
                    }

                }else if(estadoAnime.equals(getString(R.string.planeado))){
                    enLista.setChecked(true);
                    for(MangaUsuario m: usuario.getMangas()){
                        if(e.getTitulo().equals(m.getManga().getTitle())){
                            ratingBar.setRating(Float.parseFloat(String.valueOf(m.getNota())));
                            if(e.getInfo().split(" ")[0].equals("?")){
                                progressBar.setMax(10);
                                progressBar.setProgress(5);
                            }else{
                                progressBar.setMax(Integer.parseInt(e.getInfo().split(" ")[0]));
                                progressBar.setProgress(Integer.parseInt(String.valueOf(m.getCapitulos())));
                                progressBar.setBackgroundColor(getResources().getColor(R.color.enlista));
                            }
                        }
                    }
                } else if (estadoAnime.equals(getString(R.string.dejado))) {
                    dejado.setChecked(true);
                    for(MangaUsuario m: usuario.getMangas()){
                        if(e.getTitulo().equals(m.getManga().getTitle())){
                            ratingBar.setRating(Float.parseFloat(String.valueOf(m.getNota())));
                            if(e.getInfo().split(" ")[0].equals("?")){
                                progressBar.setMax(10);
                                progressBar.setProgress(5);
                            }else{
                                progressBar.setMax(Integer.parseInt(e.getInfo().split(" ")[0]));
                                progressBar.setProgress(Integer.parseInt(String.valueOf(m.getCapitulos())));
                                progressBar.setBackgroundColor(getResources().getColor(R.color.dejado));
                            }
                        }
                    }
                }
                mangaUsuario.setManga(e.getManga());
            }
        }



        if (e.getInfo().split(" ")[0].equals("?")) {
            progressBar.setMax(10);
            progressBar.setProgress(5);
        } else {
            progressBar.setMax(Integer.parseInt(e.getInfo().split(" ")[0]));
            totales.setText("/" + Integer.parseInt(e.getInfo().split(" ")[0]));
        }

        progreso.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!String.valueOf(s).equals("")) {
                    progressBar.setProgress(Integer.parseInt(String.valueOf(s)));
                    if (e.getAnime() != null && !e.getAnime().getEpisodes().equals("") && Integer.parseInt(String.valueOf(s)) == Integer.parseInt(e.getAnime().getEpisodes()) || e.getManga() != null && e.getManga().getChapters() != null && Integer.parseInt(String.valueOf(s)) == e.getManga().getChapters()) {
                        progressBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.completado)));
                        completado.setChecked(true);
                    }
                } else {
                    progressBar.setProgress(0);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== botonPlus.getId()){
            progressBar.setProgress(progressBar.getProgress()+1);
            progreso.getEditText().setText(String.valueOf(Integer.parseInt(progreso.getEditText().getText().toString())+1));
        } else if (v.getId()== botonGuardar.getId()) {
            if(cg.getCheckedChipId()!=porDefecto.getId()){
                if(busqueda.equals("Anime")){
                    if(cg.getCheckedChipId()==completado.getId()){
                        estado= getString(R.string.completado);
                    }else if(cg.getCheckedChipId()==enEspera.getId()){
                        estado= getString(R.string.enespera);
                    } else if (cg.getCheckedChipId()== enLista.getId()) {
                        estado= getString(R.string.planeado);
                    } else if (cg.getCheckedChipId()== enProceso.getId()) {
                        estado= getString(R.string.viendo);
                    } else if (cg.getCheckedChipId()== dejado.getId()) {
                        estado= getString(R.string.dejado);
                    }
                    animeUsuario.setEstado(estado);
                    animeUsuario.setEpisodios(progreso.getEditText().getText().toString());
                    animeUsuario.setNota(String.valueOf(ratingBar.getRating()));
                    if(usuario.getAnimes()!=null){
                        animes.addAll(usuario.getAnimes());
                    }
                    animes.remove(animeUsuario);
                    animes.add(animeUsuario);
                    usuario.setAnimes(animes);
                } else if (busqueda.equals("Manga")) {
                    if(cg.getCheckedChipId()==completado.getId()){
                        estado= getString(R.string.completado);
                    }else if(cg.getCheckedChipId()==enEspera.getId()){
                        estado= getString(R.string.enespera);
                    } else if (cg.getCheckedChipId()== enLista.getId()) {
                        estado= getString(R.string.planeado);
                    } else if (cg.getCheckedChipId()== enProceso.getId()) {
                        estado= getString(R.string.leyendo);
                    } else if (cg.getCheckedChipId()== dejado.getId()) {
                        estado= getString(R.string.dejado);
                    }
                    mangaUsuario.setEstado(estado);
                    assert progreso.getEditText()!=null;
                    mangaUsuario.setCapitulos(progreso.getEditText().getText().toString());
                    mangaUsuario.setNota(String.valueOf(ratingBar.getRating()));
                    if(usuario.getMangas()!=null){
                        mangas.addAll(usuario.getMangas());
                    }
                    mangas.remove(mangaUsuario);
                    mangas.add(mangaUsuario);
                    usuario.setMangas(mangas);
                }
            }else {
                if(busqueda.equals("Manga")){
                        if(usuario.getMangas()!=null){
                            mangas.addAll(usuario.getMangas());
                        }
                        mangas.remove(mangaUsuario);
                        usuario.setMangas(mangas);
                } else if (busqueda.equals("Anime")) {
                    if(usuario.getAnimes()!=null){
                        animes.addAll(usuario.getAnimes());
                    }
                    animes.remove(animeUsuario);
                    usuario.setAnimes(animes);
                }
            }


ref.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        for(DataSnapshot d: snapshot.getChildren()){
            Usuario user= d.getValue(Usuario.class);
            assert user != null;
            if(user.equals(usuario)){
                d.getRef().setValue(usuario);
                break;
            }
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});
        }
    }


    @Override
    public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
        if(group.getCheckedChipId()==completado.getId()){
            progressBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.completado)));
            if(e.getAnime()!=null){
                assert progreso.getEditText()!=null;
                progreso.getEditText().setText(String.valueOf(e.getAnime().getEpisodes()));
                progressBar.setMax(Integer.parseInt(e.getAnime().getEpisodes()));
                progressBar.setProgress(progressBar.getMax());
            } else if (e.getManga()!=null) {
                assert progreso.getEditText()!=null;
                progreso.getEditText().setText(String.valueOf(e.getManga().getChapters()));
                progressBar.setMax(Math.toIntExact(e.getManga().getChapters()));
                progressBar.setProgress(progressBar.getMax());
            }


        } else if (group.getCheckedChipId()==enEspera.getId()) {
            progressBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.espera)));
        } else if (group.getCheckedChipId()== porDefecto.getId()) {
            progressBar.setMax(10);
            progressBar.setProgress(0);
            assert progreso.getEditText()!=null;
            progreso.getEditText().setText("");
        } else if (group.getCheckedChipId()==dejado.getId()) {
            progressBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.dejado)));
        } else if (group.getCheckedChipId()== enProceso.getId()) {
            progressBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.enProceso)));
        } else if (group.getCheckedChipId()==enLista.getId()) {
            progressBar.setMax(10);
            progressBar.setProgress(0);
            assert progreso.getEditText()!=null;
            progreso.getEditText().setText("");
        }
    }
}