package com.example.animanga_universe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorBusqueda extends RecyclerView.Adapter<AdaptadorBusqueda.ViewHolder> {

    ArrayList<?> listado;
    Context context;
    int layout_id;
    View.OnClickListener onClickListener;
    public void setOnClickListener( View.OnClickListener onClickListener){
        this.onClickListener= onClickListener;
    }

    public AdaptadorBusqueda(ArrayList<?> listado, Context context, int layout_id) {
        this.listado = listado;
        this.context = context;
        this.layout_id = layout_id;
    }

    @NonNull
    @Override
    public AdaptadorBusqueda.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View elemento= LayoutInflater.from(parent.getContext()).inflate(R.layout.element_busqueda,parent,false);
        elemento.setOnClickListener(onClickListener);
        return new ViewHolder(elemento);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorBusqueda.ViewHolder holder, int position) {
        Encapsulador e= (Encapsulador) listado.get(position);
        holder.representacionElementos(e);
    }

    @Override
    public int getItemCount() {
        return listado.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView titulo, info, rating;
        ImageView imagen;
        AppCompatImageButton boton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo= itemView.findViewById(R.id.titulo);
            info= itemView.findViewById(R.id.infoAdicional);
            rating= itemView.findViewById(R.id.rating);
            imagen= itemView.findViewById(R.id.imagen);
            boton= itemView.findViewById(R.id.boton);
        }
        public void representacionElementos(Encapsulador e){
            titulo.setText(e.getTitulo());
            info.setText(e.getInfo());
            rating.setText(e.getRating());
            imagen.setImageDrawable(e.getImagen());
            boton.setBackgroundColor(e.getColor());
        }
    }
}
