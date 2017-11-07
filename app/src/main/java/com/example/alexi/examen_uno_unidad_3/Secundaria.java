package com.example.alexi.examen_uno_unidad_3;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Secundaria extends AppCompatActivity {
    ListView lv;
    static ArrayList<Dato> lista=new ArrayList<>();
    Dato Dato;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secundaria);
        actualizarLista();
        lv = (ListView)findViewById(R.id.listView);
    }
    @Override
    protected void onResume() {
        super.onResume();
        cargarArchivo();
        actualizarLista();
    }

    public void cargarArchivo(){
        try{
            File file=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"obj.obj");
            if(hasExternalStorage()&&file.exists()){
                ObjectInputStream stream=new ObjectInputStream(new FileInputStream(file));
                lista= (ArrayList<Dato>) stream.readObject();
                stream.close();
            }

        }catch(Exception e){}

    }
    public void actualizarLista(){
        ArrayList<String> datos=new ArrayList<>();
        for (Dato c:lista){
            datos.add(c.dato1);
        }
        ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,datos);
        lv.setAdapter(adapter);


    }

    public boolean hasExternalStorage(){
        String status= Environment.getExternalStorageState();
        if(status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }
}