package com.example.animanga_universe.fragments;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.animanga_universe.R;
import com.example.animanga_universe.activities.MainMenu;
import com.example.animanga_universe.classes.Anime;
import com.example.animanga_universe.classes.AnimeUser;
import com.example.animanga_universe.classes.User;
import com.example.animanga_universe.encapsulators.Encapsulator;
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
import java.text.DecimalFormat;

/**
 * Fragment que muestra toda la información relevante del amime seleccionado
 * @author Daniel Seregin Kozlov
 * @noinspection deprecation
 */
public class AnimeDescriptionFragment extends Fragment implements View.OnClickListener {
    View view;
    Drawable drawable;
    String status, sinop, temp;
    Anime anime;
    YouTubePlayerView youTubePlayerView;
    User user;
    MainMenu menu;
    TextView score, estado, episodios, duracion, titulo, generos,sinopsis, temporada, anyo, valoradoPor, fuente, compania, tituloJapones;
    FloatingActionButton fab;
    ToggleButton toggleButton;
    ImageView imageView, back;
    int check;


    public AnimeDescriptionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_anime_description, container, false);
        menu= (MainMenu) getActivity();
        assert menu != null;
        anime= menu.getAnime();
        back= menu.getBack();
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        imageView= view.findViewById(R.id.imagen);
        youTubePlayerView= view.findViewById(R.id.youtubePlayer);
        getLifecycle().addObserver(youTubePlayerView);
        score= view.findViewById(R.id.score);
        estado= view.findViewById(R.id.estado);
        episodios= view.findViewById(R.id.episodios);
        duracion= view.findViewById(R.id.duracion);
        titulo= view.findViewById(R.id.titulo);
        generos= view.findViewById(R.id.generos);
        sinopsis= view.findViewById(R.id.sinopsis);
        temporada= view.findViewById(R.id.temporada);
        anyo= view.findViewById(R.id.anyo);
        valoradoPor= view.findViewById(R.id.valoradoPor);
        fuente= view.findViewById(R.id.fuente);
        compania= view.findViewById(R.id.compania);
        tituloJapones= view.findViewById(R.id.tituloJapones);
        user= menu.devolverUser();
        fab= view.findViewById(R.id.FAB);
        fab.setOnClickListener(this);
        toggleButton= menu.getToggle();
        toggleButton.setVisibility(View.VISIBLE);
        toggleButton.setChecked(false);
        rellenoInformacion2(anime);
        TranslatorOptions options= new TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ENGLISH)
                .setTargetLanguage(TranslateLanguage.SPANISH)
                .build();
        com.google.mlkit.nl.translate.Translator translator1= Translation.getClient(options);
        DownloadConditions conditions= new DownloadConditions.Builder()
                .requireWifi()
                .build();
        translator1.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(unused -> {

                });
        getLifecycle().addObserver(translator1);
        if(anime.getStatus().contains("Finished")){
            status="Emisión finalizada";
        }else{
            status= "En emisión";
        }
        translator1.translate(anime.getSynopsis()).addOnSuccessListener(s -> sinop = s);
        translator1.translate(anime.getPremieredSeason()).addOnSuccessListener(s -> temp = s);

        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                rellenoDeInformacion(anime);
            }else {
                rellenoInformacion2(anime);
            }
        });
