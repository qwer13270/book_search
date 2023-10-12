import java.util.*;

/**
 * Red-Black Tree implementation with a Node inner class for representing the nodes of the tree.
 * Currently, this implements a Binary Search Tree that we will turn into a red black tree by
 * modifying the insert functionality. In this activity, we will start with implementing rotations
 * for the binary search tree insert algorithm. You can use this class' insert method to build a
 * regular binary search tree, and its toString method to display a level-order traversal of the
 * tree.
 *
 * @author instructors, Yan Liu , modified by Daidan Lu
 **/
public class RedBlackTree<V extends Comparable<V>, T> implements SortedCollectionInterface<V, T> {

  /**
   * This class represents a node holding a single value within a binary tree the parent, left, and
   * right child references are always maintained.
   */
  protected static class Node<V, T> {
    public V key;
    public List data; // each node has a List ValueType
    public Node<V, T> parent; // null for root node
    public Node<V, T> leftChild;
    public Node<V, T> rightChild;
    public int blackHeight;
    // This black height is meant to track the black height only for the current node:
    // 0 = red, 1 = black, and 2 = double-black.

    public Node(V key, T data) {
      this.key = key;
      this.data = (List) data; // convert into List type
      this.blackHeight = 0; // all new nodes are red by default
    }


    /**
     * Check if is left child
     *
     * @return true when this node has a parent and is the left child of that parent, otherwise
     *         return false
     */
    public boolean isLeftChild() {
      return parent != null && parent.leftChild == this;
    }

  }

  // data fields
  protected Node<V, T> root; // reference to root node of tree, null when empty
  protected int size = 0; // the number of values in the tree


  /**
   * Performs a naive insertion into a binary search tree: adding the input data value to a new node
   * in a leaf position within the tree. After this insertion, no attempt is made to restructure or
   * balance the tree. This tree will not hold null references, nor duplicate data values.
   *
   * @param data to be added into this binary search tree
   * @return true if the value was inserted, false if not
   * @throws NullPointerException     when the provided data argument is null
   * @throws IllegalArgumentException when the newNode and subtree contain equal data references
   */
  @Override
  public boolean insert(V key, T data) throws NullPointerException, IllegalArgumentException {
    // null references cannot be stored within this tree
    if (key == null)
      throw new NullPointerException("This RedBlackTree cannot store null references.");

    Node<V, T> newNode = new Node<V, T>(key, data);
    if (root == null) {
      root = newNode;
      size++;
      root.blackHeight = 1; // always set the root node of your red black tree to be black after
                            // each insert
      return true;
    } // add first node to an empty tree
    else {
      boolean returnValue = insertHelper(newNode, root); // recursively insert into subtree
      if (returnValue)
        size++;
      else
        throw new IllegalArgumentException("This RedBlackTree already contains that value.");
      root.blackHeight = 1; // always set the root node of your red black tree to be black after
                            // each insert
      return returnValue;
    }
  }

  /**
   * This helper method is to append into the list
   *
   * @param newNode is the new node that is being added to this tree
   * @param oldNode is the reference to a node within this tree which the newNode should be inserted
   *                as a descendant beneath
   */
  @SuppressWarnings("unchecked")
  private void append(Node<V, T> newNode, Node<V, T> oldNode) {
    List newNodeList = newNode.data;
    List oldNodeList = oldNode.data;
    oldNodeList.removeAll(newNodeList); // remove all duplicated elements
    oldNodeList.addAll(newNodeList); // create a list with non-repeating elements
  }

