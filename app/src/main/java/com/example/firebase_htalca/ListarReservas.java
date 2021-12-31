package com.example.firebase_htalca;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListarReservas extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Adaptador adaptador;
    private ArrayList<Regalo> regalos;
    private FirebaseAuth mAuth;
    private FirebaseUser usuario;
    private FirebaseDatabase database;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_reservas);

        mAuth = FirebaseAuth.getInstance();
        usuario = mAuth.getCurrentUser();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        regalos = new ArrayList<Regalo>();

        DatabaseReference referenciaMensajes = FirebaseDatabase.getInstance()
                .getReference("regalos").child(usuario.getUid());

        referenciaMensajes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AlertDialog.Builder dialogo = new AlertDialog.Builder(ListarReservas.this);

                for (DataSnapshot child: snapshot.getChildren()) {
                    Log.i("MyTag", child.child("nombre").getValue(String.class));
                    Log.i("MyTag", child.child("foto").getValue(String.class));
                    Log.i("MyTag", child.child("idSecreto").getValue(String.class));
                    Log.i("MyTag", child.child("id_usuario").getValue(String.class));
                    //imagesDir.add(String.valueOf(child.getValue()));

                    regalos.add(new Regalo(child.child("nombre").getValue(String.class),
                            child.child("foto").getValue(String.class),
                            child.child("idSecreto").getValue(String.class),
                            child.child("id_usuario").getValue(String.class)));

                }

                adaptador = new Adaptador(regalos);
                recyclerView = findViewById(R.id.recycler);
                recyclerView.setLayoutManager(new LinearLayoutManager(ListarReservas.this));
                recyclerView.setAdapter(adaptador);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ListarReservas.this, "Regalo no encontrado", Toast.LENGTH_LONG).show();
            }
        });










    }
}