/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.transportes;

import com.mycompany.gyerent.Coordenadas;
import java.time.LocalDate;
import com.mycompany.usuario.Usuario;
import java.util.InputMismatchException;
import java.util.Scanner;
import com.mycompany.usuario.UsuarioCharger;
import com.mycompany.usuario.CuentaBancaria;
import utility.Utility;
/**
 *
 * @author Wacho1
 */
public class Carga extends AccionUsuario{

    
    
    //Contrstuctor
    public Carga(){
        super();
    }
    
    //metodo se asume siempre es exitoso el pago al usuario
    @Override
    public boolean generarPago(Usuario usuarioLogeado,Coordenadas ubicacionFinal, Transporte transporteUsando){
        Scanner sc3 = new Scanner(System.in);
        boolean existeError = false;
        do{
            try{
                System.out.println("Ingrese nivel de bateria del vehiculo:");
                //Verificar dentro del rango de bateria 
                double cantidadBateriaFinal = sc3.nextDouble();
                while(cantidadBateriaFinal>transporteUsando.getCapacidadBateria() || cantidadBateriaFinal<transporteUsando.getCantidadBateria()){
                    System.out.println("Vuelva a ingresar nivel de bateria");
                    cantidadBateriaFinal= sc3.nextDouble();
                }
                double cobroUsuario = calcularCobro(cantidadBateriaFinal, transporteUsando);
                dineroTransaccion = cobroUsuario;
                if(cobroUsuario == 0){
                    System.out.println("Total a cobrar: " + cobroUsuario);
                }else{
                    System.out.println("Total a cobrar: " + cobroUsuario);
                    //revisar cuando las lineas 40 y 41 se deben ejecutar
                    transporteUsando.actualizarUbicacion(ubicacionFinal);
                    usuarioLogeado.agregarReporte(LocalDate.now()+"          "+transporteUsando.getCodigoTransporte()+"               "+cobroUsuario+"\n"
                    +"----------------------------------------------------------------------------------");
                    System.out.println("Seleccione cuenta bancaria para depositar.");
                    UsuarioCharger uc = (UsuarioCharger)usuarioLogeado;
                    int i=0;
                    for(CuentaBancaria c: uc.getCuentasBancarias()){
                        i++;
                        System.out.println(i+". "+c);
                    }
                    int op = i+1;
                    System.out.println(op +". Nueva cuenta bancaria");
                    System.out.println("Ingrese opcion");
                    Scanner sc = new Scanner(System.in);
                    int opcion= sc.nextInt();
                    
                    if(opcion==op){
                        System.out.println("Ingrese nombre del banco: ");
                        sc.nextLine();
                        String nombreBanco = sc.nextLine();
                        System.out.println("Ingrese numero de cuenta: ");
                        String numCuenta= sc.nextLine();
                        uc.getCuentasBancarias().add(new CuentaBancaria(nombreBanco,numCuenta));
                        System.out.println("Su cuenta bancaria ha sido agregada exitosamente." +"\n"+"Se ha depositado el valor total a pagar a la cuenta bancaria agregada ");
                    }else{
                        System.out.println("Su devolucion ha sido exitosa y se ha depositado el valor total a pagar en la cuenta bancaria elegida. " );
                    }
                
                    
                }
            }catch(InputMismatchException error){
                System.out.println("Ingrese un valor numerico");
                sc3.next();
                existeError = true;
            }
        }while(existeError);
        return true;
    }
    
    public double calcularCobro(double cantidadBateriaFinal, Transporte transporteUsando){
        double cobro = Utility.redondearDecimales(((cantidadBateriaFinal - transporteUsando.getCantidadBateria())*0.15),2);
        //actualizando nueva bateria
        transporteUsando.setCantidadBateria(cantidadBateriaFinal);
        return cobro;
    }

}
