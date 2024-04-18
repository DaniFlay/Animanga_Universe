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
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.example.animanga_universe.R;
import com.example.animanga_universe.classes.Anime;
import com.example.animanga_universe.classes.CommentScore;
import com.example.animanga_universe.classes.Forum_Post;
import com.example.animanga_universe.classes.Manga;
import com.example.animanga_universe.classes.User;
import com.example.animanga_universe.databinding.ActivityMainMenuBinding;
import com.example.animanga_universe.encapsulators.Encapsulator;
import com.example.animanga_universe.extras.Helper;
import com.example.animanga_universe.fragments.SearchFragment;
import com.example.animanga_universe.fragments.ForumsFragment;
import com.example.animanga_universe.fragments.RankingFragment;
import com.example.animanga_universe.fragments.AnimeListFragment;
import com.example.animanga_universe.fragments.MangaListFragment;
import com.example.animanga_universe.fragments.ProfileFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

/**
 * La clase Menu Principal que es la actividad principal de la aplicación, y que contiene la navegación entre los fragments de la aplicación
 * También contiene varias funciones y variables que se estarán utilizando en los fragments
 * @author Daniel Seregin Kozlov
 * @noinspection deprecation
 */
public class MainMenu extends AppCompatActivity {
    User user;
    ActivityMainMenuBinding binding;
    SQLiteDatabase db;
    Helper helper;
    Encapsulator e;
    String busqueda;
    ArrayList<Encapsulator> animes;
    ArrayList<Encapsulator> mangas;
    ArrayList<Forum_Post> posts;
    Anime anime;
    Manga manga;
    DatabaseReference ref;
    Forum_Post forumPost;
    ArrayList<String> allMangas;
    ArrayList<String> allAnimes;

