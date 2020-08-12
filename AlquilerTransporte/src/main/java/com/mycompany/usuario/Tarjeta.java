/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usuario;

/**
 *
 * @author Alejandro
 */
public class Tarjeta {
    private final String numeroTarjeta;
    private final String tipoTarjeta;
    private final String nombreTitular;

    public Tarjeta(String numeroTarjeta, String tipoTarjeta, String nombreTitular) {
        this.numeroTarjeta = numeroTarjeta;
        this.tipoTarjeta = tipoTarjeta;
        this.nombreTitular = nombreTitular;
    }

    @Override
    public String toString() {
        return "Tipo de tarjeta: "+tipoTarjeta+", Numero de tarjeta: "+numeroTarjeta+ ", Titular: "+ nombreTitular;
    }
    
}
