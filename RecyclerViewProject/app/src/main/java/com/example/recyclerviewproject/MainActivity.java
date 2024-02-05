package com.example.recyclerviewproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Adaptador adaptador;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Encapsulador> listado= new ArrayList<>();
        listado.add(new Encapsulador(R.drawable.blackclover,"Black clover","Shounen",false));
        listado.add(new Encapsulador(R.drawable.bleach,"Bleach","Shounen",false));
        listado.add(new Encapsulador(R.drawable.bluelock,"Blue Lock","Sports",false));
        listado.add(new Encapsulador(R.drawable.boku_no_pico,"Boku ni pico","Romance",false));
        listado.add(new Encapsulador(R.drawable.bokunohero,"Boku no hero academia","Shounen",false));
        listado.add(new Encapsulador(R.drawable.deathnote,"Death note","Seinen",false));
        listado.add(new Encapsulador(R.drawable.fairytail,"Fairy Tail","Shounen",false));
        listado.add(new Encapsulador(R.drawable.fma,"Fullmetal Alchemist Brotherhood","Shounen",false));
        listado.add(new Encapsulador(R.drawable.manji,"Tokyo Revengers","Shounen",false));
        listado.add(new Encapsulador(R.drawable.naruto,"Naruto","Shounen",false));
        listado.add(new Encapsulador(R.drawable.one_piece,"One piece","Shounen",false));
        listado.add(new Encapsulador(R.drawable.quintuplets,"Quissential Quintuplets","Romance",false));
        listado.add(new Encapsulador(R.drawable.shamanking,"Shaman King","Shounen",false));
        listado.add(new Encapsulador(R.drawable.tokyoghoul,"Tokyo Ghoul","Shounen",false));
        listado.add(new Encapsulador(R.drawable.haikyu,"Haikyu!!","Sports",false));
        listado.add(new Encapsulador(R.drawable.hxh,"Hunter x Hunter","Shounen",false));


        adaptador= new Adaptador(listado,this,R.layout.elemento);
        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int posicion= recyclerView.getChildAdapterPosition(view);
                String mensaje= "Has seleccionado "+listado.get(posicion).getTitulo();
                View vistaGeneral= findViewById(R.id.vistaGeneral);
                Snackbar.make(vistaGeneral,mensaje,Snackbar.LENGTH_SHORT).show();
            }
        });
        recyclerView=findViewById(R.id.RecyclerViewBaloncesto);
        recyclerView.setAdapter(adaptador);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }
}