package com.example.firebase_htalca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
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

public class ModificarPerfil extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser usuario;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    private TextView nombre,genero,edad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_perfil);

        mAuth = FirebaseAuth.getInstance();
        usuario = mAuth.getCurrentUser();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        nombre = findViewById(R.id.entradaNombre);
        genero = findViewById(R.id.entradaGenero);
        edad = findViewById(R.id.entradaEdad);
        DatabaseReference referenciaMensajes = FirebaseDatabase.getInstance()
                .getReference ("datos").child(usuario.getUid());



        referenciaMensajes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AlertDialog.Builder dialogo = new AlertDialog.Builder(ModificarPerfil.this);
                nombre.setText(snapshot.child("nombre").getValue(String.class));
                edad.setText(snapshot.child("edad").getValue(String.class));
                genero.setText(snapshot.child("genero").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    public void CambiarPerfil(View view) {

        int error = 0;

        if(nombre.getText().toString().length()==0){
            error++;
        }
        if(genero.getText().toString().length()==0){
            error++;
        }
        if(edad.getText().toString().length()==0){
            error++;
        }
        if(error>0){
            Toast.makeText(this, "Debe completar campos requeridos", Toast.LENGTH_LONG).show();
        }else{


            DatabaseReference referenciaMensajes = FirebaseDatabase.getInstance()
                    .getReference("regalos").child(usuario.getUid());
            reference.child("datos").child(usuario.getUid()).child("nombre").setValue(nombre.getText().toString());
            reference.child("datos").child(usuario.getUid()).child("edad").setValue(edad.getText().toString());
            reference.child("datos").child(usuario.getUid()).child("genero").setValue(genero.getText().toString());
            Toast.makeText(this, "Datos actualizados", Toast.LENGTH_LONG).show();



        }


    }


}