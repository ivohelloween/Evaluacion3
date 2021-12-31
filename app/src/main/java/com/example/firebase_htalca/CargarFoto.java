package com.example.firebase_htalca;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class CargarFoto extends AppCompatActivity {

    private final String[] permisos = { Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private final int ACTIVITY_CAMARA = 50;
    private final int ACTIVITY_GALERIA = 60;
    private ImageView fotito;
    private Bitmap bitmap;
    private Foto foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar_foto);


        fotito = findViewById(R.id.fotografia);

        fotito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CargarFoto.this, "Tocaste la foto", Toast.LENGTH_SHORT).show();
            }
        });

        //aquí pedimos permisos
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            requestPermissions(permisos, 100);
        }

        bitmap = null;
        foto = new Foto();

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 100){

            if(!(grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                Toast.makeText(this, "Se necesita permiso de cámara", Toast.LENGTH_LONG).show();
            }

            if(!(grantResults[1] == PackageManager.PERMISSION_GRANTED)){
                Toast.makeText(this, "Se necesita permiso de lectura de memoria", Toast.LENGTH_LONG).show();
            }

            if(!(grantResults[2] == PackageManager.PERMISSION_GRANTED)){
                Toast.makeText(this, "Se necesita permiso de escritura de memoria", Toast.LENGTH_LONG).show();
            }
        }

    }

    public void TomarFoto(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, ACTIVITY_CAMARA);
    }


    public void CargarFoto(View view){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent, ACTIVITY_GALERIA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SerializableBitmap sbitmap;
        switch (requestCode){
            case ACTIVITY_CAMARA:
                if(resultCode == RESULT_OK){
                    bitmap = (Bitmap) data.getExtras().get("data");
                    fotito.setImageBitmap(bitmap);
                    sbitmap = new SerializableBitmap(bitmap);
                    foto.setBitmap(sbitmap);
                    String url = GurdarFoto(bitmap);
                    foto.setUrl(url);
                }
                break;

            case ACTIVITY_GALERIA:
                if(resultCode == RESULT_OK){
                    Uri ruta = data.getData();
                    fotito.setImageURI(ruta);

                    sbitmap = new SerializableBitmap(bitmap);
                    foto.setBitmap(sbitmap);
                    BitmapDrawable drawable = (BitmapDrawable) fotito.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    sbitmap = new SerializableBitmap(bitmap);
                    foto.setBitmap(sbitmap);

                    String url = GurdarFoto(bitmap);
                    foto.setUrl(url);
                }
                break;
        }


    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(CargarFoto.this.getContentResolver(), inImage, UUID.randomUUID().toString() + ".png", "drawing");
        return Uri.parse(path);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

        dialogo.setTitle("Confirmación")
                .setMessage("¿Desea enviar la foto?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent resultado = new Intent();
                        //resultado.putExtra("foto", foto);
                        /*
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();*/

                        //resultado.putExtra("bitmap", bitmap);


                        resultado.putExtra("foto", foto);


                        setResult(RESULT_OK, resultado);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();



    }


    public String GurdarFoto(Bitmap bitmap) {
        String url = "";
        String filename= System.currentTimeMillis()+"_fotoPrueba.jpg";
        File sd = Environment.getExternalStorageDirectory();
        File dest = new File(sd, filename);



        try {
            FileOutputStream out = new FileOutputStream(dest);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            url = dest.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return url;
    }

    public String GurdarrFoto(){
        String url="";

        if(bitmap!=null){
            File archivoFoto =null;
            OutputStream streamSalida = null;

            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
                ContentResolver resolver = getContentResolver();
                ContentValues values = new ContentValues();


                String nombreArchivo=System.currentTimeMillis()+"_fotoPrueba";

                values.put( MediaStore.Images.Media.DISPLAY_NAME,nombreArchivo);
                values.put( MediaStore.Images.Media.MIME_TYPE,"Image/jpg");
                values.put( MediaStore.Images.Media.RELATIVE_PATH,"Picture/MyApp");
                values.put( MediaStore.Images.Media.IS_PENDING,1);
                Uri coleccion = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
                Uri fotoUri = resolver.insert(coleccion,values);
                try {
                    streamSalida=resolver.openOutputStream(fotoUri);
                }catch(FileNotFoundException e){
                    e.printStackTrace();
                }

                values.clear();
                values.put(MediaStore.Images.Media.IS_PENDING,0);

                resolver.update(fotoUri,values,null,null);

               // url=fotoUri.toString();
            }else{
                String ruta = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
                String nombreArchivo= System.currentTimeMillis()+"_fotoPrueba.jpg";
                try{
                    streamSalida = new FileOutputStream(archivoFoto);
                }catch(FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            boolean fotoOk=bitmap.compress(Bitmap.CompressFormat.JPEG,100,streamSalida);

            if(fotoOk){
                Toast.makeText(this,"Foto Guardada",Toast.LENGTH_LONG).show();
            }
            if(streamSalida!=null){
                try {
                    streamSalida.flush();
                    streamSalida.close();
                }catch(IOException e) {
                    e.printStackTrace();
                }



            }

            if(archivoFoto!=null){
                MediaScannerConnection.scanFile(this,new String[]{archivoFoto.toString()}, null, null);
            }
            url=archivoFoto.toString();

        }else{
            Toast.makeText(this,"Primero debe tomar una foto antes de usar esta opcion",Toast.LENGTH_LONG).show();
        }

        return url;

    }





}