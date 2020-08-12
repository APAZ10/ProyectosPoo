/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gyerent;

import java.util.ArrayList;

/**
 *
 * @author User
 */
public class Reporte {
    private ArrayList<String> registros;
    
    public Reporte(){
        registros = new ArrayList<>();
    }
    
    
    public Reporte(Reporte r){
        registros= r.registros;
    }


    
    public ArrayList<String> getRegistros() { 
        return registros;
    }
    

}
