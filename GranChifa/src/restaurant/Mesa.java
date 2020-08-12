/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant;

import java.io.Serializable;
import java.util.Objects;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import usuarios.UsMesero;
import usuarios.Usuario;

/**
 *
 * @author Wacho1
 */
public class Mesa implements Serializable{
    private Usuario mesero;
    private int numPersonas;
    private String numMesa;
    
    //Sust
    String nombreCliente;
    
    private ESTADO disponibilidad;
    private double posX;
    private double posY;
    
    /**
     * Constructor de la calse qe innicializa las variables instncia 
     * @param numMesa, int con el numero de la mesa
     * @param numPersonas, int con el numero total de personas de la mesa
     * @param disponibilidad, ESTADO de la mesa
     * @param posX, double con la posicion en x
     * @param posY , double con la posicion en y
     */
    public Mesa(String numMesa,int numPersonas, ESTADO disponibilidad,double posX, double posY){
        this.numMesa = numMesa; 
        this.numPersonas = numPersonas;
        this.disponibilidad = disponibilidad;
        this.posX=posX;
        this.posY=posY;
        
    }
//    public Color fijarColor(ESTADO disponibilidad){
//        if(disponibilidad == ESTADO.OCUPADO){
//            Color c = Color.RED;
//            return c;
//        }else if(disponibilidad == ESTADO.POR_ATENDER){
//            Color c = Color.GREEN;
//            return c;
//        }       
//        return Color.YELLOW;
//        
//    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.numMesa);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Mesa other = (Mesa) obj;
        if (!Objects.equals(this.numMesa, other.numMesa)) {
            return false;
        }
        return true;
    }
    public String getNumMesa() {
        return numMesa;
    }

    public void setDisponibilidad(ESTADO disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public ESTADO getDisponibilidad() {
        return disponibilidad;
    }

    public int getNumPersonas() {
        return numPersonas;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }
    public Usuario getMesero() {
        return mesero;
    }

    public void setMesero(Usuario mesero) {
        this.mesero = mesero;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
    
    



  
    

    
    
}
