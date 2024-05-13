package com.example.animanga_universe.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animanga_universe.R;
import com.example.animanga_universe.activities.MainMenu;
import com.example.animanga_universe.models.Comment;
import com.example.animanga_universe.models.CommentScore;
import com.example.animanga_universe.models.Forum_Post;
import com.example.animanga_universe.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


/**
 * El adaptador para el hilo de una discusión
 * @noinspection ALL
 */
public class ThreadAdapter extends RecyclerView.Adapter<ThreadAdapter.ViewHolder>{
    final User user;
    final Forum_Post forumPost;
    final ArrayList<Comment> comments;
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
    public ThreadAdapter(User user, Forum_Post forumPost,ArrayList<Comment> comments, Context context, int layout_id) {
        this.user = user;
        this.forumPost = forumPost;
        this.comments= comments;
        this.context = context;
        this.layout_id = layout_id;

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
        View element= LayoutInflater.from(parent.getContext()).inflate(R.layout.element_comment,parent,false);
        element.setOnClickListener(onClickListener);
        return new ViewHolder(element);
    }
    /**
     * El método se utiliza para mostrar un elemento concreto de la lista
     * @param holder  El ViewHolder que debe actualizarse para representar el contenido del item específico
     * @param position La posición del item
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Comment comment=  comments.get(position);
        holder.representation(comment);
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("CommentScore");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            /**
             * Se llama a este método para recorrer la base de datos en Firebase, para poder realizar los cambios
             * @param snapshot Es la tabla a la que se accede
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d: snapshot.getChildren()){
                    if(Objects.requireNonNull(d.getValue(CommentScore.class)).equals(new CommentScore(user,comment,true))){
                        if(Objects.requireNonNull(d.getValue(CommentScore.class)).isLike()){
                            holder.like.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.tangerine)));
                        }else {
                            holder.dislike.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.tangerine)));
                        }
                        break;
                    }
                }
            }
            /**
             * Este método se llama si hay un error, ya sea por las reglas de Firebase o por no tener conexión a internet
             * @param error El error de firebase
             */
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Aqui se setean los escucahdores para el sistema de los likes y dislikes, el cambio de color de la selección, el aumento y la disminución del número,
        //la actualización de la base de datos se hace aquí
        holder.like.setOnClickListener(v -> {
            if(!Objects.equals(holder.like.getImageTintList(), ColorStateList.valueOf(context.getResources().getColor(R.color.tangerine)))){
                holder.like.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.tangerine)));
                holder.likes.setText(String.valueOf(Integer.parseInt(holder.likes.getText().toString())+1));
                comment.setLikes(comment.getLikes()+1);
                comments.set(position,comment);
                forumPost.setComments(comments);
                ((MainMenu)context).commentScore(new CommentScore(user,comment,true));
            }else {
                holder.like.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.black)));
                holder.likes.setText(String.valueOf(Integer.parseInt(holder.likes.getText().toString())-1));
                comment.setLikes(comment.getLikes()-1);
                comments.set(position,comment);
                forumPost.setComments(comments);
                ((MainMenu)context).commentScoreRemove(new CommentScore(user,comment,true));
            }
            if(Objects.equals(holder.dislike.getImageTintList(), ColorStateList.valueOf(context.getResources().getColor(R.color.tangerine)))) {
                holder.dislike.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.black)));
                holder.dislikes.setText(String.valueOf(Integer.parseInt(holder.dislikes.getText().toString()) - 1));
                comment.setDislikes(comment.getDislikes()-1);
                comments.set(position,comment);
                forumPost.setComments(comments);
            }

        });
        holder.dislike.setOnClickListener(v -> {
            if(!Objects.equals(holder.dislike.getImageTintList(), ColorStateList.valueOf(context.getResources().getColor(R.color.tangerine)))){
                holder.dislike.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.tangerine)));
                holder.dislikes.setText(String.valueOf(Integer.parseInt(holder.dislikes.getText().toString())+1));
                comment.setDislikes(comment.getDislikes()+1);
                comments.set(position,comment);
                forumPost.setComments(comments);
                ((MainMenu)context).commentScore(new CommentScore(user,comment,false));
            }else {
                holder.dislike.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.black)));
                holder.dislikes.setText(String.valueOf(Integer.parseInt(holder.dislikes.getText().toString())-1));
                comment.setDislikes(comment.getDislikes()-1);
                comments.set(position,comment);
                forumPost.setComments(comments);
                ((MainMenu)context).commentScoreRemove(new CommentScore(user,comment,true));
            }
            if(Objects.equals(holder.like.getImageTintList(), ColorStateList.valueOf(context.getResources().getColor(R.color.tangerine)))) {
                holder.like.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.black)));
                holder.likes.setText(String.valueOf(Integer.parseInt(holder.likes.getText().toString()) - 1));
                comment.setLikes(comment.getLikes()-1);
                comments.set(position,comment);
                forumPost.setComments(comments);
            }

        });

    }
    /**
     * El método devuelve la longitud de la lista, o el número de los elementos
     * @return el numero delos elementos de la lista
     */
    @Override
    public int getItemCount() {
        if(forumPost.getComments()!=null){
            return forumPost.getComments().size();
        }else {
            return 0;
        }

    }

    /** @noinspection deprecation
     * Clase personalizada de viewHolder*/
    public static class ViewHolder extends RecyclerView.ViewHolder{
        final Context context;
        final TextView user;
        final TextView date;
        final TextView comment;
        final TextView likes;
        final TextView dislikes;
        final ImageView imageView;
        final ImageButton like;
        final ImageButton dislike;
        /**
         * Contructor para el viewHolder
         * @param itemView view del item
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context= itemView.getContext();
            user= itemView.findViewById(R.id.usuario);
            date= itemView.findViewById(R.id.date);
            comment= itemView.findViewById(R.id.comment);
            likes= itemView.findViewById(R.id.likeNum);
            dislikes= itemView.findViewById(R.id.dislikeNum);
            like= itemView.findViewById(R.id.like);
            dislike= itemView.findViewById(R.id.dislike);
            imageView= itemView.findViewById(R.id.image);
        }
        /**
         * Se encarga de utilizar los datos para representar el elemento
         * @param f el objeto con los datos a representar
         */
        @SuppressLint("UseCompatLoadingForDrawables")
        public void representation(Comment c){
            Drawable drawable= context.getResources().getDrawable(R.drawable.baseline_perfil);
            date.setText(c.getDate());
            comment.setText(c.getComentario());
            likes.setText(String.valueOf(c.getLikes()));
            dislikes.setText(String.valueOf(c.getDislikes()));
            imageView.setImageDrawable(drawable);
            user.setText(c.getUser().getUsername());
        }
    }
}

