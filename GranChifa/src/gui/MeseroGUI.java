/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import auxiliar.CONSTANTES;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import granchifa.GranChifa;
import static granchifa.GranChifa.baseDatos;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import restaurant.DataBase;
import restaurant.ESTADO;
import static restaurant.ESTADO.LIBRE;
import static restaurant.ESTADO.POR_ATENDER;
import static restaurant.ESTADO.OCUPADO;
import restaurant.Mesa;
import usuarios.UsMesero;
import usuarios.Usuario;

/**
 *
 * @author Alejandro
 */
public class MeseroGUI {
    VBox root;
    private Scene meseroScene;
    Usuario mesero;
    Pane pane;
    MenuGUI menu;
    
    /**
     * Constructor de la clase que inicializa el root y escene para el mismo
     * @param mesero, Usuario
     */
    public MeseroGUI(Usuario mesero){
        this.mesero= mesero;
        root = new VBox();
        crearRoot();
        meseroScene = new Scene(root,800,800);
        meseroScene.getStylesheets().add("recursos/estilos/estiloMeseroGUI.css");
        GranChifa.stage.setScene(meseroScene);        
    }
    
    /**
     * Metodo sin retrono que crea el root para la interfaz grafica del mesero
     */
    public void crearRoot(){
        BorderPane h = new BorderPane();
        h.setPadding(new Insets(10, 10, 10, 10));
        h.setId("seccionTop");
        Button b = new Button("Salir");
        Label nombre = new Label("Nombre del mesero: "+mesero.getNombre());
        h.setLeft(nombre);
        h.setRight(b);
        root.getChildren().add(h);
        b.setOnAction((salidaEvent)->{
            Login logGui = new Login();
            GranChifa.stage.setScene(logGui.getLoginScene());
        });
        pane= new Pane();
        pane.setId("panelMesas");
        try(ObjectInputStream ob =new ObjectInputStream(new FileInputStream(CONSTANTES.PATH_RECURSOS+"DataBase"))){
            baseDatos= (DataBase) ob.readObject();
        } catch (IOException | ClassNotFoundException ex) {   
        }
        Set<Mesa> setMesas = baseDatos.getSetMesas();
        for (Mesa mesa : setMesas) {
            if(mesa.getMesero() ==  null&&mesa.getNombreCliente()==null){
                mesa.setDisponibilidad(ESTADO.LIBRE);
            }else if(mesa.getMesero()==null&&mesa.getNombreCliente()!=null){
                mesa.setDisponibilidad(ESTADO.RESERVADO);
            }
            else if(mesa.getMesero().equals(mesero)){
                mesa.setDisponibilidad(ESTADO.POR_ATENDER);
            }
            else if(!(mesa.getMesero().equals(mesero))){
                if(mesa.getMesero()!= null){
                mesa.setDisponibilidad(ESTADO.OCUPADO);}                
            }
            MesaGUI mesaGraf = new MesaGUI(mesa);
            pane.getChildren().add(mesaGraf);
            
            mesaGraf.setOnMouseClicked((e)->{                

                if(mesa.getDisponibilidad() == POR_ATENDER){
                    if(menu == null){
                        menu = new MenuGUI(mesaGraf);
                        mesaGraf.getPedido().setNombreMesero(mesero.getNombre());
                        mesaGraf.getPedido().setNumMesa(mesaGraf.getMesa().getNumMesa());
                    }else{
                        menu.verificarPedido();
                        menu.show();
                    }
                   
                }   
                
                else if(mesa.getDisponibilidad()== OCUPADO){
                }
                
                else{
                    menu = null;
                    mesa.setDisponibilidad(ESTADO.POR_ATENDER);
                    mesaGraf.getCircle().setFill(mesaGraf.fijarColor(mesa.getDisponibilidad()));
                    mesaGraf.getPedido().setNumMesa(mesa.getNumMesa());
                    mesaGraf.getPedido().setNombreMesero(mesero.getNombre());
                    mesaGraf.getMesa().setMesero(mesero);
                    new CuentaMeseroGUI(mesaGraf.getPedido());
                }
                try(ObjectOutputStream ob = new ObjectOutputStream(new FileOutputStream(CONSTANTES.PATH_RECURSOS+"DataBase"))){
                    ob.writeObject(baseDatos);
                }
                catch (IOException ex){
                    System.out.println(ex.getMessage());
                }

            });
        }

        root.getChildren().add(pane);
        
    }
    
    

    
}
