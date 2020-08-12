/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usuario;

import com.mycompany.transportes.DistanciaTransporte;
import com.mycompany.transportes.EstadoTransporte;
import com.mycompany.transportes.Prestamo;
import java.util.ArrayList;
import com.mycompany.transportes.Transporte;
import java.util.Collections;

/**
 *
 * @author domen
 */
public class UsuarioRider extends Usuario{
    //Implementar clase Tarjeta y metodo para poder registrar tarjeta/metodo para pedir la primera tarjeta al registrarse
    private ArrayList<Tarjeta> tarjetas;

    
    
    //constructor de prueba
    public UsuarioRider(String nombre, String direccion, String username, String password){
        super(nombre,direccion,username,password);
        tarjetas = new ArrayList<>();
    }
    
    @Override
    public void verMenu(){
        System.out.println("1. Buscar Dispositivo");
        System.out.println("2. Prestar Transporte");
        System.out.println("3. Devolver Transporte");
        System.out.println("4. Ver Reportes de Préstamos");
        System.out.println("5. Cerrar Sesión");
    }
    

    @Override
    public void dispositivosDisponibles(ArrayList<DistanciaTransporte> transportesCercanos){
        if(transportesCercanos.isEmpty()){
            System.out.println("No se han encontrado dispositivos disponibles");
        } else {
            ArrayList<Double> distancias = DistanciaTransporte.getDistancias();
            Collections.sort(distancias);
            
            ArrayList<Transporte> transportesMostrados = new ArrayList<>();
            for(Double distancia: distancias){
                for(DistanciaTransporte distanciaTransporte: transportesCercanos){      
                    Transporte transporte = distanciaTransporte.getTransporte();
                    if(distanciaTransporte.getDistancia()==distancia && transporte.getEstado()==EstadoTransporte.DISPONIBLE && !transportesMostrados.contains(transporte) 
                            && transporte.getCantidadBateria()>0){
                        transportesMostrados.add(transporte);
                        System.out.println(transporte);
                    }
                }   
            }
        }
    }
    
    @Override
    public void inicioAccion(Transporte transporteObtenido){
        this.accionRealizando = new Prestamo();
        this.ocupaUnTransporte = true;
        this.transporteUsando = transporteObtenido;
        transporteObtenido.setEstado(EstadoTransporte.OCUPADO);
    }
    
    
    //Getters y Setters
    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public ArrayList<Tarjeta> getTarjetas() {
        return tarjetas;
    }
    
    public void verReportes(){
        System.out.println("Fecha                  "+"Codigo vehiculo             "+"Costo transaccion                       "+"Tiempo de uso                  "+"Distancia recorrida"+"\n"+
                "-------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        
    }
    
    
}
