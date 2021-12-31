package com.example.firebase_htalca;

import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder>{

    public ArrayList<Regalo> regalos;
    public Adaptador(ArrayList<Regalo> regalos){
        this.regalos = regalos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarjeta, parent, false);
        return new ViewHolder(view).enlaceAdaptador(this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.foto.setImageResource(R.drawable.ic_launcher_background);
        holder.titulo.setText( regalos.get(position).getIdSecreto());
        holder.texto.setText(regalos.get(position).getNombre());

       // holder.foto.setImageBitmap(noticias.get(position).getFoto().getBitmap().getBitmap() );


       // holder.texto.setText(reservas.get(position).getTipoCancha());
        holder.foto.setImageURI(Uri.fromFile(new File(regalos.get(position).getFoto())));

        holder.regalo = regalos.get(position);

        holder.foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(holder.foto.getContext(), "asdf", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return regalos.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView foto;
        private TextView titulo, texto,tipoCancha;
        private Button botonNumero, botonEliminar;
        private Regalo regalo;
        private Adaptador adaptador;

        public ViewHolder(View itemView){
            super(itemView);

            foto = itemView.findViewById(R.id.foto);
            titulo = itemView.findViewById(R.id.titulo);
            texto = itemView.findViewById(R.id.texto);
            tipoCancha = itemView.findViewById(R.id.tipoCancha);
            botonNumero = itemView.findViewById(R.id.botonNumeros);
            botonEliminar = itemView.findViewById(R.id.botonEliminar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Evento click sobre item", Toast.LENGTH_LONG).show();
                }
            });

            botonNumero.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    StringBuilder sb =new StringBuilder();
                    sb.append("----Regalo----");
                        sb.append("\n ID:"+adaptador.regalos.get(getAdapterPosition()).getIdSecreto());
                    sb.append("\n Nombre:"+adaptador.regalos.get(getAdapterPosition()).getNombre());


                    Toast.makeText(view.getContext(), sb.toString(), Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(view.getContext(), MostrarNoticia.class);
                    intent.putExtra("regalo",   adaptador.regalos.get(getAdapterPosition()) );
                    view.getContext().startActivity(intent);

                }
            });

            botonEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adaptador.regalos.remove(getAdapterPosition());
                    adaptador.notifyItemRemoved(getAdapterPosition());
                    adaptador.notifyItemRangeChanged(getAdapterPosition(), adaptador.regalos.size());
                }
            });

        }

        public ViewHolder enlaceAdaptador(Adaptador a){
            this.adaptador = a;
            return this;
        }
    }

}