  /**
   * Recursive helper method to find the subtree with a null reference in the position that the
   * newNode should be inserted, and then extend this tree by the newNode in that position.
   *
   * @param newNode is the new node that is being added to this tree
   * @param subtree is the reference to a node within this tree which the newNode should be inserted
   *                as a descendant beneath
   * @return true is the value was inserted in subtree, false if not
   */
  private boolean insertHelper(Node<V, T> newNode, Node<V, T> subtree) {
    int compare = newNode.key.compareTo(subtree.key);
    // do not allow duplicate keys and values to be stored within this tree
    if (compare == 0 && newNode.data.equals(subtree.data))
      return false;
    // if the element with the same key but different value, append into the list
    if (compare == 0) {
      append(newNode, subtree);
      return true;
    }

    // store newNode within left subtree of subtree
    else if (compare < 0) {
      if (subtree.leftChild == null) { // left subtree empty, add here
        subtree.leftChild = newNode;
        newNode.parent = subtree;
        enforceRBTreePropertiesAfterInsert(newNode);
        return true;
        // otherwise continue recursive search for location to insert
      } else
        return insertHelper(newNode, subtree.leftChild);
    }

    // store newNode within the right subtree of subtree
    else {
      if (subtree.rightChild == null) { // right subtree empty, add here
        subtree.rightChild = newNode;
        newNode.parent = subtree;
        enforceRBTreePropertiesAfterInsert(newNode);
        return true;
        // otherwise continue recursive search for location to insert
      } else
        return insertHelper(newNode, subtree.rightChild);
    }
  }

  /**
   * main purpose of this method is to resolve any red node with red parent property violations and
   * following RBT properties: The root node should always be black Every null child of a node is
   * black in red black tree The children of a red node are black All the leaves have the same black
   * depth Every simple path from the root node to the (downward) leaf node contains the same number
   * of black nodes(height)
   *
   * @param newNode the new node being inserted into the tree
   */
  protected void enforceRBTreePropertiesAfterInsert(Node<V, T> newNode) {
    Node<V, T> parent = newNode.parent;
    if (parent == null) {
      // root already be black after every insert
      // reach root end of recursion
      return;
    }

    // do nothing when parent is black
    if (parent.blackHeight == 1) {
      return;
    }

    Node<V, T> grandChild = newNode;
    Node<V, T> grandPa = parent.parent; // parent is red after the previous condition

    // parent is root, return then the end of insertHelper will update root to black
    if (grandPa == null) {
      return;
    }

    // the uncle node can be a black leaf node of the tree or black null node
    // parent and grandpa already not null and just check the one possibility of uncle
    // whether it's null or not, then check whether Grandpa has this parent as a child
    // eliminated the possibility of a parental relation validity
    // and return an uncle node
    Node<V, T> uncle = getUncleHelper(parent);

    // case 3: parent's sibling is red on conflict place
    if (uncle != null && uncle.blackHeight == 0 && parent.blackHeight == 0) {
      // step1: set parent and uncle to black
      parent.blackHeight = 1;
      uncle.blackHeight = 1;
      // step2: let grandparent be red
      grandPa.blackHeight = 0;
      // step3: if upper level red violations, solve the property again
      if (grandPa.parent != null && grandPa.parent.blackHeight == 0) {
        enforceRBTreePropertiesAfterInsert(grandPa);
      }
      return;
    }
    // uncle can be black, parent should be red
    // case in which grandpa's left child is the parent
    else if (parent == grandPa.leftChild) {
      // case 1: child is same side as parent's sibling
      if (parent.rightChild == grandChild && !grandChild.isLeftChild()) {
        // step1: rotation
        rotate(grandChild, parent); // rotate position will be handled by parameters
        parent = newNode; // parent points to new root after rotation as a subtree
      }
      // parent is a leftChild but uncle is the rightChild of grandPa
      // case 2: child is the opposite side as parent's sibling
      // step1: rotation
      rotate(parent, grandPa); // rotate position will be handled by parameters
      // step2: newGrandPa to black, oldGrandPa to red
      parent.blackHeight = 1;// newGrandPa
      grandPa.blackHeight = 0;// oldGrandPa
      return;
    }

    // case in which grandpa's right child is the parent
    else {
      // and newNode -> grandChild is a left child, meaning same side as parent's sibling
      // case 1: child is the same side as parent's sibling
      if (grandChild == parent.leftChild) {
        // step1: rotation
        rotate(newNode, parent); // rotate position will be handled by parameters
        parent = newNode; // parent points to new root after rotation as a subtree
      }
      // case 2: child is the opposite side as parent's sibling
      // step1: rotation
      rotate(parent, grandPa); // rotate position will be handled by parameters
      // step2: newGrandPa to black, oldGrandPa to red
      parent.blackHeight = 1;// newGrandPa
      grandPa.blackHeight = 0;// oldGrandPa
      return;
    }
  }

