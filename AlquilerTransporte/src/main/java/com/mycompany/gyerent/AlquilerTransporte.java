/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gyerent;
import java.util.ArrayList;
import java.util.Scanner;
import com.mycompany.transportes.*;
import com.mycompany.usuario.*;
import java.time.LocalDate;
import java.util.InputMismatchException;
import utility.Utility;

/**
 *
 * @author Alejandro
 */
public class AlquilerTransporte {
    private dataBase baseDatos;
    private Usuario usuarioLogeado;
    private Scanner sc2;
    
 
    //constantes para el menú
    private static final int BUSCAR_DISPOSITIVO = 1;
    private static final int REALIZAR_ACCION = 2;
    private static final int TERMINAR_ACCION = 3;
    private static final int VER_REPORTES = 4;
    private static final int CERRAR_SESION =5;
    
    
    //Constructor sin parametro
    public AlquilerTransporte() {
        baseDatos = new dataBase();
        sc2 = new Scanner(System.in);
        
    }
    
    //Métodos
    /**
     * Metodo que busca un usuario dentro de la base de datos segun su username y password
     * @param username
     * @param password
     * @return usuario logeado que podra realizar sus respectivas acciones
     */
    public Usuario buscarUsuario(String username, String password){
        for(Usuario usuario: baseDatos.usuarios){
            //podria primero verificar user y luego contrasena para ver posibles fallos
            if(usuario.getUserName().equals(username) && usuario.getPassword().equals(password)){
                usuarioLogeado = usuario;
                return usuarioLogeado;
            }
        }
         
        return null;
    }
    
