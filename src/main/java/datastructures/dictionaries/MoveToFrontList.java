package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.datastructures.trees.BinarySearchTree;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.SimpleIterator;
import cse332.interfaces.worklists.FIFOWorkList;
import cse332.interfaces.worklists.WorkList;
import datastructures.worklists.ArrayStack;
import datastructures.worklists.ListFIFOQueue;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 1. The list is typically not sorted.
 * 2. Add new items to the front of the list.
 * 3. Whenever find or insert is called on an existing key, move it
 * to the front of the list. This means you remove the node from its
 * current position and make it the first node in the list.
 * 4. You need to implement an iterator. The iterator SHOULD NOT move
 * elements to the front.  The iterator should return elements in
 * the order they are stored in the list, starting with the first
 * element in the list. When implementing your iterator, you should
 * NOT copy every item to another dictionary/list and return that
 * dictionary/list's iterator.
 */
public class MoveToFrontList<K, V> extends DeletelessDictionary<K, V> {

    private ListNode head;

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null){
            throw new IllegalArgumentException();
        }
        if (head != null){
            if (head.key.equals(key)){
                V prevVal = (V) head.value;
                head.value = value;
                return prevVal;
            }
            ListNode curr = head;
            while (curr.next != null) {
                if (curr.next.key.equals(key)) {
                    ListNode newFrontNode = curr.next;
                    curr.next = curr.next.next;
                    ListNode oldHead = head;
                    head = newFrontNode;
                    head.next = oldHead;
                    V prevVal = (V) head.value;
                    head.value = value;
                    System.out.println("value swapped");
                    return prevVal;
                }
                curr = curr.next;
            }
        }
        ListNode oldHead = head;
        ListNode newFront = new ListNode(key, value);
        head = newFront;
        head.next = oldHead;
        size++;
        return null;
    }

    @Override
    public V find(K key) {
        if (key == null){
            throw new IllegalArgumentException();
        }
        ListNode curr = head;
        while (curr != null){
            if (key.equals(curr.key)){
                insert((K) curr.key, (V) curr.value);
                return (V) curr.value;
            }
            curr = curr.next;
        }
        return null;
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new FrontListIterator();
    }

    private class FrontListIterator extends SimpleIterator<Item<K, V>> {
        private MoveToFrontList.ListNode current;

        public FrontListIterator() {
            if (MoveToFrontList.this.head != null) {
                this.current = MoveToFrontList.this.head;
            }
        }

        @Override
        public boolean hasNext() {
            return this.current != null;
        }

        @Override
        public Item<K,V> next(){
            if (!hasNext()){
                throw new NoSuchElementException();
            }
            Item<K,V> k = new Item(current);
            current = current.next;
            return k;
        }
    }

    /**
     * Keeps track of the data of the listnode and the next listnode it points to
     */
    private class ListNode<K, V> extends Item<K,V>{
        private ListNode next;

        public ListNode(K key, V value) {
            super(key, value);
        }
    }
}
