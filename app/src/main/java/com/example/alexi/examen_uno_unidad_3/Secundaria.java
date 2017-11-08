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
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Secundaria extends AppCompatActivity {
    ListView lv;
    Button btnCalcular;
    Dato Dato;
    Double dato1, dato2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secundaria);
        lv = (ListView)findViewById(R.id.listView);
        btnCalcular = (Button)findViewById(R.id.btnCalcular);
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tamano;
                tamano = Principal.lista.size();
                if(tamano>=5){
                    Dato num1,num2,num3;
                    int p1,p2,p3;

                    p1 = Principal.lista.size()-1;
                    p2 = Principal.lista.size()-3;
                    p3 = Principal.lista.size()-5;
                    num1 = Principal.lista.get(p1);
                    num2 = Principal.lista.get(p2);
                    num3 = Principal.lista.get(p3);

                    double res = (((num1.dato1 * num2.dato2) / (num3.dato1 + num3.dato2 - num1.dato2)) * num2.dato1);

                    Toast.makeText(getApplicationContext(), "" + res, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Secundaria.this, "Neceita  5 datos para calcular, tiene: "+tamano, Toast.LENGTH_SHORT).show();
                }

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),Editar.class);
                Dato = Principal.lista.get(position);
                dato1 = Dato.dato1;
                dato2 = Dato.dato2;
                String uno,dos;
                uno = String.valueOf(dato1);
                dos = String.valueOf(dato2);
                intent.putExtra("DATO1", uno);
                intent.putExtra("DATO2", dos);
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
            File file=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"obje.obje");
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
            datos.add("Dato 1: "+c.dato1.toString()+" Dato 2: "+c.dato2);
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