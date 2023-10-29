package proyecto1;
import java.io.FileWriter;
import java.io.IOException;

/**
* It represents a Directed Graph, it models the social network
*/
public class Grafo {
    private Lista<User> users;

    public Grafo(Lista<User> users) {
        this.users = users;
    }
   
    
    
    

    // Add a new user to the graph
    public boolean addUser(String name) {
        // Check if user is already in the list
        Nodo<User> aux = getUsers().getpFirst();
        while (aux != null) {
            if (aux.getData().getName().equals(name)) {
                return false; // Element found in the list
            }
            aux = aux.getpNext();
        }
        
        // Add user to list
        User newUser = new User(name);
        getUsers().Insertar(newUser);
        return true;
    }
    
    // Deletes a user from the graph
    public boolean EliminarUser(String name) {
        // Check if user is in list
        Nodo<User> aux = getUsers().getpFirst();
        while (aux != null) {
            if (aux.getData().getName().equals(name)) {
                // Delete user from users list
                getUsers().delete(aux.getData());
                
                // Delete user from friendships
                aux = getUsers().getpFirst();
                while (aux != null) {
                    Nodo<User> friend = aux.getData().getFriends().getpFirst();
                    while(friend != null){
                        if(friend.getData().getName().equals(name)){
                            aux.getData().getFriends().delete(friend.getData());
                        }
                        friend = friend.getpNext();
                    }
                    aux = aux.getpNext();
                }
            
                return true; // Element found in the list
            }
            aux = aux.getpNext();
        }
        return false;
    }
    
    // Deletes a frienship to the graph
    public boolean addFriend(String source, String destino) {
        User origenUser = null;
        User destUser = null;

        // Find source and destination nodes
        for (int i=0; i< getUsers().getSize(); i++) {
            User aux = getUsers().get(i);
            if (aux.getName().equals(source)) {
                origenUser = aux;
            }
            else if (aux.getName().equals(destino)) {
                destUser = aux;
            }
        }

        if (origenUser != null && destUser != null) {
            // Check if destUser is in friends list
            Nodo<User> aux = origenUser.getFriends().getpFirst();
            while (aux != null) {
                if (aux.getData().getName().equals(destUser.getName())) {
                    return false; // Element found in the list
                }
                aux = aux.getpNext();
            }
        
            origenUser.getFriends().Insertar(destUser);           
            return true;
        } else {
            System.out.println("Invalid edge.");
            return false;
        }
    }  
    
    // Get the index of a node given its name
    int getIndex(String name){
        if(getUsers().getpFirst() == null) 
            return -1;
        if(getUsers().getpFirst().getData().getName().equals(name))
            return 0;
        int index = 1;
        Nodo<User> aux = getUsers().getpFirst().getpNext();
        while (aux != null) {
            if(aux.getData().getName().equals(name)){
                return index;
            }
            aux = aux.getpNext();
            index++;
        }
        return -1;
    }
    
    // Get a Node from a Graph (it can be the transpose) given the name of the user
    Nodo<User> getNode(String name, Lista<User> list){
        Nodo<User> aux = list.getpFirst();
        while (aux != null) {
            if(aux.getData().getName().equals(name)){
               return aux;
            }
            aux = aux.getpNext();
        }
        return null;
    }
    
    // Makes the DFS and adds the visited nodes to a list
    void DFS(int v, boolean[] visited, Lista<Integer> stack) {
        visited[v] = true;
        User aux = getUsers().get(v);
        
        int n = aux.getFriends().getSize();
        for(int j = 0; j < n; j++){
            String friend = aux.getFriends().get(j).getName();
            int friendIndex = getIndex(friend);
            
            if (!visited[friendIndex])
                DFS(friendIndex, visited, stack);
        }
        
        stack.Insertar(v);
    }
    
    // Makes the DFS of the transposes graph and returns a string with the visited nodes
    private String DFSTranpose(Lista<User> grafoTranspuesto, int v, boolean[] visited) {
        visited[v] = true;
        User aux = grafoTranspuesto.get(v);
        
        int n = aux.getFriends().getSize();
        for(int j = 0; j < n; j++){
            String friend = aux.getFriends().get(j).getName();
            int friendIndex = getIndex(friend);
            
            if (!visited[friendIndex])
                return Integer.toString(v) + " " + DFSTranpose(grafoTranspuesto, friendIndex, visited);
        }
        
        return Integer.toString(v);
    }
    
    // Find all the strong connected componentes (SCC) in the graph, and returns them in a string
    public String findSCC(){
        String components = "";
        Lista<Integer> stack = new Lista<>();

        int n = getUsers().getSize();
        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; i++) {
            visited[i] = false;
        }
        // Make the DFS of the normal graph
        for (int i = 0; i < n; i++) {
            if (!visited[i])
                DFS(i, visited, stack);
        }
        
        // Create transposed graph
        Lista<User> grafoTranspuesto = new Lista<>();
        Nodo<User> aux = getUsers().getpFirst();
        while (aux != null) {
            User CopiaUser = new User(aux.getData().getName());
            grafoTranspuesto.Insertar(CopiaUser);
            aux = aux.getpNext();
        }
        
        aux = getUsers().getpFirst();
        while (aux != null) {
            Nodo<User> friend = aux.getData().getFriends().getpFirst();
            while(friend != null){
                Nodo<User> friendT = getNode(friend.getData().getName(), grafoTranspuesto);
                friendT.getData().getFriends().Insertar(aux.getData());
                friend = friend.getpNext();
            }
            aux = aux.getpNext();
        }

            
        for (int i = 0; i < n; i++) {
            visited[i] = false;
        }
        
        // Make the DFS from the transposed graph
        while (n  > 0) {
            int v = stack.get(n-1);
            if (!visited[v]) {
                String scc = DFSTranpose(grafoTranspuesto, v, visited);
                components += scc + "\n";
            }
            stack.delete(v);
            n--;
        }
        
        return components;
    }
    
    // Write nodes and relationship in file
    public void GuardarArchivo(String filename){
        try{
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write("usuarios\n");
            Nodo<User> aux = getUsers().getpFirst();
            while (aux != null) {
                myWriter.write(aux.getData().getName() + "\n");
                aux = aux.getpNext();
            }
            
            myWriter.write("relaciones\n");
            aux = getUsers().getpFirst();
            while (aux != null) {
                Nodo<User> friend = aux.getData().getFriends().getpFirst();
                while(friend != null){
                    myWriter.write(aux.getData().getName()+ ", " + friend.getData().getName() + "\n");
                    friend = friend.getpNext();
                }
                aux = aux.getpNext();
            }
            
            myWriter.close();
        } catch (IOException f) {
          f.printStackTrace();
        } 
        
    }

    /**
     * @return the users
     */
    public Lista<User> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(Lista<User> users) {
        this.users = users;
    }
}