  /**
   * uncle or the sibling node is always the opposite side of the parent's side with parent's
   * parent(grandparent)
   *
   * @param p the parent of the newNode(grandchild)
   * @return the uncle node which is the sibling of the parent node in the property enforce method
   * @throws IllegalArgumentException when grandpa node doesn't have this parent node as a child
   *                                  node
   */
  private Node<V, T> getUncleHelper(Node<V, T> p) {
    Node<V, T> grandpa = p.parent;
    if (grandpa.rightChild == p) {
      return grandpa.leftChild;
    } else if (grandpa.leftChild == p) {
      return grandpa.rightChild;
    } else {
      throw new IllegalArgumentException("grandpa doesn't have this parent node as a child...");
    }
  }

  /**
   * Performs the rotation operation on the provided nodes within this tree. When the provided child
   * is a leftChild of the provided parent, this method will perform a right rotation. When the
   * provided child is a rightChild of the provided parent, this method will perform a left
   * rotation. When the provided nodes are not related in one of these ways, this method will throw
   * an IllegalArgumentException.
   *
   * @param child  is the node being rotated from child to parent position (between these two node
   *               arguments)
   * @param parent is the node being rotated from parent to child position (between these two node
   *               arguments)
   * @throws IllegalArgumentException when the provided child and parent node references are not
   *                                  initially (pre-rotation) related that way
   */
  private void rotate(Node<V, T> child, Node<V, T> parent) throws IllegalArgumentException {

    if (child == null || parent == null) {
      throw new IllegalArgumentException("child or parent cannot be null if you want to rotate");
    }
    // the provided child is a rightChild of the provided parent
    else if (!child.isLeftChild() && child == parent.rightChild) {
      leftRotation(parent); // left rotation
    }
    // the provided child is a leftChild of the provided parent
    else if (child.isLeftChild() && child == parent.leftChild) { // right rotation
      rightRotation(parent);
    }
  }

  /**
   * This method rotates to the left like a steering wheel making tree smaller, balance the tree,
   * less height, and lower time complexity from linear to log n.
   *
   * @param node is the node being rotated from parent to child position (between these two node
   *             arguments)
   **/
  private void leftRotation(Node<V, T> node) {
    Node<V, T> p = node.parent; // root node
    Node<V, T> rightChild = node.rightChild; // right child node
    node.rightChild = rightChild.leftChild; // P points right to RL(rightChild's left child)

    if (rightChild.leftChild != null) {// R (right child node)'s left child node is not null,
                                       // meaning RL is not
      // null, then we can set RL's parent to root node P back
      // RL's parent is no longer R
      rightChild.leftChild.parent = node;
      // P and RL connection completed
    }
    // Case2: Otherwise, right child node's left child is null, meaning RL is
    // null, then we don't need to take care RL, just simply let R left child become root reference
    // P.
    // because R.leftChild will still be root node P, because P already pointed left to
    // RL(rightChild's left child)
    // and P -> R -> has no change
    // Even satisfied the Case1 if condition: RL's parent is P no longer R
    // node(the node being rotated from parent to child position) can still be assigned to R's left
    // child
    // because R.leftChild will still be root node P, and P already pointed right to RL
    // and P -> L -> : has no change
    rightChild.leftChild = node;
    node.parent = rightChild;
    reconstructionHelper(rightChild, node, p); // (R, P, root)
  }

