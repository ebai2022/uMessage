package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FIFOWorkList.java
 * for method specifications.
 */
public class ListFIFOQueue<E> extends FIFOWorkList<E> {
    private ListNode head; // start of the queue
    private int size; // size of the queue
    private ListNode end; // end of the queue

    public ListFIFOQueue() {
        size = 0;
    }

    @Override
    public void add(E work) {
        if (head == null){
            head = new ListNode(work);
            end = head;
        } else{
            end.next = new ListNode(work);
            end = end.next;
        }
        size++;
    }

    @Override
    public E peek() {
        if (head == null){
            throw new NoSuchElementException();
        }
        return head.data;
    }

    @Override
    public E next() {
        if (head == null){
            throw new NoSuchElementException();
        }
        E data = head.data;
        head = head.next;
        size--;
        return data;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Keeps track of the data of the listnode and the next listnode it points to
     */
    private class ListNode {
        private E data;
        private ListNode next;
        public ListNode(E data){
            this.data = data;
        }
    }
}