package com.example.animanga_universe.fragments;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.animanga_universe.R;
import com.example.animanga_universe.activities.MainMenu;
import com.example.animanga_universe.classes.Anime;
import com.example.animanga_universe.classes.AnimeUser;
import com.example.animanga_universe.classes.Manga;
import com.example.animanga_universe.classes.MangaUser;
import com.example.animanga_universe.classes.AnimeScore;
import com.example.animanga_universe.classes.MangaScore;
import com.example.animanga_universe.classes.User;
import com.example.animanga_universe.encapsulators.Encapsulator;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Es el fragment que se abre a la hora de querer realizar una edición. Al elegir un anime o un manga, podemos añadirlo en nuestra lista, cambiar de lista
 * cambiar el progreso, su estado, etc
 * @author Daniel Seregin Kozlov
 * @noinspection ALL
 */
public class EditItemFragment extends Fragment implements View.OnClickListener, ChipGroup.OnCheckedStateChangeListener {
    View view;
    boolean valorado;
    User user;
    Encapsulator e;
    CollectionReference collectionReference;
    String busqueda, estado, estadoAnime;
    ChipGroup cg;
    Chip completado, enProceso, enEspera, dejado, enLista, porDefecto;
    RatingBar ratingBar;
    ProgressBar progressBar;
    TextInputLayout progreso;
    MainMenu mainMenu;
    AppCompatImageButton botonPlus;
    Button botonGuardar;
    AnimeUser animeUser;
    MangaUser mangaUser;
    DatabaseReference ref;
    TextView totales;
    ArrayList<AnimeUser> animes;
    ArrayList<MangaUser> mangas;
    Boolean complete;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public EditItemFragment() {

    }
    /**
     * Se crea la instancia del fragment
     * @param param1 Parameter 1 creado automáticamente
     * @param param2 Parameter 2 creado automáticamente
     * @return Nueva instancia del Fragment
     */
    public static EditItemFragment newInstance(String param1, String param2) {
        EditItemFragment fragment = new EditItemFragment();
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

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_editar_item, container, false);
        complete = true;
        animes = new ArrayList<>();
        mangas = new ArrayList<>();
        mainMenu = (MainMenu) getActivity();
        estadoAnime = "";
        user = Objects.requireNonNull(mainMenu).devolverUser();
        e = mainMenu.getEncapsulador();
        busqueda = mainMenu.getBusqueda();
        ref = FirebaseDatabase.getInstance().getReference("Usuario");
        totales = view.findViewById(R.id.episodiosTotales);
        estado = "";
        mainMenu.switchButton();
        progreso = view.findViewById(R.id.progreso);
        animeUser = new AnimeUser();
        mangaUser = new MangaUser();
        botonPlus = view.findViewById(R.id.botonPlus);
        botonPlus.setOnClickListener(this);
        botonGuardar = view.findViewById(R.id.botonGuardar);
        botonGuardar.setOnClickListener(this);
        ratingBar = view.findViewById(R.id.ratingBar);
        progressBar = view.findViewById(R.id.progressBar);
        cg = view.findViewById(R.id.chipGroup);
        cg.setOnCheckedStateChangeListener(this);
        completado = view.findViewById(R.id.completado);
        enProceso = view.findViewById(R.id.enProceso);
        enEspera = view.findViewById(R.id.enEspera);
        dejado = view.findViewById(R.id.dejado);
        enLista = view.findViewById(R.id.planeado);
        porDefecto = view.findViewById(R.id.sinAsignar);
        assert progreso.getEditText() != null;
        //En el caso de que el encapsulador contenga un anime y el usuario tenga animes en sus listas, se recorre la lista del usuario buscando el anime del
        //encapsulador y si lo encuentra, selecciona su estado, y settea los episodios del progreso que lleva el usuario
        if (e.getAnime() != null && user.getAnimes() != null) {
            for (AnimeUser a : user.getAnimes()) {
                if (a.getAnime().equals(e.getAnime())) {
                    estadoAnime = a.getEstado();
                    progreso.getEditText().setText(a.getEpisodios());
                }
            }
            //En el caso de que el encapsulador lo que contiene es un manga, se hace el mismo proceso pero se busca en la lista de los mangas y no animes
        } else if (e.getManga() != null && user.getMangas() != null) {
            for (MangaUser m : user.getMangas()) {
                if (m.getManga().equals(e.getManga())) {
                    estadoAnime = m.getEstado();
                    progreso.getEditText().setText(m.getCapitulos());
                }
            }
        }
        //En el caso de que no esté la obra completa se pone el máximo al 10 y el progreso a 5
        if (e.getInfo().split(" ")[0].equals("?")) {
            progressBar.setMax(10);
            progressBar.setProgress(5);
            complete = false;
            //en el caso de estarlo se settea el maximo a numero de capitulos de la obra, y se settea el total de los episodios en el campo de texto
        } else {
            progressBar.setMax(Integer.parseInt(e.getInfo().split(" ")[0]));
            totales.setText(getString(R.string.divisor) + Integer.parseInt(e.getInfo().split(" ")[0]));
        }
        //Dependiendo del estado del anime, se hace check en el chip necesario, y se rellenan el resto de los datos
        if (user.getAnimes() != null) {
            if (e.getAnime() != null) {
                if (estadoAnime.equals(getString(R.string.enespera))) {
                    enEspera.setChecked(true);
                    for (AnimeUser a : user.getAnimes()) {
                        if (e.getTitulo().equals(a.getAnime().getTitle())) {
                            //Se settea la valoración que le ha puesto el usuario a la obra
                            ratingBar.setRating(Float.parseFloat(a.getNota()));
                                progressBar.setProgress(Integer.parseInt(progreso.getEditText().getText().toString()));

                            //Se cambia el color de la barra de progreso dependiendo del estado
                            progressBar.setProgressTintList(ColorStateList.valueOf(mainMenu.getResources().getColor(R.color.espera)));
                        }
                    }
                } else if (estadoAnime.equals(getString(R.string.viendo))) {
                    enProceso.setChecked(true);
                    for (AnimeUser a : user.getAnimes()) {
                        if (e.getTitulo().equals(a.getAnime().getTitle())) {
                            ratingBar.setRating(Float.parseFloat(a.getNota()));
                            progressBar.setProgress(Integer.parseInt(progreso.getEditText().getText().toString()));
                            progressBar.setProgressTintList(ColorStateList.valueOf(mainMenu.getResources().getColor(R.color.enProceso)));
                        }
                    }
                } else if (estadoAnime.equals(getString(R.string.completado))) {
                    completado.setChecked(true);
                    for (AnimeUser a : user.getAnimes()) {
                        if (e.getTitulo().equals(a.getAnime().getTitle())) {
                            ratingBar.setRating(Float.parseFloat(a.getNota()));
                            progressBar.setProgress(Integer.parseInt(progreso.getEditText().getText().toString()));
                            progressBar.setProgressTintList(ColorStateList.valueOf(mainMenu.getResources().getColor(R.color.completado)));
                        }
                    }

                } else if (estadoAnime.equals(getString(R.string.planeado))) {
                    enLista.setChecked(true);
                    for (AnimeUser a : user.getAnimes()) {
                        if (e.getTitulo().equals(a.getAnime().getTitle())) {
                            ratingBar.setRating(Float.parseFloat(a.getNota()));
                            progressBar.setProgress(Integer.parseInt(progreso.getEditText().getText().toString()));
                            progressBar.setProgressTintList(ColorStateList.valueOf(mainMenu.getResources().getColor(R.color.enlista)));
                        }
                    }
                } else if (estadoAnime.equals(getString(R.string.dejado))) {
                    dejado.setChecked(true);
                    for (AnimeUser a : user.getAnimes()) {
                        if (e.getTitulo().equals(a.getAnime().getTitle())) {
                            ratingBar.setRating(Float.parseFloat(a.getNota()));
                            progressBar.setProgress(Integer.parseInt(progreso.getEditText().getText().toString()));
                            progressBar.setProgressTintList(ColorStateList.valueOf(mainMenu.getResources().getColor(R.color.dejado)));
                        }
                    }
                }

                animeUser.setAnime(e.getAnime());
            }
            //Se hace el mismo proceso en el caso de que sea un manga, y se cambia además el texto dentro de algunos chips
            }if (user.getMangas()!=null) {
            if (e.getManga() != null) {
                enProceso.setText(getString(R.string.leyendo));
                if (estadoAnime.equals(getString(R.string.enespera))) {
                    enEspera.setChecked(true);
                    for (MangaUser m : user.getMangas()) {
                        if (e.getTitulo().equals(m.getManga().getTitle())) {
                            ratingBar.setRating(Float.parseFloat(String.valueOf(m.getNota())));
                            progressBar.setProgress(Integer.parseInt(progreso.getEditText().getText().toString()));
                            progressBar.setProgressTintList(ColorStateList.valueOf(mainMenu.getResources().getColor(R.color.espera)));
                        }
                    }
                } else if (estadoAnime.equals(getString(R.string.leyendo))) {
                    enProceso.setChecked(true);
                    for (MangaUser m : user.getMangas()) {
                        if (e.getTitulo().equals(m.getManga().getTitle())) {
                            ratingBar.setRating(Float.parseFloat(String.valueOf(m.getNota())));
                            progressBar.setProgress(Integer.parseInt(progreso.getEditText().getText().toString()));
                            progressBar.setProgressTintList(ColorStateList.valueOf(mainMenu.getResources().getColor(R.color.enProceso)));
                        }
                    }
                } else if (estadoAnime.equals(getString(R.string.completado))) {
                    completado.setChecked(true);
                    for (MangaUser m : user.getMangas()) {
                        if (e.getTitulo().equals(m.getManga().getTitle())) {
                            ratingBar.setRating(Float.parseFloat(String.valueOf(m.getNota())));
                            progressBar.setProgress(Integer.parseInt(progreso.getEditText().getText().toString()));
                            progressBar.setProgressTintList(ColorStateList.valueOf(mainMenu.getResources().getColor(R.color.completado)));
                        }
                    }

                } else if (estadoAnime.equals(getString(R.string.planeado))) {
                    enLista.setChecked(true);
                    for (MangaUser m : user.getMangas()) {
                        if (e.getTitulo().equals(m.getManga().getTitle())) {
                            ratingBar.setRating(Float.parseFloat(String.valueOf(m.getNota())));
                            progressBar.setProgress(Integer.parseInt(progreso.getEditText().getText().toString()));
                            progressBar.setProgressTintList(ColorStateList.valueOf(mainMenu.getResources().getColor(R.color.enlista)));
                        }
                    }
                } else if (estadoAnime.equals(getString(R.string.dejado))) {
                    dejado.setChecked(true);
                    for (MangaUser m : user.getMangas()) {
                        if (e.getTitulo().equals(m.getManga().getTitle())) {
                            ratingBar.setRating(Float.parseFloat(String.valueOf(m.getNota())));
                            progressBar.setProgress(Integer.parseInt(progreso.getEditText().getText().toString()));
                            progressBar.setProgressTintList(ColorStateList.valueOf(mainMenu.getResources().getColor(R.color.dejado)));
                        }
                    }
                }
                mangaUser.setManga(e.getManga());
        }
            }


