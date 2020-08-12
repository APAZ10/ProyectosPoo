/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usuario;
import com.mycompany.transportes.Carga;
import com.mycompany.transportes.DistanciaTransporte;
import com.mycompany.transportes.EstadoTransporte;
import java.util.ArrayList;
import com.mycompany.transportes.Transporte;
import java.util.Collections;
/**
 *
 * @author domen
 */
public class UsuarioCharger extends Usuario{
    
    
    private ArrayList<CuentaBancaria> cuentasBancarias;

    //Constructor de prueba
    public UsuarioCharger(String nombre, String direccion, String username, String password){
        super(nombre,direccion,username,password);
        cuentasBancarias = new ArrayList<>();
    }
    
    
    @Override
    public void verMenu(){
        System.out.println("1. Buscar Dispositivo");
        System.out.println("2. Cargar transporte");
        System.out.println("3. Terminar de cargar Transporte");
        System.out.println("4. Ver Reportes de Cargas");
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
                    if(distanciaTransporte.getDistancia()==distancia && transporte.getEstado()==EstadoTransporte.DISPONIBLE 
                            && transporte.getCantidadBateria() < 0.2*transporte.getCapacidadBateria() && !transportesMostrados.contains(transporte)){
                        transportesMostrados.add(transporte);
                        System.out.println(transporte);
                    }
                }    
            }
        }
    }
    
    @Override
    public void inicioAccion(Transporte transporteObtenido){
        this.accionRealizando = new Carga();
        this.ocupaUnTransporte = true;
        this.transporteUsando = transporteObtenido;
        transporteObtenido.setEstado(EstadoTransporte.OCUPADO);
    }
    
    
    @Override
    public void verReportes(){
        System.out.println("Fecha          "+"Codigo vehiculo        "+"Monto a cobrar  "+"\n"+
                "--------------------------------------------------------------------------------\n");
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

    public ArrayList<CuentaBancaria> getCuentasBancarias() {
        return cuentasBancarias;
    }
    
}
