package com.example.animanga_universe.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.animanga_universe.R;
import com.example.animanga_universe.activities.MainMenu;
import com.example.animanga_universe.adapters.SearchAdapter;
import com.example.animanga_universe.classes.Anime;
import com.example.animanga_universe.classes.Manga;
import com.example.animanga_universe.classes.User;
import com.example.animanga_universe.encapsulators.Encapsulator;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Es el fragment "principal" que aparece al arrancar la actividad, aparecen los rankings de animes y mangas, los 10 animes y mangas mejor valorados
 * @author Daniel Seregin Kozlov
 * @noinspection ALL
 */
public class RankingFragment extends Fragment {
    TextView cargando;
    ProgressBar progressBar;
    TabLayout tabLayout;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    CollectionReference cr;
    User user;
    Drawable drawable;
    String busqueda,  info, rating;
    SearchAdapter searchAdapter;
    View view;
    MainMenu mainMenu;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public RankingFragment() {
    }
    /**
     * Se crea la instancia del fragment
     * @param param1 Parameter 1 creado automáticamente
     * @param param2 Parameter 2 creado automáticamente
     * @return Nueva instancia del Fragment
     */
    public static RankingFragment newInstance(String param1, String param2) {
        RankingFragment fragment = new RankingFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(getContext()!=null) {
            view = inflater.inflate(R.layout.fragment_ranking, container, false);
            progressBar = view.findViewById(R.id.progressBar);
            cargando = view.findViewById(R.id.cargando);
            progressBar.setVisibility(View.VISIBLE);
            mainMenu = (MainMenu) getContext();
            user = mainMenu.devolverUser();
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            tabLayout = view.findViewById(R.id.tab_layout);
            busqueda = "Anime";
            tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.anime)));
            tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.manga)));

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                //Dependiendo del tab elegido se muestra el top de animes o de mangas
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    if (tab.getPosition() == 0) {
                        busqueda = "Anime";
                        searchAdapter = new SearchAdapter(mainMenu.devolverUser(), mainMenu.getAnimes(), getContext(), R.layout.element_busqueda, busqueda);
                        recyclerView.setAdapter(searchAdapter);
                        searchAdapter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int posicion= recyclerView.getChildAdapterPosition(v);
                                ((MainMenu)getActivity()).setAnime(mainMenu.getAnimes().get(posicion).getAnime());
                                ((MainMenu)getActivity()).reemplazarFragment(new AnimeDescriptionFragment());
                            }
                        });
                        layoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(layoutManager);
                    } else if (tab.getPosition() == 1) {
                        busqueda = "Manga";
                        searchAdapter = new SearchAdapter(mainMenu.devolverUser(), mainMenu.getMangas(), getContext(), R.layout.element_busqueda, busqueda);
                        searchAdapter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int posicion= recyclerView.getChildAdapterPosition(v);
                                ((MainMenu)getActivity()).setManga(mainMenu.getMangas().get(posicion).getManga());
                                ((MainMenu)getActivity()).reemplazarFragment(new MangaDescriptionFragment());
                            }
                        });
                        recyclerView.setAdapter(searchAdapter);
                        layoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(layoutManager);
                    }

                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
            recyclerView = view.findViewById(R.id.recycler_view);
            //Se realiza la búsqueda en la base de datos de los anines, ordenados en el orden descendiente, y se sacan los 10 primeros, y luego se hace el relleno
            //del encapsulador para mostrar la lista
            if (mainMenu.getAnimes().isEmpty()) {
                cr = FirebaseFirestore.getInstance().collection(busqueda);
                cr.orderBy("score", Query.Direction.DESCENDING).limit(10).addSnapshotListener((value, error) -> {
                    if (value != null) {
                        for (DocumentSnapshot d : value.getDocuments()) {
                            Anime a = d.toObject(Anime.class);
                            String anyo = "";
                            if (a != null) {
                                if (a.getPremieredYear() != null && !a.getPremieredYear().equals("")) {
                                    anyo = a.getPremieredYear();
                                } else {
                                    anyo = "?";
                                }
                                if (!a.getEpisodes().equals("")) {
                                    info = a.getEpisodes() + " ep, " + anyo;
                                } else {
                                    info = "? ep, " + anyo;
                                }
                                try {
                                    InputStream is = (InputStream) new URL(a.getMainPicture()).getContent();
                                    drawable = Drawable.createFromStream(is, "src name");

                                } catch (IOException e) {
                                    drawable = mainMenu.getResources().getDrawable(R.drawable.ic_launcher_foreground);
                                }
                                rating = String.valueOf(a.getScore());
                                Encapsulator e = new Encapsulator(a, drawable, R.color.pordefecto, a.getTitle(), info, rating);
                                if (!mainMenu.getAnimes().contains(e)) {
                                    mainMenu.getAnimes().add(e);
                                }
                                searchAdapter = new SearchAdapter(mainMenu.devolverUser(), mainMenu.getAnimes(), getContext(), R.layout.element_busqueda, busqueda);
                                searchAdapter.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        int posicion= recyclerView.getChildAdapterPosition(v);
                                        ((MainMenu)getActivity()).setAnime(mainMenu.getAnimes().get(posicion).getAnime());
                                        ((MainMenu)getActivity()).reemplazarFragment(new AnimeDescriptionFragment());
                                    }
                                });
                                recyclerView.setAdapter(searchAdapter);
                                layoutManager = new LinearLayoutManager(getContext());
                                recyclerView.setLayoutManager(layoutManager);
                                progressBar.setVisibility(View.GONE);
                                cargando.setText("");
                            }
                        }
                    }
                });

            }else {
                searchAdapter = new SearchAdapter(mainMenu.devolverUser(), mainMenu.getAnimes(), getContext(), R.layout.element_busqueda, busqueda);
                searchAdapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int posicion= recyclerView.getChildAdapterPosition(v);
                        ((MainMenu)getActivity()).setAnime(mainMenu.getAnimes().get(posicion).getAnime());
                        ((MainMenu)getActivity()).reemplazarFragment(new AnimeDescriptionFragment());
                    }
                });
                recyclerView.setAdapter(searchAdapter);
                layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                progressBar.setVisibility(View.GONE);
                cargando.setText("");
            }
            //Se hace el mismo proceso con los mangas
            if (mainMenu.getMangas().isEmpty()) {
                cr = FirebaseFirestore.getInstance().collection("Manga");
                cr.orderBy("score", Query.Direction.DESCENDING).limit(10).addSnapshotListener((value, error) -> {
                    if (value != null) {
                        for (DocumentSnapshot d : value.getDocuments()) {
                            Manga m = d.toObject(Manga.class);
                            String anyo = "";
                            if (m != null) {
                                if (m.getPublishedFrom() == null) {
                                    anyo = "?";
                                } else {
                                    anyo = m.getPublishedFrom().substring(m.getPublishedFrom().length() - 4);
                                }
                                if (m.getChapters() != null) {
                                    info = m.getChapters() + " cap, " + anyo;
                                } else {
                                    info = "? cap, " + anyo;
                                }
                                try {
                                    InputStream is = (InputStream) new URL(m.getMainPicture()).getContent();
                                    drawable = Drawable.createFromStream(is, "src name");

                                } catch (IOException e) {
                                    drawable = mainMenu.getResources().getDrawable(R.drawable.ic_launcher_foreground);
                                }
                                rating = String.valueOf(m.getScore());
                                Encapsulator e = new Encapsulator(m, drawable, R.color.pordefecto, m.getTitle(), info, rating);
                                if (!mainMenu.getMangas().contains(e)) {
                                    mainMenu.getMangas().add(e);
                                }
                            }
                        }
                    }
                });
            }
        }
        return view;
    }
}