import java.util.List;
import java.util.NoSuchElementException;

/**
 * This class is implemented by a red black tree that stores a sorted list of multiple values with
 * the same rating. These lists of values are sorted according to the compareTo() defined within the
 * ValueType.
 *
 * @author Haiyi Wang & Daidan Lu
 */
public interface IRedBlackTreeSortedSets<V extends Comparable<V>, T>
    extends SortedCollectionInterface<V, T> {

  /**
   * Search books containing this key
   *
   * @param key the key value of each book object
   * @return true if search some books. Otherwise,
   * @throws NoSuchElementException
   */
  public List search(V key) throws NoSuchElementException;

}
