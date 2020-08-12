/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant;

import auxiliar.CONSTANTES;
import java.io.Serializable;
import restaurant.TIPOPLATO;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Wacho1
 */
public class Plato implements Comparable<Plato>, Serializable{
    private String nombre;
    private double precio;
    private String imagen;
    private TIPOPLATO tipo;
    
    /**
     * Constructor de la clase que inicializa las variables instancias
     * @param tipo, TIPOPLATO
     * @param nombre, String
     * @param precio, double con el valor del plato
     * @param imagen , String con el url de la ubicaicon
     */
    public Plato(TIPOPLATO tipo, String nombre, double precio, String imagen) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
    }
    
    //Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public String getImagen() {
        return imagen;
    }

    public TIPOPLATO getTipo() {
        return tipo;
    }
    public int compareTo(Plato p){
        return nombre.compareTo(p.nombre);
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setTipo(TIPOPLATO tipo) {
        this.tipo = tipo;
    }
    
}
