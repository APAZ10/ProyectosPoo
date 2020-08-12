/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usuario;

import com.mycompany.gyerent.Reporte;
import com.mycompany.transportes.AccionUsuario;
import com.mycompany.transportes.DistanciaTransporte;
import java.util.ArrayList;
import com.mycompany.transportes.Transporte;
import java.time.LocalDateTime;

/**
 *
 * @author domen
 */
public abstract class Usuario{
    protected String nombre;
    protected String direccion;
    protected String userName;
    protected String password;
    protected boolean ocupaUnTransporte;
    
    protected Transporte transporteUsando;
    protected Reporte reporte;
    
    protected AccionUsuario accionRealizando;
    

    //Constructor de Prueba
    public Usuario(String nombre, String direccion, String username, String password) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.userName = username;
        this.password = password;
        ocupaUnTransporte = false;
        transporteUsando = null;
        reporte = new Reporte();
        
    }
    
    public abstract void verMenu();
    
    //Metodo que obtiene los transportes cercanos al usuario y muestra solo los que estan disponibles segun el tipo de usuario

    public abstract void dispositivosDisponibles(ArrayList<DistanciaTransporte> transportesCercanos);
    
    //Metodo para Iniciar accion segun tipo de usuario
    public abstract void inicioAccion(Transporte transporteObtenido);
    
    public void finalizaAccion(){
        accionRealizando.setFinalizaAccion(LocalDateTime.now());
    }
    
    
    public void agregarReporte(String info){
        reporte.getRegistros().add(info);
    }
    
    //modificar
    public abstract void verReportes();
    
    
   
    //Getters y Setters
    public String getUserName(){
        return userName;
    }

    public String getPassword(){
        return password;
    }

    public Transporte getTransporteUsando() {
        return transporteUsando;
    }

    public AccionUsuario getAccionRealizando() {
        return accionRealizando;
    }

    public Reporte getReporte() {
        return reporte;
    }
    

    public boolean isOcupaUnTransporte() {
        return ocupaUnTransporte;
    }
    
    public void setOcupaUnTransporte(boolean ocupaUnTransporte) {
        this.ocupaUnTransporte = ocupaUnTransporte;
    }
    
    
    public void setTransporteUsando(Transporte transporteUsando) {
        this.transporteUsando = transporteUsando;
    }

    public void setReporte(Reporte reporte) {
        this.reporte = reporte;
    }

       
    
}
