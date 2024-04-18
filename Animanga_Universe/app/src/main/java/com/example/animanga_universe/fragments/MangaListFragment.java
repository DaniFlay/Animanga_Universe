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
import com.example.animanga_universe.classes.MangaUser;
import com.example.animanga_universe.classes.User;
import com.example.animanga_universe.encapsulators.Encapsulator;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Fragment para mostrar las listas de los mangas del usuario filtrados por su estado
 * @author Daniel Seregin Kozlov
 * @noinspection ALL
 */
public class MangaListFragment extends Fragment {
    TabLayout tabLayout;
    User user;
    View view;
    int check;
    ArrayList<Encapsulator> mangas;
    ListAdapter listAdapter;
    RecyclerView.LayoutManager layoutManager;
    Drawable drawable;
    RecyclerView recyclerView;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public MangaListFragment() {

    }
    /**
     * Se crea la instancia del fragment
     * @param param1 Parameter 1 creado automáticamente
     * @param param2 Parameter 2 creado automáticamente
     * @return Nueva instancia del Fragment
     */
    public static MangaListFragment newInstance(String param1, String param2) {
        MangaListFragment fragment = new MangaListFragment();
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
        MainMenu mainMenu = (MainMenu) getActivity();
        if (mainMenu != null) {
            user= mainMenu.devolverUser();
        }
        view = inflater.inflate(R.layout.fragment_lists_manga, container, false);
        tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.todos));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.leyendo));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.planeado));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.enespera));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.dejado));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.completado));
        mangas = new ArrayList<>();
        String anyo, info;
        //Se hace el relleno de los encapsuladores para mostrar las listas que tiene el usuario
        if(user.getMangas()!=null){
            for (MangaUser m : user.getMangas()) {
                if (m.getManga().getPublishedFrom() != null && !m.getManga().getPublishedFrom().equals("")) {
                    anyo = m.getManga().getPublishedFrom().substring(m.getManga().getPublishedFrom().length()-4);
                } else {
                    anyo = "?";
                }
                if (m.getManga().getChapters() != null) {
                    info = m.getManga().getChapters() + " cap, " + anyo;
                } else {
                    info = "? cap, " + anyo;
                }
                try {
                    InputStream is = (InputStream) new URL(m.getManga().getMainPicture()).getContent();
                    drawable = Drawable.createFromStream(is, "src name");

                } catch (IOException e) {
                    drawable = getResources().getDrawable(R.drawable.ic_launcher_foreground);
                }
                if (m.getEstado().equals(getString(R.string.completado))) {
                    check = R.color.completado;
                } else if (m.getEstado().equals(getString(R.string.dejado))) {
                    check = R.color.dejado;
                } else if (m.getEstado().equals(getString(R.string.enespera))) {
                    check = R.color.espera;
                } else if (m.getEstado().equals(getString(R.string.leyendo))) {
                    check = R.color.enProceso;
                } else if (m.getEstado().equals(getString(R.string.planeado))) {
                    check = R.color.enlista;
                }
                Encapsulator e = new Encapsulator(m.getManga(), drawable, check, m.getManga().getTitle(), info, Integer.parseInt(m.getCapitulos()));
                if (!mangas.contains(e)) {
                    mangas.add(e);
                }
                recyclerView = view.findViewById(R.id.recycler_view);

                listAdapter = new ListAdapter(user, mangas, getContext(), R.layout.element_list_user, "Manga");
                listAdapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int posicion= recyclerView.getChildAdapterPosition(v);
                        ((MainMenu)getActivity()).getToggle().setVisibility(View.VISIBLE);
                        ((MainMenu)getActivity()).getSwitchButton().setVisibility(View.GONE);
                        ((MainMenu)getActivity()).setManga(mangas.get(posicion).getManga());
                        ((MainMenu)getActivity()).reemplazarFragment(new MangaDescriptionFragment());

                    }
                });
                recyclerView.setAdapter(listAdapter);
                layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    //Según el tab seleccionado se filtra la lista mostrada según su estado
                    @SuppressLint("UseCompatLoadingForDrawables")
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        mangas.clear();
                        String anyo, info;

                        if (tab.getPosition() == 0) {
                            for (MangaUser m : user.getMangas()) {
                                if (m.getManga().getPublishedFrom() != null && !m.getManga().getPublishedFrom().equals("")) {
                                    anyo = m.getManga().getPublishedFrom().substring(m.getManga().getPublishedFrom().length()-4);
                                } else {
                                    anyo = "?";
                                }
                                if (m.getManga().getChapters()!=null) {
                                    info = m.getCapitulos() + " cap, " + anyo;
                                } else {
                                    info = "? cap, " + anyo;
                                }
                                try {
                                    InputStream is = (InputStream) new URL(m.getManga().getMainPicture()).getContent();
                                    drawable = Drawable.createFromStream(is, "src name");

                                } catch (IOException e) {
                                    drawable = getResources().getDrawable(R.drawable.ic_launcher_foreground);
                                }
                                if (m.getEstado().equals(getString(R.string.completado))) {
                                    check = R.color.completado;
                                } else if (m.getEstado().equals(getString(R.string.dejado))) {
                                    check = R.color.dejado;
                                } else if (m.getEstado().equals(getString(R.string.enespera))) {
                                    check = R.color.espera;
                                } else if (m.getEstado().equals(getString(R.string.leyendo))) {
                                    check = R.color.enProceso;
                                } else if (m.getEstado().equals(getString(R.string.planeado))) {
                                    check = R.color.enlista;
                                }
                                Encapsulator e = new Encapsulator(m.getManga(), drawable, check, m.getManga().getTitle(), info, Integer.parseInt(m.getCapitulos()));
                                if (!mangas.contains(e)) {
                                    mangas.add(e);
                                }

                            }
                        } else if (tab.getPosition() == 1) {
                            check = R.color.enProceso;
                            for (MangaUser m : user.getMangas()) {
                                if (m.getEstado().equals(getString(R.string.leyendo))) {
                                    if (m.getManga().getPublishedFrom() != null && !m.getManga().getPublishedFrom().equals("")) {
                                        anyo = m.getManga().getPublishedFrom().substring(m.getManga().getPublishedFrom().length()-4);
                                    } else {
                                        anyo = "?";
                                    }
                                    if (m.getManga().getChapters()!=null) {
                                        info = m.getManga().getChapters() + " cap, " + anyo;
                                    } else {
                                        info = "? cap, " + anyo;
                                    }
                                    try {
                                        InputStream is = (InputStream) new URL(m.getManga().getMainPicture()).getContent();
                                        drawable = Drawable.createFromStream(is, "src name");

                                    } catch (IOException e) {
                                        drawable = getResources().getDrawable(R.drawable.ic_launcher_foreground);
                                    }

                                    Encapsulator e = new Encapsulator(m.getManga(), drawable, check, m.getManga().getTitle(), info, Integer.parseInt(m.getCapitulos()));
                                    if (!mangas.contains(e)) {
                                        mangas.add(e);
                                    }
                                }

                            }
                        } else if (tab.getPosition() == 2) {
                            check = R.color.enlista;
                            for (MangaUser m : user.getMangas()) {
                                if (m.getEstado().equals(getString(R.string.planeado))) {
                                    if (m.getManga().getPublishedFrom() != null && !m.getManga().getPublishedFrom().equals("")) {
                                        anyo = m.getManga().getPublishedFrom().substring(m.getManga().getPublishedFrom().length()-4);
                                    } else {
                                        anyo = "?";
                                    }
                                    if (m.getManga().getChapters()!=null) {
                                        info = m.getManga().getChapters() + " cap, " + anyo;
                                    } else {
                                        info = "? cap, " + anyo;
                                    }
                                    try {
                                        InputStream is = (InputStream) new URL(m.getManga().getMainPicture()).getContent();
                                        drawable = Drawable.createFromStream(is, "src name");

                                    } catch (IOException e) {
                                        drawable = getResources().getDrawable(R.drawable.ic_launcher_foreground);
                                    }

                                    Encapsulator e = new Encapsulator(m.getManga(), drawable, check, m.getManga().getTitle(), info, Integer.parseInt(m.getCapitulos()));
                                    if (!mangas.contains(e)) {
                                        mangas.add(e);
                                    }
                                }
                            }

                        } else if (tab.getPosition() == 3) {
                            check = R.color.espera;
                            for (MangaUser m : user.getMangas()) {
                                if (m.getEstado().equals(getString(R.string.enespera))) {
                                    if (m.getManga().getPublishedFrom() != null && !m.getManga().getPublishedFrom().equals("")) {
                                        anyo = m.getManga().getPublishedFrom().substring(m.getManga().getPublishedFrom().length()-4);
                                    } else {
                                        anyo = "?";
                                    }
                                    if (m.getManga().getChapters()!=null) {
                                        info = m.getManga().getChapters() + " cap, " + anyo;
                                    } else {
                                        info = "? cap, " + anyo;
                                    }
                                    try {
                                        InputStream is = (InputStream) new URL(m.getManga().getMainPicture()).getContent();
                                        drawable = Drawable.createFromStream(is, "src name");

                                    } catch (IOException e) {
                                        drawable = getResources().getDrawable(R.drawable.ic_launcher_foreground);
                                    }

                                    Encapsulator e = new Encapsulator(m.getManga(), drawable, check, m.getManga().getTitle(), info, Integer.parseInt(m.getCapitulos()));
                                    if (!mangas.contains(e)) {
                                        mangas.add(e);
                                    }
                                }

                            }
                        } else if (tab.getPosition() == 4) {
                            check = R.color.dejado;
                            for (MangaUser m : user.getMangas()) {
                                if (m.getEstado().equals(getString(R.string.dejado))) {
                                    if (m.getManga().getPublishedFrom() != null && !m.getManga().getPublishedFrom().equals("")) {
                                        anyo = m.getManga().getPublishedFrom().substring(m.getManga().getPublishedFrom().length()-4);
                                    } else {
                                        anyo = "?";
                                    }
                                    if (m.getManga().getChapters()!=null) {
                                        info = m.getManga().getChapters() + " cap, " + anyo;
                                    } else {
                                        info = "? cap, " + anyo;
                                    }
                                    try {
                                        InputStream is = (InputStream) new URL(m.getManga().getMainPicture()).getContent();
                                        drawable = Drawable.createFromStream(is, "src name");

                                    } catch (IOException e) {
                                        drawable = getResources().getDrawable(R.drawable.ic_launcher_foreground);
                                    }

                                    Encapsulator e = new Encapsulator(m.getManga(), drawable, check, m.getManga().getTitle(), info, Integer.parseInt(m.getCapitulos()));
                                    if (!mangas.contains(e)) {
                                        mangas.add(e);
                                    }
                                }

                            }
                        } else if (tab.getPosition() == 5) {
                            check = R.color.completado;
                            for (MangaUser m : user.getMangas()) {
                                if (m.getEstado().equals(getString(R.string.completado))) {
                                    if (m.getManga().getPublishedFrom() != null && !m.getManga().getPublishedFrom().equals("")) {
                                        anyo = m.getManga().getPublishedFrom().substring(m.getManga().getPublishedFrom().length()-4);
                                    } else {
                                        anyo = "?";
                                    }
                                    if (m.getManga().getChapters()!=null) {
                                        info = m.getManga().getChapters() + " cap, " + anyo;
                                    } else {
                                        info = "? cap, " + anyo;
                                    }
                                    try {
                                        InputStream is = (InputStream) new URL(m.getManga().getMainPicture()).getContent();
                                        drawable = Drawable.createFromStream(is, "src name");

                                    } catch (IOException e) {
                                        drawable = getResources().getDrawable(R.drawable.ic_launcher_foreground);
                                    }

                                    Encapsulator e = new Encapsulator(m.getManga(), drawable, check, m.getManga().getTitle(), info, Integer.parseInt(m.getCapitulos()));
                                    if (!mangas.contains(e)) {
                                        mangas.add(e);
                                    }
                                }

                            }
                        }
                        recyclerView = view.findViewById(R.id.recycler_view);

                        listAdapter = new ListAdapter(user, mangas, getContext(), R.layout.element_list_user, "Manga");
                        listAdapter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int posicion= recyclerView.getChildAdapterPosition(v);
                                ((MainMenu)getActivity()).getToggle().setVisibility(View.VISIBLE);
                                ((MainMenu)getActivity()).getSwitchButton().setVisibility(View.GONE);
                                ((MainMenu)getActivity()).setManga(mangas.get(posicion).getManga());
                                ((MainMenu)getActivity()).reemplazarFragment(new MangaDescriptionFragment());
                            }
                        });

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
        }

        return view;
    }
}