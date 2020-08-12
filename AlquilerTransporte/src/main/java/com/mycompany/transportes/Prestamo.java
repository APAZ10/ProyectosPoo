/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.transportes;

import com.mycompany.gyerent.Coordenadas;
import java.time.LocalDate;
import com.mycompany.usuario.Usuario;
import java.time.Duration;
import utility.Utility;
import com.mycompany.usuario.UsuarioRider;
import com.mycompany.usuario.Tarjeta;
import java.util.Scanner;
/**
 *
 * @author Wacho1
 */
public class Prestamo extends AccionUsuario{
    private double tiempoUso;
    private double distanciaRecorrida;
    
    //Constructor
    public Prestamo(){
        super();
       
    }
    
    //metodo que deberia generar el costo a pagar por el usuario con tarjetas, se asume que un pago siempre es exitoso
    @Override
    public boolean generarPago(Usuario usuarioLogeado,Coordenadas ubicacionFinal, Transporte transporteUsando){
        //Parte de Cobro
        tiempoUso = Utility.redondearDecimales(tiempoUso(),2);
        System.out.println(tiempoUso + " minutos de uso");
        dineroTransaccion = Utility.redondearDecimales(calcularCosto(tiempoUso, transporteUsando.getCostoPorMinuto()),2);
        System.out.println("Total a pagar: " + dineroTransaccion);
        
        //Parte de descargar Bateria
        distanciaRecorrida = distanciaReccorida(transporteUsando.getUbicacionActual(), ubicacionFinal);
        transporteUsando.restarBateria(distanciaRecorrida);
        transporteUsando.actualizarUbicacion(ubicacionFinal);
        //Parte verificar pago con tarjetas, //en caso de transc invalida = false, se repite finalizarAccion = nuevo costo, y nueva descarga bateria por si se mueve
        System.out.println("Seleccione tarejta de credito");
        UsuarioRider ur = (UsuarioRider)usuarioLogeado;
        int i=0;
        for(Tarjeta t: ur.getTarjetas()){
            i++;
            System.out.println(i+". "+t);
        }
        int op = i+1;
        System.out.println(op +". Nueva tarjeta");
        System.out.println("Ingrese opcion");
        Scanner sc = new Scanner(System.in);
        int opcion= sc.nextInt();
        if(opcion==op){
            System.out.println("Ingrese numero de tarjeta:");
            sc.nextLine();
            String numTarjeta = sc.nextLine();
            System.out.println("Ingrese tipo de tarjeta:");
            String banco= sc.nextLine();
            System.out.println("Ingrese nombre del titular:");
            String nombreTar= sc.nextLine();
            ur.getTarjetas().add(new Tarjeta(numTarjeta,banco,nombreTar));
            System.out.println("Su tarjeta ha sido agregada exitosamente.");
        }
        System.out.println("El pago ha sido exitoso.");
        //al finalizar si transaccion exitosa agregar la info al reporte del usuario
        usuarioLogeado.agregarReporte(LocalDate.now()+"                    "+transporteUsando.getCodigoTransporte()+"                       "+dineroTransaccion+ "                                     "+tiempoUso+"                          "+distanciaRecorrida+"\n"+
                "--------------------------------------------------------------------------------------------------------------------------------------------------------------------"); 
        //return de prueba
        return true;
    }
    
    public double tiempoUso(){
        Duration tiempo = Duration.between(inicioAccion,finalizaAccion);
        double tiempoMinutos = tiempo.getSeconds()/(double)60;
        return tiempoMinutos;
    }
    
    public double calcularCosto(double tiempoUsado, double costoPorMinuto){
        double costo = (tiempoUsado * costoPorMinuto ) +1;
        return costo;
    }
    
    public double distanciaReccorida(Coordenadas ubicacionInicial, Coordenadas ubicacionFinal){
        double latitudInicial = ubicacionInicial.getLatitud();
        double longitudInicial = ubicacionInicial.getLongitud();
        double latitudFinal = ubicacionFinal.getLatitud();
        double longitudFinal = ubicacionFinal.getLongitud();
        double distancia = Utility.distanciaGeo(latitudInicial,longitudInicial, latitudFinal, longitudFinal);
        return Utility.redondearDecimales(distancia,2);
    }

    public double getTiempoUso() {
        return tiempoUso;
    }

    public double getDistanciaRecorrida() {
        return distanciaRecorrida;
    }


    @Override
    public double getDineroTransaccion() {
        return dineroTransaccion;
    }
    
    
}
