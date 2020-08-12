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
import granchifa.GranChifa;
import static granchifa.GranChifa.baseDatos;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javax.swing.event.HyperlinkEvent;
import restaurant.DataBase;
import restaurant.ESTADO;
import restaurant.Mesa;
import restaurant.Pedido;
import restaurant.Plato;
import restaurant.TIPOPLATO;

/**
 *
 * @author Alejandro
 */
public class AdminGUI {
    private VBox root;
    private Scene adminScene;
    private double xinicial;
    private double yinicial;
    private Label montoFacturado;
    private boolean monitoreando=false;
    private TableView<Pedido> tabla;
    //sustentacion
    ReservarGui reserva;
    
    /**
     * Constructor de la clase que inicializa el root del administrador y crea
     * los botones con sus respectivas acciones
     */
    public AdminGUI(){
        root = new VBox();
        crearOpciones();
        adminScene = new Scene(root,800,800);
        adminScene.getStylesheets().add("recursos/estilos/estiloAdminGUI.css");
        GranChifa.stage.setScene(adminScene);
    }
    
    /**
     * Metodo sin retorno que genera los botones en la parte superior del root
     * del administrador
     */
    public void crearOpciones(){
        HBox seccionBotones = new HBox();
        seccionBotones.setId("seccionBotones");
        seccionBotones.setAlignment(Pos.CENTER);
        seccionBotones.setPadding(new Insets(10,10,10,10));
        seccionBotones.setSpacing(30);
        Button opcionMon = new Button("Monitoreo");
        Button opcionDesign = new Button("Diseño Plano");
        Button opcionGestion = new Button("Gestión Menú");
        Button opcionReporte = new Button("Reporte Ventas");
        Button opcionSalida = new Button("Salir");
        seccionBotones.getChildren().addAll(opcionMon,opcionDesign,opcionGestion,opcionReporte,opcionSalida);
        root.getChildren().add(seccionBotones);
        
        //Evento para Volver a Log In
        opcionSalida.setOnAction((salidaEvent)->{
            Login logGui = new Login();
            GranChifa.stage.setScene(logGui.getLoginScene());
        });
        
        //Evento para Monitoreo
        opcionMon.setOnAction((monEvent) -> {
            mostrarMonitoreo();
            monitoreando=true;
            Thread t = new Thread(new Refrescador());
            t.start();
        });
        
        //Evento para Design
        opcionDesign.setOnAction((designEvent)->{
            monitoreando=false;
            mostrarDesign();
        });
        
        //Evento para Gestionar Menu
        opcionGestion.setOnAction((gestionEvent)->{
            monitoreando=false;
            mostrarGestion();
        });
        
        //Evento para Reporte
        opcionReporte.setOnAction((reporteEvent)->{
            monitoreando=false;
            mostrarReporte();
        });
        
          
    }
    
    /**
     * Metodo sin retorno que da la accion al boton de monitorear el 
     * establecimiento
     */
    public void mostrarMonitoreo(){
        Pane panelMesas = new Pane();
        panelMesas.setId("panelMesas");
        try(ObjectInputStream ob =new ObjectInputStream(new FileInputStream(CONSTANTES.PATH_RECURSOS+"DataBase"))){
            baseDatos= (DataBase) ob.readObject();       
        } catch (IOException | ClassNotFoundException ex) {   
            System.out.println("errorx");
        }
        double monto= 0;
        ArrayList<Pedido> arrayListPedido= baseDatos.getPedidos();
        for (Pedido pedido : arrayListPedido){
            if(pedido.getLc().isEqual(LocalDate.now())){
                for (Plato plato : pedido.getPlatosPedidos()){
                    monto+=plato.getPrecio();
                }
            }
        }
        Set<Mesa> setMesasMonitoreo = baseDatos.getSetMesas();
        for (Mesa mesa : setMesasMonitoreo) {
            MesaGUI mesaGraf = new MesaGUI(mesa);
            
            panelMesas.getChildren().add(mesaGraf);           
            mesaGraf.setOnMouseEntered((monitoreoEvent)->{
                //para sustentacion comentar
                //mostrarInfo(mesaGraf);
                if(mesaGraf.getMesa().getDisponibilidad()==ESTADO.RESERVADO){
                    Tooltip.install(mesaGraf, new Tooltip(mesa.getNombreCliente()));
//                    if(reserva!=null){
//                        Tooltip.install(mesaGraf, new Tooltip(mesa.getNombreCliente()));
//                    }
                }
            });           
            //Buscar mejor solucion 
            mesaGraf.setOnMousePressed((pressEvent)->{
                mostrarReservador(mesaGraf,baseDatos,this);
            });
            mesaGraf.setOnMouseDragged((dragEvent)->{
            });            
        }
        montoFacturado = new Label("Monto: "+String.valueOf(monto));
        montoFacturado.setLayoutX(100);
        montoFacturado.setLayoutY(30);
        panelMesas.getChildren().add(montoFacturado);
        verificarSize();
        root.getChildren().add(panelMesas);
    }
    
