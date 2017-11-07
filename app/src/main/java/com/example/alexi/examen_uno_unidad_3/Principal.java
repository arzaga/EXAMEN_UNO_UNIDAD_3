package com.example.alexi.examen_uno_unidad_3;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Principal extends AppCompatActivity {
EditText edtDato1,edtDato2;
    Button btnAgregar,btnSecundaria;
    int posicion = -1;
    Dato dato;
    static ArrayList<Dato> lista=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        edtDato1 = (EditText)findViewById(R.id.edtDato1);
        edtDato2 = (EditText)findViewById(R.id.edtDato2);
        btnAgregar = (Button)findViewById(R.id.btnAgregar);
        btnSecundaria = (Button)findViewById(R.id.btnSecundaria);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (posicion == -1) {
                    if (!edtDato1.getText().toString().isEmpty() && !edtDato2.getText().toString().isEmpty()) {
                        lista.add(new Dato(Double.parseDouble(edtDato1.getText().toString()), Double.parseDouble(edtDato2.getText().toString())));
                        guardarArchivo();
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Dejó un campo Vacío", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }else{
                    dato = lista.get(posicion);
                    Dato d;
                    int p = lista.size();
                    d = lista.get(p-1);
                    if(d.dato1== Double.parseDouble(edtDato1.getText().toString()) || d.dato2== Double.parseDouble(edtDato2.getText().toString())){
                        Toast.makeText(Principal.this, "Datos Repetidos", Toast.LENGTH_SHORT).show();
                    }else{
                        dato.dato1 = Double.parseDouble(edtDato1.getText().toString());
                        dato.dato2 = Double.parseDouble(edtDato2.getText().toString());
                        guardarArchivo();
                        posicion = 0;
                    }

                }
            }
        });

        btnSecundaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intSecundaria = new Intent(getApplicationContext(),Secundaria.class);
                startActivity(intSecundaria);
            }
        });
    }

    public void guardarArchivo() {
        try {
            if (hasExternalStorage()) {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "obje.obje");
                ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file,false));
                stream.writeObject(lista);
                stream.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean hasExternalStorage(){
        String status= Environment.getExternalStorageState();
        if(status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }
}
