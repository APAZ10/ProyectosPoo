/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usuarios;

import gui.AdminGUI;

/**
 *
 * @author User
 */
public class UsAdmin extends Usuario{
    
   /**
    * Constructor para usuario admin
    * @param correo, string
    * @param password, string
    */ 
   public UsAdmin(String correo,String password){super(correo,password);
    }

    @Override
    public void activarGUI() {
        AdminGUI admingui= new AdminGUI();
    }
    
    //metodos de usuarios llaman a instancia de admingui 
    
}