    /**
     * Metodo que obtinene el pedido
     * @return un obcservablelist
     */
     public ObservableList<Pedido> obtenerPedidos(){
        ObservableList<Pedido> listaPedido = FXCollections.observableArrayList();
        try(ObjectInputStream ob =new ObjectInputStream(new FileInputStream(CONSTANTES.PATH_RECURSOS+"DataBase"))){
            baseDatos= (DataBase) ob.readObject();       
        } catch (IOException | ClassNotFoundException ex) {   
            System.out.println("errorx");
        }
        for (Pedido p : baseDatos.getPedidos()) {
            listaPedido.add(p);

        }
        return listaPedido;
    }
       
    /**
     * Metodo sin retorno que muestra un TableView con los pedidos filtrdos
     * por fecha
     */
     
    public void mostrarReporte(){
        VBox seccionReporte = new VBox();
        
        DatePicker minDate = new DatePicker();
        DatePicker maxDate = new DatePicker();
        Button buscarBut = new Button("Buscar");
        buscarBut.setOnAction((e)-> {
            Predicate<Pedido> entre = pedido -> pedido.getLc().isAfter(minDate.getValue())&&pedido.getLc().isBefore(maxDate.getValue());
            ObservableList<Pedido> listaPed = FXCollections.observableArrayList();
            try(ObjectInputStream ob =new ObjectInputStream(new FileInputStream(CONSTANTES.PATH_RECURSOS+"DataBase"))){
            baseDatos= (DataBase) ob.readObject();       
            } catch (IOException | ClassNotFoundException ex) {   
                System.out.println("errorx");
            }
            for (Pedido p : baseDatos.getPedidos()) {
                if(p.getLc().isAfter(minDate.getValue())&&p.getLc().isBefore(maxDate.getValue())){
                    listaPed.add(p);
                    
                }
               
            }
            tabla.setItems(listaPed);
        });
        HBox boxFechas = new HBox(minDate,maxDate,buscarBut);
        boxFechas.setSpacing(30);
        
        tabla = new TableView<>();
        TableColumn<Pedido,String> colFecha = new TableColumn<>("Fecha");
        TableColumn<Pedido,String> colMesa = new TableColumn<>("Mesa");
        TableColumn<Pedido,String> colMesero = new TableColumn<>("Mesero");
        TableColumn<Pedido,String> colNumCuenta = new TableColumn<>("# Cuenta");
        TableColumn<Pedido,String> colCliente = new TableColumn<>("Cliente");
        TableColumn<Pedido,String> colTotal = new TableColumn<>("Total");
        
        tabla.getColumns().addAll(colFecha,colMesa,colMesero,colNumCuenta,colCliente,colTotal);
        //agregar elementos
        
        colFecha.setCellValueFactory(new PropertyValueFactory<>("lc"));
        colMesa.setCellValueFactory(new PropertyValueFactory<>("numMesa"));
        colMesero.setCellValueFactory(new PropertyValueFactory<>("nombreMesero"));
        colNumCuenta.setCellValueFactory(new PropertyValueFactory<>("numPedido")); 
        colCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        tabla.setItems(obtenerPedidos());
        
        seccionReporte.getChildren().addAll(boxFechas,tabla);
//        if(root.getChildren().size()==2){
//            root.getChildren().remove(1);
//        }
        verificarSize();
        root.getChildren().add(seccionReporte);
        
    
    
    }
    
