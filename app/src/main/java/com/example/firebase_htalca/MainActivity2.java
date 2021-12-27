package com.example.firebase_htalca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity2 extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser usuario;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    private TextView titulo, mensaje, idMensaje;

    private int contador;

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
        mensaje = findViewById(R.id.crearMensaje);
        idMensaje = findViewById(R.id.buscarMensaje);
    }

    public void CrearMensaje(View view){
        contador++;
        reference.child("mensajes").child(usuario.getUid()).child("mensaje_"+contador).child("titulo").setValue(titulo.getText().toString());
        reference.child("mensajes").child(usuario.getUid()).child("mensaje_"+contador).child("mensaje").setValue(mensaje.getText().toString());

        String claveUnica = reference.push().getKey();
        reference.child("mensajes").child(usuario.getUid()).child("mensaje_"+contador).child("idSecreto").setValue(claveUnica);

        Toast.makeText(this, "Mensaje creado con id: "+contador, Toast.LENGTH_LONG).show();

        titulo.setText("");
        mensaje.setText("");
    }

    public void BuscarMensaje(View view){

        DatabaseReference referenciaMensajes = FirebaseDatabase.getInstance()
                .getReference("mensajes").child(usuario.getUid())
                .child("mensaje_"+idMensaje.getText().toString());

        referenciaMensajes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AlertDialog.Builder dialogo = new AlertDialog.Builder(MainActivity2.this);

                dialogo.setTitle(snapshot.child("titulo").getValue(String.class))
                        .setMessage(snapshot.child("mensaje").getValue(String.class))
                        .setCancelable(true).create().show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity2.this, "Mensaje no encontrado", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void CerrarSesion(View view){
        mAuth.signOut();
        finish();
    }
}