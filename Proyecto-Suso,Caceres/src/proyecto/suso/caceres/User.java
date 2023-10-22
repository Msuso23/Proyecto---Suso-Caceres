/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto.suso.caceres;

/**
 *
 * @author Contingencia
 */
public class User {
    String name;
   Lista<User> friends;
   
   public User(String name) {
        this.name = name;
        this.friends = new Lista<>();
    }
    
    
}
