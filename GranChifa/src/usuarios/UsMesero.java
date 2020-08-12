/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usuarios;

import gui.MeseroGUI;
import java.util.Objects;

/**
 *
 * @author Arturo
 */
public class UsMesero extends Usuario {
    String nombre;
    
    /**
     * Constructor para usuario mesero
     * @param correo, string
     * @param password, string
     * @param nombre string
     */
    public UsMesero(String correo,String password,String nombre){
        super(correo,password);
        this.nombre=nombre;
    }

    @Override
    public void activarGUI() {
        MeseroGUI meserogui = new MeseroGUI(this); 
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UsMesero other = (UsMesero) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }
    
    
    
}
