/**
 * A resizable-array implementation of the QueueInterface.
 * The elements in a queue are processed in a FIFO (first-in-first-out) manner.
 * New elements are added to the back of a queue. Elements can only be removed
 * from the front of a queue. 
 * 
 * @author Brandon Campbell
 * @author Eugene Koval
 * @version 2017.04.22
 *
 * @param <T> the type of elements held in this collection
 */
public class Queue<T> implements QueueInterface<T> {

    /** The array of items in this queue. */
    protected T[] items;
    /** The index of the first item. */
    protected int front = 0;
    /** The index of the last item. */
    protected int back = 0;
    /** The total number of items in this queue. */
    protected int numItems = 0;

    /**
     * Constructs an empty queue.
     * The underlying array has the initial size of 3.
     */
    public Queue() {
        items = (T[]) new Object[3];
    }

    /**
     * Returns true if there are no items in this queue.
     *
     * @return true if there are no elements 
     */
    public boolean isEmpty() {
        return numItems == 0;
    }

    /**
     * Returns the total number of items in this queue.
     *
     * @return the total number of items.
     */
    public int size() {
        return numItems;
    }

    /**
     * Inserts the specified item into this queue.
     *
     * @param newItem the item to add
     */
    public void enqueue(T newItem) {
        if (numItems == items.length)
            resize();
        items[back] = newItem;
        back = (back + 1) % items.length;
        numItems++;
    }

    /**
     * Retrieves and removes the first item in this queue.
     * 
     * @return the removed item
     */
    public T dequeue() {
        if (! (numItems == 0)) {
            T item = items[front];
            items[front] = null;
            front = (front + 1) % items.length;
            numItems--;
            return item;
        }
        else
            throw new QueueException("Queue is empty. Nothing to dequeue.");
    }

    /**
     * Removes all items from this queue.
     * The underlying array returns to initial size of 3.
     */
    public void dequeueAll() {
        items = (T[]) new Object[3];
        front = 0;
        back = 0;
        numItems = 0;
    }

    /**
     * Retrieves, but does not remove, the first item in this queue. 
     * Throws a QueueException if this queue is empty.
     *
     * @return the first item in this queue
     */
    public T peek() {
        if (! (numItems == 0))
            return items[front];
        else
            throw new QueueException("Queue is empty. Nothing to peek.");
    }

    /**
     * Returns the String representation of this queue.
     * The returned String will contain the String representation of all of 
     * this queue's items.
     *
     * @return this queue's String representation.
     */
    @Override
    public String toString() {
        String queueString = "";
        for (int i = 0; i < numItems; i++)
            queueString += items[(front + i) % items.length].toString() + ' ';
        return queueString;
    }

    /**
     * Resizes this queue's array to make room for additional items.
     * The current array's size is increased by a factor of 1.5.
     */
    private void resize() {
        T[] resizedItems = (T[]) new Object[(items.length * 3)/2 + 1];
        int index = 0;
        for (; index < numItems; index++)
            resizedItems[index] = items[(front + index) % items.length];
        items = resizedItems;
        front = 0;
        back = index;
    }
}
