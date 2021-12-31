package com.example.firebase_htalca;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser usuario;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    private TextView titulo;
    private TextView idMensaje;
    private int contador;


    private ImageView fotito;
    private Foto foto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mAuth = FirebaseAuth.getInstance();
        usuario = mAuth.getCurrentUser();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        contador = 0;
        titulo = findViewById(R.id.crearTitulo);
        //idMensaje = findViewById(R.id.buscarMensaje);

        fotito = findViewById(R.id.fotografia);


        foto = new Foto();






    }

    public void CrearMensaje(View view){



        int error = 0;
        if(titulo.getText().toString().length()==0){
            error++;
        }
        if(foto.getUrl().length()==0){
            error++;
        }

        if(error>0){
            Toast.makeText(this,"Debe completar campos requeridos",Toast.LENGTH_LONG).show();
        }else{
            String claveUnica = reference.push().getKey();
            //contador++;
            //contador =(int) referenciaMensajes.get().getResult().getChildrenCount();


            reference.child("regalos").child(usuario.getUid()).child("regalo_"+claveUnica).child("nombre").setValue(titulo.getText().toString());
            reference.child("regalos").child(usuario.getUid()).child("regalo_"+claveUnica).child("id_usuario").setValue(usuario.getUid());
            reference.child("regalos").child(usuario.getUid()).child("regalo_"+claveUnica).child("foto").setValue(foto.getUrl());


            reference.child("regalos").child(usuario.getUid()).child("regalo_"+claveUnica).child("idSecreto").setValue(claveUnica);

            Toast.makeText(this, "Regalo agregado con id: "+claveUnica, Toast.LENGTH_LONG).show();

            titulo.setText("");

            foto.setUrl("");
        }



    }
/*
    public void BuscarMensaje(View view){

        DatabaseReference referenciaMensajes = FirebaseDatabase.getInstance()
                .getReference("regalos").child(usuario.getUid())
                .child("regalo_"+idMensaje.getText().toString());

        referenciaMensajes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AlertDialog.Builder dialogo = new AlertDialog.Builder(MainActivity2.this);

                dialogo.setTitle(snapshot.child("titulo").getValue(String.class))
                        .setMessage(snapshot.child("regalo").getValue(String.class))
                        .setCancelable(true).create().show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity2.this, "Regalo no encontrado", Toast.LENGTH_LONG).show();
            }
        });
    }
*/
    public void CerrarSesion(View view){
        mAuth.signOut();
        finish();
    }

    public void CargarFoto(View view) {

        Intent intent = new Intent(this, CargarFoto.class);
        startActivityForResult(intent, 200);


    }

    public void ModificarPerfil(View view) {

        Intent intent = new Intent(this, ModificarPerfil.class);
        startActivity(intent);
    }

    public void ListarRegalos(View view) {
        StringBuilder str = new StringBuilder();
        /*if(listaNoticias.size()==0){
            Toast.makeText(MainActivity2.this, "No hay noticias ingresadas.", Toast.LENGTH_LONG).show();
        }else {*/

            Intent intent = new Intent(this, ListarReservas.class);

            startActivity(intent);
        /*}*/

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == 200){
            if(resultCode == RESULT_OK){

                foto = (Foto) data.getSerializableExtra("foto");

                if(foto.getBitmap()!=null){
                    fotito.setImageBitmap(foto.getBitmap().getBitmap() );

                }


            }
        }



    }


    public void cambiarDatos(View view) {
    }
}