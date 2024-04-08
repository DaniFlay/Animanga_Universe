package com.example.animanga_universe.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.animanga_universe.activities.MainMenu;
import com.example.animanga_universe.adapters.SearchAdapter;
import com.example.animanga_universe.R;
import com.example.animanga_universe.classes.Anime;
import com.example.animanga_universe.classes.Manga;
import com.example.animanga_universe.classes.User;
import com.example.animanga_universe.encapsulators.Encapsulator;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Fragment de búsqueda, contiene una barra de búsqueda para buscar el anime/manga y 2 tabs para una búsqueda separada de animes y mangas
 * Al buscar aparecerá un recycler view con los animes/mangas que contienen lo introducido
 * @noinspection ALL
 */
public class SearchFragment extends Fragment  {
    View view;
    SearchView searchView;
    TabLayout tabLayout;
    RecyclerView recyclerView;
    CollectionReference cr;
    User user;
    String info, rating, nombre, busqueda;
    AppCompatImageButton boton;
    Drawable drawable;
    ArrayList<Encapsulator> animes;
    SearchAdapter searchAdapter;
    RecyclerView.LayoutManager layoutManager;
    MainMenu mainMenu;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public SearchFragment() {

    }

    /**
     * Se crea la instancia del fragment
     * @param param1 Parameter 1 creado automáticamente
     * @param param2 Parameter 2 creado automáticamente
     * @return Nueva instancia del Fragment
     */

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        busqueda="Anime";
        view =inflater.inflate(R.layout.fragment_buscar, container, false);
        searchView= view.findViewById(R.id.search_view);
        tabLayout= view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.anime)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.manga)));
        boton= view.findViewById(R.id.boton);
        mainMenu = (MainMenu)getActivity();
        //se cambia la variable de busqueda al cambiar de tab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                animes.clear();
                if(tab.getPosition()==0){
                    busqueda="Anime";
                } else if (tab.getPosition()==1) {
                    busqueda = "Manga";
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        recyclerView= view.findViewById(R.id.recycler_view);
        animes= new ArrayList<>();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                animes.clear();
                nombre="";
                String[] palabras = query.split(" ");
                //Se realiza un cambio de lo introducido, se coge palabra por palabra y y se pone en mayúsucla la primera letra de cada palabra y en minúsculas el resto
                //para realizar la búsqueda en la base de datos
                for(String s: palabras){
                    nombre+= s.substring(0,1).toUpperCase()+s.substring(1).toLowerCase()+" ";
                }
                nombre=nombre.trim();
                if(busqueda.equals(getString(R.string.anime))){
                    cr=FirebaseFirestore.getInstance().collection(busqueda);
                    //Se busca por titulo y que contenga lo introducido
                    cr.orderBy("title").startAt(nombre).endAt(nombre+"\uf8ff").addSnapshotListener((value, error) -> {
                        if (value != null) {
                            //Se recogen los datos necesarios para crear el encapsulador
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
                                        drawable = getResources().getDrawable(R.drawable.ic_launcher_foreground);
                                    }
                                    rating = String.valueOf(a.getScore());
                                    Encapsulator e = new Encapsulator(a, drawable, R.color.pordefecto, a.getTitle(), info, rating);
                                    if (!animes.contains(e)) {
                                        animes.add(e);
                                    }
                                    searchAdapter = new SearchAdapter(mainMenu.devolverUser(), animes, getContext(), R.layout.element_busqueda, busqueda);

                                    recyclerView.setAdapter(searchAdapter);
                                    layoutManager = new LinearLayoutManager(getContext());
                                    recyclerView.setLayoutManager(layoutManager);
                                }

                            }


                        }
                    });
                    //Se hace el mismo proceso para los mangas, pero cambian algunos atributos en el encapsulador, y se realiza la búsqueda en una tabla
                    //diferente en la base de datos
                } else if (busqueda.equals(getString(R.string.manga))) {
                    CollectionReference cr=FirebaseFirestore.getInstance().collection(busqueda);
                    cr.orderBy("title").startAt(nombre).endAt(nombre+"\uf8ff").addSnapshotListener((value, error) -> {
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
                                        drawable = getResources().getDrawable(R.drawable.ic_launcher_foreground);
                                    }
                                    rating = String.valueOf(m.getScore());
                                    Encapsulator e = new Encapsulator(m, drawable, R.color.pordefecto, m.getTitle(), info, rating);
                                    if (!animes.contains(e)) {
                                        animes.add(e);
                                    }
                                    searchAdapter = new SearchAdapter(mainMenu.devolverUser(), animes, getContext(), R.layout.element_busqueda, busqueda);

                                    recyclerView.setAdapter(searchAdapter);
                                    layoutManager = new LinearLayoutManager(getContext());
                                    recyclerView.setLayoutManager(layoutManager);
                                }


                            }


                        }
                    });
                }


                if(getActivity()!= null){
                    user= getActivity().getIntent().getParcelableExtra("usuario");
                }
                StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return view;

    }


}