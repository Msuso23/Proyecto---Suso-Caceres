package proyecto1;

/**
* It represents a Node of type T
 * @param <T>
*/
public class Nodo<T> {
    private T data;
    private Nodo<T> pNext;

    public Nodo(T data){
        this.data = data;
        this.pNext = null;
    }

    /**
     * @return the data
     */
    public T getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * @return the pNext
     */
    public Nodo<T> getpNext() {
        return pNext;
    }

    /**
     * @param pNext the pNext to set
     */
    public void setpNext(Nodo<T> pNext) {
        this.pNext = pNext;
    }
    
    
}
