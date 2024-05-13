package com.example.animanga_universe.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import com.example.animanga_universe.R;
import com.example.animanga_universe.activities.Login;
import com.example.animanga_universe.activities.MainMenu;
import com.example.animanga_universe.models.AnimeUser;
import com.example.animanga_universe.models.MangaUser;
import com.example.animanga_universe.models.User;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

/**
 * El Fragment del Perfil, que muestra la información relevante del usuario y donde se puede cambiar la contraseña o editar el perfil
 * @author Daniel Seregin Kozlov
 * @noinspection ALL
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {
    TextView usuario, sexo, fechaNac, fechaReg, correo, editarPerfil, cambiarContraseña,salir, todos, enProceso, completado, dejado, enLista, enEspera, viendo, anyos, meses,semanas, dias,horas, minutos, media ;
    TabLayout tabLayout;
    TableLayout tableLayout, tableLayoutTime;
    User user;
    int counter;
    MainMenu mainMenu;
    PieChart pieChart;
    PieData pieData;
    PieDataSet pieDataSet;
    ArrayList<PieEntry> pieEntriesAnime;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public ProfileFragment() {

    }

    /**
     * Se crea la instancia del fragment
     * @param param1 Parameter 1 creado automáticamente
     * @param param2 Parameter 2 creado automáticamente
     * @return Nueva instancia del Fragment
     */
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.fragment_profile, container, false);
       mainMenu = (MainMenu) getActivity();
       mainMenu.guardarUsuario(mainMenu.devolverUser());
        if (mainMenu != null) {
            user= mainMenu.devolverUser();
        }
        todos=view.findViewById(R.id.todos);
       counter=0;
       salir= view.findViewById(R.id.salir);
       salir.setOnClickListener(this);
        pieEntriesAnime= new ArrayList<>();
       anyos= view.findViewById(R.id.anyos);
       meses= view.findViewById(R.id.meses);
       semanas= view.findViewById(R.id.semanas);
       dias= view.findViewById(R.id.dias);
       horas= view.findViewById(R.id.horas);
       minutos= view.findViewById(R.id.minutos);
       media= view.findViewById(R.id.media);
       completado= view.findViewById(R.id.completados);
       enProceso= view.findViewById(R.id.viendo);
       dejado= view.findViewById(R.id.dejado);
       enLista= view.findViewById(R.id.enlista);
       enEspera= view.findViewById(R.id.enespera);
       viendo= view.findViewById(R.id.viendo);
        usuario= view.findViewById(R.id.user);
        usuario.setText(user.getUsername());
        sexo= view.findViewById(R.id.sexo);
        if(user.getSexo().equals(getString(R.string.hombre))){
            sexo.setText(getString(R.string.hombre));
            sexo.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.baseline_male_24,0,0,0);
        }else{
            sexo.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.baseline_female_24,0,0,0);
            sexo.setText(getString(R.string.mujer));
        }
        fechaNac= view.findViewById(R.id.fechaNacimiento);
        fechaNac.setText(user.getFecha_de_nacimiento());
        fechaReg= view.findViewById(R.id.fechaRegistro);
        fechaReg.setText(user.getFecha_de_registro());
        correo= view.findViewById(R.id.correo);
        correo.setText(user.getCorreo());
        editarPerfil= view.findViewById(R.id.editarPerfil);
        cambiarContraseña= view.findViewById(R.id.cambiarContraseña);
        tabLayout= view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.anime));
        if(user.getMangas()!=null){
            tabLayout.addTab(tabLayout.newTab().setText(R.string.manga));
        }
        tableLayout= view.findViewById(R.id.tableLayout);
        editarPerfil.setOnClickListener(this);
        cambiarContraseña.setOnClickListener(this);
        viendo.setText(R.string.viendo);
        pieChart= view.findViewById(R.id.pieChart);
        //Si el usuario tiene animes guardados, se setea el PieChart, en caso contrario se hace invisible el elemento
        if(user.getAnimes()!=null) {
            todos.setText(String.valueOf(user.getAnimes().size()));
            rellenarAnimes();
            pieChart.setVisibility(View.VISIBLE);
            getDataAnime();
            pieDataSet = new PieDataSet(pieEntriesAnime, "Animes");
            pieDataSet.setColors(getResources().getColor(R.color.enProceso), getResources().getColor(R.color.dejado), getResources().getColor(R.color.enlista), getResources().getColor(R.color.espera), getResources().getColor(R.color.completado));
            pieDataSet.setValueTextSize(18f);
            pieDataSet.setValueFormatter(new LargeValueFormatter());
            pieData = new PieData(pieDataSet);
            pieChart.setData(pieData);
            pieChart.getDescription().setEnabled(false);
            pieChart.animateY(1300);
            convertMinutes(timeAnime());
            scoreAnime();
        }  else {
            pieChart.setVisibility(View.GONE);
        }



