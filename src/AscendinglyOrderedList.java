/**
 * A sorted list with all of its elements in ascending order.
 * The items in this list are ordered based on their search keys.
 * An ArrayBasedList is used to hold all of the items in this collection.
 * Items can retrieved and removed using their search keys or by index.
 * Adding items can only be done based on their search keys. This ensures
 * all items are always in sorted order.
 *
 * @author Eugene Koval
 * @version 2017.04.22
 *
 * @param <T> the type of items held in this collection
 * @param <KT> the type of each item's search key
 */
public class AscendinglyOrderedList<T extends KeyedItem<KT>, 
KT extends Comparable<? super KT>> {
   
    /* The list containing this list's items. */
    private ArrayBasedList<T> items;
    
    /**
     * Constructs an empty Ascendingly-Ordered List.
     */
    public AscendinglyOrderedList() {
        items = new ArrayBasedList<T>();
    }

    /**
     * Returns true if this list contains no items.
     *
     * @return true if this list contains no items.
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * Returns the total number of items in this list.
     *
     * @return the total number of items.
     */
    public int size() {
        return items.size();
    }

    /**
     * Returns the index of the item with a search key that matches the 
     * specified search key. If no item matching the search key is found,
     * the index where the item would go is returned in an encoded manner.
     * To decode the index of failed search, multiply it by -1 and add 1.
     *
     * @param searchKey the search key to search for
     * @return index of the found item or encoded index of where the item would go
     */
    public int indexOf(KT searchKey) {
        int result = 0;
        int low = 0;
        int high = items.size() - 1;
        while (low < high) {
            int mid = (low + high) / 2;
            if (items.get(mid).getKey().compareTo(searchKey) < 0)
                low = mid + 1;
            else
                high = mid;
        }
        if (high >= 0 && items.get(low).getKey().equals(searchKey))
            result = low;
        else // Encode failed search result.
            result = (low + 1) * -1;
        return result;
    }

    /**
     * Returns true if an item with a search key matching the specified search
     * key is in this list.
     *
     * @param searchKey the search key to search for
     * @return true if the item searched for is in this list
     */
    public boolean contains(KT searchKey) {
        return indexOf(searchKey) >= 0;
    }

    /**
     * Returns the item at the specified position in this list.
     *
     * @param index index of the item to return
     * @return the item at the specified position in this list.
     */
    public T get(int index) {
        return items.get(index);
    }

    /**
     * Returns the item with a search key equal to the specified one.
     *
     * @param searchKey the search key to search for
     * @return the item matching the specified search key
     */
    public T get(KT searchKey) {
        int index = indexOf(searchKey);
        if (index >= 0)
            return items.get(index);
        else
            throw new ListException("Item not found in list.");
    }
    
    /**
     * Inserts the specified item into the proper position in the list.
     * After this call, the new item will be in the list with the list retaining
     * its sorted fashion.
     *
     * @param newItem item to be inserted
     */
    public void add(T newItem) {
        int numItems = items.size();
        KT searchKey = newItem.getKey();
        if (numItems == 0 || searchKey.compareTo(items.get(0).getKey()) < 0)
            items.add(0, newItem);
        else if (searchKey.compareTo(items.get(numItems - 1).getKey()) > 0)
            items.add(numItems, newItem);
        else {
            int target = indexOf(searchKey);
            if (target < 0)
                target = (target * -1) - 1;
            items.add(target, newItem);
        }
    }
   
    /**
     * Returns and removes the item at the specified index.
     *
     * @param index the index of the item to be removed
     * @return the removed item
     */
    public T remove(int index) {
        return items.remove(index);
    }

    /**
     * Returns and removes the item with the search key matching the specified
     * one. If the item is not found, a ListException is thrown.
     *
     * @param searchKey the search key to search for
     * @return the removed item
     */
    public T remove(KT searchKey) {
        int target = indexOf(searchKey);
        if (target >= 0)
            return items.remove(target);
        else
            throw new ListException("Item not found in list.");
    }
    
    /**
     * Removes all items in this list.
     * After this call, the list will be empty.
     */
    public void removeAll() {
        items.removeAll();
    }

    /**
     * Returns a String representation of this list.
     *
     * @return a String representation of this list
     */
    @Override
    public String toString() {
        return items.toString();
    }
}
