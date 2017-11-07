package com.example.alexi.examen_uno_unidad_3;

import java.io.Serializable;

/**
 * Created by alexi on 06/11/2017.
 */

public class Dato implements Serializable{
    Double dato1, dato2;

    public Dato(Double dato1, Double... datos){
        this.dato1 = dato1;
        dato2 = datos[0];
    }


}
