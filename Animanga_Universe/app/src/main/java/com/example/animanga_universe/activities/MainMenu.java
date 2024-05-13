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
import com.example.animanga_universe.models.Anime;
import com.example.animanga_universe.models.CommentScore;
import com.example.animanga_universe.models.Forum_Post;
import com.example.animanga_universe.models.Manga;
import com.example.animanga_universe.models.User;
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
    ArrayList<Encapsulator> animesRanking;
    ArrayList<Encapsulator> mangasRanking;
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
        animesRanking= new ArrayList<>();
        mangasRanking= new ArrayList<>();
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
                binding.back.setVisibility(View.GONE);
                binding.switchButton.setVisibility(View.GONE);
                reemplazarFragment(new RankingFragment());
                binding.toggle.setVisibility(View.GONE);
            }else if(id== R.id.forums){
                setToolbar();
                updateThread(forumPost);
                binding.back.setVisibility(View.GONE);
                binding.switchButton.setVisibility(View.GONE);
                reemplazarFragment(new ForumsFragment());
                binding.toggle.setVisibility(View.GONE);

            }else if(id== R.id.buscar){
                setToolbar();
                updateThread(forumPost);
                binding.back.setVisibility(View.GONE);
                binding.switchButton.setVisibility(View.GONE);
                reemplazarFragment(new SearchFragment());
                binding.toggle.setVisibility(View.GONE);

            } else if (id== R.id.listas) {
                setToolbar();
                updateThread(forumPost);
                binding.back.setVisibility(View.GONE);
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
                binding.back.setVisibility(View.GONE);
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
            /**
             * Se llama a este método para recorrer la base de datos en Firebase, para poder realizar los cambios
             * @param snapshot Es la tabla a la que se accede
             */
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
            /**
             * Este método se llama si hay un error, ya sea por las reglas de Firebase o por no tener conexión a internet
             * @param error El error de firebase
             */
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
                /**
                 * Se llama a este método para recorrer la base de datos en Firebase, para poder realizar los cambios
                 * @param snapshot Es la tabla a la que se accede
                 */
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
                /**
                 * Este método se llama si hay un error, ya sea por las reglas de Firebase o por no tener conexión a internet
                 * @param error El error de firebase
                 */
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

    /**
     * El setter para la variable del anime que se utilizará en varios fragments de la actividad
     * @param a Anime para settear la variable
     */
    public void setAnime(Anime a){
        anime= a;
    }

    /**
     * Getter para la variable del anime que se utilizará en varios fragments de la actividad
     * @return a La variable anime guardada en la actividad
     */
    public Anime getAnime(){
        return anime;
    }

    /**
     * El setter para la variable del manga que se utilizará en varios fragments de la actividad
     * @param m Manga para settear la variable
     */
    public void setManga(Manga m){
        manga= m;
    }
    /**
     * Getter para la variable del anime que se utilizará en varios fragments de la actividad
     * @return m La variable manga guardada en la actividad
     */
    public Manga getManga(){
        return manga;
    }

    /**
     * Getter para el ToggleButton para poder modificar el estado de visibilidad en cualquier momento
     * @return ToggleButton de la barra superior
     */
    public ToggleButton getToggle(){
        return binding.toggle;
}

    /**
     * La función que busca todos los posts de los foros y rellena el listado para la pestaña de los foros
     */
    public void fillPosts(){
        posts.clear();
        ref= FirebaseDatabase.getInstance().getReference("Forum_Posts");
        ref.addValueEventListener(new ValueEventListener() {
            /**
             * Se llama a este método para recorrer la base de datos en Firebase, para poder realizar los cambios
             * @param snapshot Es la tabla a la que se accede
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d: snapshot.getChildren()){
                    Forum_Post f= d.getValue(Forum_Post.class);
                    posts.add(f);

                }
            }
            /**
             * Este método se llama si hay un error, ya sea por las reglas de Firebase o por no tener conexión a internet
             * @param error El error de firebase
             */
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    /**
     * Getter para la variable del listado de los Forum_Post
     * @return posts El listado con los Forum_Post
     */
    public ArrayList<Forum_Post> getPosts(){
        return posts;
    }

    /**
     * Setter para la variable del Forum_Post
     * @param forumPost la variable para settear la variable
     */
    public void setPost(Forum_Post forumPost){
        this.forumPost= forumPost;
    }
    /**
     * Getter para la variable del Forum_Post
     * @return  forumPost la variable del Forum_Post
     */
    public Forum_Post getPost(){
        return this.forumPost;
    }

    /**
     * Getter para Toolbar de la actividad
     * @return Toolbar de la actividad
     */
    public MaterialToolbar getToolbar(){
       return binding.toolBar;

}

    /**
     * Setter de las características para Toolbar de la actividad
     */
    public void setToolbar(){
        binding.toolBar.setTitleTextAppearance(this, R.style.NarutoFont);
        binding.toolBar.setTitle(getString(R.string.AnimangaUniverse));
}

    /**
     * Actualiza un Forum_Post en la base de datos en la nube
     * @param forumPost con datos actualizados
     */
    public void updateThread(Forum_Post forumPost){
        if(forumPost!=null){
            ref= FirebaseDatabase.getInstance().getReference("Forum_Posts");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                /**
                 * Se llama a este método para recorrer la base de datos en Firebase, para poder realizar los cambios
                 * @param snapshot Es la tabla a la que se accede
                 */
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
                /**
                 * Este método se llama si hay un error, ya sea por las reglas de Firebase o por no tener conexión a internet
                 * @param error El error de firebase
                 */
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

}

    /**
     * Añade o actualiza una reacción de un usuario a un comentario dentro de una discusión
     * @param cs El objeto CommentScore con los datos
     */
    public void commentScore(CommentScore cs){
        ref= FirebaseDatabase.getInstance().getReference("CommentScore");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            /**
             * Se llama a este método para recorrer la base de datos en Firebase, para poder realizar los cambios
             * @param snapshot Es la tabla a la que se accede
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int counter=0;
                //En el caso de que exista ya una reacción, esa se actualiza
                for(DataSnapshot d: snapshot.getChildren()){
                    if(Objects.requireNonNull(d.getValue(CommentScore.class)).equals(cs)){
                        counter++;
                        d.getRef().setValue(cs);
                    }
                    //En caso contrario se añade
                }if(counter==0){
                    ref.push().setValue(cs);
                }
            }
            /**
             * Este método se llama si hay un error, ya sea por las reglas de Firebase o por no tener conexión a internet
             * @param error El error de firebase
             */
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
}

    /**
     * Esta función borra una reacción específica de la base de datos
     * @param cs CommentScore a borrar
     */
    public void commentScoreRemove(CommentScore cs){
        ref= FirebaseDatabase.getInstance().getReference("CommentScore");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            /**
             * Se llama a este método para recorrer la base de datos en Firebase, para poder realizar los cambios
             * @param snapshot Es la tabla a la que se accede
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d: snapshot.getChildren()){
                    if(Objects.requireNonNull(d.getValue(CommentScore.class)).equals(cs)){
                        d.getRef().removeValue();
                        break;
                    }
                }
            }
            /**
             * Este método se llama si hay un error, ya sea por las reglas de Firebase o por no tener conexión a internet
             * @param error El error de firebase
             */
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
}

    /**
     * Esta función rellena el listado con todos los nombres de los animes que se sacan de la base de datos
     * @param animes EL listado a rellenar
     */
    public void fillAnimes(ArrayList<String> animes){
    CollectionReference cr= FirebaseFirestore.getInstance().collection("Anime");
    cr.addSnapshotListener((value, error) -> {
        for (DocumentSnapshot d : Objects.requireNonNull(value).getDocuments()) {
            Anime a = d.toObject(Anime.class);
            animes.add(Objects.requireNonNull(a).getTitle());
        }
    });
}

    /**
     * Esta función rellena el listado con todos los nombres de los mangas que se sacan de la base de datos
     * @param mangas El listado a rellenar
     */
    public void fillMangas(ArrayList<String> mangas){
        CollectionReference cr= FirebaseFirestore.getInstance().collection("Manga");
        cr.addSnapshotListener((value, error) -> {
            for (DocumentSnapshot d : Objects.requireNonNull(value).getDocuments()) {
                Manga m = d.toObject(Manga.class);
                mangas.add(Objects.requireNonNull(m).getTitle());
            }
        });
    }

    /**
     * Rellena un Array de Strings con los nombres de los animes
     * @return El array con los nombres de los animes
     */
    public String[] getAnimesNames(){
        String[] names= new String[allAnimes.size()];
        for(int i=0; i<names.length;i++){
            names[i]= allAnimes.get(i);
        }
        return names;
    }

    /**
     * Rellena un Array de Strings con los nombres de los mangas
     * @return El array con los nombres de los mangas
     */
    public String[] getMangaNames(){
        String[] names= new String[allMangas.size()];
        for(int i=0; i<names.length;i++){
            names[i]= allMangas.get(i);
        }
        return names;
    }

    /**
     * Getter de la imagen que sirve como botón de atrás
     * @return el ImageView que sirve de botón atrás
     */
    public ImageView getBack(){


        return binding.back;
    }

    /**
     * Devuelve el id del tab seleccionado del menú inferior
     * @return el id del tab seleccionado del menú inferior
     */
    public int getNavBarId(){
        return binding.bottonNavigationView.getSelectedItemId();
    }

    /**
     * Setter para la lista de los Forum_Post
     * @param posts variable para settear la variable de la actividad
     */
    public void setPosts(ArrayList<Forum_Post> posts){
        this.posts= posts;
    }

    /**
     * Getter para el listado del ranking de los animes
     * @return el listado con los rankings de los animes
     */
    public ArrayList<Encapsulator> getAnimesRanking(){
        return animesRanking;
    }

    /**
     * Getter para el listado del ranking de los mangas
     * @return el listado con los rankings de los mangas
     */
    public ArrayList<Encapsulator> getMangasRanking(){
        return mangasRanking;
    }
}