    /**
     * Metodo que permite registrar una cuenta ya sea rider o charger
     * @param opcionUsuario opcion con la que se verifica si se hace un registro rider o charger
     */
    public void registrarCuenta(int opcionUsuario){     
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Ingrese su username:");
        //Revisar problema con nextLine cuando es al inicio no hay problema, luego con muchos procesos no toma bien los datos.
        String user = sc1.nextLine();
        System.out.println("Ingrese su Password:");
        String pass = sc1.nextLine();
        Usuario us = buscarUsuario(user,pass);
        if (us == null){
            
            if (opcionUsuario == 1){
                
                System.out.println("Ingrese su nombre:");
                String nombre = sc1.nextLine();
                System.out.println("Ingrese su dirección:");
                String direc = sc1.nextLine();
                System.out.println("Ingrese su licencia de conducir:");
                String lic = sc1.nextLine();
                Usuario nuevo_us = new UsuarioRider(nombre,direc,user,pass);
                baseDatos.usuarios.add(nuevo_us);
                System.out.println("Ingrese el numero de tarjeta de credito:");
                String numt = sc1.nextLine();
                System.out.println("Ingrese tipo de tarjeta:");
                String tipo = sc1.nextLine();
                System.out.println("Ingrese nombre del titular de la tarjeta:");
                String titular = sc1.nextLine();
                UsuarioRider usr= (UsuarioRider)nuevo_us;
                Tarjeta t = new Tarjeta(numt,tipo,titular);
                usr.getTarjetas().add(t);
                
            }else if(opcionUsuario == 2){
                System.out.println("Ingrese su nombre:");
                String nombre = sc1.nextLine();
                System.out.println("Ingrese su dirección:");
                String direc = sc1.nextLine();               
                Usuario nuevo_us = new UsuarioCharger(nombre,direc,user,pass);
                baseDatos.usuarios.add(nuevo_us);
                System.out.println("Ingrese su nombre de banco:");
                String nom_b = sc1.nextLine();
                System.out.println("Ingrese su número de cuenta:");
                String num_c = sc1.nextLine();
                CuentaBancaria c = new CuentaBancaria(nom_b,num_c);
                UsuarioCharger uc = (UsuarioCharger)nuevo_us;
                uc.getCuentasBancarias().add(c);
                
            }
            System.out.println("Registro exitoso");
        }else{
            System.out.println("Ya tiene un usuario registrado");
        }
        
    }
    
    
    /**
     * Metodo que muestra el menu segun el tipo de usuario logeado
     */
    public void menuLogeado(){
        int opcionLogeado = 0;
        while(opcionLogeado!=CERRAR_SESION){
            //validar la entrada que no sea int 
            usuarioLogeado.verMenu();
            System.out.println("Ingrese una opción: ");
            boolean existeError;
            do{
                try{
                    opcionLogeado = sc2.nextInt();
                    existeError = false;
                }
                catch(InputMismatchException error){
                    System.out.println("Ingrese opcion valida");
                    sc2.next();
                    existeError = true;
                }
            }while(existeError);
            //
            switch(opcionLogeado){
                case BUSCAR_DISPOSITIVO:
                    //Validar las entradas
                    System.out.println("Ingrese su ubicación actual: ");
                    sc2.nextLine();
                    String ubicacionActual = sc2.nextLine();
                    //Distancia en kilometros
                    System.out.println("Ingrese una distancia máxima en kilómetros: ");
                    double distanciaMax = 0;
                    do{
                        try{
                            distanciaMax = sc2.nextDouble();
                            existeError = false;
                        }
                        catch(InputMismatchException error){
                            System.out.println("Ingrese Km en numero");
                            sc2.next();
                            existeError = true;
                        }
                    }while(existeError);
                    
                    ArrayList<DistanciaTransporte> transportesCercanos = transportesCercanos(ubicacionActual, Math.abs(distanciaMax));
                    usuarioLogeado.dispositivosDisponibles(transportesCercanos);
                    break;
                    
                case REALIZAR_ACCION:
                    //Primero se verifica que no este usando un transporte
                    if(usuarioLogeado.isOcupaUnTransporte()){
                        System.out.println("Ya tiene un dispositivo con usted.");
                        System.out.println(usuarioLogeado.getTransporteUsando());
                    }else{
                        //Continuar
                        System.out.println("Ingrese el codigo del vehiculo: ");
                        //validar entradas
                        sc2.nextLine();
                        String codigoTransporte = sc2.nextLine();
                        //en caso de disponible retorna el transporte, caso contrario retorna null
                        Transporte transporteObtenido = obtenerTransporte(codigoTransporte);
                        if(transporteObtenido!= null){
                            System.out.println(transporteObtenido);
                            int confirma = 0;
                            System.out.println("Desea confirmar? ");
                            System.out.println("1. Confirmar ");
                            System.out.println("2. Salir");
                            do{
                                try{
                                    confirma = sc2.nextInt();
                                    existeError = false;
                                }
                                catch(InputMismatchException error){
                                    System.out.println("Ingrese opcion valida");
                                    sc2.next();
                                    existeError = true;
                                }
                            }while(existeError);

                            switch(confirma){
                            case 1:
                                usuarioLogeado.inicioAccion(transporteObtenido);
                                System.out.println("Prestamo realizado.");
                                break;
                            case 2:
                                System.out.println("Regresando al menu");
                                break;
                            default:
                                System.out.println("Opcion no valida");    
                            }
                        }  
                    }
                    
                    break;
                    //falta agregar la accion a lista en usuario
                case TERMINAR_ACCION:
                    //pedir codigo transporte y validar si es el que lo esta usando, luego guardarlo en transporteUsando si es que se necesita??
                    if(usuarioLogeado.isOcupaUnTransporte()){
                        Transporte transporteUsando = usuarioLogeado.getTransporteUsando();
                        System.out.println("Ingrese codigo del vehiculo: ");
                        sc2.nextLine();
                        String codigoIngresado= sc2.nextLine();
                        if(!codigoIngresado.equals(transporteUsando.getCodigoTransporte()))
                        {
                            System.out.println("El codigo ingresado no corresponde al vehiculo.");
                            
                        }
                        else
                        {
                            
                            System.out.println("Ingrese su ubicacion final: ");
                            String ubicacionFinal = sc2.nextLine();
                            Coordenadas posicionFinal = new Coordenadas(ubicacionFinal);
                            while(!posicionFinal.isUbicacionExite()){
                                System.out.println("Ingrese otra vez su ubicacion final: ");
                                ubicacionFinal = sc2.nextLine();
                                posicionFinal = new Coordenadas(ubicacionFinal);
                            }
                            //Obtiene el LocalDateTime final en AccionTransporte
                            usuarioLogeado.finalizaAccion();
                            //Calcular el tiempo, distancia, carga segun el usuario y procesar los pagos o cobros
                            AccionUsuario accionRealizando = usuarioLogeado.getAccionRealizando();
                            boolean transaccionExitosa = accionRealizando.generarPago(usuarioLogeado,posicionFinal,transporteUsando);
                        
                            //fijar disponibilidad transporte disponible, usuario usandoTransporte false y transporte usando null
                            if(transaccionExitosa){
                                accionRealizando.actualizarDisponibilidades(usuarioLogeado);
                            }//Continuar con un else en caso de verificar si transaccion es exitosa
                        }
                    }else{
                        System.out.println("No tiene transporte prestado.");
                    }
                    break;
                case VER_REPORTES:
                    System.out.println("Ingrese fecha maxima: ");
                    LocalDate dmax = Utility.consultarFecha();

                    System.out.println("Ingrese fecha minima: ");
                    LocalDate dmin = Utility.consultarFecha();
                    
                    usuarioLogeado.verReportes();
                    Utility.MostrarRegistros(dmax,dmin,usuarioLogeado.getReporte());
                    
                    break;
                case CERRAR_SESION:
                    usuarioLogeado = null;
                    break;
                default:
                    System.out.println("Ingreso no valido");               
            }
        }
    }
    
