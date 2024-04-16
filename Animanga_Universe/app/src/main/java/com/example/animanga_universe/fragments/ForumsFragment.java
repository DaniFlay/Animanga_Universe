package com.example.animanga_universe.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.animanga_universe.R;
import com.example.animanga_universe.activities.MainMenu;
import com.example.animanga_universe.adapters.ForumAdapter;
import com.example.animanga_universe.classes.Forum_Post;
import com.example.animanga_universe.classes.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * El fragment de los foros
 */
public class ForumsFragment extends Fragment implements View.OnClickListener {
    View view;
    User user;
    RecyclerView recyclerView;
    FloatingActionButton fab;
    DatabaseReference ref;
    RecyclerView.LayoutManager layoutManager;
    ForumAdapter forumAdapter;
    ArrayList<Forum_Post> posts;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public ForumsFragment() {

    }

    /**
     * Crea una nueva instancia del fragment
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return Nueva instancia del fragment
     */

    public static ForumsFragment newInstance(String param1, String param2) {
        ForumsFragment fragment = new ForumsFragment();

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_forums, container, false);
        user= ((MainMenu)getActivity()).devolverUser();
        posts=new ArrayList<>();
        for(Forum_Post f: ((MainMenu)getActivity()).getPosts() ){
            if(!posts.contains(f)){
                posts.add(f);
            }
        }
        recyclerView= view.findViewById(R.id.recycler);
        forumAdapter= new ForumAdapter(user,posts,getContext(),R.layout.element_discussion);
        forumAdapter.serOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainMenu)getActivity()).setPost(posts.get(recyclerView.getChildAdapterPosition(v)));
                ((MainMenu)getActivity()).reemplazarFragment(new DiscussionFragment());
            }
        });
        recyclerView.setAdapter(forumAdapter);
        layoutManager= new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        fab= view.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        ((MainMenu)getActivity()).reemplazarFragment(new NewForumPostFragment());
    }

}