  /**
     * Metodo sin retorno que da la accion al bootn de diseno del restaurant
     * agregando, moviendo las respectivas mesas
     */
    public void mostrarDesign(){
        Pane panelMesas = new Pane();
        panelMesas.setId("panelMesas");
        panelMesas.setOnMouseClicked((agregarEvent)->{
            
            if(agregarEvent.isStillSincePress()){
                //Crear ventana para pedir datos num personas y num mesa
                double posX = agregarEvent.getSceneX();
                double posY = agregarEvent.getSceneY();
                CrearMesaGUI creador = new CrearMesaGUI(panelMesas,posX,posY);
            }
        });
        try(ObjectInputStream ob =new ObjectInputStream(new FileInputStream(CONSTANTES.PATH_RECURSOS+"DataBase"))){
            baseDatos= (DataBase) ob.readObject();
        } catch (IOException | ClassNotFoundException ex) {   
            System.out.println("errory");
        }
        Set<Mesa> setMesas = baseDatos.getSetMesas();
        for (Mesa mesa : setMesas) {
            MesaGUI mesaGraf = new MesaGUI(mesa);
            panelMesas.getChildren().add(mesaGraf);
            //
            if (mesaGraf.getMesa().getDisponibilidad() != ESTADO.LIBRE){
            Tooltip.uninstall(mesaGraf, new Tooltip(mesaGraf.getMesa().getMesero().getNombre()));
            }
            
            mesaGraf.setOnMousePressed((pressEvent)->{
                xinicial = mesaGraf.getCircle().getCenterX();
                yinicial = mesaGraf.getCircle().getCenterY();
            });
            mesaGraf.setOnMouseDragged((dragEvent)->{
                double posX = dragEvent.getSceneX()-xinicial;
                double posY = dragEvent.getSceneY()-yinicial;
                mesaGraf.mover(posX,posY);
            });
            //buscar mejor solucion
            mesaGraf.setOnMouseEntered((monitoreoEvent)->{
            }); 
            mesaGraf.setOnMouseClicked((clickIz)->{
                if(clickIz.isSecondaryButtonDown()){
                    System.out.println("borrar");
                }
            });
        }
        verificarSize();
        root.getChildren().add(panelMesas);
    }
    
    /**
     * Metodo sin retorno que muestra la info del mesero que tiene cada mesa
     * @param m , Variable tipo MesaGUI
     */
    public void mostrarInfo(MesaGUI m){
            if(m.getMesa().getDisponibilidad()==ESTADO.OCUPADO || m.getMesa().getDisponibilidad()==ESTADO.POR_ATENDER){
                Tooltip.install(m, new Tooltip(m.getMesa().getMesero().getNombre()));
            } 
    }    
    
    /**
     * Sustentacion
     * @param mesa
     * @param baseDatos 
     */
    public void mostrarReservador(MesaGUI mesa, DataBase baseDatos, AdminGUI admingui){
        if(mesa.getMesa().getDisponibilidad()==ESTADO.LIBRE){
            reserva = new ReservarGui(mesa,baseDatos,admingui);
            mostrarMonitoreo();
        }
    }
    
    
    /**
     * Metodo sin retorno que le permite al administrador tanto como editar
     * y agregar platos al menu
     */
    public void mostrarGestion(){
        VBox gestion= new VBox();
        HBox botones= new HBox();
        botones.setAlignment(Pos.CENTER);
        botones.setPadding(new Insets(10,10,10,10));
        botones.setSpacing(30);       
        Button beditar= new Button("Editar platos");
        Button bagregar= new Button("Agregar platos");
        botones.getChildren().addAll(beditar,bagregar);
        gestion.getChildren().add(botones);
        VBox down = new VBox();
        bagregar.setOnAction((agregarEvent)->{
            agregarPlatos(down);
                
        });
        beditar.setOnAction((e)-> {        
            editarPlatos(down);
                
        });
//        if(root.getChildren().size()==2){
//            root.getChildren().remove(1,root.getChildren().size());
//        }
        verificarSize();
        root.getChildren().addAll(gestion,down);        
    }
    
