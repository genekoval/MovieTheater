// ********************************************************
//  Interface ListInterface for the ADT list.
// *********************************************************
public interface ListInterface<T> {

    boolean isEmpty();

    int size();
    
    void add(int index, T item);
    
    T get(int index);
    
    T remove(int index);
    
    void removeAll();
}
