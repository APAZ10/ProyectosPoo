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
public class Scooter extends Transporte{

    public Scooter(String codigoTransporte, double bateria, Double latitud, Double longitud){
        super(codigoTransporte, bateria,latitud,longitud);
        consumoPorKm = 10;
        costoPorMinuto = 0.15;
    }
   @Override
    public String toString() {
        return "Tipo                 "+"Codigo          "+"Bateria             "+"Ubicacion"
                +"\n"+"Scooter               "+super.toString();
    } 
    
}
