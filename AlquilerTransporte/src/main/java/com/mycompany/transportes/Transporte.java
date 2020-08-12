/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.transportes;
import com.mycompany.gyerent.Coordenadas;
import utility.Utility;

/**
 *
 * @author Alejandro
 */
public abstract class Transporte {
    protected String codigoTransporte;
    protected double cantidadBateria;
    protected Coordenadas ubicacionActual;
    protected EstadoTransporte estado;
    protected double capacidadBateria;
    protected double costoPorMinuto;
    protected double consumoPorKm;
    
    //Constructor de un transporte dado la latitud y longitud (prueba) Agregar mas parametros?
    public Transporte(String codigoTransporte, double bateria, Double latitud, Double longitud ){
        //Predeterminado al crearse un transporte
        estado = EstadoTransporte.DISPONIBLE;
        capacidadBateria = 500;
        //Definido al momento de inicializar datos
        this.codigoTransporte = codigoTransporte;
        this.cantidadBateria = bateria;
        ubicacionActual = new Coordenadas(latitud,longitud);
    }
    
    //Metodos
    
    //Metodo para poder actualizar ubicacionActual al final de carga o prestamo
    public void actualizarUbicacion(Coordenadas ubicacionFinal){
        this.ubicacionActual = ubicacionFinal;
    }
    
    public void restarBateria(double distanciaRecorrida){
        double consumoBateria = distanciaRecorrida * consumoPorKm;
        //verificar por numeros negativos
        this.cantidadBateria -= Utility.redondearDecimales(consumoBateria,2);
        if(cantidadBateria<0){
            this.cantidadBateria=0;
        }
    }
    

    //Getters y Setters
    public Coordenadas getUbicacionActual() {
        return ubicacionActual;
    }

    public double getCantidadBateria() {
        return cantidadBateria;
    }

    public EstadoTransporte getEstado() {
        return estado;
    }


    public double getCapacidadBateria() {
        return capacidadBateria;
    }

    public String getCodigoTransporte() {
        return codigoTransporte;
    }

    public double getCostoPorMinuto() {
        return costoPorMinuto;
    }

    public double getConsumoPorKm() {
        return consumoPorKm;
    }

    public void setEstado(EstadoTransporte estado) {
        this.estado = estado;
    }

    public void setCantidadBateria(double cantidadBateria) {
        this.cantidadBateria = cantidadBateria;
    }
    
    
    
    //toString Arreglarlo para msotrar lo importante
    
    @Override
    public String toString() {
        String ubica= Coordenadas.Ubicar(ubicacionActual.getLatitud(),ubicacionActual.getLongitud());
        String[] nombreMostrar = ubica.split(",");
        return codigoTransporte + "              "+cantidadBateria+"              "+ nombreMostrar[0] +"\n"+
                "---------------------------------------------------------------------------";
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if(obj==null){
            return false;
        }else if (obj.getClass()!= getClass()){
            return false;
        }else{
            Transporte transporte = (Transporte) obj;
            return codigoTransporte.equals(transporte.getCodigoTransporte());
        }  
    }   
}
