package com.example.recyclerviewproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder> {
    private ArrayList<?> listado;
    private Context contexto;
    private int r_laypout_id;
    private View.OnClickListener onClickListener;
    public void setOnClickListener(View.OnClickListener onClickListener){
        this.onClickListener=onClickListener;
    }

    public Adaptador(ArrayList<?> listado, Context contexto, int r_laypout_id) {
        this.listado = listado;
        this.contexto = contexto;
        this.r_laypout_id = r_laypout_id;
    }

    @NonNull
    @Override
    public Adaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View elemento= LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento,parent,false);
        elemento.setOnClickListener(onClickListener);
        return new ViewHolder(elemento);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador.ViewHolder holder, int position) {
        Encapsulador e= (Encapsulador) listado.get(position);
        holder.representacionElementos(e);
    }

    @Override
    public int getItemCount() {
        return listado.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView titulo,texto;
        public ImageView foto;
        public CheckBox checkbox;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            titulo= itemView.findViewById(R.id.tituloGrande);
            texto= itemView.findViewById(R.id.Subtitulo);
            foto= itemView.findViewById(R.id.imagenElemento);
            checkbox= itemView.findViewById(R.id.checkbox);

        }
        public void representacionElementos(Encapsulador e){
            titulo.setText(e.getTitulo());
            texto.setText(e.getSubtitulo());
            foto.setImageResource(e.getImagen());
            checkbox.setChecked(e.isCheckbox());
        }

    }
}
