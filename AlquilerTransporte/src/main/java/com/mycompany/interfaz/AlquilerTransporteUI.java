/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.interfaz;

import java.util.Scanner;
import com.mycompany.gyerent.AlquilerTransporte;
import com.mycompany.usuario.Usuario;
import java.util.InputMismatchException;

/**
 *
 * @author Alejandro
 */
public class AlquilerTransporteUI {

    /**
     * @param args the command line arguments
     *
     */
    private final AlquilerTransporte sistema;
    private final Scanner sc;
    //Constantes para el menú de inicio
    private static final int INICIAR_SESION = 1;
    private static final int REGISTRARSE = 2;
    private static final int SALIR = 3;
    //Constantes para el menu de Registro de Cuenta
    private static final int REGISTRO_RIDER = 1;
    private static final int REGISTRO_CHARGER = 2;
    
    //Constructor sin parametros
    public AlquilerTransporteUI(){
        sistema = new AlquilerTransporte();
        sc = new Scanner(System.in);
    }
    
    public void menu(){
        System.out.println("Bienvenido");
        int opcion=0;
        while(opcion!=3){System.out.println("Ingrese una opcion");
            System.out.println("1. Iniciar Sesión");
            System.out.println("2. Registrarse");
            System.out.println("3. Salir");
            
            //Validar por si entran algo que no es int
            boolean existeError;
            do{
                try{
                    opcion = sc.nextInt();
                    existeError = false;
                }
                catch(InputMismatchException error){
                    System.out.println("Ingrese opcion valida");
                    sc.next();
                    existeError = true;
                }
            }while(existeError);
            
            //Opciones
            switch(opcion){
                case INICIAR_SESION:
                    //Log in
                    System.out.println("Inicio de sesión");
                    System.out.println("Ingrese su username: ");
                    //verificar entradas
                    sc.nextLine();
                    String usernameUsuario = sc.nextLine();
                    System.out.println("Ingrese su password: ");
                    //verificar entradas
                    String passwordUsuario = sc.nextLine();
                    Usuario us_b = sistema.buscarUsuario(usernameUsuario, passwordUsuario);
                    if(us_b!=null){
                        sistema.menuLogeado();
                    }else{
                        System.out.println("No se encuentra el usuario");  
                    }
                    break;
                case REGISTRARSE:
                    //Registrarse
                    System.out.println("Registro, seleccione un tipo de cuenta");
                    System.out.println("1. Rider");
                    System.out.println("2. Charger");
                    //Validar por si entran algo que no es int
                    int opcionUsuario = 0;
                    boolean error=true;
                    do{
                        try{
                            opcionUsuario = sc.nextInt();
                            error = false;
                        }
                        catch(InputMismatchException err){
                            System.out.println("Ingrese opcion valida");
                            sc.next();
                        }
                    }while(error);
                    if(opcionUsuario == REGISTRO_RIDER || opcionUsuario == REGISTRO_CHARGER){
                        sistema.registrarCuenta(opcionUsuario);
                    }else{
                        System.out.println("Opcion no valida, regresando al menu principal");
                    }
                            
                    break;
                case SALIR:
                    //Cerrar
                    System.out.println("Saliendo del sistema.");
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;
            }
        }
    }
        
        
    public static void main(String[] args) {
        // TODO code application logic here
        AlquilerTransporteUI interfaz = new AlquilerTransporteUI();
        interfaz.sistema.inicializarDatos();
        interfaz.menu();
    }    
    
    
}
