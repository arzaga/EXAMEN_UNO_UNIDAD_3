package com.example.alexi.examen_uno_unidad_3;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Secundaria extends AppCompatActivity {
    ListView lv;
    Button btnCalcular;
    Dato Dato;
    String dato1, dato2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secundaria);
        lv = (ListView)findViewById(R.id.listView);
        btnCalcular = (Button)findViewById(R.id.btnCalcular);
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),Editar.class);
                Dato = Principal.lista.get(position);
                dato1 = Dato.dato1;
                dato2 = Dato.dato2;
                intent.putExtra("DATO1", dato1);
                intent.putExtra("DATO2", dato2);
                intent.putExtra("posicion", position);
                startActivity(intent);


            }
        });
        cargarArchivo();
        actualizarLista();
    }
    @Override
    protected void onResume() {
        super.onResume();
        cargarArchivo();
        actualizarLista();
    }

    public void cargarArchivo(){
        try{
            File file=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),"obje.obje");
            if(hasExternalStorage()&&file.exists()){
                ObjectInputStream stream=new ObjectInputStream(new FileInputStream(file));
                Principal.lista= (ArrayList<Dato>) stream.readObject();
                stream.close();
            }

        }catch(Exception e){}

    }
    public void actualizarLista(){
        ArrayList<String> datos=new ArrayList<>();
        for (Dato c: Principal.lista){
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