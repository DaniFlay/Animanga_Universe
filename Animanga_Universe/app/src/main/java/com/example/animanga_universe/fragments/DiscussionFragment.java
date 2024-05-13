package com.example.animanga_universe.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.animanga_universe.R;
import com.example.animanga_universe.activities.MainMenu;
import com.example.animanga_universe.adapters.ThreadAdapter;
import com.example.animanga_universe.models.Comment;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

/**
 * El fragment de las discusiones, donde se muestra el hilo de una discusión
 */
public class DiscussionFragment extends Fragment implements View.OnClickListener {
    View view;
    ThreadAdapter threadAdapter;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    MaterialToolbar toolbar;
    EditText message;
    ImageButton send;
    ImageView back;

    public DiscussionFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_discussion, container, false);
        back= ((MainMenu) requireActivity()).getBack();
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        toolbar= ((MainMenu) requireActivity()).getToolbar();
        toolbar.setTitle((((MainMenu) requireActivity()).getPost().getTopic()));
        toolbar.setTitleTextAppearance(getContext(), R.style.RobotoFont);
        recyclerView= view.findViewById(R.id.recycler);
        message= view.findViewById(R.id.message);
        send= view.findViewById(R.id.sendmsg);
        threadAdapter= new ThreadAdapter(((MainMenu) requireActivity()).devolverUser(),((MainMenu) requireActivity()).getPost(),((MainMenu) requireActivity()).getPost().getComments(),getContext(),R.layout.element_comment);
        recyclerView.setAdapter(threadAdapter);
        layoutManager= new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        send.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        //En el caso de envio, se borra el campo de texto, se crea un nuevo comentario y se actaulzia con este comentario
        //el listado de los comentarios de la discusion
        if(v.getId()== send.getId()){
            Comment c= new Comment(((MainMenu) requireActivity()).devolverUser(),message.getText().toString(),0,0);
            ArrayList<Comment> comments= ((MainMenu) requireActivity()).getPost().getComments();
            message.setText("");
            comments.add(c);
            ((MainMenu) requireActivity()).getPost().setComments(comments);
            threadAdapter.notifyItemInserted(comments.size()-1);
            //En el caso de darl eal botón atrás se vuelve al fragment de los foros
        } else if (v.getId()==back.getId()) {
            back.setVisibility(View.GONE);
            ((MainMenu) requireActivity()).reemplazarFragment(new ForumsFragment());
        }

    }
}