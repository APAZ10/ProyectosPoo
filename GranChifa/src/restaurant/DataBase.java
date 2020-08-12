/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant;

import auxiliar.CONSTANTES;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import usuarios.UsAdmin;
import usuarios.UsMesero;
import usuarios.Usuario;

/**
 *
 * @author DELL
 */
public class DataBase implements Serializable{
     private Map<String,Usuario> mapUs;
    private TreeSet<Plato> setPlatos;
    private Set<Mesa> setMesas;
    private ArrayList<Pedido> pedidos;
    private static final long serialVersionUID = 42L;
    
     /**
     * Constructor de l clase que inicializa las variables instancia
     */
    public DataBase(){
        mapUs = new HashMap<>();
        setPlatos = new TreeSet<>();
        setMesas = new HashSet<>();
        pedidos = new ArrayList<Pedido>();
        cargarDatos();
        cargarMesas();
        cargarPlatos();
    }    
    
     /**
     * Metodo sin retorno que lee el archivo de usuarios.txt con la info decada usuario
     * registrado
     */
    private void cargarDatos(){
        try(BufferedReader br = 
                new BufferedReader(new FileReader(CONSTANTES.PATH_RECURSOS+"usuarios.txt"))){
            String line;
            while((line=br.readLine()) != null){
                String[] info_us = line.split(",");
                //Agregando al mapa los usuaruios, clave: correo, valor: el usuario
                if(info_us[0].equals("Administrador")){
                    mapUs.put(info_us[1],new UsAdmin(info_us[1],info_us[2]));
                }else if(info_us[0].equals("Mesero")){
                    mapUs.put(info_us[1],new UsMesero(info_us[1],info_us[2],info_us[3]));
                    
                }
            }  
        }catch (IOException ex) {
            System.out.println("Error en la carga de archivos a la base de datos.");
            System.out.println(ex.getMessage());
        }        
    }
    
    /**
     * Metodo sin retorno que lee el archivo de txt de platos pra cargarlos en un set
     */
    private void cargarPlatos(){
        try(BufferedReader br = 
                new BufferedReader(new FileReader(CONSTANTES.PATH_RECURSOS+"platos.txt"))){
            String line="";
            while((line=br.readLine()) != null){
                String[] info_plato = line.split(",");
                setPlatos.add(new Plato(TIPOPLATO.valueOf(info_plato[0]),info_plato[1],Double.parseDouble(info_plato[2]),CONSTANTES.PATH_PLATOS+info_plato[3]));  
            } 
            br.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }catch (IOException ex) {
            System.out.println("Error en la carga de archivos a la base de datos.");
            System.out.println(ex.getMessage());
        }
    }
    
/**
 * Metodo sin retorno que lee el rchivo de mesas y las carga en un set para mostrarlas en el panel
 */      
    private void cargarMesas(){
        try(BufferedReader bf = new BufferedReader(new FileReader(CONSTANTES.PATH_RECURSOS+"mesas.txt"))){
            String line;
            while((line = bf.readLine())!= null){
                String[] p = line.split(",");
                setMesas.add(new Mesa(p[0],Integer.parseInt(p[1]),ESTADO.valueOf(p[2]),Double.parseDouble(p[3]),Double.parseDouble(p[4])));   
            }
            
        } catch (FileNotFoundException ex) {
            System.out.println("Archivo no encontrado");
        } catch (IOException ex) {
            System.out.println("Ha ocurrido un error");
        }
    }
    
    //Getters y Setters
    public Map<String,Usuario> getMapUs(){
        return mapUs;
    }
    
    public Set<Mesa> getSetMesas() {
        return setMesas;
    }

    public TreeSet<Plato> getSetPlatos() {
        return setPlatos;
    }

    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }
       
    
}
