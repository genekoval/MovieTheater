/**
 * A Keyed Item is any item that can be identified by a Comparable data field.
 * 
 * @author Eugene Koval
 * @version 2017.04.21
 */
public abstract class KeyedItem<KT extends Comparable<? super KT>> {

    /* The search key used to identify this item. */
    private KT searchKey;

    /** 
     * Constructor for a KeyedItem.
     * Creates a KeyedItem with a comparable search key.
     *
     * @param key the comparable search key.
     */
    public KeyedItem(KT key) {
        searchKey = key;
    }

    /**
     * Returns this KeyedItem's search key.
     *
     * @return the the search key.
     */
    public KT getKey() {
        return searchKey;
    }
}
