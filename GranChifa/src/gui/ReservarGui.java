/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import auxiliar.CONSTANTES;
import static granchifa.GranChifa.baseDatos;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import restaurant.DataBase;
import restaurant.ESTADO;

/**
 *Sustentacion
 * @author DELL
 */
public class ReservarGui extends Stage{
    MesaGUI mesaGraf;
    VBox root;
    String nombreCliente;
    DataBase baseDatos;
    AdminGUI admingui;
    
    public ReservarGui(MesaGUI mesaGraf,DataBase baseDatos, AdminGUI admingui){
        this.admingui = admingui;
        this.baseDatos = baseDatos;
        this.mesaGraf = mesaGraf;
        this.root =new VBox();
        mostrarIngreso();
        this.setScene(new Scene(root,400,400));
        this.show();
    }
    
    
    
    public void mostrarIngreso(){
        Label lCliente = new Label("Cliente: ");
        TextField tNombre = new TextField();
        HBox top = new HBox(lCliente,tNombre);
        Button btReservar = new Button("Reservar");
        btReservar.disableProperty().bind(
        Bindings.isEmpty(tNombre.textProperty()));
        btReservar.setOnAction((evReservar)->{
            nombreCliente=tNombre.getText();
            //Tooltip.install(mesaGraf, new Tooltip(nombreCliente));
            mesaGraf.getMesa().setDisponibilidad(ESTADO.RESERVADO);
            mesaGraf.getMesa().setNombreCliente(nombreCliente);
            mesaGraf.getCircle().setFill(mesaGraf.fijarColor(ESTADO.RESERVADO));
            try(ObjectOutputStream ob = new ObjectOutputStream(new FileOutputStream(CONSTANTES.PATH_RECURSOS+"DataBase"))){
                ob.writeObject(baseDatos);
            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }
            admingui.mostrarMonitoreo();
            this.hide();
        });
        root.getChildren().addAll(top,btReservar);
        
    }

    public String getNombreCliente() {
        return nombreCliente;
    }
    
    
    
    
    
    
}