    /**
     * Metodo sin retorno que maneja el evento de agregar platos
     * @param down, Variable de tipo VBox que contiene los textFields
     */
    public  void agregarPlatos(VBox down){
        down.getChildren().clear();
        VBox agregarMenu= new VBox();
        agregarMenu.getChildren().clear();
        TextField t1=new TextField();
        TextField t2=new TextField();
        TextField t3=new TextField();
        TextField t4=new TextField();
        Button agg= new Button("Agregar plato");

        t1.setPromptText("Tipo de plato");
        t2.setPromptText("Nombre del plato");
        t3.setPromptText("Precio");
        t4.setPromptText("Ruta de la imagen");

        agg.disableProperty().bind(
        Bindings.isEmpty(t1.textProperty())
        .or(Bindings.isEmpty(t2.textProperty()))
        .or(Bindings.isEmpty(t3.textProperty()))
        .or(Bindings.isEmpty(t4.textProperty())));

        agg.setOnAction((platoEvent)->{
            GranChifa.baseDatos.getSetPlatos().add(new Plato(TIPOPLATO.valueOf(t1.getText()),
            t2.getText(),Double.parseDouble(t3.getText()),t4.getText()));
            try(ObjectOutputStream ob = new ObjectOutputStream(new FileOutputStream(CONSTANTES.PATH_RECURSOS+"DataBase"))){
                ob.writeObject(baseDatos);
            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }
        });

        agregarMenu.getChildren().addAll(t1,t2,t3,t4,agg);
        down.getChildren().add(agregarMenu);
}
    
    /**
     * Metodo sin retorno que mneja el evento de editar platos que se muestra en el 
     * VBox que se pasa por parametros
     * @param down, VBox que coniene los platos  editar
     */
    public void editarPlatos(VBox down){
        down.getChildren().clear();
        VBox contCambios = new VBox();
        Label seccion = new Label("Elija la seccion");
        down.getChildren().add(seccion);
        ComboBox<TIPOPLATO> tiposp = new ComboBox<>();
        tiposp.setItems(FXCollections.observableArrayList(TIPOPLATO.values()));
        
        tiposp.setOnAction((e)->{
        contCambios.getChildren().clear();
        try(ObjectInputStream ob =new ObjectInputStream(new FileInputStream(CONSTANTES.PATH_RECURSOS+"DataBase"))){
            baseDatos= (DataBase) ob.readObject();
        } catch (IOException | ClassNotFoundException ex) {   
            System.out.println("errorz");
        }
        for(Plato p : baseDatos.getSetPlatos()){
            
            if(p.getTipo() == tiposp.getValue()){
                VBox cartaPlato = new VBox();
                System.out.println(p.getImagen());
                ImageView imgPlato = new ImageView(new Image(p.getImagen()));
                imgPlato.setFitWidth(30);
                imgPlato.setFitHeight(30);
                cartaPlato.getChildren().addAll(imgPlato,new Label(p.getNombre()),new Label(String.valueOf(p.getPrecio())));
                contCambios.getChildren().add(cartaPlato);
                //Manejar evento de clickear la carta de platos
                cartaPlato.setOnMouseClicked((event)->{
                    TextField editarN = new TextField("Nuevo nombre");
                    TextField editarP = new TextField("Nuevo precio");
                    TextField editarTipo = new TextField("Nuevo tipo");
                    Button aceptar = new Button("Aceptar");
                    //toda esta parte podemos ponerla en hilo decidadn companeros del futuro
                    contCambios.getChildren().addAll(editarN,editarP,editarTipo,aceptar);
                    aceptar.setOnAction((event2)-> {
                        if(editarN != null){
                        p.setNombre(editarN.getText());
                        }else if (editarP!= null) {
                        p.setPrecio(Double.valueOf(editarP.getText()));
                        }else if (editarTipo!= null) {
                        p.setTipo(TIPOPLATO.valueOf(editarTipo.getText()));
                        }
                        try(ObjectOutputStream ob = new ObjectOutputStream(new FileOutputStream(CONSTANTES.PATH_RECURSOS+"DataBase"))){
                            ob.writeObject(baseDatos);
                        }
                        catch (IOException ex){
                            System.out.println(ex.getMessage());
                        }
                    });

                });
                
            } 

            
        }
                
        });

        down.getChildren().addAll(tiposp,contCambios);
        contCambios.getChildren().clear();
    }
    
    /**
     * Metodo sin retorno que verifica el size del root
     */
    public void verificarSize(){
        if(!(root.getChildren().size()<1)){
            root.getChildren().remove(1, root.getChildren().size());
        }
    }
    
    /**
     * hilo para el tiempo
     */
    class Refrescador implements Runnable{

        @Override
        public void run() {
            try{
                while(monitoreando){
                    Thread.sleep(60000);
                    Platform.runLater(()->{
                        mostrarMonitoreo();
                    });
                }
            }catch(InterruptedException rm){
                
            }
       
        }
        
    }
}
