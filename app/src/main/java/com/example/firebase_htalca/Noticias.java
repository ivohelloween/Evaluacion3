package com.example.firebase_htalca;

import java.io.Serializable;

public class Noticias implements Serializable {
    private String titulo;
    private String texto;

    private Foto foto;

    public Noticias(String titulo, String texto,  Foto foto) {
        this.titulo = titulo;
        this.texto = texto;

        this.foto = foto;
    }


    public Noticias() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }



    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }
}
