package com.example.animanga_universe.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animanga_universe.R;
import com.example.animanga_universe.models.Forum_Post;
import com.example.animanga_universe.models.User;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Es el adaptador para el RecyclerView de los foros
 * @author Daniel Seregin Kozlov
 */
public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ViewHolder>{
    final User user;
    final ArrayList<Forum_Post> list;
    final Context context;
    final int layout_id;
    View.OnClickListener onClickListener;

    /**
     * El constructor para el adaptador
     * @param user el usuario cuya sesión está iniciada
     * @param list la lista de las discusiones
     * @param context el contexto
     * @param layout_id el layout utilizado para este adaptador
     */
    public ForumAdapter(User user, ArrayList<Forum_Post> list, Context context, int layout_id) {
        this.user = user;
        this.list = list;
        this.context = context;
        this.layout_id = layout_id;

    }

    /**
     * Setter para el escuchador de los clicks
     * @param onClickListener el escuchador que se setea
     */
    public void serOnClickListener(View.OnClickListener onClickListener){
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View element= LayoutInflater.from(parent.getContext()).inflate(R.layout.element_discussion,parent,false);
        element.setOnClickListener(onClickListener);
        return new ViewHolder(element);
    }

    /**
     * El método se utiliza para mostrar un elemento concreto de la lista
     * @param holder  El ViewHolder que debe actualizarse para representar el contenido del item específico
     * @param position La posición del item
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Forum_Post f= list.get(position);
        holder.representation(f);

    }

    /**
     * El método devuelve la longitud de la lista, o el número de los elementos
     * @return el numero delos elementos de la lista
     */
    @Override
    public int getItemCount() {
        return list.size();
    }


    /** @noinspection deprecation
     * La clase ViewHolder personalizada
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{
        final Context context;
        final TextView title;
        final TextView discussion;
        final TextView comments;
        final ImageView imageView;
        final TextView date;

        /**
         * Contructor para el viewHolder
         * @param itemView view del item
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context= itemView.getContext();
            title= itemView.findViewById(R.id.titulo);
            discussion= itemView.findViewById(R.id.topic);
            comments= itemView.findViewById(R.id.comments);
            imageView= itemView.findViewById(R.id.imagen);
            date= itemView.findViewById(R.id.date);
        }

        /**
         * Se encarga de utilizar los datos para representar el elemento
         * @param f el objeto con los datos a representar
         */
        @SuppressLint("UseCompatLoadingForDrawables")
        public void representation(Forum_Post f){
            Drawable drawable= context.getResources().getDrawable(R.drawable.ic_launcher_foreground);
            if(f.getAnime()!=null){
                title.setText(f.getAnime().getTitle());
                try {
                    InputStream is = (InputStream) new URL(f.getAnime().getMainPicture()).getContent();
                    drawable = Drawable.createFromStream(is, "src name");
                } catch (IOException e) {
                    e.getStackTrace();
                }
            }else if(f.getManga()!=null){
                title.setText(f.getManga().getTitle());
                try {
                    InputStream is = (InputStream) new URL(f.getManga().getMainPicture()).getContent();
                    drawable = Drawable.createFromStream(is, "src name");
                } catch (IOException e) {
                    e.getStackTrace();
                }
            }
            discussion.setText(f.getTopic());
            if(f.getComments()!=null){
                comments.setText(String.valueOf(f.getComments().size()-1));
            }
            imageView.setImageDrawable(drawable);
            date.setText(f.getComments().get(0).getDate());

        }
    }
}
