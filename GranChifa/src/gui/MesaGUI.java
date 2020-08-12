package gui;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import restaurant.ESTADO;
import restaurant.Mesa;
import restaurant.Pedido;
import usuarios.Usuario;



public class MesaGUI extends StackPane {
    Mesa mesa;
    Circle circle;
    Label number;
    Pedido pedido;
    
    /**
     * Constructor de la clase que 
     * @param mesa 
     */
    public MesaGUI(Mesa mesa){
        
        this.mesa=mesa;
        circle=new Circle();
        number=new Label(mesa.getNumMesa());
        circle.setRadius(10*mesa.getNumPersonas());
        circle.setFill(fijarColor(mesa.getDisponibilidad()));
        setLayoutX(mesa.getPosX());
        setLayoutY(mesa.getPosY());
        getChildren().addAll(circle,number);
        pedido = new Pedido();
    }

    public Mesa getMesa(){
        return mesa;
    }
    
    /**
     * Metodo que recibe un estado y devuelve un color acorde a ese estado
     * @param disponibilidad, ESTADO
     * @return COLOR
     */
    public Color fijarColor(ESTADO disponibilidad){
        if(disponibilidad == ESTADO.OCUPADO){
            Color c = Color.RED;
            return c;
        }else if(disponibilidad == ESTADO.POR_ATENDER){
            Color c = Color.GREEN;
            return c;
        }else if(disponibilidad == ESTADO.RESERVADO){
            Color c = Color.BLUEVIOLET;
            return c;
        }
        return Color.YELLOW;

    }
    
    /**
     * Metodo sin retorno que mueve la figura en x, y segun la posicion
     * que se les pasa por parametro
     * @param posx, double con posicion en x
     * @param posy, double con posicion en y
     */
    public void mover(double posx, double posy ){
        setLayoutX(posx-circle.getRadius());
        setLayoutY(posy-2*circle.getRadius());
        mesa.setPosX(posx-circle.getRadius());
        mesa.setPosY(posy-2*circle.getRadius());

    }
    
    public Circle getCircle() {
        return circle;
    }
    public Pedido getPedido() {
        return pedido;
    }
    public void SetPedido(Pedido pedido){
        this.pedido = pedido;
    }
}
