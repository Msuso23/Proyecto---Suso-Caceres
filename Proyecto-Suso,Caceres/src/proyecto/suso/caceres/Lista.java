/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto.suso.caceres;

/**
 *
 * @author Contingencia
 * @param <T>
 */
public class Lista <T> {
    private Nodo<T> pFirst;
    private int size = 0;

    public Lista() {
        this.pFirst = null;
    }
    
    
    public void Insertar(T data){
        Nodo<T> nuevoNodo = new Nodo<>(data);
        
        if(getpFirst() == null){
            setpFirst(nuevoNodo);
        } else {
            Nodo<T> aux = getpFirst();
            while(aux.getpNext() != null)
                aux = aux.getpNext();
            aux.setpNext(nuevoNodo);
        }
        
        setSize(getSize() + 1);
    }

    public T getElement(int index){
        if(getpFirst() == null)
            return null;
        if(index == 0)
            return getpFirst().getData();
        int i = 1;
        Nodo<T> aux = getpFirst();
        while(aux.getpNext() != null){
            if(i == index)
                return aux.getpNext().getData();
            aux = aux.getpNext();
            i++;
        }
        return null;
    }
    
    
    public void delete(T key){
        if(getpFirst() == null)
            return;
        if(getpFirst().getData() == key){
            setpFirst(getpFirst().getpNext());
            setSize(getSize() - 1);
            return;
         }
        Nodo aux = getpFirst();
        while(aux.getpNext() != null){
            if(aux.getpNext().getData() == key){
                aux.setpNext(aux.getpNext().getpNext());
                setSize(getSize() - 1);
                return;
            }
            aux = aux.getpNext();
        }
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
