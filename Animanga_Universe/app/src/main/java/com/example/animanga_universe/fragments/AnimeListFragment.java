package com.example.animanga_universe.fragments;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.animanga_universe.R;
import com.example.animanga_universe.activities.MainMenu;
import com.example.animanga_universe.adapters.ListAdapter;
import com.example.animanga_universe.classes.AnimeUser;
import com.example.animanga_universe.classes.User;
import com.example.animanga_universe.encapsulators.Encapsulator;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * El fragment qu emuestra las listas de los animes del usuario, que se filtran por su estado
 * @author Daniel Seregin Kozlov
 * @noinspection ALL
 */
public class AnimeListFragment extends Fragment {
    TabLayout tabLayout;
    User user;
    View view;
    int check;
    ArrayList<Encapsulator> animes;
    ListAdapter listAdapter;
    RecyclerView.LayoutManager layoutManager;
    Drawable drawable;
    RecyclerView recyclerView;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public AnimeListFragment() {
    }
    /**
     * Se crea la instancia del fragment
     * @param param1 Parameter 1 creado automáticamente
     * @param param2 Parameter 2 creado automáticamente
     * @return Nueva instancia del Fragment
     */
    public static AnimeListFragment newInstance(String param1, String param2) {
        AnimeListFragment fragment = new AnimeListFragment();
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

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        MainMenu activity= (MainMenu) getActivity();
        if (activity != null) {
            user = activity.devolverUser();
        }

        view = inflater.inflate(R.layout.fragment_anime_listas, container, false);
        tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.todos));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.viendo));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.planeado));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.enespera));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.dejado));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.completado));
        animes = new ArrayList<>();
        String anyo, info = "";
        //Se hace el relleno de los encapsuladores
        if(user.getAnimes()!=null){
            for (AnimeUser a : user.getAnimes()) {
                if (a!=null&& a.getAnime().getPremieredYear() != null && !a.getAnime().getPremieredYear().equals("")) {
                    anyo = a.getAnime().getPremieredYear();
                } else {
                    anyo = "?";
                }
                if (a != null && !a.getAnime().getEpisodes().equals("")) {
                    info = a.getAnime().getEpisodes() + " ep, " + anyo;
                }
                try {
                    InputStream is = (InputStream) new URL(a.getAnime().getMainPicture()).getContent();
                    drawable = Drawable.createFromStream(is, "src name");

                } catch (IOException e) {
                    drawable = getResources().getDrawable(R.drawable.ic_launcher_foreground);
                }
                if (a.getEstado().equals(getString(R.string.completado))) {
                    check = R.color.completado;
                } else if (a.getEstado().equals(getString(R.string.dejado))) {
                    check = R.color.dejado;
                } else if (a.getEstado().equals(getString(R.string.enespera))) {
                    check = R.color.espera;
                } else if (a.getEstado().equals(getString(R.string.viendo))) {
                    check = R.color.enProceso;
                } else if (a.getEstado().equals(getString(R.string.planeado))) {
                    check = R.color.enlista;
                }
                Encapsulator e = new Encapsulator(a.getAnime(), drawable, check, a.getAnime().getTitle(), info, Integer.parseInt(a.getEpisodios()));
                if (!animes.contains(e)) {
                    animes.add(e);
                }
            }
                recyclerView = view.findViewById(R.id.recycler_view);

                listAdapter = new ListAdapter(user, animes, getContext(), R.layout.element_lista_usuario,"Anime" );
                recyclerView.setAdapter(listAdapter);
                layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @SuppressLint("UseCompatLoadingForDrawables")
                    @Override
                    //Dependiedo del tab seleccionado se muestra la lista de los animes flitrados según su estado
                    public void onTabSelected(TabLayout.Tab tab) {
                        animes.clear();
                        String anyo, info = "";

                        if (tab.getPosition() == 0) {
                            for (AnimeUser a : user.getAnimes()) {
                                if (a!=null&&a.getAnime().getPremieredYear() != null && !a.getAnime().getPremieredYear().equals("")) {
                                    anyo = a.getAnime().getPremieredYear();
                                } else {
                                    anyo = "?";
                                }
                                if (a != null && !a.getAnime().getEpisodes().equals("")) {
                                    info = a.getAnime().getEpisodes() + " ep, " + anyo;
                                }
                                try {
                                    InputStream is = (InputStream) new URL(a.getAnime().getMainPicture()).getContent();
                                    drawable = Drawable.createFromStream(is, "src name");

                                } catch (IOException e) {
                                    drawable = getResources().getDrawable(R.drawable.ic_launcher_foreground);
                                }
                                if (a.getEstado().equals(getString(R.string.completado))) {
                                    check = R.color.completado;
                                } else if (a.getEstado().equals(getString(R.string.dejado))) {
                                    check = R.color.dejado;
                                } else if (a.getEstado().equals(getString(R.string.enespera))) {
                                    check = R.color.espera;
                                } else if (a.getEstado().equals(getString(R.string.viendo))) {
                                    check = R.color.enProceso;
                                } else if (a.getEstado().equals(getString(R.string.planeado))) {
                                    check = R.color.enlista;
                                }
                                Encapsulator e = new Encapsulator(a.getAnime(), drawable, check, a.getAnime().getTitle(), info, Integer.parseInt(a.getEpisodios()));
                                if (!animes.contains(e)) {
                                    animes.add(e);
                                }

                            }
                        } else if (tab.getPosition() == 1) {
                            check = R.color.enProceso;
                            for (AnimeUser a : user.getAnimes()) {
                                if (a!=null&&a.getEstado().equals(getString(R.string.viendo))) {
                                    if (a.getAnime().getPremieredYear() != null && !a.getAnime().getPremieredYear().equals("")) {
                                        anyo = a.getAnime().getPremieredYear();
                                    } else {
                                        anyo = "?";
                                    }
                                    if (!a.getAnime().getEpisodes().equals("")) {
                                        info = a.getAnime().getEpisodes() + " ep, " + anyo;
                                    } else {
                                        info = "? ep, " + anyo;
                                    }
                                    try {
                                        InputStream is = (InputStream) new URL(a.getAnime().getMainPicture()).getContent();
                                        drawable = Drawable.createFromStream(is, "src name");

                                    } catch (IOException e) {
                                        drawable = getResources().getDrawable(R.drawable.ic_launcher_foreground);
                                    }

                                    Encapsulator e = new Encapsulator(a.getAnime(), drawable, check, a.getAnime().getTitle(), info, Integer.parseInt(a.getEpisodios()));
                                    if (!animes.contains(e)) {
                                        animes.add(e);
                                    }
                                }

                            }
                        } else if (tab.getPosition() == 2) {
                            check = R.color.enlista;
                            for (AnimeUser a : user.getAnimes()) {
                                if (a!=null&&a.getEstado().equals(getString(R.string.planeado))) {
                                    if (a.getAnime().getPremieredYear() != null && !a.getAnime().getPremieredYear().equals("")) {
                                        anyo = a.getAnime().getPremieredYear();
                                    } else {
                                        anyo = "?";
                                    }
                                    if (!a.getAnime().getEpisodes().equals("")) {
                                        info = a.getAnime().getEpisodes() + " ep, " + anyo;
                                    } else {
                                        info = "? ep, " + anyo;
                                    }
                                    try {
                                        InputStream is = (InputStream) new URL(a.getAnime().getMainPicture()).getContent();
                                        drawable = Drawable.createFromStream(is, "src name");

                                    } catch (IOException e) {
                                        drawable = getResources().getDrawable(R.drawable.ic_launcher_foreground);
                                    }

                                    Encapsulator e = new Encapsulator(a.getAnime(), drawable, check, a.getAnime().getTitle(), info, Integer.parseInt(a.getEpisodios()));
                                    if (!animes.contains(e)) {
                                        animes.add(e);
                                    }
                                }
                            }

                        } else if (tab.getPosition() == 3) {
                            check = R.color.espera;
                            for (AnimeUser a : user.getAnimes()) {
                                if (a!=null&&a.getEstado().equals(getString(R.string.enespera))) {
                                    if (a.getAnime().getPremieredYear() != null && !a.getAnime().getPremieredYear().equals("")) {
                                        anyo = a.getAnime().getPremieredYear();
                                    } else {
                                        anyo = "?";
                                    }
                                    if (!a.getAnime().getEpisodes().equals("")) {
                                        info = a.getAnime().getEpisodes() + " ep, " + anyo;
                                    } else {
                                        info = "? ep, " + anyo;
                                    }
                                    try {
                                        InputStream is = (InputStream) new URL(a.getAnime().getMainPicture()).getContent();
                                        drawable = Drawable.createFromStream(is, "src name");

                                    } catch (IOException e) {
                                        drawable = getResources().getDrawable(R.drawable.ic_launcher_foreground);
                                    }

                                    Encapsulator e = new Encapsulator(a.getAnime(), drawable, check, a.getAnime().getTitle(), info, Integer.parseInt(a.getEpisodios()));
                                    if (!animes.contains(e)) {
                                        animes.add(e);
                                    }
                                }

                            }
                        } else if (tab.getPosition() == 4) {
                            check = R.color.dejado;
                            for (AnimeUser a : user.getAnimes()) {
                                if (a!=null&&a.getEstado().equals(getString(R.string.dejado))) {
                                    if (a.getAnime().getPremieredYear() != null && !a.getAnime().getPremieredYear().equals("")) {
                                        anyo = a.getAnime().getPremieredYear();
                                    } else {
                                        anyo = "?";
                                    }
                                    if (!a.getAnime().getEpisodes().equals("")) {
                                        info = a.getAnime().getEpisodes() + " ep, " + anyo;
                                    } else {
                                        info = "? ep, " + anyo;
                                    }
                                    try {
                                        InputStream is = (InputStream) new URL(a.getAnime().getMainPicture()).getContent();
                                        drawable = Drawable.createFromStream(is, "src name");

                                    } catch (IOException e) {
                                        drawable = getResources().getDrawable(R.drawable.ic_launcher_foreground);
                                    }

                                    Encapsulator e = new Encapsulator(a.getAnime(), drawable, check, a.getAnime().getTitle(), info, Integer.parseInt(a.getEpisodios()));
                                    if (!animes.contains(e)) {
                                        animes.add(e);
                                    }
                                }

                            }
                        } else if (tab.getPosition() == 5) {
                            check = R.color.completado;
                            for (AnimeUser a : user.getAnimes()) {
                                if (a!=null&&a.getEstado().equals(getString(R.string.completado))) {
                                    if (a.getAnime().getPremieredYear() != null && !a.getAnime().getPremieredYear().equals("")) {
                                        anyo = a.getAnime().getPremieredYear();
                                    } else {
                                        anyo = "?";
                                    }
                                    if (!a.getAnime().getEpisodes().equals("")) {
                                        info = a.getAnime().getEpisodes() + " ep, " + anyo;
                                    } else {
                                        info = "? ep, " + anyo;
                                    }
                                    try {
                                        InputStream is = (InputStream) new URL(a.getAnime().getMainPicture()).getContent();
                                        drawable = Drawable.createFromStream(is, "src name");

                                    } catch (IOException e) {
                                        drawable = getResources().getDrawable(R.drawable.ic_launcher_foreground);
                                    }

                                    Encapsulator e = new Encapsulator(a.getAnime(), drawable, check, a.getAnime().getTitle(), info, Integer.parseInt(a.getEpisodios()));
                                    if (!animes.contains(e)) {
                                        animes.add(e);
                                    }
                                }

                            }
                        }
                        recyclerView = view.findViewById(R.id.recycler_view);

                        listAdapter = new ListAdapter(user, animes, getContext(), R.layout.element_lista_usuario,"Anime" );
                        recyclerView.setAdapter(listAdapter);
                        layoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(layoutManager);

                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });




        }

        return view;
    }
}