        progreso.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            //En el campo de texto para poner el número de episodios, se hace sensible al cambio de texto, y en el caso de cambiarse se cambia el progreso
            //de la barra de progreso
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //En el caso de que no esté vacio el campo se settea con el número introducido, sino se settea a 0, si la obra está completa
                if(complete){
                    if (!String.valueOf(s).equals("")) {
                        progressBar.setProgress(Integer.parseInt(String.valueOf(s)));

                    } else {
                        progressBar.setProgress(0);
                    }
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        //Si se hace click en el boton plus
        if (v.getId() == botonPlus.getId()) {
//Si el anime está completo, la barra de progreso progresa en una unidad, y el numero de episodios del campo de texto editable incrementa en 1 también
            if (complete) {
                if (progreso.getEditText() != null) {
                    progressBar.setProgress(progressBar.getProgress() + 1);
                    progreso.getEditText().setText(String.valueOf(Integer.parseInt(progreso.getEditText().getText().toString()) + 1));
                }
                //En el caso de no estar completo, solamente se cambia el campo de texto
            } else {
                if (progreso.getEditText() != null) {
                    progreso.getEditText().setText(String.valueOf(Integer.parseInt(progreso.getEditText().getText().toString()) + 1));
                }
            }
//Si se pulsa el botón guardar
        } else if (v.getId() == botonGuardar.getId()) {
            //En el caso de que el chip seleccionado sea un estado vaído (es decir, cualquiera menos el de Sin Asignar)
            if (cg.getCheckedChipId() != porDefecto.getId()) {
                if (busqueda.equals("Anime")) {
                    //Se asigna el estado dependiendo del chip
                    if (cg.getCheckedChipId() == completado.getId()) {
                        estado = getString(R.string.completado);
                    } else if (cg.getCheckedChipId() == enEspera.getId()) {
                        estado = getString(R.string.enespera);
                    } else if (cg.getCheckedChipId() == enLista.getId()) {
                        estado = getString(R.string.planeado);
                    } else if (cg.getCheckedChipId() == enProceso.getId()) {
                        estado = getString(R.string.viendo);
                    } else if (cg.getCheckedChipId() == dejado.getId()) {
                        estado = getString(R.string.dejado);
                    }
                    //Se settea el anime en el objeto AnimeUsuario
                    animeUser.setAnime(e.getAnime());
                    //Se hace uso de la función subir nota para realiar actualizaciones en las bases de datos
                    subirNotaAnime(new AnimeScore(e.getAnime(), String.valueOf(ratingBar.getRating() * 2), user));
                    animeUser.setEstado(estado); //Se settea el estado del anime
                    //Se comprueba el campo de texto de los episodios, si no esta vacío, se guardan los episodios introducidos
                    if (!Objects.requireNonNull(progreso.getEditText()).getText().toString().equals("")) {
                        animeUser.setEpisodios(progreso.getEditText().getText().toString());
                        //Si esta vacío se guardan 0 episodios
                    } else {
                        animeUser.setEpisodios(getString(R.string.cero));
                    }
                    //Se settea la valoracion según la barra de valoración
                    animeUser.setNota(String.valueOf(ratingBar.getRating()));
                    //Se añade a la lista de los animes y se settea en el usuario la lista nueva
                    if (user.getAnimes() != null) {
                        animes.addAll(user.getAnimes());
                    }
                    animes.remove(animeUser);
                    animes.add(animeUser);
                    user.setAnimes(animes);
                    //Se hace el mismo proceso con los mangas
                } else if (busqueda.equals("Manga")) {
                    if (cg.getCheckedChipId() == completado.getId()) {
                        estado = getString(R.string.completado);
                    } else if (cg.getCheckedChipId() == enEspera.getId()) {
                        estado = getString(R.string.enespera);
                    } else if (cg.getCheckedChipId() == enLista.getId()) {
                        estado = getString(R.string.planeado);
                    } else if (cg.getCheckedChipId() == enProceso.getId()) {
                        estado = getString(R.string.leyendo);
                    } else if (cg.getCheckedChipId() == dejado.getId()) {
                        estado = getString(R.string.dejado);
                    }
                    mangaUser.setManga(e.getManga());
                    subirNotaManga(new MangaScore(String.valueOf(ratingBar.getRating() * 2), e.getManga(), user));
                    mangaUser.setEstado(estado);
                    assert progreso.getEditText() != null;
                    if (!progreso.getEditText().getText().toString().equals("")) {
                        mangaUser.setCapitulos(progreso.getEditText().getText().toString());
                    } else {
                        mangaUser.setCapitulos(getString(R.string.cero));
                    }
                    mangaUser.setNota(String.valueOf(ratingBar.getRating()));
                    if (user.getMangas() != null) {
                        mangas.addAll(user.getMangas());
                    }
                    mangas.remove(mangaUser);
                    mangas.add(mangaUser);
                    user.setMangas(mangas);
                }
                //En el caso de que el chip elegido sea sin asignar, se elimina el anime o el manga de la lista del usuario y se guardan los cambios en la
                //base de datos
            } else {
                if (busqueda.equals("Manga")) {
                    if (user.getMangas() != null) {
                        mangas.addAll(user.getMangas());
                    }
                    mangaUser.setManga(e.getManga());
                    mangas.remove(mangaUser);
                    user.setMangas(mangas);
                } else if (busqueda.equals("Anime")) {
                    if (user.getAnimes() != null) {
                        animes.addAll(user.getAnimes());
                    }
                    animeUser.setAnime(e.getAnime());
                    animes.remove(animeUser);
                    user.setAnimes(animes);
                }
            }
            Snackbar.make(v, getString(R.string.cambiosGuardados), Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.ok), v1 -> {
                        mainMenu.tab();
                        mainMenu.switchButton();
                        mainMenu.guardarUsuarioNuevo(user, user.getUsername());
                        //mainMenu.reemplazarFragment(new ProfileFragment());
                    }).show();

        }
    }

