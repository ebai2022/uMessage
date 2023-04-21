package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.datastructures.trees.BinarySearchTree;

import java.lang.reflect.Array;
import java.util.NoSuchElementException;

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
    private V prevVal = null;
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
        root = insert(key, value, (AVLNode) root);
        // add value change!
        return prevVal;
    }

    private AVLNode insert(K key, V value, AVLNode root){
        // we have found the empty spot to insert the root
        if (root == null){
            root = new AVLNode(key, value, 0);
            this.size++;
            return root;
        }
        int direction = Integer.signum(key.compareTo(root.key));
        // we just need to replace to duplicate
        if (direction == 0) {
            prevVal = root.value;
            root.value = value;
            return root;
        } else{
            int child = Integer.signum(direction + 1);
            root.children[child] = insert(key, value, (AVLNode) root.children[child]);
        }
        root.height = maxHeight((AVLNode) root.children[0], (AVLNode) root.children[1]) + 1;
        int swapCase = findCase(root);
        if (swapCase == 1){
            root = rotateWithLeft(root);
        } else if (swapCase == 2){
            root.children[0] = rotateWithRight((AVLNode) root.children[0]);
            root = rotateWithLeft(root);
        } else if (swapCase == 3){
            root.children[1] = rotateWithLeft((AVLNode) root.children[1]);
            root = rotateWithRight(root);
        } else if (swapCase == 4){
            root = rotateWithRight(root);
        }
        return root;
    }

    private int findCase(AVLNode root){
        AVLNode leftSubtree = (AVLNode) root.children[0];
        AVLNode rightSubtree = (AVLNode) root.children[1];
        // we only have a right subtree
        if (leftSubtree == null){
            // we have an imbalance if one side null (-1) and the other size is greater than 0 (difference is >1)
            if (rightSubtree.height > 0){
                AVLNode leftSubOfChild = (AVLNode) rightSubtree.children[0];
                AVLNode rightSubOfChild = (AVLNode) rightSubtree.children[1];
                if (leftSubOfChild == null){
                    return 4;
                } else if (rightSubOfChild == null){
                    return 3;
                } else if (leftSubOfChild.height > rightSubOfChild.height){
                    return 3;
                } else if (leftSubOfChild.height < rightSubOfChild.height){
                    return 4;
                } else{
                    throw new NoSuchElementException("You screwed up somehow! No cases if these are equal");
                }
            }
        }
        // we only have a left subtree
        else if (rightSubtree == null){
            // we have an imbalance if one side null (-1) and the other size is greater than 0 (difference is >1)
            if (leftSubtree.height > 0){
                AVLNode leftSubOfChild = (AVLNode) leftSubtree.children[0];
                AVLNode rightSubOfChild = (AVLNode) leftSubtree.children[1];
                if (leftSubOfChild == null){
                    return 2;
                } else if (rightSubOfChild == null){
                    return 1;
                } else if (leftSubOfChild.height > rightSubOfChild.height){
                    return 1;
                } else if (leftSubOfChild.height < rightSubOfChild.height){
                    return 2;
                } else{
                    throw new NoSuchElementException("You screwed up somehow! No cases if these are equal");
                }
            }
        }
        // we have both subtrees
        else{
            // we know that the imbalance is in the left subtree
            if (leftSubtree.height - rightSubtree.height > 1){
                AVLNode leftSubOfChild = (AVLNode) leftSubtree.children[0];
                AVLNode rightSubOfChild = (AVLNode) leftSubtree.children[1];
                if (leftSubOfChild == null){
                    return 2;
                } else if (rightSubOfChild == null){
                    return 1;
                } else if (leftSubOfChild.height > rightSubOfChild.height){
                    return 1;
                } else if (leftSubOfChild.height < rightSubOfChild.height){
                    return 2;
                } else{
                    throw new NoSuchElementException("You screwed up somehow! No cases if these are equal");
                }
            }
            // we know the imbalance is in the right subtree
            else if (rightSubtree.height - leftSubtree.height > 1){
                AVLNode leftSubOfChild = (AVLNode) rightSubtree.children[0];
                AVLNode rightSubOfChild = (AVLNode) rightSubtree.children[1];
                if (leftSubOfChild == null){
                    return 4;
                } else if (rightSubOfChild == null){
                    return 3;
                } else if (leftSubOfChild.height > rightSubOfChild.height){
                    return 3;
                } else if (leftSubOfChild.height < rightSubOfChild.height){
                    return 4;
                } else{
                    throw new NoSuchElementException("You screwed up somehow! No cases if these are equal");
                }
            }
        }
        return 0;
    }

    private AVLNode rotateWithRight(AVLNode root){
        // right child
        AVLNode temp = (AVLNode) root.children[1];
        // taking the child's left subtree
        root.children[1] = temp.children[0];
        // taking the root as its left subtree
        temp.children[0] = root;

        // null cases
        root.height = maxHeight(((AVLNode) root.children[1]), ((AVLNode) root.children[0])) + 1;
        temp.height = maxHeight(((AVLNode) temp.children[1]), ((AVLNode) temp.children[0])) + 1;

        // reassigning root to the top
        root = temp;
        return root;
    }

    private AVLNode rotateWithLeft(AVLNode root){
        // left child
        AVLNode temp = (AVLNode) root.children[0];
        // taking the child's right subtree
        root.children[0] = temp.children[1];
        // taking the root as its right subtree
        temp.children[1] = root;

        // null cases
        root.height = maxHeight(((AVLNode) root.children[1]), ((AVLNode) root.children[0])) + 1;
        temp.height = maxHeight(((AVLNode) temp.children[1]), ((AVLNode) temp.children[0])) + 1;

        // reassigning root to the top
        root = temp;
        return root;
    }

    private int maxHeight(AVLNode leftNode, AVLNode rightNode){
        if (leftNode == null && rightNode == null){
            return -1;
        } else if (leftNode == null){
            return rightNode.height;
        } else if (rightNode == null){
            return leftNode.height;
        } else{
            return Math.max(leftNode.height, rightNode.height);
        }
    }
}