  /**
   * This method rotates to the right like a steering wheel making tree smaller, balance the tree,
   * less height, and lower time complexity from linear to log n.
   *
   * @param node P is the node being rotated from parent to child position (between these two node
   *             arguments)
   **/
  private void rightRotation(Node<V, T> node) {
    Node<V, T> p = node.parent; // root node
    Node<V, T> leftChild = node.leftChild; // left child node
    node.leftChild = leftChild.rightChild;// P points left to LR(leftChild's right child)

    // Case1:
    if (leftChild.rightChild != null) { // L (left child node)'s right child node is not null,
                                        // meaning LR is not
      // null, then we can set LR's parent to root node P back
      // LR's parent is no longer L
      leftChild.rightChild.parent = node;
      // P and LR connection completed
    }

    // Case2: Otherwise, left child node's right child is null, meaning LR is
    // null, then we don't need to take care LR, just simply let L right child become root reference
    // P.
    // because L.rightChild will still be root node P, because P already pointed left to
    // LR(leftChild's right child)
    // and P -> R -> has no change

    // Even satisfied the Case1 if condition: LR's parent is P no longer L
    // node(the node being rotated from parent to child position) can still be assigned to L's right
    // child
    // because L.rightChild will still be root node P, and P already pointed left to LR(leftChild's
    // right child)
    // and P -> R -> has no change
    leftChild.rightChild = node;
    node.parent = leftChild; // and set P's parent back to L
    reconstructionHelper(leftChild, node, p); // (L, P, root)
  }

  /**
   * relatively abstract method representing the reconstruction of old parent to new parent along
   * the entire tree
   *
   * @param parentOld  the old parent was pointed by root,and it points to its subtrees
   * @param parentNew  the new parent was pointed by root,and it points to its subtrees
   * @param parentCopy the root node for the replica original tree
   */
  private void reconstructionHelper(Node<V, T> parentNew, Node<V, T> parentOld,
      Node<V, T> parentCopy) {
    if (parentCopy == null) {// simply let root points to the updated subtrees
      root = parentNew;
    } else if (parentCopy.leftChild == parentOld) {// simply let root points left to the updated
                                                   // subtrees
      parentCopy.leftChild = parentNew;
    } else if (parentCopy.rightChild == parentOld) {// simply let root points right to the updated
                                                    // new parent with
      // updated subtrees
      parentCopy.rightChild = parentNew;
    } else {
      throw new IllegalStateException("child node is not the parent node's child...");
    }
    if (parentNew != null) {
      parentNew.parent = parentCopy;
    }
  }

  /**
   * Get the size of the tree (its number of nodes).
   *
   * @return the number of nodes in the tree
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * Method to check if the tree is empty (does not contain any node).
   *
   * @return true of this.size() return 0, false if this.size() > 0
   */
  @Override
  public boolean isEmpty() {
    return this.size() == 0;
  }

  /**
   * Checks whether the tree contains the value *data*.
   *
   * @param key the key to test for
   * @return true if *data* is in the tree, false if it is not in the tree
   */
  @Override
  public boolean contains(V key) {
    // null references will not be stored within this tree
    if (key == null)
      throw new NullPointerException("This RedBlackTree cannot store null references.");
    return this.containsHelper(key, root);
  }

  /**
   * Recursive helper method that recurses through the tree and looks for the value *key*.
   *
   * @param key     the key to look for
   * @param subtree the subtree to search through
   * @return true of the value is in the subtree, false if not
   */
  private boolean containsHelper(V key, Node<V, T> subtree) {
    if (subtree == null) {
      // we are at a null child, value is not in tree
      return false;
    } else {
      int compare = key.compareTo(subtree.key);
      if (compare < 0) {
        // go left in the tree
        return containsHelper(key, subtree.leftChild);
      } else if (compare > 0) {
        // go right in the tree
        return containsHelper(key, subtree.rightChild);
      } else {
        // we found it :)
        return true;
      }
    }
  }