//Dependiedo del tab seleccionado se muestran las estadísticas de los animes o de los mangas por sus estados
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 0) {
                    if(user.getAnimes()!=null){
                        rellenarAnimes();
                        convertMinutes(timeAnime());
                        scoreAnime();
                        if(user.getAnimes().size()!=0){

                            pieChart.setVisibility(View.VISIBLE);
                            getDataAnime();

                            pieDataSet= new PieDataSet(pieEntriesAnime, "Animes");

                            pieDataSet.setColors(getResources().getColor(R.color.enProceso),getResources().getColor(R.color.dejado),getResources().getColor(R.color.enlista),getResources().getColor(R.color.espera),getResources().getColor(R.color.completado));
                            pieDataSet.setValueTextSize(18f);
                            pieDataSet.setValueFormatter(new LargeValueFormatter());
                            pieData= new PieData(pieDataSet);
                            pieChart.setData(pieData);

                            pieChart.getDescription().setEnabled(false);
                            pieChart.animateY(1300);
                        }else {
                            pieChart.setVisibility(View.GONE);
                        }

                    }

                } else if (tab.getPosition() == 1) {
                        if(user.getMangas()!=null){
                            scoreManga();
                            rellenarMangas();
                            convertMinutes(timeManga());
                            if(user.getMangas().size()!=0){
                                pieChart.setVisibility(View.VISIBLE);
                                getDataAnime();
                                pieDataSet= new PieDataSet(pieEntriesAnime, "Mangas");
                                pieDataSet.setColors(getResources().getColor(R.color.enProceso),getResources().getColor(R.color.dejado),getResources().getColor(R.color.enlista),getResources().getColor(R.color.espera),getResources().getColor(R.color.completado));
                                pieDataSet.setValueTextSize(18f);
                                pieDataSet.setValueFormatter(new LargeValueFormatter());
                                pieData= new PieData(pieDataSet);
                                pieChart.setData(pieData);
                                pieChart.getDescription().setEnabled(false);
                                pieChart.animateY(1300);
                            }else {
                                pieChart.setVisibility(View.GONE);
                            }

                        }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
       return view;

    }

    @Override
    public void onClick(View v) {
        //Si se pulsa editar perfil, se cambia al fragment de la ediccón del perfil
if(v.getId()==editarPerfil.getId()){
    mainMenu.reemplazarFragment(new EditProfileFragment());
    //Si se pulsa el cambio de contraseña se cambia al fragment del cambio de contraseña
} else if (v.getId()== cambiarContraseña.getId()) {
    mainMenu.reemplazarFragment(new ChangePasswordFragment());
    //Si se pulsa el texto salir, se sale de la actividad, y se vuelve a la pantalla del Login
} else if (v.getId()==salir.getId()) {
    Intent intent= new Intent(getContext(), Login.class);
    startActivity(intent);
}
    }

    /**
     * Esta función rellena la tabla con las estadísticas de los animes del usuario, mostradno todos los estados y el número de animes que tiene
     * de cada estado
     */
    public void rellenarAnimes(){
        counter=0;
        viendo.setText(R.string.viendo);
        todos.setText(String.valueOf(user.getAnimes().size()));
        for(AnimeUser a: user.getAnimes()){
            if(a.getEstado().equals(getString(R.string.completado))){
                counter++;
            }
        }
        completado.setText(String.valueOf(counter));
        counter=0;
        for(AnimeUser a: user.getAnimes()){
            if(a.getEstado().equals(getString(R.string.enespera))){
                counter++;
            }
        }
        enEspera.setText(String.valueOf(counter));
        counter=0;
        for(AnimeUser a: user.getAnimes()){
            if(a.getEstado().equals(getString(R.string.dejado))){
                counter++;
            }
        }
        dejado.setText(String.valueOf(counter));
        counter=0;
        for(AnimeUser a: user.getAnimes()){
            if(a.getEstado().equals(getString(R.string.planeado))){
                counter++;
            }
        }
        enLista.setText(String.valueOf(counter));
        counter=0;
        for(AnimeUser a: user.getAnimes()){
            if(a.getEstado().equals(getString(R.string.viendo))){
                counter++;
            }
        }
        enProceso.setText(String.valueOf(counter));
        counter=0;
    }

    /**
     * Esta función rellena la tabla con las estadísticas de los mangas del usuario, mostradno todos los estados y el número de mangas que tiene
     * de cada estado
     */
    public void rellenarMangas(){
            viendo.setText(getString(R.string.leyendo));
            todos.setText(String.valueOf(user.getMangas().size()));
            for(MangaUser m: user.getMangas()){
                if(m.getEstado().equals(getString(R.string.completado))){
                    counter++;
                }
            }
            completado.setText(String.valueOf(counter));
            counter=0;
            for(MangaUser m: user.getMangas()){
                if(m.getEstado().equals(getString(R.string.enespera))){
                    counter++;
                }
            }
            enEspera.setText(String.valueOf(counter));
            counter=0;
            for(MangaUser m: user.getMangas()){
                if(m.getEstado().equals(getString(R.string.dejado))){
                    counter++;
                }
            }
            dejado.setText(String.valueOf(counter));
            counter=0;
            for(MangaUser m: user.getMangas()){
                if(m.getEstado().equals(getString(R.string.planeado))){
                    counter++;
                }
            }
            enLista.setText(String.valueOf(counter));
            counter=0;
            for(MangaUser m: user.getMangas()){
                if(m.getEstado().equals(getString(R.string.leyendo))){
                    counter++;
                }
            }
            enProceso.setText(String.valueOf(counter));
            counter=0;
        }

    /**
     * Se obtienen los datos de la tabla con los numeros de las obras segun el estado, y se introducen al PieChart
     */
    public void getDataAnime(){
        pieEntriesAnime.clear();
        if(!viendo.getText().toString().equals("0")){
            if(tabLayout.getSelectedTabPosition()==0){
                pieEntriesAnime.add(new PieEntry(Float.valueOf(viendo.getText().toString()),getString(R.string.viendo)));
            }
            else {
                pieEntriesAnime.add(new PieEntry(Float.valueOf(viendo.getText().toString()),getString(R.string.leyendo)));
            }
        }
        if(!dejado.getText().toString().equals("0")) {
            pieEntriesAnime.add(new PieEntry(Float.valueOf(dejado.getText().toString()), getString(R.string.dejado)));
        }
        if(!enLista.getText().toString().equals("0")) {
                pieEntriesAnime.add(new PieEntry(Float.valueOf(enLista.getText().toString()), getString(R.string.planeado)));
        }
        if(!enEspera.getText().toString().equals("0")) {
            pieEntriesAnime.add(new PieEntry(Float.valueOf(enEspera.getText().toString()), getString(R.string.enespera)));
        }
        if(!completado.getText().toString().equals("0")) {
                pieEntriesAnime.add(new PieEntry(Integer.parseInt(completado.getText().toString()), getString(R.string.completado)));
            }
    }

    /**
     * Esta funcion convierte el total de los minutos en años, meses, semanas, dias, horas, y minutos
     * @param minutes Minutos a convertir
     */
    public void convertMinutes(int minutes){
        int minutesPerHour =60;
        int minutesPerDay = 1440;
        int minutesPerWeek = 10080;
        int minutesPerMonth = 43800;
        int minutesPerYear = 525600;
        int years = minutes / minutesPerYear;
        int remainingMinutes = minutes % minutesPerYear;
        int months = remainingMinutes / minutesPerMonth;
        remainingMinutes %= minutesPerMonth;
        int weeks = remainingMinutes / minutesPerWeek;
        remainingMinutes %= minutesPerWeek;
        int days = remainingMinutes / minutesPerDay;
        remainingMinutes %= minutesPerDay;
        int hours = remainingMinutes / minutesPerHour;
        remainingMinutes %= minutesPerHour;
        anyos.setText(String.valueOf(years));
        meses.setText(String.valueOf(months));
        semanas.setText(String.valueOf(weeks));
        dias.setText(String.valueOf(days));
        horas.setText(String.valueOf(hours));
        minutos.setText(String.valueOf(remainingMinutes));
    }

    /**
     * Esta función calcula el tiempo invertido en el anime
     * @return los minutos invertidos en el anime
     */
    public int timeAnime(){
        int minutes=0;
        for(AnimeUser a: user.getAnimes()){
            if(a.getAnime().getDuration().contains("hr")){
                minutes+=Integer.parseInt(a.getAnime().getDuration().substring(5,7));
                for(int i=0; i<Integer.parseInt(a.getAnime().getDuration().substring(0,1));i++){
                    minutes+=60;
                }
            }else {
                for(int i=0; i<Integer.parseInt(a.getEpisodios());i++){
                    minutes+=Integer.parseInt(a.getAnime().getDuration().substring(0,2));
                }
            }

        }
        return minutes;
    }

    /**
     * El tiempo invertido en el manga, ya que no tiene duración, se ha cogido una media de 22 minutos por capitulo
     * @return los minutos invertidos en el manga
     */
    public int timeManga(){
        int minutes=0;
        for(MangaUser m: user.getMangas()){
            for(int i=0; i<Integer.parseInt(m.getCapitulos());i++){
                minutes+=22;
            }
        }
        return minutes;
    }

    /**
     * Calcula la nota media del usuario de los animes, y lo settea
     */
    public void scoreAnime(){
        float score=0;
        for(AnimeUser a: user.getAnimes()){
            score+=Float.parseFloat(a.getNota());
        }

        media.setText(String.format("%.2f",2*(score/(user.getAnimes().size()))));
    }

    /**
     * Calcula la nota media de los mangas, y lo settea
     */
    public void scoreManga(){
        float score=0;
        for(MangaUser m: user.getMangas()){
            score+=Float.parseFloat(m.getNota());
        }
        media.setText(String.format("%.2f",2*(score/(user.getMangas().size()))));
    }
        }
