/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant;

import granchifa.GranChifa;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Wacho1
 */
public class Pedido implements Serializable{
    private String nombreCliente;
    private int numPedido;
    private ArrayList<Plato> platosPedidos;
    private LocalDate lc;
    private String numMesa;
    private String nombreMesero;
    private double total;
    
    /**
     * Constructor de la clase que inicializa el arraylist con los platos pedidos
     * en una orden
     */
    public Pedido() {
        
        platosPedidos= new ArrayList<>();
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public int getNumPedido() {
        return numPedido;
    }

    public void setNumPedido(int numPedido) {
        this.numPedido = numPedido;
    }

    public ArrayList<Plato> getPlatosPedidos() {
        return platosPedidos;
    }

    public void setPlatosPedidos(ArrayList<Plato> platosPedidos) {
        this.platosPedidos = platosPedidos;
    }

    public LocalDate getLc() {
        return lc;
    }

    public void setLc(LocalDate lc) {
        this.lc = lc;
    }

    public String getNumMesa() {
        return numMesa;
    }

    public void setNumMesa(String numMesa) {
        this.numMesa = numMesa;
    }

    public String getNombreMesero() {
        return nombreMesero;
    }

    public void setNombreMesero(String nombreMesero) {
        this.nombreMesero = nombreMesero;
    }
     public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Pedido{" + "nombreCliente=" + nombreCliente + ", numPedido=" + numPedido + ", platosPedidos=" + platosPedidos + ", lc=" + lc + ", numMesa=" + numMesa + ", nombreMesero=" + nombreMesero + '}';
    }
    
    
    
}
