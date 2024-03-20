package com.example.animanga_universe.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
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
import com.example.animanga_universe.activities.EditarItem;
import com.example.animanga_universe.clases.Usuario;
import com.example.animanga_universe.encapsuladores.Encapsulador;

import java.util.ArrayList;

public class AdaptadorLista extends RecyclerView.Adapter<AdaptadorLista.ViewHolder> {
        Usuario usuario;
        String busqueda;
        ArrayList<?> listado;
        Context context;
        int layout_id;
        View.OnClickListener onClickListener;
public void setOnClickListener( View.OnClickListener onClickListener){
        this.onClickListener= onClickListener;
        }

public AdaptadorLista(Usuario usuario,ArrayList<?> listado, Context context, int layout_id, String busqueda) {
        this.usuario= usuario;
        this.listado = listado;
        this.context = context;
        this.layout_id = layout_id;
        this.busqueda= busqueda;
        }


@NonNull
@Override
public AdaptadorLista.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View elemento= LayoutInflater.from(parent.getContext()).inflate(R.layout.element_lista_usuario,parent,false);
        elemento.setOnClickListener(onClickListener);
        return new ViewHolder(elemento);
        }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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

            holder.botonPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

    }


    @Override
public int getItemCount() {
        return listado.size();
        }

    public static class ViewHolder extends RecyclerView.ViewHolder{
    Context context;
    TextView titulo, info;
    ImageView imagen;
    ProgressBar progressBar;
    AppCompatImageButton boton, botonPlus;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        context= itemView.getContext();
        titulo= itemView.findViewById(R.id.titulo);
        info= itemView.findViewById(R.id.infoAdicional);
        progressBar= itemView.findViewById(R.id.progressBar);
        botonPlus= itemView.findViewById(R.id.botonPlus);
        imagen= itemView.findViewById(R.id.imagen);
        boton= itemView.findViewById(R.id.botonEditar);

    }
    public void representacionElementos(Encapsulador e){
        titulo.setText(e.getTitulo());
        info.setText(e.getInfo());
        progressBar.setProgress(e.getProgreso());
        imagen.setImageDrawable(e.getImagen());
        boton.setBackgroundColor(e.getColor());
    }
}

}