//Dependiedo del chip elegido se realiza el setteo de la barra de progreso, como su color, el progreso etc
    @Override
    public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {

        if (group.getCheckedChipId() == completado.getId()) {
            progressBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.completado)));
            if (e.getAnime() != null) {
                assert progreso.getEditText() != null;
                progreso.getEditText().setText(String.valueOf(e.getAnime().getEpisodes()));
                progressBar.setMax(Integer.parseInt(e.getAnime().getEpisodes()));
                progressBar.setProgress(progressBar.getMax());
            } else if (e.getManga() != null) {
                assert progreso.getEditText() != null;
                progreso.getEditText().setText(String.valueOf(e.getManga().getChapters()));
                progressBar.setMax(Math.toIntExact(e.getManga().getChapters()));
                progressBar.setProgress(progressBar.getMax());
            }


        } else if (group.getCheckedChipId() == enEspera.getId()) {
            progressBar.setProgressTintList(ColorStateList.valueOf(mainMenu.getResources().getColor(R.color.espera)));
        } else if (group.getCheckedChipId() == porDefecto.getId()) {
            progressBar.setMax(10);
            progressBar.setProgress(0);
            assert progreso.getEditText() != null;
            progreso.getEditText().setText("");
        } else if (group.getCheckedChipId() == dejado.getId()) {
            progressBar.setProgressTintList(ColorStateList.valueOf(mainMenu.getResources().getColor(R.color.dejado)));
        } else if (group.getCheckedChipId() == enProceso.getId()) {
            progressBar.setProgressTintList(ColorStateList.valueOf(mainMenu.getResources().getColor(R.color.enProceso)));
        } else if (group.getCheckedChipId() == enLista.getId()) {
            progressBar.setMax(10);
            progressBar.setProgress(0);
            assert progreso.getEditText() != null;
            progreso.getEditText().setText("");
        }
    }

    /**
     * Esta función actualiza el anime en la base de datos
     * @param anime con los cambos realizados
     */
    public void actualizarRatingAnime(Anime anime) {
        collectionReference = FirebaseFirestore.getInstance().collection("Anime");
        collectionReference.whereEqualTo("title", anime.getTitle()).addSnapshotListener((value, error) -> {
            if (value != null) {
                for (DocumentSnapshot d : value.getDocuments()) {
                    if (d.toObject(Anime.class).equals(anime)) {
                        d.getReference().set(anime);
                        break;
                    }
                }
            }
        });
    }

    /**
     * Actualiza un manga en la base de datos
     * @param manga manga con los datos nuevos
     */
    public void actualizarRatingManga(Manga manga) {
        collectionReference = FirebaseFirestore.getInstance().collection("Manga");
        collectionReference.whereEqualTo("title", manga.getTitle()).addSnapshotListener((value, error) -> {
            if (value != null) {
                for (DocumentSnapshot d : value.getDocuments()) {
                    if (d.toObject(Manga.class).equals(manga)) {
                        d.getReference().set(manga);
                        break;
                    }
                }
            }
        });
    }

    /**
     * Recalcular la nota de un anime utilizando la valoración actual, nota nueva, y el número de personas que han valotado dicho anime
     * @param anime el anime que debe cambiar de nota
     * @param nota la nota nueva
     * @return devuelve el anime con la nota recalculada
     */
    public Anime recalculoAnime(Anime anime, float nota) {
        int personas = Integer.parseInt(anime.getScoredBy());
        personas++;
        float score = Float.parseFloat(anime.getScore());
        float newScore = ((score * personas) + nota) / (personas);
        anime.setScore(String.valueOf(newScore));
        anime.setScoredBy(String.valueOf(personas));
        return anime;
    }
    /**
     * Recalcular la nota de un manga utilizando la valoración actual, nota nueva, y el número de personas que han valotado dicho manga
     * @param manga el manga que debe cambiar de nota
     * @param nota la nota nueva
     * @return el manga con la nota recalculada
     */
    public Manga recalculoManga(Manga manga, float nota) {
        int personas = Integer.parseInt(manga.getScoredBy());
        personas++;
        double score = manga.getScore();
        double newScore = ((score * personas) + nota) / (personas);
        manga.setScore(newScore);
        manga.setScoredBy(String.valueOf(personas));
        return manga;
    }

    /**
     * Es la función para actualizar la nota de un anime, pero que ya ha sido valorada por dicho usuario
     * @param anime el anime que debe sufrir el cambio de nota
     * @param notaAntigua la nota antigua puesta por el usuario
     * @param notaNueva nota nueva del usuario
     * @return devuelve el anime con la nota recalculada
     */
    public Anime recaluclarAnime2(Anime anime, AnimeScore notaAntigua, AnimeScore notaNueva) {
        int personas = Integer.parseInt(anime.getScoredBy());
        float score = Float.parseFloat(anime.getScore());
        float newScore = (((score * personas) - Float.parseFloat(notaAntigua.getNota()) + Float.parseFloat(notaNueva.getNota())) / personas);
        DecimalFormat formato = new DecimalFormat("#.##");
        newScore= Float.parseFloat(formato.format(newScore));
        anime.setScore(String.valueOf(newScore));
        return anime;
    }
    /**
     * Es la función para actualizar la nota de un manga, pero que ya ha sido valorad por dicho usuario
     * @param manga el manga que debe sufrir el cambio de nota
     * @param notaAntigua la nota antigua puesta por el usuario
     * @param notaNueva nota nueva del usuario
     * @return el manga con la nota recalculada
     */
    public Manga recalcularManga2(Manga manga, MangaScore notaAntigua, MangaScore notaNueva) {
        int personas = Integer.parseInt(manga.getScoredBy());
        double score = manga.getScore();
        double newScore = (((score * personas) - Float.parseFloat(notaAntigua.getNota()) + Float.parseFloat(notaNueva.getNota())) / personas);
        DecimalFormat formato = new DecimalFormat("#.##");
        newScore= Double.parseDouble(formato.format(newScore));
        manga.setScore(newScore);
        return manga;
    }

    /**
     * Esta función actauliza la tabla NotaAnime en la base de datos, para saber que un cierto usuario ha puesto una cierta nota a un cierto anime
     * @param nota el objeto con todos los datos
     */
    public void subirNotaAnime(AnimeScore nota) {

        valorado = false;
        ref = FirebaseDatabase.getInstance().getReference("NotaAnime");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.getChildren()) {
                    //Se busca en la base de datos si este anime ya fue valorado por dicho usuario y en el caso de que si, se marca como valorado
                    AnimeScore n = d.getValue(AnimeScore.class);
                    assert n != null;
                    if (n.equals(nota)) {
                        d.getRef().setValue(nota);
                        valorado = true;
                        //Se actualiza el rating con la funcion actualizar que recibe la nota recalculada
                        actualizarRatingAnime(recaluclarAnime2((Anime) nota.getAnime(), n, nota));
                        break;
                    }
                }
                //En el caso de no estarlo se hace un push con la valoración
                if (!valorado) {
                    ref.push().setValue(nota);
                    actualizarRatingAnime(recalculoAnime((Anime) nota.getAnime(), Float.parseFloat(nota.getNota())));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    /**
     * Esta función actauliza la tabla NotaManga en la base de datos, para saber que un cierto usuario ha puesto una cierta nota a un cierto manga
     * @param nota el objeto con todos los datos
     */
    public void subirNotaManga(MangaScore nota) {
        valorado = false;
        ref = FirebaseDatabase.getInstance().getReference("NotaManga");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.getChildren()) {
                    MangaScore n = d.getValue(MangaScore.class);
                    assert n != null;
                    if (n.equals(nota)) {
                        d.getRef().setValue(nota);
                        valorado = true;
                        actualizarRatingManga(recalcularManga2(nota.getManga(), n, nota));
                        break;
                    }
                }
                if (!valorado) {
                    ref.push().setValue(nota);
                    actualizarRatingManga(recalculoManga(nota.getManga(), Float.parseFloat(nota.getNota())));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}