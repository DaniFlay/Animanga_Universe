package com.example.animanga_universe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.RatingBar;

import com.example.animanga_universe.clases.Anime;
import com.example.animanga_universe.clases.Manga;
import com.example.animanga_universe.clases.Usuario;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.firestore.CollectionReference;

import java.util.ArrayList;

public class EditarItem extends AppCompatActivity {
    Usuario usuario;
    Encapsulador e;
    String busqueda;
    ChipGroup cg;
    Chip completado, enProceso, enEspera, dejado, enLista;
    RatingBar ratingBar;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_item);
        ratingBar= findViewById(R.id.ratingBar);
        progressBar= findViewById(R.id.progressBar);
        cg= findViewById(R.id.chipGroup);
        completado= findViewById(R.id.completado);
        enProceso= findViewById(R.id.enProceso);
        enEspera= findViewById(R.id.enEspera);
        dejado= findViewById(R.id.dejado);
        enLista= findViewById(R.id.planeado);
        usuario = getIntent().getParcelableExtra("usuario");
        e = getIntent().getParcelableExtra("encapsulador");
        busqueda = getIntent().getStringExtra("busqueda");
        if (busqueda.equals("Anime")) {
            if(e.getColor()== R.color.espera){
               enEspera.setChecked(true);
               for(Anime a: usuario.getAnimesEspera()){
                   if(e.getTitulo().equals(a.getTitle())){
                       ratingBar.setRating(Float.parseFloat(a.getScore()));
                       if(e.getInfo().split(" ")[0].equals("?")){
                           progressBar.setMax(10);
                           progressBar.setProgress(5);
                       }else{
                           progressBar.setMax(Integer.parseInt(e.getInfo().split(" ")[0]));
                           progressBar.setProgress(Integer.parseInt(a.getEpisodes()));
                           progressBar.setBackgroundColor(getResources().getColor(R.color.espera));
                       }
                   }
               }
            } else if (e.getColor()== R.color.enProceso) {
                enProceso.setChecked(true);
            } else if (e.getColor()== R.color.completado) {
                completado.setChecked(true);

            }else if(e.getColor()== R.color.enlista){
                enLista.setChecked(true);
            } else if (e.getColor()== R.color.dejado) {
                dejado.setChecked(true);
            }

        } else {

        }
    }
}