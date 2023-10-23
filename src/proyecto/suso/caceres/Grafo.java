/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto.suso.caceres;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Contingencia
 */
public class Grafo {
    private Lista<User> users;

    public Grafo(Lista<User> users) {
        this.users = users;
    }

   
    

    // Añade un nuevo usuario al grafo
    public boolean addUser(String name) {
        // Revisa si el usuario está en la lista
        Nodo<User> aux = getUsers().getpFirst();
        while (aux != null) {
            if (aux.getData().getName().equals(name)) {
                return false; // Se encuentra en la lista
            }
            aux = aux.getpNext();
        }
        
        // Añade un usuario a la lista
        User newUser = new User(name);
        getUsers().Insertar(newUser);
        return true;
    }
    
    
    public boolean EliminarUser(String name) {
        // Busca si se encuentra en la lista
        Nodo<User> aux = getUsers().getpFirst();
        while (aux != null) {
            if (aux.getData().getName().equals(name)) {
                // Elimina el usuario de la lista
                getUsers().delete(aux.getData());
                
                // Elimina el usuario de las relaciones
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
            
                return true; // Se encuentra en la lista
            }
            aux = aux.getpNext();
        }
        return false;
    }
    
    // Elimina una amistad de las relaciones en el grafo
    public boolean addFriend(String source, String destino) {
        User origenUser = null;
        User destUser = null;

        // Busca los nodos origen y destino
        for (int i=0; i< getUsers().getSize(); i++) {
            User aux = getUsers().getElement(i);
            if (aux.getName().equals(source)) {
                origenUser = aux;
            }
            else if (aux.getName().equals(destino)) {
                destUser = aux;
            }
        }

        if (origenUser != null && destUser != null) {
            // Revisa si el destinatario se encuentra en la lista
            Nodo<User> aux = origenUser.getFriends().getpFirst();
            while (aux != null) {
                if (aux.getData().getName().equals(destUser.getName())) {
                    return false; 
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
    
    // Busca el nodo segun el indice 
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
    
    // Extrae los datos del nodo
    Nodo<User> getNodo(String name, Lista<User> list){
        Nodo<User> aux = list.getpFirst();
        while (aux != null) {
            if(aux.getData().getName().equals(name)){
               return aux;
            }
            aux = aux.getpNext();
        }
        return null;
    }
    
    // Crea el DFS y añade los nodos a la lista
    void DFS(int v, boolean[] visited, Lista<Integer> stack) {
        visited[v] = true;
        User aux = getUsers().getElement(v);
        
        int n = aux.getFriends().getSize();
        for(int j = 0; j < n; j++){
            String friend = aux.getFriends().getElement(j).getName();
            int friendIndex = getIndex(friend);
            
            if (!visited[friendIndex])
                DFS(friendIndex, visited, stack);
        }
        
        stack.Insertar(v);
    }
    
    //Crea el DFS  del grafo tranpuesto y retorna un string con los nodos visitados

    private String DFSTranpose(Lista<User> grafoTranspuesto, int v, boolean[] visited) {
        visited[v] = true;
        User aux = grafoTranspuesto.getElement(v);
        
        int n = aux.getFriends().getSize();
        for(int j = 0; j < n; j++){
            String friend = aux.getFriends().getElement(j).getName();
            int friendIndex = getIndex(friend);
            
            if (!visited[friendIndex])
                return Integer.toString(v) + " " + DFSTranpose(grafoTranspuesto, friendIndex, visited);
        }
        
        return Integer.toString(v);
    }
    
    // Busca todos los enlaces fuertes SCC y los retorna como string
    public String findSCC(){
        String components = "";
        Lista<Integer> stack = new Lista<>();

        int n = getUsers().getSize();
        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; i++) {
            visited[i] = false;
        }
        // Crea el DFS del grafo normal
        for (int i = 0; i < n; i++) {
            if (!visited[i])
                DFS(i, visited, stack);
        }
        
        // Crea el grafo transpuesto
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
                Nodo<User> friendT = getNodo(friend.getData().getName(), grafoTranspuesto);
                friendT.getData().getFriends().Insertar(aux.getData());
                friend = friend.getpNext();
            }
            aux = aux.getpNext();
        }

            
        for (int i = 0; i < n; i++) {
            visited[i] = false;
        }
        
        // Crea el DFS del grafo transpuesto
        while (n  > 0) {
            int v = stack.getElement(n-1);
            if (!visited[v]) {
                String scc = DFSTranpose(grafoTranspuesto, v, visited);
                components += scc + "\n";
            }
            stack.delete(v);
            n--;
        }
        
        return components;
    }
    
    // Guarda y escribe nodos y relaciones en un archivo
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
