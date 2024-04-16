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

import com.example.animanga_universe.R;
import com.example.animanga_universe.activities.MainMenu;
import com.example.animanga_universe.adapters.ThreadAdapter;
import com.example.animanga_universe.classes.Comment;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.Objects;

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

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public DiscussionFragment() {

    }

    /**
     * Crea una nueva instancia del fragment
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return Nueva instancia del fragment
     */
    public static DiscussionFragment newInstance(String param1, String param2) {
        DiscussionFragment fragment = new DiscussionFragment();
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
        view= inflater.inflate(R.layout.fragment_discussion, container, false);
        toolbar= ((MainMenu)getActivity()).getToolbar();
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
        Comment c= new Comment(((MainMenu)getActivity()).devolverUser(),message.getText().toString(),0,0);
        ArrayList<Comment> comments= ((MainMenu)getActivity()).getPost().getComments();
        message.setText("");
        comments.add(c);
        ((MainMenu)getActivity()).getPost().setComments(comments);
        threadAdapter.notifyItemInserted(comments.size()-1);
    }
}