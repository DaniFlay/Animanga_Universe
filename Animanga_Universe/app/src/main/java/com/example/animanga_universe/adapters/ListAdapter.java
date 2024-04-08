package com.example.animanga_universe.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animanga_universe.R;
import com.example.animanga_universe.activities.MainMenu;
import com.example.animanga_universe.classes.User;
import com.example.animanga_universe.encapsulators.Encapsulator;
import com.example.animanga_universe.fragments.EditItemFragment;

import java.util.ArrayList;

/**
 * El adaptador para los listados de animes en la cuenta del usuario
 * @author Daniel Seregin Kozlov
 * @noinspection ALL
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
        final User user;
        final String busqueda;
        final ArrayList<?> listado;
        final Context context;
        final int layout_id;
        View.OnClickListener onClickListener;


public ListAdapter(User user, ArrayList<?> listado, Context context, int layout_id, String busqueda) {
        this.user = user;
        this.listado = listado;
        this.context = context;
        this.layout_id = layout_id;
        this.busqueda= busqueda;

        }

public void setOnClickListener( View.OnClickListener onClickListener){
        this.onClickListener= onClickListener;
        }

    @NonNull
@Override
public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View elemento= LayoutInflater.from(parent.getContext()).inflate(R.layout.element_lista_usuario,parent,false);
        elemento.setOnClickListener(onClickListener);
        return new ViewHolder(elemento);
        }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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

public User user(){
    return user;
}

    public static class ViewHolder extends RecyclerView.ViewHolder{
    final Context context;
    final TextView titulo;
        final TextView info;
        final TextView vistos;
        final TextView totales;
    final ImageView imagen;
    final ProgressBar progressBar;
    final AppCompatImageButton boton;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        context= itemView.getContext();
        titulo= itemView.findViewById(R.id.titulo);
        info= itemView.findViewById(R.id.infoAdicional);
        progressBar= itemView.findViewById(R.id.progressBar);
        imagen= itemView.findViewById(R.id.imagen);
        boton= itemView.findViewById(R.id.botonEditar);
        vistos= itemView.findViewById(R.id.vistos);
        totales= itemView.findViewById(R.id.totales);

    }
    public void representacionElementos(Encapsulator e){
        titulo.setText(e.getTitulo());
        info.setText(e.getInfo());
        if(e.getAnime()!=null){
            if(!e.getAnime().getEpisodes().equals("")){
                progressBar.setMax(Integer.parseInt(e.getAnime().getEpisodes()));
                progressBar.setProgress(e.getProgreso());
                totales.setText(String.valueOf(progressBar.getMax()));
            }else{
                progressBar.setMax(10);
                progressBar.setProgress(5);
                totales.setText("?");
            }

        } else if (e.getManga()!=null) {
            if(e.getManga().getChapters()!=null){
                progressBar.setMax(Math.toIntExact(e.getManga().getChapters()));
                progressBar.setProgress(e.getProgreso());
                totales.setText(String.valueOf(progressBar.getMax()));
            }else{
                progressBar.setMax(10);
                progressBar.setProgress(5);
                totales.setText("?");
            }

        }
        vistos.setText(String.valueOf(progressBar.getProgress()));
        progressBar.setProgressTintList(ColorStateList.valueOf(context.getResources().getColor(e.getColor())));
        imagen.setImageDrawable(e.getImagen());
    }
}

}

