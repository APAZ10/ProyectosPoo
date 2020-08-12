/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package granchifa;

import auxiliar.CONSTANTES;
import gui.Login;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import restaurant.DataBase;
import restaurant.Pedido;

/**
 *
 * @author Alejandro
 */
public class GranChifa extends Application {
    public static DataBase baseDatos;
    public static Stage stage;
    
    @Override
    public void init(){
        try(ObjectInputStream ob =new ObjectInputStream(new FileInputStream(CONSTANTES.PATH_RECURSOS+"DataBase"))){
            baseDatos= (DataBase) ob.readObject();
        } catch (IOException | ClassNotFoundException ex) {   
            baseDatos = new DataBase();
        }
    }
    
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("G5 SYSTEM");
        Login logInicio = new Login();
        stage.getIcons().add(new Image("recursos/logo.png"));
        stage.setScene(logInicio.getLoginScene());
        stage.show();
    }
    
    @Override
    public void stop (){
        for(Pedido pedido: GranChifa.baseDatos.getPedidos()){
               System.out.println(pedido);
           }
        try(ObjectOutputStream ob = new ObjectOutputStream(new FileOutputStream(CONSTANTES.PATH_RECURSOS+"DataBase"))){
            ob.writeObject(baseDatos);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
