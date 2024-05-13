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
import com.example.animanga_universe.models.User;
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
    /**
     * Setter para el escuchador de los clicks
     * @param onClickListener el escuchador que se setea
     */
    public void setOnClickListener( View.OnClickListener onClickListener){
        this.onClickListener= onClickListener;
    }
    /**
     * El constructor para el adaptador
     * @param user el usuario cuya sesión está iniciada
     * @param list la lista de las discusiones
     * @param context el contexto
     * @param layout_id el layout utilizado para este adaptador
     * @param busqueda es el indicador de si se realiza la busqueda de un anime o un manga
     */
    public SearchAdapter(User user, ArrayList<?> listado, Context context, int layout_id, String busqueda) {
        this.user = user;
        this.listado = listado;
        this.context = context;
        this.layout_id = layout_id;
        this.busqueda= busqueda;
    }

    /**
     * El view holder necesario para el recyclerView
     * @param parent El grupo de vistas al que se añadirá la vista creada
     * @param viewType el tipo de view del nuevo view
     * @return el ViewHolder creado
     */
    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View elemento= LayoutInflater.from(parent.getContext()).inflate(R.layout.element_search,parent,false);
        elemento.setOnClickListener(onClickListener);
        return new ViewHolder(elemento);
    }
    /**
     * El método se utiliza para mostrar un elemento concreto de la lista
     * @param holder  El ViewHolder que debe actualizarse para representar el contenido del item específico
     * @param position La posición del item
     */
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
    /**
     * El método devuelve la longitud de la lista, o el número de los elementos
     * @return el numero delos elementos de la lista
     */
    @Override
    public int getItemCount() {
        return listado.size();
    }

    /**
     * La clase viewHolder personalizada
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{
        final Context context;
        final TextView titulo;
        final TextView info;
        final TextView rating;
        final ImageView imagen;
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
            rating= itemView.findViewById(R.id.rating);
            imagen= itemView.findViewById(R.id.imagen);
            boton= itemView.findViewById(R.id.boton);
        }
        /**
         * Se encarga de utilizar los datos para representar el elemento
         * @param f el objeto con los datos a representar
         */
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
