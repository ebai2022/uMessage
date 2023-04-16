package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E extends Comparable<E>> extends FixedSizeFIFOWorkList<E> {
    private E[] array;
    private int size;
    private int front;
    private int end;
    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        array = (E[])new Object[capacity];
        size = 0;
        front = 0;
        end = 0;
    }

    @Override
    public void add(E work) {
        if (size == capacity()){
            throw new IllegalStateException();
        }
        array[end] = work;
        end = (end + 1) % capacity();
        size++;
    }

    @Override
    public E peek() {
        if (size == 0){
            throw new NoSuchElementException();
        }
        return array[front];
    }

    @Override
    public E peek(int i) {
        if (size == 0){
            throw new NoSuchElementException();
        }
        if (i < 0 || i >= size()){
            throw new IndexOutOfBoundsException();
        }
        int adjustedIndex = (i + front) % capacity();
        return array[adjustedIndex];
    }

    @Override
    public E next() {
        if (size() == 0){
            throw new NoSuchElementException();
        }
        // set removed element to a default value? e.g. null
        E data = array[front];
        array[front] = null;
        front = (front + 1) % capacity();
        size--;
        return data;
    }

    @Override
    public void update(int i, E value) {
        // check the size here? BE CAREFUL - DO I NEED TO UPDATE SIZE? check a default value?
        if (size == 0){
            throw new NoSuchElementException();
        }
        if (i < 0 || i >= size()) {
            throw new IndexOutOfBoundsException();
        }
        int adjustedIndex = (i + front) % capacity();
        if (array[adjustedIndex] == null){
            size++;
        }
        array[adjustedIndex] = value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        array = (E[])new Object[capacity()];
        size = 0;
    }

    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        throw new NotYetImplementedException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        // You will finish implementing this method in project 2. Leave this method unchanged for project 1.
        if (this == obj) {
            return true;
        } else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        } else {
            // Uncomment the line below for p2 when you implement equals
            // FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;

            // Your code goes here

            throw new NotYetImplementedException();
        }
    }

    @Override
    public int hashCode() {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        throw new NotYetImplementedException();
    }
}
