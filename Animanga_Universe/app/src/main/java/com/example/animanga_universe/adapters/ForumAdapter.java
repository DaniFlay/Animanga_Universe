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
import com.example.animanga_universe.classes.Forum_Post;
import com.example.animanga_universe.classes.User;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ViewHolder>{
    User user;
    ArrayList<Forum_Post> list;
    Context context;
    int layout_id;
    View.OnClickListener onClickListener;

    public ForumAdapter(User user, ArrayList<Forum_Post> list, Context context, int layout_id) {
        this.user = user;
        this.list = list;
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
        Forum_Post f= (Forum_Post) list.get(position);
        holder.representation(f);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        Context context;
        TextView title, discussion, comments;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context= itemView.getContext();
            title= itemView.findViewById(R.id.titulo);
            discussion= itemView.findViewById(R.id.topic);
            comments= itemView.findViewById(R.id.comments);
            imageView= itemView.findViewById(R.id.imagen);
        }
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

        }
    }
}
