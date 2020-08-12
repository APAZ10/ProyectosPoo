/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import com.mycompany.gyerent.Reporte;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 *
 * @author Alejandro
 */
public class Utility {
    
    /**
     * Metodo para obtener distancia geográfica entre dos posiciones geográficas utilizando fórmula Harvesine
     * @param latitud1 Latitud Posición 1
     * @param longitud1 Longitud Posición 1
     * @param latitud2 Latitud Posición 2
     * @param longitud2 Longitud Posición 2
     * @return distancia entre dos posiciones geográficas
     */
    public static double distanciaGeo(double latitud1, double longitud1, double latitud2, double longitud2){
        final int R = 6378;
        double difLatitud = (Math.PI/180)*(latitud1 - latitud2);
        double difLongitud = (Math.PI/180)*(longitud2 - longitud1);
        double a = Math.pow(Math.sin(difLatitud/2),2)+(Math.cos(latitud1)*Math.cos(latitud2)*Math.pow(Math.sin(difLongitud/2), 2));
        double c = 2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distancia = R*c;
        return distancia;
    }
    
    
    /**
     * Método para pasar una fecha de String a LocalDate
     * @param fechaString String que representa una fecha
     * @return fecha transformada en LocalDate
     */
    public static LocalDate stringAFecha(String fechaString){
        LocalDate localDate = LocalDate.parse(fechaString);
        return localDate;
    }
    
    
    /**
     * Método que permite consultar una fecha al usuario y transforma lo obtenido a LocalDate
     * @return fecha consultada por el usuario transformado en LocalDate
     */
    public static LocalDate consultarFecha(){
        LocalDate localDate = null;
        boolean existeError;
        Scanner sc = new Scanner(System.in);
        do{
            try{
                System.out.println("Dia (dd):");
                String dia = sc.nextLine();
                System.out.println("Mes (mm):");
                String mes = sc.nextLine();
                System.out.println("Año (yyyy):");
                String year = sc.nextLine();
                String fechaString = year+"-"+mes+"-"+dia;
                localDate = LocalDate.parse(fechaString);
                existeError = false;
            }catch(DateTimeParseException error){
                System.out.println("Ingrese la fecha correctamente");
                existeError = true;
            }
        }while(existeError);
        return localDate;
    }
    
    /**
     * Método que muestra los reportes dentro de un rango de fecha, de más reciente a más antiguo
     * @param maxd1 fecha máxima a consultar
     * @param mind2 fecha mínima a consultar
     * @param r Objeto reporte que contiene los registros de un Usuario
     */
     public static void MostrarRegistros(LocalDate maxd1,LocalDate mind2, Reporte r){
        if(r.getRegistros().isEmpty()){
            System.out.println("no hay registros\n");
        }else{
            for (String registro : r.getRegistros()) {
                String[] partes= registro.split(" ");
                String fecha= partes[0];
                LocalDate fechaDate = Utility.stringAFecha(fecha);
                if((fechaDate.isAfter(mind2)||fechaDate.isEqual(mind2)) && (fechaDate.isBefore(maxd1)||fechaDate.isEqual(maxd1))){
                    System.out.println(registro);
                }
            }    
        }
    }
     
    /**
     * Método que redondea valores numéricos dado una cantidad de números decimales
     * @param valorInicial valor a redondear
     * @param numeroDecimales número de decimales a redondear
     * @return valor numérico final ya redondeado
     */ 
    public static double redondearDecimales(double valorInicial, int numeroDecimales) {
        double parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = Math.floor(resultado);
        resultado=(resultado-parteEntera)*Math.pow(10, numeroDecimales);
        resultado=Math.round(resultado);
        resultado=(resultado/Math.pow(10, numeroDecimales))+parteEntera;
        return resultado;
    } 
   
}
