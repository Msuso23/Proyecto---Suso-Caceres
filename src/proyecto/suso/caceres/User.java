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
   private String name;
   private Lista<User> friends;

    public User(String name) {
        this.name = name;
        this.friends = new Lista<>();
    }

   
   

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the friends
     */
    public Lista<User> getFriends() {
        return friends;
    }

    /**
     * @param friends the friends to set
     */
    public void setFriends(Lista<User> friends) {
        this.friends = friends;
    }

   
   
}
