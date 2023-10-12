/**
 * This interface is modified based on the provided interface.
 *
 * @param <V> the key of the node
 * @param <T> the data of the node
 */
public interface SortedCollectionInterface<V extends Comparable, T> extends Iterable<V> {
    // Note that the provided iterators step through the data within this
    // collection in sorted order, as defined by their compareTo() method.

    /**
     * The insert operation
     *
     * @param key  the key of the node
     * @param data the data of the node
     * @return true if the insert operation is successful, false otherwise.
     * @throws NullPointerException     if no instance
     * @throws IllegalArgumentException if the argument is illegal
     */
    public boolean insert(V key, T data) throws NullPointerException, IllegalArgumentException;

    /**
     * The lookup operation
     *
     * @param key the key of the node
     * @return true if the contains operation is successful, false otherwise.
     */
    public boolean contains(V key);

    /**
     * Return the size of the RBTree
     *
     * @return the size of the RBTree
     */
    public int size();

    /**
     * Check whether the RBTree is empty or not
     *
     * @return true if the RBTree is empty, false otherwise.
     */
    public boolean isEmpty();

}
