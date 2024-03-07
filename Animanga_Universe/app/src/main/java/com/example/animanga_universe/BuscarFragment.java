package com.example.animanga_universe;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.provider.Telephony;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.animanga_universe.clases.Anime;
import com.example.animanga_universe.clases.Manga;
import com.example.animanga_universe.clases.Usuario;
import com.google.android.material.search.SearchBar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BuscarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuscarFragment extends Fragment {
    View view;
    SearchView searchView;
    TabLayout tabLayout;
    RecyclerView recyclerView;
    DatabaseReference ref;
    Usuario user;
    int color;
    String info, rating, nombre;

    Drawable drawable;
    ArrayList<Encapsulador> animes, mangas;
    ArrayList<EncapsuladorPersonaje> personajes;
    AdaptadorBusqueda adaptadorBusqueda;
    RecyclerView.LayoutManager layoutManager;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BuscarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BuscarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BuscarFragment newInstance(String param1, String param2) {
        BuscarFragment fragment = new BuscarFragment();
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
        animes= new ArrayList<>();
        mangas= new ArrayList<>();
        personajes= new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_buscar, container, false);
        searchView= view.findViewById(R.id.search_view);
        tabLayout= view.findViewById(R.id.tab_layout);
        recyclerView= view.findViewById(R.id.recycler_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                nombre= query;
                if(getActivity()!= null){
                    user= getActivity().getIntent().getParcelableExtra("usuario");
                }
                StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                ref=  FirebaseDatabase.getInstance().getReference("Manga").child(nombre);
                ref.orderByChild("title").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Manga m= snapshot.getValue(Manga.class);
                        Log.d("mangaaaa",m.toString());
                        try {
                            InputStream is= (InputStream) new URL(m.getMain_picture()).getContent();
                            drawable= Drawable.createFromStream(is,"src name");

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        char[] fecha= m.getPublished_from().toCharArray();
                        String anyo="";
                        for(int i=fecha.length-1;i>fecha.length-5;i--){
                            anyo+=fecha[i];
                        }

                        info = m.getChapters() + " ch," + anyo;
                        rating= String.valueOf(m.getScore());
                        mangas.add(new Encapsulador(drawable,R.color.pordefecto,m.getTitle(),info,rating));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


      /*  ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d: snapshot.getChildren()){
                    Anime a = d.getValue(Anime.class);
                    color= R.color.pordefecto;
                    if(a.getTitle().toLowerCase().contains(nombre.toLowerCase())){
                        if(user.getAnimesEspera()!=null){
                            if(user.getAnimesEspera().contains(a)){
                                color= R.color.espera;
                            }
                        }else{
                            color= R.color.pordefecto;
                        }
                        if(user.getAnimesDejados()!=null){
                            if(user.getAnimesDejados().contains(a)){
                                color= R.color.dejado;
                            }
                        }else{
                            color= R.color.pordefecto;
                        }
                        if(user.getAnimesPlaneados()!=null){
                            if(user.getAnimesPlaneados().contains(a)){
                                color= R.color.enlista;
                            }
                        }else{
                            color= R.color.pordefecto;
                        }
                        if(user.getAnimesViendo()!=null){
                            if(user.getAnimesViendo().contains(a)){
                                color= R.color.enProceso;
                            }
                        }else{
                            color= R.color.pordefecto;
                        }if(user.getAnimesVistos()!=null){
                            if(user.getAnimesVistos().contains(a)){
                                color= R.color.completado;
                            }
                        }else{
                            color= R.color.pordefecto;
                        }
                        if(a!=null) {
                            try {
                                InputStream is= (InputStream) new URL(a.getMain_picture()).getContent();
                                drawable= Drawable.createFromStream(is,"src name");

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            info = a.getEpisodes() + " ep," + a.getPremiered_season() + " " + a.getPremiered_year();
                            rating= String.valueOf(a.getScore());
                            Log.d("info", drawable.toString()+"  "+ a.toString());
                            animes.add(new Encapsulador(drawable,color,a.getTitle(),info, rating));
                        }

                    }
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
        adaptadorBusqueda= new AdaptadorBusqueda(mangas, getContext(), R.layout.element_busqueda);
        recyclerView.setAdapter(adaptadorBusqueda);
        layoutManager= new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        Log.d("longitud",mangas.size()+"");
        return view;

    }

}