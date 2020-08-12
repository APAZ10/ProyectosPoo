/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.transportes;

/**
 *
 * @author Alejandro
 */
public class Bicicleta extends Transporte {
    
    
    
    //Metodos 
    public Bicicleta(String codigoTransporte, double bateria, Double latitud, Double longitud){
        super(codigoTransporte, bateria, latitud, longitud);
        consumoPorKm = 7;
        costoPorMinuto = 0.20;
    }

    @Override
    public String toString(){
        return "Tipo                 "+"Codigo          "+"Bateria             "+"Ubicacion"
                +"\n"+"Bicicleta             "+super.toString();
    }
       
}
