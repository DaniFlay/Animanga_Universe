package com.example.animanga_universe.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.animanga_universe.R;
import com.example.animanga_universe.activities.MenuPrincipal;
import com.example.animanga_universe.clases.AnimeUsuario;
import com.example.animanga_universe.clases.MangaUsuario;
import com.example.animanga_universe.clases.Usuario;
import com.google.android.material.tabs.TabLayout;

/**
 * El Fragment del Perfil, que muestra la información relevante del usuario y donde se puede cambiar la contraseña o editar el perfil
 * @author Daniel Seregin Kozlov
 */
public class PerfilFragment extends Fragment implements View.OnClickListener {
    TextView usuario, sexo, fechaNac, fechaReg, correo, editarPerfil, cambiarContraseña, todos, enProceso, completado, dejado, enLista, enEspera, viendo;
    TabLayout tabLayout;
    TableLayout tableLayout;
    Usuario user;
    int counter;
    MenuPrincipal menuPrincipal;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public PerfilFragment() {

    }

    /**
     * Se crea la instancia del fragment
     * @param param1 Parameter 1 creado automáticamente
     * @param param2 Parameter 2 creado automáticamente
     * @return Nueva instancia del Fragment
     */
    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
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
       View view= inflater.inflate(R.layout.fragment_perfil, container, false);
       menuPrincipal= (MenuPrincipal) getActivity();
       user= menuPrincipal.devolverUser();
       todos=view.findViewById(R.id.todos);
       counter=0;
       completado= view.findViewById(R.id.completados);
       enProceso= view.findViewById(R.id.enproceso);
       dejado= view.findViewById(R.id.dejado);
       enLista= view.findViewById(R.id.enlista);
       enEspera= view.findViewById(R.id.enespera);
       viendo= view.findViewById(R.id.viendo);
        usuario= view.findViewById(R.id.usuario);
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
        tabLayout.addTab(tabLayout.newTab().setText(R.string.manga));
        tableLayout= view.findViewById(R.id.tableLayout);
        editarPerfil.setOnClickListener(this);
        cambiarContraseña.setOnClickListener(this);
        viendo.setText(R.string.viendo);
        if(user.getAnimes()!=null){
            todos.setText(String.valueOf(user.getAnimes().size()));
            rellenarAnimes();
        }
//Dependiedo del tab seleccionado se muestran las estadísticas de los animes o de los mangas por sus estados
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 0) {
                    if(user.getAnimes()!=null){
                        rellenarAnimes();
                    }

                } else if (tab.getPosition() == 1) {
                        if(user.getMangas()!=null){
                            rellenarMangas();
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
    menuPrincipal.reemplazarFragment(new EditarPerfilFragment());
    //Si se pulsa el cambio de contraseña se cambia al fragment del cambio de contraseña
} else if (v.getId()== cambiarContraseña.getId()) {
    menuPrincipal.reemplazarFragment(new CambiarPasswordFragment());
}
    }

    /**
     * Esta función rellena la tabla con las estadísticas de los animes del usuario, mostradno todos los estados y el número de animes que tiene
     * de cada estado
     */
    public void rellenarAnimes(){
        viendo.setText(R.string.viendo);
        todos.setText(String.valueOf(user.getAnimes().size()));
        for(AnimeUsuario a: user.getAnimes()){
            if(a.getEstado().equals(getString(R.string.completado))){
                counter++;
            }
        }
        completado.setText(String.valueOf(counter));
        counter=0;
        for(AnimeUsuario a: user.getAnimes()){
            if(a.getEstado().equals(getString(R.string.enespera))){
                counter++;
            }
        }
        enEspera.setText(String.valueOf(counter));
        counter=0;
        for(AnimeUsuario a: user.getAnimes()){
            if(a.getEstado().equals(getString(R.string.dejado))){
                counter++;
            }
        }
        dejado.setText(String.valueOf(counter));
        counter=0;
        for(AnimeUsuario a: user.getAnimes()){
            if(a.getEstado().equals(getString(R.string.planeado))){
                counter++;
            }
        }
        enLista.setText(String.valueOf(counter));
        counter=0;
        for(AnimeUsuario a: user.getAnimes()){
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
            for(MangaUsuario m: user.getMangas()){
                if(m.getEstado().equals(getString(R.string.completado))){
                    counter++;
                }
            }
            completado.setText(String.valueOf(counter));
            counter=0;
            for(MangaUsuario m: user.getMangas()){
                if(m.getEstado().equals(getString(R.string.enespera))){
                    counter++;
                }
            }
            enEspera.setText(String.valueOf(counter));
            counter=0;
            for(MangaUsuario m: user.getMangas()){
                if(m.getEstado().equals(getString(R.string.dejado))){
                    counter++;
                }
            }
            dejado.setText(String.valueOf(counter));
            counter=0;
            for(MangaUsuario m: user.getMangas()){
                if(m.getEstado().equals(getString(R.string.planeado))){
                    counter++;
                }
            }
            enLista.setText(String.valueOf(counter));
            counter=0;
            for(MangaUsuario m: user.getMangas()){
                if(m.getEstado().equals(getString(R.string.leyendo))){
                    counter++;
                }
            }
            enProceso.setText(String.valueOf(counter));
            counter=0;
        }
    }
