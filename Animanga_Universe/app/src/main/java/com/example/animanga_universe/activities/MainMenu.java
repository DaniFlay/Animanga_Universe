package com.example.animanga_universe.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import com.example.animanga_universe.R;
import com.example.animanga_universe.classes.User;
import com.example.animanga_universe.databinding.ActivityMenuPrincipalBinding;
import com.example.animanga_universe.encapsulators.Encapsulator;
import com.example.animanga_universe.extras.Helper;
import com.example.animanga_universe.fragments.SearchFragment;
import com.example.animanga_universe.fragments.ForumsFragment;
import com.example.animanga_universe.fragments.RankingFragment;
import com.example.animanga_universe.fragments.AnimeListFragment;
import com.example.animanga_universe.fragments.MangaListFragment;
import com.example.animanga_universe.fragments.ProfileFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * La clase Menu Principal que es la actividad principal de la aplicación, y que contiene la navegación entre los fragments de la aplicación
 * También contiene varias funciones y variables que se estarán utilizando en los fragments
 * @author Daniel Seregin Kozlov
 */
public class MainMenu extends AppCompatActivity {
    User user;
    ActivityMenuPrincipalBinding binding;
    SQLiteDatabase db;
    Helper helper;
    Encapsulator e;
    String busqueda;
    ArrayList<Encapsulator> animes;
    ArrayList<Encapsulator> mangas;

    /**
     * funcion onCreate que se utiliza para crear la actividad
     * @param savedInstanceState En el caso de que se reinicie la actvidad es el Budnle que contiene la información goardada
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Hola","Hola");
        helper= new Helper(this, "bbdd",null,1);
        db= helper.getWritableDatabase();
        user= getIntent().getParcelableExtra("usuario");
        binding= ActivityMenuPrincipalBinding.inflate(getLayoutInflater());
        binding.toolBar.setTitleTextAppearance(this, R.style.NarutoFont);
        setContentView(binding.getRoot());
        animes= new ArrayList<>();
        mangas=new ArrayList<>();
        //Listener para la navegación entre framents
        binding.bottonNavigationView.setOnItemSelectedListener(item -> {
            int id= item.getItemId();
            if(id==R.id.ranking){
                binding.switchButton.setVisibility(View.GONE);
                reemplazarFragment(new RankingFragment());
            }else if(id== R.id.forums){
                binding.switchButton.setVisibility(View.GONE);
                reemplazarFragment(new ForumsFragment());

            }else if(id== R.id.buscar){
                binding.switchButton.setVisibility(View.GONE);
                reemplazarFragment(new SearchFragment());

            } else if (id== R.id.listas) {
                binding.switchButton.setVisibility(View.VISIBLE);
                binding.switchButton.setChecked(false);

                reemplazarFragment(new AnimeListFragment());
                binding.switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    /**
                     * Función onCheckChanged para el escuchador puesto para el switch, para poder cambiar de fragment dentro de la ventana de las listas
                     * @param buttonView El botón cuyo estado ha cambiado
                     * @param isChecked  El nuevo estado del botón
                     */
                    @SuppressLint("UseCompatLoadingForDrawables")
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            binding.switchButton.setThumbIconDrawable(getDrawable(R.drawable.ic_m_foreground));
                            reemplazarFragment(new MangaListFragment());
                        }else{
                            binding.switchButton.setThumbIconDrawable(getDrawable(R.drawable.ic_a_foreground));
                            reemplazarFragment(new AnimeListFragment());
                        }
                    }
                });
            } else if (id==R.id.perfil) {
                binding.switchButton.setVisibility(View.GONE);
                reemplazarFragment(new ProfileFragment());

            }

            return true;
        });

        }

    /**
     * Función para cambiar de fragment
     * @param fragment es el fragment al que se quiere cambiar
     */
    public void reemplazarFragment(Fragment fragment){
            FragmentManager fragmentManager= getSupportFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment);
            fragmentTransaction.commit();

        }

    /**
     * La función que actauliza el usuario en la base de datos externa
     * @param user el usuario con los datos nuevos que introducir a la base datos
     */
    public void guardarUsuario(User user){
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Usuario");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d: snapshot.getChildren()){
                    User u= d.getValue(User.class);
                    if (u != null && u.getUsername().equals(user.getUsername())) {
                        d.getRef().setValue(user);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * Devuelve el Usuario que tiene la activdad
     * @return el Usuario de la actividad
     */
    public User devolverUser(){
        return user;
    }

    /**
     * Actualiza el nombre del usuario en la base de datos interna
     * @param userNuevo el nuevo nombre del usuario
     * @param userAntiguo el antiguo nombre del usuario
     */
    public void actualizarUsuario(String userNuevo, String userAntiguo){
        db.execSQL("update usuario set usuario= '"+userNuevo+"' where usuario= '"+userAntiguo+"'");

    }

    /**
     * Actualiza la contraseña del usuario en la base de datos interna
     * @param user el nombre del usuario
     * @param password la nueva contraseña
     */
    public void actualizarPassword(String user, String password){
        db.execSQL("update usuario set password= '"+password+"' where usuario ='"+user+"'");
    }

    /**
     * Función para actualizar el usuario en la base de datos externa, se utiliza para la edición del perfil
     * @param user el usuario con todos los datos nuevos
     * @param name el nombre del usuario con el que está guardado en la base de datos
     */
    public void guardarUsuarioNuevo(User user, String name){
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Usuario");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d: snapshot.getChildren()){
                    User u= d.getValue(User.class);
                    if (u != null && u.getUsername().equals(name)) {
                        d.getRef().setValue(user);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * Settea el encapsulador que se crea al crear la actividad
     * @param encapsulator ENcapsulador apra settear la variable
     */
    public void setEncapsulador(Encapsulator encapsulator){
        e= encapsulator;
    }

    /**
     * Getter para el encapsulador de la actividad
     * @return el encapsulador de la actividad
     */
    public Encapsulator getEncapsulador(){
        return e;
    }

    /**
     * Setter para la variable de la búsqueda
     * @param b el String para settear la variable
     */
    public void setBusqueda(String b){
        busqueda= b;
    }

    /**
     * Getter para la variable de la búsqueda
     * @return la búsqueda
     */
    public String getBusqueda(){
        return busqueda;
    }

    /**
     * La función para cambiar el estado del botón swtich a GONE
     */
    public void switchButton(){
        binding.switchButton.setVisibility(View.GONE);
    }

    /**
     * Función para la selección de la tab del perfil en el menú de navegación de abajo
     */
    public void tab(){
        binding.bottonNavigationView.setSelectedItemId(R.id.perfil);
    }

    /**
     * Setter para el ArrayList de los enapsualdores de los mangas
     * @param m El ArrayList para settear la variable
     */
    public void setMangas(ArrayList<Encapsulator> m){
        mangas=m;
    }

    /**
     * Getter para el ArrayList de los encapsuladores de los mangas
     * @return el ArrayLIst de los encapsuladores de los mangas
     */
    public ArrayList<Encapsulator> getMangas(){
        return mangas;
    }

    /**
     * El setter para el ArrayList de los encapsuladores de los animes
     * @param a el ArrayList para settear la variable
     */
    public void setAnimes(ArrayList<Encapsulator> a){
        animes=a;
    }

    /**
     * El getter para el ArrayList de los encapsuladores de los animes
     * @return el ArrayList de los encapsuladores de los animes
     */
    public ArrayList<Encapsulator> getAnimes(){
        return animes;
    }

}