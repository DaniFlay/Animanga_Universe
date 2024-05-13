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
import com.example.animanga_universe.models.User;
import com.example.animanga_universe.encapsulators.Encapsulator;
import com.example.animanga_universe.fragments.EditItemFragment;
import com.google.android.material.materialswitch.MaterialSwitch;

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
        MaterialSwitch materialSwitch;

    /**
     * El constructor para el adaptador
     * @param user el usuario cuya sesión está iniciada
     * @param list la lista de las discusiones
     * @param context el contexto
     * @param layout_id el layout utilizado para este adaptador
     * @param busqueda es el idnicador si se busca un anime o un manga
     */
public ListAdapter(User user, ArrayList<?> listado, Context context, int layout_id, String busqueda) {
        this.user = user;
        this.listado = listado;
        this.context = context;
        this.layout_id = layout_id;
        this.busqueda= busqueda;

        }
    /**
     * Setter para el escuchador de los clicks
     * @param onClickListener el escuchador que se setea
     */
public void setOnClickListener( View.OnClickListener onClickListener){
        this.onClickListener= onClickListener;
        }
    /**
     * El view holder necesario para el recyclerView
     * @param parent El grupo de vistas al que se añadirá la vista creada
     * @param viewType el tipo de view del nuevo view
     * @return el ViewHolder creado
     */
    @NonNull
@Override
public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View elemento= LayoutInflater.from(parent.getContext()).inflate(R.layout.element_list_user,parent,false);
        elemento.setOnClickListener(onClickListener);
        return new ViewHolder(elemento);
        }
    /**
     * El método se utiliza para mostrar un elemento concreto de la lista
     * @param holder  El ViewHolder que debe actualizarse para representar el contenido del item específico
     * @param position La posición del item
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Encapsulator e= (Encapsulator) listado.get(position);
            holder.representacionElementos(e);
            holder.boton.setOnClickListener(v -> {
                ((MainMenu)context).setBusqueda(busqueda);
                ((MainMenu)context).setEncapsulador(e);
                materialSwitch= (((MainMenu) context).getSwitchButton());
                materialSwitch.setVisibility(View.GONE);
                ((MainMenu)context).reemplazarFragment(new EditItemFragment());
            });


    }

    /**
     * El método devuelve la longitud de la lista, o el número de los elementos
     * @return el numero delos elementos de la lista
     */
    @Override
public int getItemCount() {
        return listado.size();
        }

    /**
     * el metodo devuelve el usuario de la actividad
     * @return el usuario
     */
    public User user(){
    return user;
}

    /**
     * Clase personalizada del viewHolder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{
    final Context context;
    final TextView titulo;
        final TextView info;
        final TextView vistos;
        final TextView totales;
    final ImageView imagen;
    final ProgressBar progressBar;
    final AppCompatImageButton boton;
        /**
         * Contructor para el viewHolder
         * @param itemView view del item
         */
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
        /**
         * Se encarga de utilizar los datos para representar el elemento
         * @param f el objeto con los datos a representar
         */
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