  /**
   * Returns an iterator over the values in in-order (sorted) order.
   *
   * @return iterator object that traverses the tree in in-order sequence
   */
  @Override
  public Iterator<V> iterator() {
    // use an anonymous class here that implements the Iterator interface
    // we create a new on-off object of this class everytime the iterator
    // method is called
    return new Iterator<V>() {
      // a stack and current reference store the progress of the traversal
      // so that we can return one value at a time with the Iterator
      Stack<Node<V, T>> stack = null;
      Node<V, T> current = root;

      /**
       * The next method is called for each value in the traversal sequence. It returns one value at
       * a time.
       * 
       * @return next value in the sequence of the traversal
       * @throws NoSuchElementException if there is no more elements in the sequence
       */
      public V next() {
        // if stack == null, we need to initialize the stack and current element
        if (stack == null) {
          stack = new Stack<Node<V, T>>();
          current = root;
        }
        // go left as far as possible in the sub tree we are in un8til we hit a null
        // leaf (current is null), pushing all the nodes we fund on our way onto the
        // stack to process later
        while (current != null) {
          stack.push(current);
          current = current.leftChild;
        }
        // as long as the stack is not empty, we haven't finished the traversal yet;
        // take the next element from the stack and return it, then start to step down
        // its right subtree (set its right sub tree to current)
        if (!stack.isEmpty()) {
          Node<V, T> processedNode = stack.pop();
          current = processedNode.rightChild;
          return processedNode.key;
        } else {
          // if the stack is empty, we are done with our traversal
          throw new NoSuchElementException("There are no more elements in the tree");
        }

      }

      /**
       * Returns a boolean that indicates if the iterator has more elements (true), or if the
       * traversal has finished (false)
       * 
       * @return boolean indicating whether there are more elements / steps for the traversal
       */
      public boolean hasNext() {
        // return true if we either still have a current reference, or the stack
        // is not empty yet
        return !(current == null && (stack == null || stack.isEmpty()));
      }

    };
  }

  /**
   * This method performs an inorder traversal of the tree. The string representations of each data
   * value within this tree are assembled into a comma separated string within brackets (similar to
   * many implementations of java.util.Collection, like java.util.ArrayList, LinkedList, etc). Note
   * that this RedBlackTree class implementation of toString generates an inorder traversal. The
   * toString of the Node class class above produces a level order traversal of the nodes / values
   * of the tree.
   *
   * @return string containing the ordered values of this tree (in-order traversal)
   */
  public String toInOrderString() {
    // use the inorder Iterator that we get by calling the iterator method above
    // to generate a string of all values of the tree in (ordered) in-order
    // traversal sequence
    Iterator<V> treeNodeIterator = this.iterator();
    StringBuffer sb = new StringBuffer();
    sb.append("[ ");
    if (treeNodeIterator.hasNext())
      sb.append(treeNodeIterator.next());
    while (treeNodeIterator.hasNext()) {
      V data = treeNodeIterator.next();
      sb.append(", ");
      sb.append(data.toString());
    }
    sb.append(" ]");
    return sb.toString();
  }

  /**
   * This method performs a level order traversal of the tree rooted at the current node. The string
   * representations of each data value within this tree are assembled into a comma separated string
   * within brackets (similar to many implementations of java.util.Collection). Note that the Node's
   * implementation of toString generates a level order traversal. The toString of the RedBlackTree
   * class below produces an inorder traversal of the nodes / values of the tree. This method will
   * be helpful as a helper for the debugging and testing of your rotation implementation.
   *
   * @return string containing the values of this tree in level order
   */
  public String toLevelOrderString() {
    String output = "[ ";
    LinkedList<Node<V, T>> q = new LinkedList<>();
    q.add(this.root);
    while (!q.isEmpty()) {
      Node<V, T> next = q.removeFirst();
      if (next.leftChild != null)
        q.add(next.leftChild);
      if (next.rightChild != null)
        q.add(next.rightChild);
      output += next.data.toString();
      if (!q.isEmpty())
        output += ", ";
    }
    return output + " ]";
  }

  /**
   * This method returns string representations of the level order traversal and in-order traversal
   *
   * @return string representations of the level order traversal and in-order traversal
   */
  @Override
  public String toString() {
    return "level order: " + this.toLevelOrderString() + "/nin order: " + this.toInOrderString();
  }
}

