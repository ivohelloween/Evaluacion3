package com.example.firebase_htalca;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;

public class MostrarNoticia extends AppCompatActivity {
    private Regalo regalo;
    private TextView titulo, texto;
    private Button textoUbicacion;
    private ImageView fotito;
    private ArrayList<Noticias> listaNoticias;
    private Foto foto;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_noticia);

        regalo = (Regalo) getIntent().getSerializableExtra("regalo");

        titulo = findViewById(R.id.entradaFecha);
        fotito = findViewById(R.id.fotografia2);
        texto= findViewById(R.id.texto);

        titulo.setText(regalo.getIdSecreto());
        texto.setText(regalo.getNombre());

        fotito.setImageURI(Uri.fromFile(new File(regalo.getFoto())));
        //fotito.setImageBitmap(noticia.getFoto().getBitmap().getBitmap() );


    }




}