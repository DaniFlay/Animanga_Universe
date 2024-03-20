package com.example.animanga_universe.fragments;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.animanga_universe.R;
import com.example.animanga_universe.adaptadores.AdaptadorLista;
import com.example.animanga_universe.clases.AnimeUsuario;
import com.example.animanga_universe.clases.Usuario;
import com.example.animanga_universe.encapsuladores.Encapsulador;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListasAnimeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListasAnimeFragment extends Fragment {
    TabLayout tabLayout;
    Usuario user;
    View view;
    String check;
    ArrayList<Encapsulador> animes;
    AdaptadorLista adaptadorLista;
    RecyclerView.LayoutManager layoutManager;
    Drawable drawable;
    RecyclerView recyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListasAnimeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListasAnimeFragment newInstance(String param1, String param2) {
        ListasAnimeFragment fragment = new ListasAnimeFragment();
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
        user= getActivity().getIntent().getParcelableExtra("usuario");
        view = inflater.inflate(R.layout.fragment_anime_listas,container,false);
        tabLayout= view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.todos));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.viendo));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.planeado));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.enespera));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.dejado));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.completado));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String anyo="",info="", rating="";

                if(tab.getPosition()==0){
                    for(AnimeUsuario a: user.getAnimes()){
                        if(a.getAnime().getPremieredYear()!=null&&!a.getAnime().getPremieredYear().equals("")){
                            anyo= a.getAnime().getPremieredYear();
                        }else{
                            anyo="?";
                        }
                        if(!a.getAnime().getEpisodes().equals("")){
                            info = a.getAnime().getEpisodes() + " ep, " + anyo;
                        }else{
                            info= "? ep, "+anyo;
                        }
                        try {
                            InputStream is= (InputStream) new URL(a.getAnime().getMainPicture()).getContent();
                            drawable= Drawable.createFromStream(is,"src name");

                        } catch (IOException e) {
                            drawable= getResources().getDrawable(R.drawable.ic_launcher_foreground);
                        }
                        rating= String.valueOf(a.getAnime().getScore());
                        Encapsulador e= new Encapsulador(a.getAnime(),drawable,R.color.pordefecto,a.getAnime().getTitle(),info,Integer.parseInt(a.getEpisodios()));
                        if(!animes.contains(e)){
                            animes.add(e);
                        }
                    }
                } else if (tab.getPosition()==1) {
                    for(AnimeUsuario a: user.getAnimes()){
                        if(a.getEstado().equals(getString(R.string.viendo))){
                            if(a.getAnime().getPremieredYear()!=null&&!a.getAnime().getPremieredYear().equals("")){
                                anyo= a.getAnime().getPremieredYear();
                            }else{
                                anyo="?";
                            }
                            if(!a.getAnime().getEpisodes().equals("")){
                                info = a.getAnime().getEpisodes() + " ep, " + anyo;
                            }else{
                                info= "? ep, "+anyo;
                            }
                            try {
                                InputStream is= (InputStream) new URL(a.getAnime().getMainPicture()).getContent();
                                drawable= Drawable.createFromStream(is,"src name");

                            } catch (IOException e) {
                                drawable= getResources().getDrawable(R.drawable.ic_launcher_foreground);
                            }
                            rating= String.valueOf(a.getAnime().getScore());
                            Encapsulador e= new Encapsulador(a.getAnime(),drawable,R.color.pordefecto,a.getAnime().getTitle(),info,Integer.parseInt(a.getEpisodios()));
                            if(!animes.contains(e)){
                                animes.add(e);
                            }
                        }

                    }
                }else if (tab.getPosition()==2) {
                    for(AnimeUsuario a: user.getAnimes()){
                        if(a.getEstado().equals(getString(R.string.planeado))){
                            if(a.getAnime().getPremieredYear()!=null&&!a.getAnime().getPremieredYear().equals("")){
                                anyo= a.getAnime().getPremieredYear();
                            }else{
                                anyo="?";
                            }
                            if(!a.getAnime().getEpisodes().equals("")){
                                info = a.getAnime().getEpisodes() + " ep, " + anyo;
                            }else{
                                info= "? ep, "+anyo;
                            }
                            try {
                                InputStream is= (InputStream) new URL(a.getAnime().getMainPicture()).getContent();
                                drawable= Drawable.createFromStream(is,"src name");

                            } catch (IOException e) {
                                drawable= getResources().getDrawable(R.drawable.ic_launcher_foreground);
                            }
                            rating= String.valueOf(a.getAnime().getScore());
                            Encapsulador e= new Encapsulador(a.getAnime(),drawable,R.color.pordefecto,a.getAnime().getTitle(),info,Integer.parseInt(a.getEpisodios()));
                            if(!animes.contains(e)){
                                animes.add(e);
                            }
                        }
                        }

                }
                else if (tab.getPosition()==3) {
                    for(AnimeUsuario a: user.getAnimes()){
                        if(a.getEstado().equals(getString(R.string.enespera))){
                            if(a.getAnime().getPremieredYear()!=null&&!a.getAnime().getPremieredYear().equals("")){
                                anyo= a.getAnime().getPremieredYear();
                            }else{
                                anyo="?";
                            }
                            if(!a.getAnime().getEpisodes().equals("")){
                                info = a.getAnime().getEpisodes() + " ep, " + anyo;
                            }else{
                                info= "? ep, "+anyo;
                            }
                            try {
                                InputStream is= (InputStream) new URL(a.getAnime().getMainPicture()).getContent();
                                drawable= Drawable.createFromStream(is,"src name");

                            } catch (IOException e) {
                                drawable= getResources().getDrawable(R.drawable.ic_launcher_foreground);
                            }
                            rating= String.valueOf(a.getAnime().getScore());
                            Encapsulador e= new Encapsulador(a.getAnime(),drawable,R.color.pordefecto,a.getAnime().getTitle(),info,Integer.parseInt(a.getEpisodios()));
                            if(!animes.contains(e)){
                                animes.add(e);
                            }
                        }

                    }
                }
                else if (tab.getPosition()==4) {
                    for(AnimeUsuario a: user.getAnimes()){
                        if(a.getEstado().equals(getString(R.string.dejado))){
                            if(a.getAnime().getPremieredYear()!=null&&!a.getAnime().getPremieredYear().equals("")){
                                anyo= a.getAnime().getPremieredYear();
                            }else{
                                anyo="?";
                            }
                            if(!a.getAnime().getEpisodes().equals("")){
                                info = a.getAnime().getEpisodes() + " ep, " + anyo;
                            }else{
                                info= "? ep, "+anyo;
                            }
                            try {
                                InputStream is= (InputStream) new URL(a.getAnime().getMainPicture()).getContent();
                                drawable= Drawable.createFromStream(is,"src name");

                            } catch (IOException e) {
                                drawable= getResources().getDrawable(R.drawable.ic_launcher_foreground);
                            }
                            rating= String.valueOf(a.getAnime().getScore());
                            Encapsulador e= new Encapsulador(a.getAnime(),drawable,R.color.pordefecto,a.getAnime().getTitle(),info,Integer.parseInt(a.getEpisodios()));
                            if(!animes.contains(e)){
                                animes.add(e);
                            }
                        }

                    }
                }
                else if (tab.getPosition()==5) {
                    for(AnimeUsuario a: user.getAnimes()){
                        if(a.getEstado().equals(getString(R.string.completado))){
                            if(a.getAnime().getPremieredYear()!=null&&!a.getAnime().getPremieredYear().equals("")){
                                anyo= a.getAnime().getPremieredYear();
                            }else{
                                anyo="?";
                            }
                            if(!a.getAnime().getEpisodes().equals("")){
                                info = a.getAnime().getEpisodes() + " ep, " + anyo;
                            }else{
                                info= "? ep, "+anyo;
                            }
                            try {
                                InputStream is= (InputStream) new URL(a.getAnime().getMainPicture()).getContent();
                                drawable= Drawable.createFromStream(is,"src name");

                            } catch (IOException e) {
                                drawable= getResources().getDrawable(R.drawable.ic_launcher_foreground);
                            }
                            rating= String.valueOf(a.getAnime().getScore());
                            Encapsulador e= new Encapsulador(a.getAnime(),drawable,R.color.pordefecto,a.getAnime().getTitle(),info,Integer.parseInt(a.getEpisodios()));
                            if(!animes.contains(e)){
                                animes.add(e);
                            }
                        }

                    }
                }
                recyclerView=view.findViewById(R.id.recycler_view);

                adaptadorLista= new AdaptadorLista(user,animes,getContext(),R.layout.element_lista_usuario,"Anime");
                recyclerView.setAdapter(adaptadorLista);
                layoutManager= new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
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
}