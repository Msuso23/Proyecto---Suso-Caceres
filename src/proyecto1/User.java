package proyecto1;

/**
* It represents a User in the social network, each one has a list of friends (Users)
*/
public class User {
   private String name;
   private Lista<User> Friends;

    public User(String name) {
        this.name = name;
        this.Friends = new Lista<>();
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
        return Friends;
    }

    /**
     * @param friends the friends to set
     */
    public void setFriends(Lista<User> friends) {
        this.Friends = friends;
    }

    
   
   
}
