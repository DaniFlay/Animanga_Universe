package com.example.animanga_universe.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.animanga_universe.R;
import com.example.animanga_universe.activities.MainMenu;
import com.example.animanga_universe.classes.Anime;
import com.example.animanga_universe.classes.Manga;
import com.example.animanga_universe.classes.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.TranslatorOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Crea una instancia del fragment
 */
public class MangaDescriptionFragment extends Fragment implements View.OnClickListener {

    View view;
    Drawable drawable;
    String status, sinop;
    Manga manga;
    User user;
    MainMenu menu;
    TextView capitulos, estado, volumenes,  titulo, generos,sinopsis, tipo, anyo, valoradoPor, tituloIngles, autores, tituloJapones,score;
    FloatingActionButton fab;
    ToggleButton toggleButton;
    ImageView imageView;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public MangaDescriptionFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MangaDescriptionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MangaDescriptionFragment newInstance(String param1, String param2) {
        MangaDescriptionFragment fragment = new MangaDescriptionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_manga_description, container, false);
        menu= (MainMenu) getActivity();
        assert menu != null;
        manga= menu.getManga();
        imageView= view.findViewById(R.id.imagen);
        score= view.findViewById(R.id.score);
        estado= view.findViewById(R.id.estado);
        capitulos= view.findViewById(R.id.capitulos);
        volumenes= view.findViewById(R.id.volumenes);
        titulo= view.findViewById(R.id.titulo);
        generos= view.findViewById(R.id.generos);
        sinopsis= view.findViewById(R.id.sinopsis);
        tipo= view.findViewById(R.id.tipo);
        anyo= view.findViewById(R.id.anyo);
        valoradoPor= view.findViewById(R.id.valoradoPor);
        tituloIngles= view.findViewById(R.id.tituloIngles);
        autores= view.findViewById(R.id.autores);
        tituloJapones= view.findViewById(R.id.tituloJapones);
        user= menu.devolverUser();
        fab= view.findViewById(R.id.FAB);
        fab.setOnClickListener(this);
        toggleButton= menu.getToggle();
        menu.toggleState();
        menu.changeToggle();
        rellenoInformacion2(manga);
        TranslatorOptions options= new TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ENGLISH)
                .setTargetLanguage(TranslateLanguage.SPANISH)
                .build();
        com.google.mlkit.nl.translate.Translator translator1= Translation.getClient(options);
        DownloadConditions conditions= new DownloadConditions.Builder()
                .requireWifi()
                .build();
        translator1.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                });
        getLifecycle().addObserver(translator1);
        if(manga.getStatus().contains("Finished")){
            status="Publicación finalizada";
        }else{
            status= "Publicando";
        }
        translator1.translate(manga.getSynopsis()).addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                sinop= s;
            }
        });


        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    rellenoDeInformacion(manga);
                }else {
                    rellenoInformacion2(manga);
                }
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {

    }
    public String capitulos(Manga m){
        if(m.getChapters()==null){
            return "?";
        }else {
            return String.valueOf(m.getChapters());
        }

    }
    public String volumenes(Manga m){
        if(m.getVolumes()==null){
            return "?";
        }else {
            return String.valueOf(m.getVolumes());
        }

    }
    public String generos(Manga m){
        String formateo="";
        String generos= m.getGenres().substring(1,m.getGenres().length()-1);
        String[] generosSeparados= generos.split(",");
        String demographics= m.getDemographics().substring(1,m.getDemographics().length()-1);
        String[] demographicsSeparados= demographics.split(",");
        int counter=0;
        for(int i=0; i<generosSeparados.length;i++){
            formateo+=generosSeparados[i]+"\t";
            counter++;
            if(counter==2){
                formateo+="\n";
                counter=0;
            }
        }
        for(int i=0; i<demographicsSeparados.length;i++){
            formateo+=demographicsSeparados[i]+"\t";
            counter++;
            if(counter==2){
                formateo+="\n";
                counter=0;
            }
        }
        return formateo;
    }
    public void rellenoDeInformacion(Manga m){
        try {
            InputStream is = (InputStream) new URL(m.getMainPicture()).getContent();
            drawable = Drawable.createFromStream(is, "src name");

        } catch (IOException e) {
            drawable = getResources().getDrawable(R.drawable.ic_launcher_foreground);
        }
        imageView.setImageDrawable(drawable);
        estado.setText(status);
        sinopsis.setText(sinop);
        score.setText(String.valueOf(m.getScore()));
        capitulos.setText(capitulos(m));
        volumenes.setText(volumenes(m));
        titulo.setText(m.getTitle());
        generos.setText(generos(m));
        tipo.setText(m.getType());
        anyo.setText(m.getPublishedFrom().substring(m.getPublishedFrom().length()-4));
        valoradoPor.setText(m.getScoredBy());
        tituloIngles.setText(m.getTitleEnglish());
        autores.setText(m.getAuthors().substring(8,m.getAuthors().length()-3));
        tituloJapones.setText(m.getTitleJapanese());
    }
    public void rellenoInformacion2(Manga m){
        try {
            InputStream is = (InputStream) new URL(m.getMainPicture()).getContent();
            drawable = Drawable.createFromStream(is, "src name");

        } catch (IOException e) {
            drawable = getResources().getDrawable(R.drawable.ic_launcher_foreground);
        }
        imageView.setImageDrawable(drawable);
        estado.setText(m.getStatus());
        score.setText(String.valueOf(m.getScore()));
        capitulos.setText(capitulos(m));
        volumenes.setText(volumenes(m));
        titulo.setText(m.getTitle());
        generos.setText(generos(m));
        tipo.setText(m.getType());
        anyo.setText(m.getPublishedFrom().substring(m.getPublishedFrom().length()-4));
        valoradoPor.setText(m.getScoredBy());
        tituloIngles.setText(m.getTitleEnglish());
        autores.setText(m.getAuthors().substring(8,m.getAuthors().length()-3));
        tituloJapones.setText(m.getTitleJapanese());
    }
}