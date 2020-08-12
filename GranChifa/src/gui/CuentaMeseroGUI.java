/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import restaurant.Pedido;

/**
 *
 * @author Wacho1
 */
public class CuentaMeseroGUI extends Stage {
    TextField campoTexto;
    VBox vbox= new VBox();
    Label l1= new Label("Ingrese nombre del cliente");
    Button confirmar;
    
    
    /**
     * Constructor de la clase que inicializa la cuenta de una mesa con su mesero
     * @param pedido, Pedidio 
     */
    public CuentaMeseroGUI(Pedido pedido){   
        campoTexto= new TextField();
        confirmar = new Button("Confirmar");
        vbox.getChildren().addAll(l1,campoTexto,confirmar);
        this.setScene(new Scene(vbox,300,300));
        confirmar.setOnAction((click)->{
            pedido.setNombreCliente(campoTexto.getText());
            this.close();
        });
        
        this.show();
    }
    
}
