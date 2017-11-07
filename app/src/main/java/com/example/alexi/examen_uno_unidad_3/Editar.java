package com.example.alexi.examen_uno_unidad_3;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Editar extends AppCompatActivity {
    EditText edtDATO1,edtDATO2;
    Button btnGUARDAR,btnBORRAR;
    int posicion = -1;
    Dato dato;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        edtDATO1 = (EditText)findViewById(R.id.edtDATO1);
        edtDATO2 = (EditText)findViewById(R.id.edtDATO2);
        btnGUARDAR = (Button)findViewById(R.id.btnGUARDAR);
        btnBORRAR = (Button)findViewById(R.id.btnBORRAR);

        try{
            edtDATO1.setText(getIntent().getExtras().getString("DATO1"));
            edtDATO2.setText(getIntent().getExtras().getString("DATO2"));
            posicion = getIntent().getExtras().getInt("posicion");
        }catch (Exception e){

        }

        btnBORRAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Principal.lista.remove(posicion);
                guardarArchivo();
                Intent intent = new Intent(Editar.this,Principal.class);
                startActivity(intent);
                finish();
            }
        });
btnGUARDAR.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if (posicion == -1) {
            if (!edtDATO1.getText().toString().isEmpty() && !edtDATO2.getText().toString().isEmpty()) {
                Principal.lista.add(new Dato(edtDATO1.getText().toString(), edtDATO2.getText().toString()));
                guardarArchivo();
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Dejó un campo Vacío", Toast.LENGTH_SHORT);
                toast.show();
            }
        }else{
            dato = Principal.lista.get(posicion);
            dato.dato1 = edtDATO1.getText().toString();
            dato.dato2 = edtDATO2.getText().toString();
            guardarArchivo();
            posicion = 0;
        }
        Intent intPrin = new Intent(getApplicationContext(),Principal.class);
        startActivity(intPrin);
        finish();
    }
});
    }
    public void guardarArchivo() {
        try {
            if (hasExternalStorage()) {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "obje.obje");
                ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file,false));
                stream.writeObject(Principal.lista);
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
