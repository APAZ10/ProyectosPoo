/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import granchifa.GranChifa;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import usuarios.Usuario;

/**
 *
 * @author Alejandro
 */
public class Login {
    private GridPane loginRoot;
    private Scene loginScene;
    private Map<String,Usuario> mapUsuarios;
    private Usuario usuarioLogeado;
    private Label mensajeLog;
    
    /**
     * Constructor de la clase que inicializ el root principal donde se inicia 
     * sesion
     */
    public Login(){
        mapUsuarios = GranChifa.baseDatos.getMapUs();
        loginRoot = new GridPane();
        loginRoot.setId("loginRoot");
        crearLogin();
        loginScene = new Scene(loginRoot,600,600);
        loginScene.getStylesheets().add("recursos/estilos/estiloLogIn.css");
    }
    
    /**
     * Metodo sin retorno que inicia sesion sea administrador o empleado
     */
    public void crearLogin(){
        loginRoot.setPadding(new Insets(10,10,10,10));
        loginRoot.setAlignment(Pos.CENTER);
        loginRoot.setVgap(10);
        loginRoot.setHgap(10);
        
        Label userL = new Label("Username:");
        Label passL = new Label("Password:");
        TextField userField = new TextField();
        PasswordField passField = new PasswordField();
        passField.setPromptText("password");
        Button logButton = new Button("Log In");
        mensajeLog = new Label();
        
        loginRoot.add(userL, 0, 0);
        loginRoot.add(passL, 0, 1);
        loginRoot.add(userField, 1, 0);
        loginRoot.add(passField, 1, 1);
        loginRoot.add(logButton, 1, 2);
        loginRoot.add(mensajeLog, 1, 3);
        //Obtener info del usuario
        //cambio de Scene al dar click
        logButton.setOnAction((logEvent)->{
            String correoIngresado = userField.getText();
            String passIngresado = passField.getText();
            usuarioLogeado = obtenerUsuario(correoIngresado, passIngresado);
            //activar el metodo GUI con el usuarioLogeado
            if(usuarioLogeado != null){
                usuarioLogeado.activarGUI();
            }
        });      
    }
    
     /**
     * Metodo get
     * @return devuelve la escena del root
     */
    public Scene getLoginScene() {
        return loginScene;
    }
    
     /**
     * Metodo que obtiene el usuario logedo al inicio
     * @param mail, string correo del usuario
     * @param password, string clave del usuario
     * @return 
     */
    public Usuario obtenerUsuario(String mail, String password){
        Usuario userEncontrado = mapUsuarios.get(mail);
        if(userEncontrado!= null){
            if(userEncontrado.getPassword().equals(password)){
                return userEncontrado;
            }else{
                mensajeLog.setText("Password Incorrecta");
                return null;
            }
        }else{
            mensajeLog.setText("No se encuentra registrado");
            return null;
        }
    }
       
}
