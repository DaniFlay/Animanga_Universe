package com.example.animanga_universe.fragments;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.animanga_universe.R;
import com.example.animanga_universe.activities.MainMenu;
import com.example.animanga_universe.classes.Manga;
import com.example.animanga_universe.classes.MangaUser;
import com.example.animanga_universe.classes.User;
import com.example.animanga_universe.encapsulators.Encapsulator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;

/**
 * Crea una instancia del fragment
 * @noinspection deprecation
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
    ImageView imageView, back;

    public MangaDescriptionFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_manga_description, container, false);
        menu= (MainMenu) getActivity();
        assert menu != null;
        manga= menu.getManga();
        back= menu.getBack();
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
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
        toggleButton.setVisibility(View.VISIBLE);
        toggleButton.setChecked(false);
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
                .addOnSuccessListener(unused -> {

                });
        getLifecycle().addObserver(translator1);
        if(manga.getStatus().contains("Finished")){
            status="Publicación finalizada";
        }else{
            status= "Publicando";
        }
        translator1.translate(manga.getSynopsis()).addOnSuccessListener(s -> sinop = s);


        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                rellenoDeInformacion(manga);
            }else {
                rellenoInformacion2(manga);
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        //En el caso de pulsar el botón atrás, se vuelve atrás
        if(v.getId()==back.getId()){
            int id= menu.getNavBarId();
            back.setVisibility(View.GONE);
            toggleButton.setVisibility(View.GONE);
            menu.getSwitchButton().setVisibility(View.GONE);
            if(id==R.id.ranking) {
                menu.reemplazarFragment(new RankingFragment());
            } else if (id== R.id.buscar) {
                menu.reemplazarFragment(new SearchFragment());
            } else if (id== R.id.listas) {
                menu.getSwitchButton().setVisibility(View.VISIBLE);
                menu.reemplazarFragment(new MangaListFragment());
            }
        } else if (v.getId()== fab.getId()) {
            //En el caso de pulsar el FAB se abre el fragment de la edición del manga
            toggleButton.setVisibility(View.GONE);
            menu.setBusqueda("Manga");
            String dummy="", info, year;
            int progress=0, check=0;
            if (manga!=null&& manga.getPublishedFrom() != null && !manga.getPublishedFrom().equals("")) {
                year = manga.getPublishedFrom().substring(manga.getPublishedFrom().length()-4);
            } else {
                year = "?";
            }
            if (manga != null && manga.getChapters()!=null) {
                info = manga.getChapters() + " cap, " + year;
            }else {
                info= "? cap, "+year;
            }
            if(user.getMangas()!=null){
                for(MangaUser mangaUser: user.getMangas()){
                    if(mangaUser.getManga().equals(manga)){
                        dummy= mangaUser.getEstado();
                        progress= Math.toIntExact(Long.parseLong(mangaUser.getCapitulos()));
                        break;
                    }
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
            menu.setEncapsulador(new Encapsulator(manga,drawable,check, manga.getTitle(),info, progress ));
            menu.reemplazarFragment(new EditItemFragment());
        }

    }

    /**
     * Formatea los capitulso del manga
     * @param m Manga cuyos capitulos se deben formatear
     * @return los capitulos formateados
     */
    public String capitulos(Manga m){
        if(m.getChapters()==null){
            return "?";
        }else {
            return String.valueOf(m.getChapters());
        }

    }

    /**
     * Formatea los volumenes de un manga
     * @param m Manga cuyos volumenes deben ser formateados
     * @return los volumenes fomrmateados
     */
    public String volumenes(Manga m){
        if(m.getVolumes()==null){
            return "?";
        }else {
            return String.valueOf(m.getVolumes());
        }

    }

    /**
     * Formatea los generos del manga
     * @param m Manga cuyos generos deben ser formateados
     * @return los generos formateados
     */
    public String generos(Manga m){
        StringBuilder formateo= new StringBuilder();
        String generos= m.getGenres().substring(1,m.getGenres().length()-1);
        String[] generosSeparados= generos.split(",");
        String demographics= m.getDemographics().substring(1,m.getDemographics().length()-1);
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
     * Se encarga de rellenar la información del manga, traduciendo alguna información al castellani
     * @param m Manga cuya información se utiliza para el relleno de los campos
     */
    @SuppressLint("UseCompatLoadingForDrawables")
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
        Double newScore = m.getScore();
        DecimalFormat formato = new DecimalFormat("#.##");
        score.setText(formato.format(newScore));
        capitulos.setText(capitulos(m));
        volumenes.setText(volumenes(m));
        titulo.setText(m.getTitle());
        generos.setText(generos(m));
        tipo.setText(m.getType());
        anyo.setText(m.getPublishedFrom().substring(m.getPublishedFrom().length()-4));
        valoradoPor.setText(m.getScoredBy());
        tituloIngles.setText(m.getTitleEnglish());
        autores.setText(authors(m));
        tituloJapones.setText(m.getTitleJapanese());
    }

    /**
     * Se encarga de rellenar la información del manga
     * @param m Manga cuya información se utiliza para el relleno de los campos
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    public void rellenoInformacion2(Manga m){
        try {
            InputStream is = (InputStream) new URL(m.getMainPicture()).getContent();
            drawable = Drawable.createFromStream(is, "src name");

        } catch (IOException e) {
            drawable = getResources().getDrawable(R.drawable.ic_launcher_foreground);
        }
        imageView.setImageDrawable(drawable);
        estado.setText(m.getStatus());
        Double newScore = m.getScore();
        DecimalFormat formato = new DecimalFormat("#.##");
        score.setText(formato.format(newScore));
        capitulos.setText(capitulos(m));
        volumenes.setText(volumenes(m));
        titulo.setText(m.getTitle());
        generos.setText(generos(m));
        tipo.setText(m.getType());
        sinopsis.setText(m.getSynopsis());
        anyo.setText(m.getPublishedFrom().substring(m.getPublishedFrom().length()-4));
        valoradoPor.setText(m.getScoredBy());
        tituloIngles.setText(m.getTitleEnglish());
        autores.setText(authors(m));
        tituloJapones.setText(m.getTitleJapanese());
    }

    /**
     * Se utiliza pra el formateo de los autores del manga
     * @param m Manga cuyos autores han de ser formateados
     * @return los autores formateados
     */
    public String authors(Manga m){
        StringBuilder a= new StringBuilder(m.getAuthors());
        char[] array1= a.toString().toCharArray();
        a = new StringBuilder();
        for (char c : array1) {
            if (c != '[' && c != ']' && c != '(' && c != ')') {
                a.append(c);
            }
        }
        String[] array2= a.toString().split(",");
        a = new StringBuilder();
        for(int i=0; i<array2.length;i++){
            if(array2[i].contains("'")){
                a.append(array2[i]);
                if(i<array2.length-1){
                    a.append(",");
                }
            }
        }
        return a.toString();
    }
}