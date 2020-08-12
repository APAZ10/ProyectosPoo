/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import restaurant.TIPOPLATO;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import restaurant.Plato;
import granchifa.GranChifa;
import java.time.LocalDate;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import restaurant.ESTADO;
import restaurant.Pedido;
/**
 *
 * @author DELL
 */
public class MenuGUI extends Stage{
    private BorderPane root;
    private VBox centro;
    private VBox left;
    private VBox cuenta;
    private double total = 0;
    private Label ltotal;
//    private Pedido pedido;
    private MesaGUI mesaGraf;
    
     /**
     * Constructor de la clase que inicializa el root del menu
     * la cuenta de la mesa
     * @param mesaGraf, MesaGUI
     */
    public MenuGUI(MesaGUI mesaGraf) {
        this.mesaGraf = mesaGraf;
//        this.pedido = pedido;
        this.root = new BorderPane();
        root.setId("root");
        centro = new VBox();
        left = new VBox();
        left.setId("seccionPedido");
        
        cuenta = new VBox();
        
        cuenta.setPrefWidth(200);
        ltotal = new Label();
        
        Label ltop = new Label("Mesa "+this.mesaGraf.getPedido().getNumMesa()+","+this.mesaGraf.getPedido().getNombreCliente());
        ltop.setAlignment(Pos.CENTER);
        
        root.setTop(ltop);
        root.setCenter(centro);
        mostrarSecciones();
        seccionLeft();
       
        root.setLeft(left);
        this.setScene(new Scene(root,800,800));
        this.getScene().getStylesheets().add("recursos/estilos/estiloMenuGUI.css");
        this.show();
    }
    /**
     * Metodo sin retorno que verifica el pedidio con sus platos
     */
    public void verificarPedido(){
        if(mesaGraf.getPedido().getPlatosPedidos().size()!= 0){
            for(Plato p: mesaGraf.getPedido().getPlatosPedidos()){
                mostrarCuenta(p);
            }
        }
    }
    
    /**
     * Metodo sin retorno que muestra las secciones con los tipos de platos
     * al abrir la cuenta
     */
    public void mostrarSecciones() {
        HBox topOpciones = new HBox();
        topOpciones.setSpacing(10);
        topOpciones.setId("topOpciones");
        for(TIPOPLATO tipo : TIPOPLATO.values()){
            Button seccion = new Button(String.valueOf(tipo));
            topOpciones.getChildren().add(seccion);
            seccion.setOnMouseClicked((e)->{
                if(centro.getChildren().size()==2){
                    centro.getChildren().remove(1);
                }
                seccionCentro(tipo);
            });
        }
        centro.getChildren().add(topOpciones);
    }
    
        /**
     * Metodo sin retorno que setea el centro del root principal
     * @param secciontipo, TIPOPLATO
     */
    public void seccionCentro(TIPOPLATO secciontipo){
        FlowPane platos = new FlowPane();
        platos.setId("seccionPlatos");
        for(Plato p : GranChifa.baseDatos.getSetPlatos()){
            if(p.getTipo() == secciontipo){
                VBox cartaPlato = new VBox();
                System.out.println(p.getImagen());
                ImageView imgPlato = new ImageView(new Image(p.getImagen()));
                imgPlato.setFitWidth(30);
                imgPlato.setFitHeight(30);
                cartaPlato.getChildren().addAll(imgPlato,new Label(p.getNombre()),new Label(String.valueOf(p.getPrecio())));
                platos.getChildren().add(cartaPlato);
                //Manejar evento de clickear la carta de platos
                cartaPlato.setOnMouseClicked((e)->{
                    mostrarCuenta(p);
                    mesaGraf.getPedido().getPlatosPedidos().add(p);
                    ltotal.setText(String.valueOf(total)); //Ponerlo debajo de los precios, mandar a derecha
                });
            } 
        }
        centro.getChildren().add(platos);
        
    }
    
    /**
     * Metodo sin retorno que muestra la cuenta a medida que se vayan agregando platos
     * @param p,Plato 
     */
    public void mostrarCuenta(Plato p){
        HBox item = new HBox();
        item.setSpacing(50);
        Label lnombre = new Label(p.getNombre());
        Label precio = new Label(String.valueOf(p.getPrecio()));
        item.getChildren().addAll(lnombre,precio);
        total+= p.getPrecio(); //revisar
        cuenta.getChildren().add(item);
        
    }
    
    /**
     * Metodo sin retorno que setea la seccion izquierda del root para la cuenta
     */
    public void seccionLeft(){
       Button finalOrden = new Button("Finalizar orden");
       finalOrden.setOnAction((eventoFinal) ->{
           mesaGraf.getMesa().setDisponibilidad(ESTADO.LIBRE);
           mesaGraf.getCircle().setFill(mesaGraf.fijarColor(mesaGraf.getMesa().getDisponibilidad()));
           mesaGraf.getPedido().setLc(LocalDate.now());
           mesaGraf.getPedido().setTotal(Double.valueOf(ltotal.getText()));
           GranChifa.baseDatos.getPedidos().add(mesaGraf.getPedido());
           mesaGraf.getPedido().setNumPedido(GranChifa.baseDatos.getPedidos().size());
           mesaGraf.SetPedido(new Pedido());
           this.hide();
          
       });
       Button regresar = new Button("Regresar");
       regresar.setOnAction((e)->{
           this.hide();
       });
       left.getChildren().addAll(cuenta,ltotal,finalOrden,regresar); //revisar     
   }
    
    
       
}

