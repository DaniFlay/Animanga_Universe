package com.example.animanga_universe.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animanga_universe.R;
import com.example.animanga_universe.activities.MainMenu;
import com.example.animanga_universe.classes.User;
import com.example.animanga_universe.encapsulators.Encapsulator;
import com.example.animanga_universe.fragments.EditItemFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * El adaptador para el recycler view de la búsqueda de los animes, en el fragment de búsqueda
 * @author Daniel Seregin Kozlov
 * @noinspection ALL
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    final User user;
    final String busqueda;
    final ArrayList<?> listado;
    final Context context;
    final int layout_id;
    View.OnClickListener onClickListener;
    public void setOnClickListener( View.OnClickListener onClickListener){
        this.onClickListener= onClickListener;
    }

    public SearchAdapter(User user, ArrayList<?> listado, Context context, int layout_id, String busqueda) {
        this.user = user;
        this.listado = listado;
        this.context = context;
        this.layout_id = layout_id;
        this.busqueda= busqueda;
    }


    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View elemento= LayoutInflater.from(parent.getContext()).inflate(R.layout.element_search,parent,false);
        elemento.setOnClickListener(onClickListener);
        return new ViewHolder(elemento);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        Encapsulator e= (Encapsulator) listado.get(position);
        holder.representacionElementos(e);
        holder.boton.setOnClickListener(v -> {
            ((MainMenu)context).setBusqueda(busqueda);
            ((MainMenu)context).setEncapsulador(e);
            ((MainMenu)context).reemplazarFragment(new EditItemFragment());
        });
    }

    @Override
    public int getItemCount() {
        return listado.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        final Context context;
        final TextView titulo;
        final TextView info;
        final TextView rating;
        final ImageView imagen;
        final AppCompatImageButton boton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context= itemView.getContext();
            titulo= itemView.findViewById(R.id.titulo);
            info= itemView.findViewById(R.id.infoAdicional);
            rating= itemView.findViewById(R.id.rating);
            imagen= itemView.findViewById(R.id.imagen);
            boton= itemView.findViewById(R.id.boton);
        }
        public void representacionElementos(Encapsulator e){
            titulo.setText(e.getTitulo());
            info.setText(e.getInfo());
            float newScore = Float.parseFloat(e.getRating());
            DecimalFormat formato = new DecimalFormat("#.##");
            newScore= Float.parseFloat(formato.format(newScore));
            rating.setText(String.valueOf(newScore));
            imagen.setImageDrawable(e.getImagen());
            boton.setBackgroundColor(e.getColor());
        }
    }

}
