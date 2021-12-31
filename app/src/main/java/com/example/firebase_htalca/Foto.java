package com.example.firebase_htalca;

import java.io.Serializable;

public class Foto implements Serializable {

    private SerializableBitmap bitmap;
    private String url;
    public Foto() {
        url = "";
        bitmap = null;
    }




    public SerializableBitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(SerializableBitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
