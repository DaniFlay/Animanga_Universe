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
import com.example.animanga_universe.classes.Comment;
import com.example.animanga_universe.classes.Forum_Post;
import com.example.animanga_universe.classes.User;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;


public class ThreadAdapter extends RecyclerView.Adapter<ThreadAdapter.ViewHolder>{
    User user;
    Forum_Post forumPost;
    ArrayList<Comment> comments;
    Context context;
    int layout_id;
    View.OnClickListener onClickListener;

    public ThreadAdapter(User user, Forum_Post forumPost,ArrayList<Comment> comments, Context context, int layout_id) {
        this.user = user;
        this.forumPost = forumPost;
        this.comments= comments;
        this.context = context;
        this.layout_id = layout_id;

    }
    public void serOnClickListener(View.OnClickListener onClickListener){
        this.onClickListener= onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View element= LayoutInflater.from(parent.getContext()).inflate(R.layout.element_discussion,parent,false);
        element.setOnClickListener(onClickListener);
        return new ViewHolder(element);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment=  comments.get(position);
        holder.representation(comment);
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Objects.equals(holder.like.getImageTintList(), ColorStateList.valueOf(context.getResources().getColor(R.color.tangerine)))){
                    holder.like.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.tangerine)));
                    holder.likes.setText(String.valueOf(Integer.parseInt(holder.likes.getText().toString())+1));
                    comment.setLikes(comment.getLikes()+1);
                }else {
                    holder.like.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.black)));
                    holder.likes.setText(String.valueOf(Integer.parseInt(holder.likes.getText().toString())-1));
                    comment.setLikes(comment.getLikes()-1);
                }
                if(Objects.equals(holder.dislike.getImageTintList(), ColorStateList.valueOf(context.getResources().getColor(R.color.tangerine)))) {
                    holder.dislike.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.black)));
                    holder.dislikes.setText(String.valueOf(Integer.parseInt(holder.dislikes.getText().toString()) - 1));
                    comment.setDislikes(comment.getDislikes()-1);
                }

            }
        });
        holder.dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Objects.equals(holder.dislike.getImageTintList(), ColorStateList.valueOf(context.getResources().getColor(R.color.tangerine)))){
                    holder.dislike.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.tangerine)));
                    holder.dislikes.setText(String.valueOf(Integer.parseInt(holder.dislikes.getText().toString())+1));
                    comment.setDislikes(comment.getDislikes()+1);
                }else {
                    holder.dislike.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.black)));
                    holder.dislikes.setText(String.valueOf(Integer.parseInt(holder.dislikes.getText().toString())-1));
                    comment.setDislikes(comment.getDislikes()-1);
                }
                if(Objects.equals(holder.like.getImageTintList(), ColorStateList.valueOf(context.getResources().getColor(R.color.tangerine)))) {
                    holder.like.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.black)));
                    holder.likes.setText(String.valueOf(Integer.parseInt(holder.likes.getText().toString()) - 1));
                    comment.setLikes(comment.getLikes()-1);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        if(forumPost.getComments()!=null){
            return forumPost.getComments().size();
        }else {
            return 0;
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        Context context;
        TextView user, date, comment, likes, dislikes;
        ImageView imageView;
        ImageButton like, dislike;

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
            imageView= itemView.findViewById(R.id.imagen);
        }
        @SuppressLint("UseCompatLoadingForDrawables")
        public void representation(Comment c){
            Drawable drawable= context.getResources().getDrawable(R.drawable.baseline_perfil);
            date.setText(c.getDate());
            comment.setText(c.getComentario());
            likes.setText(c.getLikes());
            dislikes.setText(c.getDislikes());
            imageView.setImageDrawable(drawable);
            user.setText(c.getUser().getUsername());

        }
    }
}