if(anime.getTrailerUrl()!=null&&!anime.getTrailerUrl().trim().equals("")){
    youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
        @Override
        public void onReady(@NonNull YouTubePlayer youTubePlayer) {
            String[] divisionLink= anime.getTrailerUrl().split("v=");
            youTubePlayer.cueVideo(divisionLink[1],0);
        }
    });
}else {
    youTubePlayerView.setVisibility(View.GONE);
}

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==fab.getId()) {

            toggleButton.setVisibility(View.GONE);
            menu.setBusqueda("Anime");
            String dummy="", info, year;
            int progress=0;
            if (anime!=null&& anime.getPremieredYear() != null && !anime.getPremieredYear().equals("")) {
                year = anime.getPremieredYear();
            } else {
                year = "?";
            }
            if (anime != null && !anime.getEpisodes().equals("")) {
                info = anime.getEpisodes() + " ep, " + year;
            }else {
                info= "? ep, "+year;
            }
            for(AnimeUser animeUser: user.getAnimes()){
                if(animeUser.getAnime().equals(anime)){
                    dummy= animeUser.getEstado();
                    progress=Integer.parseInt(animeUser.getEpisodios());
                    break;
                }
            }
            if (dummy.equals(getString(R.string.completado))) {
                check = R.color.completado;
            } else if (dummy.equals(getString(R.string.dejado))) {
                check = R.color.dejado;
            } else if (dummy.equals(getString(R.string.enespera))) {
                check = R.color.espera;
            } else if (dummy.equals(getString(R.string.viendo))) {
                check = R.color.enProceso;
            } else if (dummy.equals(getString(R.string.planeado))) {
                check = R.color.enlista;
            }
            menu.setEncapsulador(new Encapsulator(anime,drawable,check, anime.getTitle(),info, progress ));
            menu.reemplazarFragment(new EditItemFragment());
        }else if(v.getId()==back.getId()){
            menu.getSwitchButton().setVisibility(View.GONE);
            toggleButton.setVisibility(View.GONE);
            int id = menu.getNavBarId();
            back.setVisibility(View.GONE);
            if(id==R.id.ranking) {
                menu.reemplazarFragment(new RankingFragment());
            } else if (id== R.id.buscar) {
                menu.reemplazarFragment(new SearchFragment());
            } else if (id== R.id.listas) {
                menu.getSwitchButton().setVisibility(View.VISIBLE);
                menu.reemplazarFragment(new AnimeListFragment());
            }

        }
    }

    /**
     * Esta función se encarga de coger el dato de la duración del anime, y hacer el formateo necesario, y devuelve el dato formateado
     * @param a Es el anime del que se va a recoger el dato de la duración
     * @return String de la duración formateado
     */
    public String conversionEpisodios(Anime a){
        if(a.getDuration().contains("per")){
            return a.getDuration().split(" per ")[0]+getString(R.string.divisor)+a.getDuration().split(" per ")[1];
        }else if(a.getDuration()==null||a.getDuration().equals("")){
            return "? mins";
        }else {
            return a.getDuration();
        }
    }

    /**
     * Esta función hace el formateo de los episodios de un anime, en el caso de que no esté acabado el anime, es decir, sigue en emisión
     * se mostrará el símbolo de incógnita, en el caso contrario los episodios totales
     * @param a E anime del que se deben obtener los epidosios
     * @return String de los episodios
     */
    public String episodios(Anime a){
        if(a.getEpisodes()==null||a.getEpisodes().trim().equals("")){
            return "?";
        }else {
            return a.getEpisodes();
        }

    }

    /**
     * Esta función obtiene todos los generos del anime pasado, y los formatea de una manera determinada
     * @param a El anime del que se quieren recoger los generos
     * @return Los generos formateados
     */
    public String generos(Anime a){
        StringBuilder formateo= new StringBuilder();
        String generos= a.getGenres().substring(1,a.getGenres().length()-1);
        String[] generosSeparados= generos.split(",");
        String demographics= a.getDemographics().substring(1,a.getDemographics().length()-1);
        String[] demographicsSeparados= demographics.split(",");
        int counter=0;
        for (String generosSeparado : generosSeparados) {
            formateo.append(generosSeparado).append("\t");
            counter++;
            if (counter == 2) {
                formateo.append("\n");
                counter = 0;
            }
        }
        for (String demographicsSeparado : demographicsSeparados) {
            formateo.append(demographicsSeparado).append("\t");
            counter++;
            if (counter == 2) {
                formateo.append("\n");
                counter = 0;
            }
        }
        return formateo.toString();
    }

    /**
     * Esta funcion recibe el anime, y se rellenan toda la información necesaria en la página
     * en algunos casos haciendo uso de otras funciones para formateo
     * @param a El anime del que se quiere recoger la información
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    public void rellenoDeInformacion(Anime a){
        try {
            InputStream is = (InputStream) new URL(a.getMainPicture()).getContent();
            drawable = Drawable.createFromStream(is, "src name");

        } catch (IOException e) {
            drawable = getResources().getDrawable(R.drawable.ic_launcher_foreground);
        }
        imageView.setImageDrawable(drawable);
        estado.setText(status);
        sinopsis.setText(sinop);
        float newScore = Float.parseFloat(a.getScore());
        DecimalFormat formato = new DecimalFormat("#.##");
        score.setText(formato.format(newScore));
        episodios.setText(episodios(a));
        duracion.setText(conversionEpisodios(a));
        titulo.setText(a.getTitle());
        generos.setText(generos(a));
        temporada.setText(temp);
        anyo.setText(a.getPremieredYear());
        valoradoPor.setText(a.getScoredBy());
        fuente.setText(a.getSource());
        compania.setText(a.getStudios().substring(1,a.getStudios().length()-1));
        tituloJapones.setText(a.getTitleJapanese());
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    public void rellenoInformacion2(Anime a){
        try {
            InputStream is = (InputStream) new URL(a.getMainPicture()).getContent();
            drawable = Drawable.createFromStream(is, "src name");

        } catch (IOException e) {
            drawable = getResources().getDrawable(R.drawable.ic_launcher_foreground);
        }
        imageView.setImageDrawable(drawable);
        estado.setText(a.getStatus());
        float newScore = Float.parseFloat(a.getScore());
        DecimalFormat formato = new DecimalFormat("#.##");
        score.setText(formato.format(newScore));
        episodios.setText(episodios(a));
        duracion.setText(conversionEpisodios(a));
        titulo.setText(a.getTitle());
        generos.setText(generos(a));
        sinopsis.setText(a.getSynopsis());
        temporada.setText(a.getPremieredSeason());
        anyo.setText(a.getPremieredYear());
        valoradoPor.setText(a.getScoredBy());
        fuente.setText(a.getSource());
        compania.setText(a.getStudios().substring(1,a.getStudios().length()-1));
        tituloJapones.setText(a.getTitleJapanese());
    }

}