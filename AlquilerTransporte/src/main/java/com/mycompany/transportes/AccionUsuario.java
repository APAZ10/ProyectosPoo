/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.transportes;

import com.mycompany.gyerent.Coordenadas;
import com.mycompany.usuario.Usuario;
import java.time.LocalDateTime;

/**
 *
 * @author Alejandro
 */
public abstract class AccionUsuario {
    protected LocalDateTime inicioAccion;
    protected LocalDateTime finalizaAccion;
    protected String codigoTransporteUsando;
    protected double dineroTransaccion;
    
 
    /**
     * Constructor que registra Fecha de inicio de acción
     */
    public AccionUsuario(){
        inicioAccion = LocalDateTime.now();
    }
    
    
    //Metodos
    public abstract boolean generarPago(Usuario usuarioLogeado, Coordenadas ubicacionFinal, Transporte transporteUsando);
    
    /**
     * Método que actualiza los estados de los usuarios y transportes al momento de finalizar una acción
     * @param usuarioLogeado Usuario que se encuentra utilizando el sistema
     */
    public void actualizarDisponibilidades(Usuario usuarioLogeado){
        usuarioLogeado.setOcupaUnTransporte(false);
        usuarioLogeado.getTransporteUsando().setEstado(EstadoTransporte.DISPONIBLE);
        usuarioLogeado.setTransporteUsando(null);
    }

    
    //Getters y Setters
    //verificar cuales no han sido necesarios
    public void setFinalizaAccion(LocalDateTime finalizaAccion) {
        this.finalizaAccion = finalizaAccion;
    }

    public LocalDateTime getInicioAccion() {
        return inicioAccion;
    }

    public LocalDateTime getFinalizaAccion() {
        return finalizaAccion;
    }

    public String getCodigoTransporteUsando() {
        return codigoTransporteUsando;
    }

    public double getDineroTransaccion() {
        return dineroTransaccion;
    }
    
    

    public void setCodigoTransporteUsando(String codigoTransporteUsado) {
        this.codigoTransporteUsando = codigoTransporteUsado;
    }

    public void setDineroTransaccion(double dineroTransaccion) {
        this.dineroTransaccion = dineroTransaccion;
    }
    
    
    @Override
    public String toString() {
        return "AccionTransporte{" + "inicioAccion=" + inicioAccion + ", finalizaAccion=" + finalizaAccion + ", codigoTransporteUsado=" + codigoTransporteUsando + ", dineroTransaccion=" + dineroTransaccion + '}';
    }
    
    
}
