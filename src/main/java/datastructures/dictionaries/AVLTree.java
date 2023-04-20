package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.datastructures.trees.BinarySearchTree;

import java.lang.reflect.Array;

/**
 * AVLTree must be a subclass of BinarySearchTree<E> and must use
 * inheritance and calls to superclass methods to avoid unnecessary
 * duplication or copying of functionality.
 * <p>
 * 1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override the insert method such that it creates AVLNode instances
 * instead of BSTNode instances.
 * 3. Do NOT "replace" the children array in BSTNode with a new
 * children array or left and right fields in AVLNode.  This will
 * instead mask the super-class fields (i.e., the resulting node
 * would actually have multiple copies of the node fields, with
 * code accessing one pair or the other depending on the type of
 * the references used to access the instance).  Such masking will
 * lead to highly perplexing and erroneous behavior. Instead,
 * continue using the existing BSTNode children array.
 * 4. Ensure that the class does not have redundant methods
 * 5. Cast a BSTNode to an AVLNode whenever necessary in your AVLTree.
 * This will result a lot of casts, so we recommend you make private methods
 * that encapsulate those casts.
 * 6. Do NOT override the toString method. It is used for grading.
 * 7. The internal structure of your AVLTree (from this.root to the leaves) must be correct
 */

public class AVLTree<K extends Comparable<? super K>, V> extends BinarySearchTree<K, V> {
    // TODO: Implement me!

    public class AVLNode extends BSTNode{
        public int height;
        public AVLNode(K key, V value, int height){
            super(key, value);
            this.height = height;
        }
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        AVLNode prev = null;
        AVLNode current = (AVLNode) this.root;

        int height = 0;
        int child = -1;

        while (current != null) {
            int direction = Integer.signum(key.compareTo(current.key));

            // We found the key!
            if (direction == 0) {
                break;
            }
            else {
                // direction + 1 = {0, 2} -> {0, 1}
                child = Integer.signum(direction + 1);
                prev = current;
                current = (AVLNode) current.children[child];
                // ALSO NEED TO UPDATE THE OTHER HEIGHTS AFTER SWAPPING
                height++;
            }
        }

        current = new AVLNode(key, value, height);
        if (this.root == null) {
            this.root = current;
        }
        else {
            assert(child >= 0); // child should have been set in the loop
            // above
            // swap here? check stuff
            prev.children[child] = current;
        }
        V oldValue = current.value;
        if (oldValue == null){
            this.size++;
        }
        current.value = value;
        return oldValue;
    }

    public void updateHeights(AVLNode root, int height){

    }

    // can we combine cases one and four?
    public AVLNode caseOne(AVLNode root){

        return root;
    }

    // can we just do two single rotations?
    public AVLNode caseTwo(AVLNode root){

        return root;
    }
}
