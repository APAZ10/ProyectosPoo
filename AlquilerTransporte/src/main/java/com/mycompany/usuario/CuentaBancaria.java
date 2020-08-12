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
public class CuentaBancaria {
    String nombreBanco;
    String numCuenta;
    
    public CuentaBancaria(String nombreBanco, String numCuenta){
        this.nombreBanco = nombreBanco;
        this.numCuenta = numCuenta;
    }

    @Override
    public String toString() {
        return  "Numero de cuenta:"+numCuenta+" del banco del "+nombreBanco;
    }

   
    
}
