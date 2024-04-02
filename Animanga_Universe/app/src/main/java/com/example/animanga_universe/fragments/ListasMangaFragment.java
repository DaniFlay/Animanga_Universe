package com.example.animanga_universe.fragments;

import android.annotation.SuppressLint;
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

import com.example.animanga_universe.R;
import com.example.animanga_universe.adaptadores.AdaptadorLista;
import com.example.animanga_universe.clases.AnimeUsuario;
import com.example.animanga_universe.clases.MangaUsuario;
import com.example.animanga_universe.clases.Usuario;
import com.example.animanga_universe.encapsuladores.Encapsulador;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListasMangaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListasMangaFragment extends Fragment {
    TabLayout tabLayout;
    Usuario user;
    View view;
    int check;
    ArrayList<Encapsulador> mangas;
    AdaptadorLista adaptadorLista;
    RecyclerView.LayoutManager layoutManager;
    Drawable drawable;
    RecyclerView recyclerView;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ListasMangaFragment() {
        // Required empty public constructor
    }

    public static ListasMangaFragment newInstance(String param1, String param2) {
        ListasMangaFragment fragment = new ListasMangaFragment();
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

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        user = getActivity().getIntent().getParcelableExtra("usuario");
        view = inflater.inflate(R.layout.fragment_listas_manga, container, false);
        tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.todos));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.leyendo));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.planeado));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.enespera));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.dejado));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.completado));
        mangas = new ArrayList<>();
        String anyo = "", info = "";
        if(user.getMangas()!=null){
            for (MangaUsuario m : user.getMangas()) {
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
                Encapsulador e = new Encapsulador(m.getManga(), drawable, check, m.getManga().getTitle(), info, Integer.parseInt(m.getCapitulos()));
                if (!mangas.contains(e)) {
                    mangas.add(e);
                }
                recyclerView = view.findViewById(R.id.recycler_view);

                adaptadorLista = new AdaptadorLista(user, mangas, getContext(), R.layout.element_lista_usuario, "Manga");
                recyclerView.setAdapter(adaptadorLista);
                layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @SuppressLint("UseCompatLoadingForDrawables")
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        mangas.clear();
                        String anyo = "", info = "";

                        if (tab.getPosition() == 0) {
                            for (MangaUsuario m : user.getMangas()) {
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
                                Encapsulador e = new Encapsulador(m.getManga(), drawable, check, m.getManga().getTitle(), info, Integer.parseInt(m.getCapitulos()));
                                if (!mangas.contains(e)) {
                                    mangas.add(e);
                                }

                            }
                        } else if (tab.getPosition() == 1) {
                            check = R.color.enProceso;
                            for (MangaUsuario m : user.getMangas()) {
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

                                    Encapsulador e = new Encapsulador(m.getManga(), drawable, check, m.getManga().getTitle(), info, Integer.parseInt(m.getCapitulos()));
                                    if (!mangas.contains(e)) {
                                        mangas.add(e);
                                    }
                                }

                            }
                        } else if (tab.getPosition() == 2) {
                            check = R.color.enlista;
                            for (MangaUsuario m : user.getMangas()) {
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

                                    Encapsulador e = new Encapsulador(m.getManga(), drawable, check, m.getManga().getTitle(), info, Integer.parseInt(m.getCapitulos()));
                                    if (!mangas.contains(e)) {
                                        mangas.add(e);
                                    }
                                }
                            }

                        } else if (tab.getPosition() == 3) {
                            check = R.color.espera;
                            for (MangaUsuario m : user.getMangas()) {
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

                                    Encapsulador e = new Encapsulador(m.getManga(), drawable, check, m.getManga().getTitle(), info, Integer.parseInt(m.getCapitulos()));
                                    if (!mangas.contains(e)) {
                                        mangas.add(e);
                                    }
                                }

                            }
                        } else if (tab.getPosition() == 4) {
                            check = R.color.dejado;
                            for (MangaUsuario m : user.getMangas()) {
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

                                    Encapsulador e = new Encapsulador(m.getManga(), drawable, check, m.getManga().getTitle(), info, Integer.parseInt(m.getCapitulos()));
                                    if (!mangas.contains(e)) {
                                        mangas.add(e);
                                    }
                                }

                            }
                        } else if (tab.getPosition() == 5) {
                            check = R.color.completado;
                            for (MangaUsuario m : user.getMangas()) {
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

                                    Encapsulador e = new Encapsulador(m.getManga(), drawable, check, m.getManga().getTitle(), info, Integer.parseInt(m.getCapitulos()));
                                    if (!mangas.contains(e)) {
                                        mangas.add(e);
                                    }
                                }

                            }
                        }
                        recyclerView = view.findViewById(R.id.recycler_view);

                        adaptadorLista = new AdaptadorLista(user, mangas, getContext(), R.layout.element_lista_usuario, "Manga");

                        recyclerView.setAdapter(adaptadorLista);
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