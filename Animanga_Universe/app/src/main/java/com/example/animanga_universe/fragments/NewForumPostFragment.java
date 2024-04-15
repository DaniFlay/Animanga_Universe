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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public NewForumPostFragment() {

    }

    /**
     * Crea una nueva instancia del fragment
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return Nueva instancia del fragment
     */

    public static NewForumPostFragment newInstance(String param1, String param2) {
        NewForumPostFragment fragment = new NewForumPostFragment();
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
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_new_forum_post, container, false);
        user= ((MainMenu)getActivity()).devolverUser();
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
        adapter= new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,names);
        obras.setAdapter(adapter);
        publicar.setOnClickListener(this);
        return view;
    }

    @Override
    public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
        if(group.getCheckedChipId()== anime.getId()){
            names.clear();
            for(AnimeUser a: user.getAnimes()){
                names.add(a.getAnime().getTitle());
            }
            adapter= new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,names);
            obras.setAdapter(adapter);
        }else if(group.getCheckedChipId()== manga.getId()){
            names.clear();
            for(MangaUser m: user.getMangas()){
                names.add(m.getManga().getTitle());
            }
            adapter= new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,names);
            obras.setAdapter(adapter);
        }
    }

    @Override
    public void onClick(View v) {
        if(anime.isChecked()){
            for(AnimeUser a: user.getAnimes()){
                if(a.getAnime().getTitle().equals(obras.getSelectedItem().toString())){
                    an= a.getAnime();
                    break;
                }
            }
            forumPost= new Forum_Post(user,an,discusion.getEditText().getText().toString(),mensaje.getEditText().getText().toString());
        } else if (manga.isChecked()) {
            for(MangaUser m: user.getMangas()){
                if(m.getManga().getTitle().equals(obras.getSelectedItem().toString())){
                    man= m.getManga();
                    break;
                }
            }
            forumPost= new Forum_Post(user,man,discusion.getEditText().getText().toString(),mensaje.getEditText().getText().toString());
        }
        ArrayList<Comment> comments= new ArrayList<>();
        comments.add(new Comment(user, forumPost.getMessage(),0,0));
        forumPost.setComments(comments);
        ref= FirebaseDatabase.getInstance().getReference("Forum_Posts");
        ref.push().setValue(forumPost);
        ((MainMenu)getActivity()).reemplazarFragment(new ForumsFragment());
    }
}