    /**
     * Funcion onCreate que se utiliza para crear la actividad
     * @param savedInstanceState En el caso de que se reinicie la actvidad es el Bundle que contiene la información goardada
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper= new Helper(this, "bbdd",null,1);
        db= helper.getWritableDatabase();
        user= getIntent().getParcelableExtra("usuario");

        binding= ActivityMainMenuBinding.inflate(getLayoutInflater());
        allMangas= new ArrayList<>();
        binding.toggle.setVisibility(View.GONE);
        allAnimes= new ArrayList<>();
        fillAnimes(allAnimes);
        fillMangas(allMangas);
        setToolbar();
        setContentView(binding.getRoot());
        animes= new ArrayList<>();
        mangas=new ArrayList<>();
        posts= new ArrayList<>();
        fillPosts();
        reemplazarFragment(new RankingFragment());
        //Listener para la navegación entre framents
        binding.bottonNavigationView.setOnItemSelectedListener(item -> {
            int id= item.getItemId();
            if(id==R.id.ranking){
                setToolbar();
                updateThread(forumPost);
                binding.switchButton.setVisibility(View.GONE);
                reemplazarFragment(new RankingFragment());
                binding.toggle.setVisibility(View.GONE);
            }else if(id== R.id.forums){
                setToolbar();
                updateThread(forumPost);
                binding.switchButton.setVisibility(View.GONE);
                reemplazarFragment(new ForumsFragment());
                binding.toggle.setVisibility(View.GONE);

            }else if(id== R.id.buscar){
                setToolbar();
                updateThread(forumPost);
                binding.switchButton.setVisibility(View.GONE);
                reemplazarFragment(new SearchFragment());
                binding.toggle.setVisibility(View.GONE);

            } else if (id== R.id.listas) {
                setToolbar();
                updateThread(forumPost);
                binding.switchButton.setVisibility(View.VISIBLE);
                binding.switchButton.setChecked(false);
                binding.toggle.setVisibility(View.GONE);

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
                            binding.toggle.setVisibility(View.GONE);
                        }else{
                            binding.switchButton.setThumbIconDrawable(getDrawable(R.drawable.ic_a_foreground));
                            reemplazarFragment(new AnimeListFragment());
                            binding.toggle.setVisibility(View.GONE);
                        }
                    }
                });
            } else if (id==R.id.perfil) {
                setToolbar();
                updateThread(forumPost);
                binding.switchButton.setVisibility(View.GONE);
                reemplazarFragment(new ProfileFragment());
                binding.toggle.setVisibility(View.GONE);

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
        try{
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
        } catch (Exception ex) {
            Log.d("excepcim", Objects.requireNonNull(ex.getMessage()));
        }

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
    public MaterialSwitch getSwitchButton(){
        return binding.switchButton;
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

    public void setAnime(Anime a){
        anime= a;
    }
    public Anime getAnime(){
        return anime;
    }
    public void setManga(Manga m){
        manga= m;
    }
    public Manga getManga(){
        return manga;
    }

public ToggleButton getToggle(){
        return binding.toggle;
}
    public void fillPosts(){
        posts.clear();
        ref= FirebaseDatabase.getInstance().getReference("Forum_Posts");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d: snapshot.getChildren()){
                    Forum_Post f= d.getValue(Forum_Post.class);
                    posts.add(f);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public ArrayList<Forum_Post> getPosts(){
        return posts;
    }
    public void setPost(Forum_Post forumPost){
        this.forumPost= forumPost;
    }
    public Forum_Post getPost(){
        return this.forumPost;
    }
public MaterialToolbar getToolbar(){
       return binding.toolBar;

}
public void setToolbar(){
        binding.toolBar.setTitleTextAppearance(this, R.style.NarutoFont);
        binding.toolBar.setTitle(getString(R.string.AnimangaUniverse));
}
public void updateThread(Forum_Post forumPost){
        if(forumPost!=null){
            ref= FirebaseDatabase.getInstance().getReference("Forum_Posts");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot d: snapshot.getChildren()){
                        Forum_Post f= d.getValue(Forum_Post.class);
                        if(forumPost.equals(f)){
                            d.getRef().setValue(forumPost);
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

}
public void commentScore(CommentScore cs){
        ref= FirebaseDatabase.getInstance().getReference("CommentScore");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int counter=0;
                for(DataSnapshot d: snapshot.getChildren()){
                    if(Objects.requireNonNull(d.getValue(CommentScore.class)).equals(cs)){
                        counter++;
                        d.getRef().setValue(cs);
                    }
                }if(counter==0){
                    ref.push().setValue(cs);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
}
public void CommentScoreRemove(CommentScore cs){
        ref= FirebaseDatabase.getInstance().getReference("CommentScore");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d: snapshot.getChildren()){
                    if(Objects.requireNonNull(d.getValue(CommentScore.class)).equals(cs)){
                        d.getRef().removeValue();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
}
public void fillAnimes(ArrayList<String> animes){
    CollectionReference cr= FirebaseFirestore.getInstance().collection("Anime");
    cr.addSnapshotListener((value, error) -> {
        for (DocumentSnapshot d : Objects.requireNonNull(value).getDocuments()) {
            Anime a = d.toObject(Anime.class);
            animes.add(Objects.requireNonNull(a).getTitle());
        }
    });
}
    public void fillMangas(ArrayList<String> mangas){
        CollectionReference cr= FirebaseFirestore.getInstance().collection("Manga");
        cr.addSnapshotListener((value, error) -> {
            for (DocumentSnapshot d : Objects.requireNonNull(value).getDocuments()) {
                Manga m = d.toObject(Manga.class);
                mangas.add(Objects.requireNonNull(m).getTitle());
            }
        });
    }
    public String[] getAnimesNames(){
        String[] names= new String[allAnimes.size()];
        for(int i=0; i<names.length;i++){
            names[i]= allAnimes.get(i);
        }
        return names;
    }
    public String[] getMangaNames(){
        String[] names= new String[allMangas.size()];
        for(int i=0; i<names.length;i++){
            names[i]= allMangas.get(i);
        }
        return names;
    }
    public ImageView getBack(){
        return binding.back;
    }
    public int getNavBarId(){
        return binding.bottonNavigationView.getSelectedItemId();
    }
}
