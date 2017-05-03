/**
 * A resizable-array implementation of the ListInterface.
 * 
 * @author Eugene Koval
 * @version 2017.04.21
 *
 * @param <T> the type of items held in this collection
 */
public class ArrayBasedList<T> implements ListInterface<T> {

    /** The array containing this list's items. */
    protected T[] items;
    /** The total number of items in this list. */
    protected int numItems;

    /**
     * Constructs an empty list with an initial capacity of three.
     */
    public ArrayBasedList() {
        items = (T[]) new Object[3];
        numItems = 0;
    }

    /** 
     * Returns true if this list contains no items.
     *
     * @return true if this list contains no items.
     */
    public boolean isEmpty() {
        return numItems == 0;
    }

    /**
     * Returns the total number of items in this list.
     *
     * @return the total number of items.
     */
    public int size() {
        return numItems;
    }
    
    /**
     * Returns the item at the specified position in this list.
     *
     * @param index index of the item to return
     * @return the item at the specified positino in this list
     */
    public T get(int index) {
        if (index >= 0 && index < numItems) 
            return items[index];
        else
            throw new ListIndexOutOfBoundsException(
                "List index " + index + " out of bounds on get.");
    }

    /**
     * Inserts the specified item at the specified position in this list.
     * Shifts the item currently at that position (if any) and any 
     * subsequent items to the right.
     *
     * @param index index at which the specified item is to be inserted
     * @param item item to be inserted
     */
    public void add(int index, T item) {
        if (index >= 0 && index <= numItems) {
            if (numItems == items.length)
                resize();
            for (int pos = numItems - 1; pos >= index; pos--) 
                items[pos + 1] = items[pos];
            items[index] = item;
            numItems++;
        }
        else
            throw new ListIndexOutOfBoundsException(
               "List index " + index + " out of bounds on add.");
    }
    
    /**
     * Removes the item at the specified position in this list.
     * Shifts any subsequent items to the left.
     *
     * @param index the index of the item to be removed
     */
    public T remove(int index) {
        if (index >= 0 && index < numItems) {
            T item = items[index];
            for (int pos = index + 1; pos < numItems; pos++)
                items[pos - 1] = items[pos];
            items[numItems - 1] = null;
            numItems--;
            return item;
        }
        else
            throw new ListIndexOutOfBoundsException(
                "List index " + index + " out of bonds on remove.");
    }

    /**
     * Removes all of the items from this list.
     * The list will be empty after this call returns.
     * The list's capacity will return to the initial size of three.
     */
    public void removeAll() {
        items = (T[]) new Object[3];
        numItems = 0;
    }

    /** 
     * Returns the String representation of this list.
     * The returned String will include the String representation of all of 
     * this list's item.
     *
     * @return the String representation of this list
     */
    @Override
    public String toString() {
        String listString = "";
        for (int i = 0; i < numItems; i++)
            listString += items[i].toString() + '\n';
        return listString;
    }
    
    /**
     * Resizes this list to make room for additional items.
     * The list's sie is increased by a factor of 1.5.
     */
    private void resize() {
        T[] resizedItems = (T[]) new Object[(items.length * 3) / 2 + 1];
        for (int i = 0; i < numItems; i++)
            resizedItems[i] = items[i];
        items = resizedItems;
    }
}
