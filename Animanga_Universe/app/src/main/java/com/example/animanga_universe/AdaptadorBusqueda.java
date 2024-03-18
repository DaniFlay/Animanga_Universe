package com.example.animanga_universe;


import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animanga_universe.clases.Usuario;

import java.util.ArrayList;

public class AdaptadorBusqueda extends RecyclerView.Adapter<AdaptadorBusqueda.ViewHolder> {
    Usuario usuario;
    String busqueda;
    ArrayList<?> listado;
    Context context;
    int layout_id;
    View.OnClickListener onClickListener;
    public void setOnClickListener( View.OnClickListener onClickListener){
        this.onClickListener= onClickListener;
    }

    public AdaptadorBusqueda(Usuario usuario,ArrayList<?> listado, Context context, int layout_id, String busqueda) {
        this.usuario= usuario;
        this.listado = listado;
        this.context = context;
        this.layout_id = layout_id;
        this.busqueda= busqueda;
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
        holder.boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, EditarItem.class);
                intent.putExtra("usuario",(Parcelable) usuario);
                intent.putExtra("encapsulador",(Parcelable) e);
                intent.putExtra("busqueda",busqueda);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listado.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        Context context;
        TextView titulo, info, rating;
        ImageView imagen;
        AppCompatImageButton boton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context= itemView.getContext();
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
