package com.example.firebase_htalca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    TextView correo, contraseña;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        correo = findViewById(R.id.entradaCorreo);
        contraseña = findViewById(R.id.entradaContraseña);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser usuarioActual = mAuth.getCurrentUser();

        if(usuarioActual != null){
            Toast.makeText(this, "Existe un usuario activo (correo: "+usuarioActual.getEmail()+")", Toast.LENGTH_LONG).show();
            LanzarActivity();
        } else {
            Toast.makeText(this, "No existen usuarios activos", Toast.LENGTH_LONG).show();
        }
    }

    public void IniciarSesion(View view){
        mAuth.signInWithEmailAndPassword(correo.getText().toString(), contraseña.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser usuarioActivo = mAuth.getCurrentUser();
                    Toast.makeText(MainActivity.this, "Usuario activo ("+usuarioActivo.getEmail()+")", Toast.LENGTH_LONG).show();
                    LanzarActivity();
                } else{
                    Toast.makeText(MainActivity.this, "No fue posible iniciar sesión (compruebe el correo y contraseña)", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void RegistrarUsuario(View view){
        mAuth.createUserWithEmailAndPassword(correo.getText().toString(), contraseña.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser usuarioNuevo = mAuth.getCurrentUser();
                    Toast.makeText(MainActivity.this, "Usuario creado: "+usuarioNuevo.getUid(), Toast.LENGTH_LONG).show();
                    LanzarActivity();
                } else {
                    Toast.makeText(MainActivity.this, "No se pudo crear la cuenta", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void LanzarActivity(){
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
}