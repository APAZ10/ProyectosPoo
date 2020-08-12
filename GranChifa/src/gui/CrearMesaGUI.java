/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import granchifa.GranChifa;
import java.util.Random;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import restaurant.ESTADO;
import restaurant.Mesa;

/**
 *
 * @author DELL
 */
public class CrearMesaGUI extends Stage{
    VBox root;
    int numeroPersonas=0;
    double posX =0;
    double posY = 0;
    double xinicial;
    double yinicial;
    Pane panelMesas;
    
      /**
     * Constructor de la clase que inicializa el root,la escena y las posiciones
     * @param panelMesas, un pane donde se muestras las mesas creadas
     * @param posX, double con l posicion en el eje x
     * @param posY , double con la posicion en el eje y
     */
    public CrearMesaGUI(Pane panelMesas,double posX,double posY){
        this.panelMesas = panelMesas;
        this.posX = posX;
        this.posY = posY;
        root = new VBox();
        crearVentana();
        Scene scene = new Scene(root,300,100);
        this.setScene(scene);
        this.show();
    }
    
     /**
     * Metodo sin retorno que crea la ventan que solicita las especificaciones de 
     * la mesa
     */
    public void crearVentana(){
        Label mensaje = new Label("Ingrese numero de personas para la mesa.");
        ComboBox<Integer> opciones = new ComboBox<Integer>();
        opciones.setItems(FXCollections.observableArrayList(2,3,4,5,6,7,8,10));
        Button butCrear = new Button("Crear");
        root.getChildren().addAll(mensaje,opciones,butCrear);
        opciones.setOnAction((comboEvent)->{
            numeroPersonas = opciones.getValue();
        });
        butCrear.setOnAction((eventCrar)->{
            crearMesa(numeroPersonas);
        });
    }
    
    /**
     * Metodo sin retorno que crea la mesa como tal en el panel de mesas
     * @param numPersonas, int que indica el numero de persons de la mesa
     */
    public void crearMesa(int numPersonas){
        Mesa mesaAgregar = new Mesa(String.valueOf(String.valueOf(GranChifa.baseDatos.getSetMesas().size()+1)), numPersonas, ESTADO.LIBRE, posX, posY);
        MesaGUI mesa = new MesaGUI(mesaAgregar);
        panelMesas.getChildren().add(mesa);
        mesa.setOnMousePressed((pressEvent)->{
            xinicial = mesa.getCircle().getCenterX();
            yinicial = mesa.getCircle().getCenterY();
        });
        mesa.setOnMouseDragged((dragEvent)->{
            double posX = dragEvent.getSceneX()-xinicial;
            double posY = dragEvent.getSceneY()-yinicial;
            mesa.mover(posX, posY);
        });
        GranChifa.baseDatos.getSetMesas().add(mesaAgregar);
    }
    
}
