/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.transportes;

import java.util.ArrayList;

/**
 *
 * @author Alejandro
 */
public class DistanciaTransporte {
    private final Transporte transporte;
    private final double distancia;
    //publica u otro acc mod
    private static final ArrayList<Double> distancias = new ArrayList<>();
    
    public DistanciaTransporte(Transporte transporte, double distancia){
        this.transporte = transporte;
        this.distancia = distancia;
    }
    
    
    //Getter y Setter
    public Transporte getTransporte() {
        return transporte;
    }

    public double getDistancia() {
        return distancia;
    }

    public static ArrayList<Double> getDistancias() {
        return distancias;
    }
    
    
}
