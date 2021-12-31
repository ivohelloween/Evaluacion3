package com.example.firebase_htalca;

import android.util.Log;

import java.io.Serializable;

public class Regalo implements Serializable {
    private String nombre;
    private String foto;
    private String idSecreto;
    private String id_usuario;


    public Regalo(String nombre, String foto, String idSecreto, String id_usuario) {
        this.nombre = nombre;
        this.foto = foto;
        this.idSecreto = idSecreto;
        this.id_usuario = id_usuario;
    }


    public Regalo() {
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getIdSecreto() {
        return idSecreto;
    }

    public void setIdSecreto(String idSecreto) {
        this.idSecreto = idSecreto;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }
}
