package com.example.animanga_universe;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorPersonaje extends RecyclerView.Adapter<AdaptadorPersonaje.ViewHolder> {

    ArrayList<?> listado;
    Context context;
    int layout_id;
    View.OnClickListener onClickListener;
    public void setOnClickListener( View.OnClickListener onClickListener){
        this.onClickListener= onClickListener;
    }

    public AdaptadorPersonaje(ArrayList<?> listado, Context context, int layout_id) {
        this.listado = listado;
        this.context = context;
        this.layout_id = layout_id;
    }

    @NonNull
    @Override
    public AdaptadorPersonaje.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorPersonaje.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombre, favs;
        ImageView imagen;
        AppCompatImageButton boton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre= itemView.findViewById(R.id.nombre);
            favs= itemView.findViewById(R.id.favs);
            imagen= itemView.findViewById(R.id.imagen);
            boton= itemView.findViewById(R.id.boton);
        }
        public void representacionElementos(EncapsuladorPersonaje e){
            nombre.setText(e.getNombre());
            favs.setText(e.getFavs());
            imagen.setImageResource(e.getImagen());
            boton.setBackgroundColor(e.getColor());
        }
    }
}
