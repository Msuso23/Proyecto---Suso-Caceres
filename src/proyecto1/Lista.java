package proyecto1;

/**
* It represents a simple linked list of Nodes of type T
*/
class Lista<T> {
    private Nodo<T> pFirst;
    private int size = 0;
    
    // Adds a new node to the list
    public void Insertar(T data){
        Nodo<T> newNode = new Nodo<>(data);
        
        if(getpFirst() == null){
            setpFirst(newNode);
        } else {
            Nodo<T> aux = getpFirst();
            while(aux.getpNext() != null)
                aux = aux.getpNext();
            aux.setpNext(newNode);
        }
        
        setSize(getSize() + 1);
    }
    
    // It gets the node at the position given by index
    public T get(int index){
        if(getpFirst() == null)
            return null;
        if(index == 0)
            return getpFirst().getData();
        int i = 1;
        Nodo<T> current = getpFirst();
        while(current.getpNext() != null){
            if(i == index)
                return current.getpNext().getData();
            current = current.getpNext();
            i++;
        }
        return null;
    }
    
    // Deletes the node given by key
    public void delete(T key){
        if(getpFirst() == null)
            return;
        if(getpFirst().getData() == key){
            setpFirst(getpFirst().getpNext());
            setSize(getSize() - 1);
            return;
         }
        Nodo current = getpFirst();
        while(current.getpNext() != null){
            if(current.getpNext().getData() == key){
                current.setpNext(current.getpNext().getpNext());
                setSize(getSize() - 1);
                return;
            }
            current = current.getpNext();
        }
    }


    public Lista() {
        this.pFirst = null;
    }


    
    /**
     * @return the pFirst
     */
    public Nodo<T> getpFirst() {
        return pFirst;
    }

    /**
     * @param pFirst the pFirst to set
     */
    public void setpFirst(Nodo<T> pFirst) {
        this.pFirst = pFirst;
    }

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }
    
    
    
}
