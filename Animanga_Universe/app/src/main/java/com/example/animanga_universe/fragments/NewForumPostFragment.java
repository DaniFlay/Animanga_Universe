package com.example.animanga_universe.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.animanga_universe.R;
import com.example.animanga_universe.activities.MainMenu;
import com.example.animanga_universe.classes.Anime;
import com.example.animanga_universe.classes.AnimeUser;
import com.example.animanga_universe.classes.Comment;
import com.example.animanga_universe.classes.Forum_Post;
import com.example.animanga_universe.classes.Manga;
import com.example.animanga_universe.classes.MangaUser;
import com.example.animanga_universe.classes.User;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Fragment para crear un nuevo post en el foro
 */
public class NewForumPostFragment extends Fragment implements ChipGroup.OnCheckedStateChangeListener, View.OnClickListener {
    View view;
    ArrayList<String> names;
    ArrayAdapter<String> adapter;
    ChipGroup chipGroup;
    Chip anime, manga;
    TextInputLayout discusion, mensaje;
    Spinner obras;
    Button publicar;
    User user;
    DatabaseReference ref;
    Forum_Post forumPost;
    Anime an;
    Manga man;


    public NewForumPostFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_new_forum_post, container, false);
        user= ((MainMenu) requireActivity()).devolverUser();
        names= new ArrayList<>();
        chipGroup= view.findViewById(R.id.chipsObras);
        anime= view.findViewById(R.id.chipAnime);
        manga= view.findViewById(R.id.chipManga);
        obras= view.findViewById(R.id.animeSpinner);
        discusion= view.findViewById(R.id.discusion);
        mensaje= view.findViewById(R.id.mensaje);
        chipGroup.setOnCheckedStateChangeListener(this);
        anime.setChecked(true);
        publicar= view.findViewById(R.id.publicar);
        for(AnimeUser a: user.getAnimes()){
            names.add(a.getAnime().getTitle());
        }
        adapter= new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item,names);
        obras.setAdapter(adapter);
        publicar.setOnClickListener(this);
        return view;
    }
//Dependiendo del tab seleccionado se cargaran animes o mangas en el seleccionador
    @Override
    public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
        if(group.getCheckedChipId()== anime.getId()){
            names.clear();
            for(AnimeUser a: user.getAnimes()){
                names.add(a.getAnime().getTitle());
            }
            adapter= new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item,names);
            obras.setAdapter(adapter);
        }else if(group.getCheckedChipId()== manga.getId()){
            names.clear();
            for(MangaUser m: user.getMangas()){
                names.add(m.getManga().getTitle());
            }
            adapter= new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item,names);
            obras.setAdapter(adapter);
        }
    }
//A la hora de guardar, se crea un nuevo post, se actualiza la lista de los posts y se vuelve al fragment
    //de los foros, donde se cargará el listado actualizado
    @Override
    public void onClick(View v) {
        if(anime.isChecked()){
            for(AnimeUser a: user.getAnimes()){
                if(a.getAnime().getTitle().equals(obras.getSelectedItem().toString())){
                    an= a.getAnime();
                    break;
                }
            }
            forumPost= new Forum_Post(user,an, Objects.requireNonNull(discusion.getEditText()).getText().toString(), Objects.requireNonNull(mensaje.getEditText()).getText().toString());
        } else if (manga.isChecked()) {
            for(MangaUser m: user.getMangas()){
                if(m.getManga().getTitle().equals(obras.getSelectedItem().toString())){
                    man= m.getManga();
                    break;
                }
            }
            forumPost= new Forum_Post(user,man, Objects.requireNonNull(discusion.getEditText()).getText().toString(), Objects.requireNonNull(mensaje.getEditText()).getText().toString());
        }
        ArrayList<Comment> comments= new ArrayList<>();
        comments.add(new Comment(user, forumPost.getMessage(),0,0));
        forumPost.setComments(comments);
        ref= FirebaseDatabase.getInstance().getReference("Forum_Posts");
        ref.push().setValue(forumPost);
        ((MainMenu) requireActivity()).reemplazarFragment(new ForumsFragment());
        ((MainMenu)requireActivity()).getPosts().add(forumPost);
        ((MainMenu)requireActivity()).setPosts(((MainMenu)requireActivity()).getPosts());
    }
}