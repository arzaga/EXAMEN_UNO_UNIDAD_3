package com.example.alexi.examen_uno_unidad_3;

import java.io.Serializable;

/**
 * Created by alexi on 06/11/2017.
 */

public class Dato implements Serializable{
    String dato1, dato2;

    public Dato(String dato1, String... datos){
        this.dato1 = dato1;
        dato2 = datos[0];
    }
}
