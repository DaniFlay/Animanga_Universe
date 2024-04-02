package com.example.animanga_universe.fragments;

import android.annotation.SuppressLint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.animanga_universe.R;
import com.example.animanga_universe.adaptadores.AdaptadorBusqueda;
import com.example.animanga_universe.clases.Anime;
import com.example.animanga_universe.clases.Manga;
import com.example.animanga_universe.clases.Usuario;
import com.example.animanga_universe.encapsuladores.Encapsulador;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    TextView cargando;
    ProgressBar progressBar;
    TabLayout tabLayout;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    CollectionReference cr;
    Usuario user;
    Drawable drawable;
    String busqueda,  info, rating;
    ArrayList<Encapsulador> animes, mangas;
    AdaptadorBusqueda adaptadorBusqueda;
    View view;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        view= inflater.inflate(R.layout.fragment_home, container, false);
        progressBar= view.findViewById(R.id.progressBar);
        cargando= view.findViewById(R.id.cargando);
        animes= new ArrayList<>();
        mangas= new ArrayList<>();

        tabLayout= view.findViewById(R.id.tab_layout);
        busqueda= "Anime";
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.anime)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.manga)));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){
                    busqueda= "Anime";
                    adaptadorBusqueda = new AdaptadorBusqueda(getActivity().getIntent().getParcelableExtra("usuario"), animes, getContext(), R.layout.element_busqueda, busqueda);
                    recyclerView.setAdapter(adaptadorBusqueda);
                    layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);
                }else if(tab.getPosition()==1){
                    busqueda="Manga";
                    adaptadorBusqueda = new AdaptadorBusqueda(getActivity().getIntent().getParcelableExtra("usuario"), mangas, getContext(), R.layout.element_busqueda, busqueda);
                    recyclerView.setAdapter(adaptadorBusqueda);
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
        recyclerView= view.findViewById(R.id.recycler_view);
        cr= FirebaseFirestore.getInstance().collection(busqueda);
        cr.orderBy("score", Query.Direction.DESCENDING).limit(100).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value!=null){
                    for(DocumentSnapshot d: value.getDocuments()){
                        Anime a = d.toObject(Anime.class);
                        String anyo="";
                        if(a!=null){
                            if(a.getPremieredYear()!=null&&!a.getPremieredYear().equals("")){
                                anyo=a.getPremieredYear();
                            }else {
                                anyo="?";
                            }
                            if(!a.getEpisodes().equals("")){
                                info= a.getEpisodes()+" ep, "+anyo;
                            }else {
                                info= "? ep, "+anyo;
                            }
                            try {
                                InputStream is= (InputStream) new URL(a.getMainPicture()).getContent();
                                drawable= Drawable.createFromStream(is,"src name");

                            } catch (IOException e) {
                                drawable= getResources().getDrawable(R.drawable.ic_launcher_foreground);
                            }
                            rating= String.valueOf(a.getScore());
                            Encapsulador e= new Encapsulador(a,drawable,R.color.pordefecto,a.getTitle(),info,rating);
                            if(!animes.contains(e)){
                                animes.add(e);
                            }
                            adaptadorBusqueda= new AdaptadorBusqueda(getActivity().getIntent().getParcelableExtra("usuario"),animes, getContext(), R.layout.element_busqueda,busqueda);

                            recyclerView.setAdapter(adaptadorBusqueda);
                            layoutManager= new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(layoutManager);
                            progressBar.setVisibility(View.GONE);
                            cargando.setText("");
                        }
                    }
                }
            }
        });
        cr= FirebaseFirestore.getInstance().collection("Manga");
        cr.orderBy("score",Query.Direction.DESCENDING).limit(100).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
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
                            Encapsulador e = new Encapsulador(m, drawable, R.color.pordefecto, m.getTitle(), info, rating);
                            if (!mangas.contains(e)) {
                                mangas.add(e);
                            }
                        }
                    }
                }
            }
        });
        if(getActivity()!= null){
            user= getActivity().getIntent().getParcelableExtra("usuario");
        }
        StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        return view;
    }
}