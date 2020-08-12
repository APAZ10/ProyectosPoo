/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gyerent;

import java.util.ArrayList;
import com.mycompany.usuario.Usuario;
import com.mycompany.transportes.Transporte;

/**
 *
 * @author User
 */
public class dataBase {
    protected ArrayList<Usuario> usuarios;
    protected ArrayList<Transporte> transportes;
    protected ArrayList<Reporte> reportes;
    

    public dataBase() {
        usuarios = new ArrayList<>();
        transportes = new ArrayList<>();
        reportes = new ArrayList<>();
    }
    
}

