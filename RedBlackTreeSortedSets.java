import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * This class implements the extension functions of the RBT:
 * (a) insert object with same key but different value in RBT, which is modified in the
 * RedBlackTree class
 * (b) search for a key, all values with same key is returned in a list
 * The returned list is sorted, and the search supports partial key word search.
 *
 * @param <V> the key
 * @param <T> the value
 * @author Daidan Lu
 */
public class RedBlackTreeSortedSets<V extends Comparable<V>, T> extends RedBlackTree<V, T>
    implements IRedBlackTreeSortedSets<V, T> {


  /**
   * This method is to search for a key, and all values with same key will be returned in a list.
   * This method can be used to partial key word search.
   * The returned result list is sorted.
   * This method is a extension function of the contains method, and modified based on the recursive
   * implementation.
   *
   * @param key the partial key to search
   * @return true if successfully search, false otherwise
   * @throws NoSuchElementException if no such key in the RBT
   */
  @Override
  public List search(V key) throws NoSuchElementException {
    if (key == null)
      throw new NullPointerException("This RedBlackTree cannot store null references.");
    // the data field is the List type, and sort the list in the returned result
    return sort(this.searchHelper(key, root).data);
  }

  /**
   * The recursive helper method of the search method
   *
   * @param key     the partial key to search (target)
   * @param current the current subtree of the RBT
   * @return the node of the RBT
   */
  private Node<V, T> searchHelper(V key, Node<V, T> current) {
    // Base case 1: an empty RBT or unsuccessful search
    if (current == null) {
      throw new NoSuchElementException("No such element in this RedBlackTree.");
    } else {

      // partial key word search function
      if (current.key.toString().contains(key.toString())) {
        key = current.key;
      }

      // comparator
      int cmp = key.toString().compareTo(current.key.toString());

      // Base case 2: successful search, find the target node
      if (cmp == 0) {
        return current;
      }

      // Recursive cases:
      if (cmp < 0) {
        // go left
        return searchHelper(key, current.leftChild);
      } else {
        // go right
        return searchHelper(key, current.rightChild);
      }
    }
  }

  /**
   * This method is to sort the list in an alphabetical order
   * 
   * @param list the list to be sort
   * @return the sorted list
   */
  @SuppressWarnings("unchecked")
  private List sort(List list) {
    // rewrite the comparator to define the way to compare
    list.sort(new Comparator() {
      @Override
      public int compare(Object o1, Object o2) {
        if (o1.toString().compareTo(o2.toString()) > 0)
          return 1;
        else if (o1.toString().compareTo(o2.toString()) < 0)
          return -1;
        else
          return 0;
      }
    });
    return list;
  }
}