    public ArrayList<DistanciaTransporte> transportesCercanos(String ubicacionActualUsuario, double distanciaMax){
        Coordenadas posicionActualUsuario = new Coordenadas(ubicacionActualUsuario);
        ArrayList<DistanciaTransporte> transportesEncontrados = new ArrayList<>();
        //
        while(!posicionActualUsuario.isUbicacionExite()){
            System.out.println("Ingrese otra vez su ubicacion actual: ");
            Scanner sc = new Scanner(System.in);
            ubicacionActualUsuario = sc.nextLine();
            posicionActualUsuario = new Coordenadas(ubicacionActualUsuario);
        }
        double latitudUsuario = posicionActualUsuario.getLatitud();
        double longitudUsuario = posicionActualUsuario.getLongitud();
        for(Transporte transporte: baseDatos.transportes){
            double latitudTransporte = transporte.getUbicacionActual().getLatitud();
            double longitudTransporte = transporte.getUbicacionActual().getLongitud();
            double distancia = Utility.distanciaGeo(latitudUsuario, longitudUsuario, latitudTransporte, longitudTransporte);
            if(distancia <= distanciaMax){
                transportesEncontrados.add(new DistanciaTransporte(transporte, distancia));
                DistanciaTransporte.getDistancias().add(distancia);
            }
        }   
        return transportesEncontrados;
    }
    
    public Transporte obtenerTransporte(String codigoTransporte){
        for(Transporte transporte: baseDatos.transportes){
            if(transporte.getEstado() == EstadoTransporte.DISPONIBLE && codigoTransporte.equals(transporte.getCodigoTransporte())){
                return transporte; 
            }
        }
        System.out.println("Transporte no encontrado o no disponible.");
        return null;
    }
    
    
    //Getters y Setters
    public Usuario getUsuarioLogeado() {
        return usuarioLogeado;
    }
    
        //Datos Iniciales dentro del Sistema
    public void inicializarDatos(){
          //Ingresando transportes
          Transporte bicicleta1 = new Bicicleta("111",15,-2.1850148,-79.8759606); //malecon 2000
          Transporte bicicleta2 = new Bicicleta("174",150,-2.1850148,-79.8759606); //malecon 2000
          Transporte bicicleta3 = new Bicicleta("191",100,-2.2294,-79.8985);// mall del sur
          //
          Transporte scooter1 = new Scooter("211",100,-2.1546264,-79.8925899); //mall del sol
          Transporte scooter2 = new Scooter("261",15,-2.1392518,-79.9302834); //colegio americano guayaquil
          Transporte scooter3 = new Scooter("200",75,-2.1038,-79.8966); //parque samanes
          
          baseDatos.transportes.add(bicicleta1);
          baseDatos.transportes.add(bicicleta2);
          baseDatos.transportes.add(bicicleta3);
          baseDatos.transportes.add(scooter1);
          baseDatos.transportes.add(scooter2);
          baseDatos.transportes.add(scooter3);
          //Ingresando Usuarios
          Usuario rider1 = new UsuarioRider("Alejandro","Acuarela","rideruser","rideruser");
          UsuarioRider r1 = (UsuarioRider)rider1;
          r1.getTarjetas().add(new Tarjeta("105364","Visa","Alejandro"));
          //registros de prueba
          rider1.getReporte().getRegistros().add(LocalDate.of(2019, 10, 10)+"cod prueba"+"pago prueba");
          rider1.getReporte().getRegistros().add(LocalDate.of(2019, 10, 12)+"cod prueba1"+"pago prueba1");
          rider1.getReporte().getRegistros().add(LocalDate.of(2019, 10, 14)+"cod prueba2"+"pago prueba2");
          rider1.getReporte().getRegistros().add(LocalDate.of(2019, 10, 19)+"cod prueba3"+"pago prueba3");
          rider1.getReporte().getRegistros().add(LocalDate.of(2019, 10, 21)+"cod prueba4"+"pago prueba4");
          Usuario rider2 = new UsuarioRider("alex","Sauces 8","rideruser2","rideruser2");
          UsuarioRider r2 = (UsuarioRider)rider2;
          r2.getTarjetas().add(new Tarjeta("159743","Mastercard","Alex"));
          Usuario rider3 = new UsuarioRider("jose","Sauces 5","rideruser3","rideruser3");
          UsuarioRider r3 = (UsuarioRider)rider3;
          r3.getTarjetas().add(new Tarjeta("1591644","Mastercard","Jose"));
          Usuario charger1 = new UsuarioCharger("Domenica","FAE","chargeruser","chargeruser");
          UsuarioCharger c1 = (UsuarioCharger)charger1;
          c1.getCuentasBancarias().add(new CuentaBancaria("Pacifico","03124675"));
          Usuario charger2 = new UsuarioCharger("Brandon","Samanes","chargeruser2","chargeruser2");
          UsuarioCharger c2 = (UsuarioCharger)charger2;
          c2.getCuentasBancarias().add(new CuentaBancaria("Pichincha","13457985"));
          baseDatos.usuarios.add(rider1);
          baseDatos.usuarios.add(rider2);
          baseDatos.usuarios.add(rider3);
          baseDatos.usuarios.add(charger1);   
    }
  
}
