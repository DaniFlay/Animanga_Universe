package com.example.animanga_universe.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.animanga_universe.R;
import com.example.animanga_universe.activities.MainMenu;
import com.example.animanga_universe.adapters.ForumAdapter;
import com.example.animanga_universe.classes.Forum_Post;
import com.example.animanga_universe.classes.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * El fragment de los foros
 */
public class ForumsFragment extends Fragment implements View.OnClickListener {
    View view;
    User user;
    RecyclerView recyclerView;
    FloatingActionButton fab;
    RecyclerView.LayoutManager layoutManager;
    ForumAdapter forumAdapter;
    ArrayList<Forum_Post> posts;

    public ForumsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_forums, container, false);
        user= ((MainMenu) requireActivity()).devolverUser();
        posts=new ArrayList<>();
        for(Forum_Post f: ((MainMenu) requireActivity()).getPosts() ){
            if(!posts.contains(f)){
                posts.add(f);
            }
        }
        recyclerView= view.findViewById(R.id.recycler);
        forumAdapter= new ForumAdapter(user,posts,getContext(),R.layout.element_discussion);
        forumAdapter.serOnClickListener(v -> {
            ((MainMenu) requireActivity()).setPost(posts.get(recyclerView.getChildAdapterPosition(v)));
            ((MainMenu) requireActivity()).reemplazarFragment(new DiscussionFragment());
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
        ((MainMenu) requireActivity()).reemplazarFragment(new NewForumPostFragment());
    }

}