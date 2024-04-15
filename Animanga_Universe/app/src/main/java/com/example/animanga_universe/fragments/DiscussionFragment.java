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
import com.google.android.material.appbar.MaterialToolbar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiscussionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiscussionFragment extends Fragment {
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
        toolbar= view.findViewById(R.id.toolbar);
        recyclerView= view.findViewById(R.id.recycler);
        message= view.findViewById(R.id.message);
        send= view.findViewById(R.id.sendmsg);
        threadAdapter= new ThreadAdapter(((MainMenu)getActivity()).devolverUser(),((MainMenu)getActivity()).getPost(),((MainMenu)getActivity()).getPost().getComments(),getContext(),R.layout.element_comment);
        recyclerView.setAdapter(threadAdapter);
        layoutManager= new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }
}