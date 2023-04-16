package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.LIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 */
public class ArrayStack<E> extends LIFOWorkList<E> {
    private int capacity; // capacity of the array
    private E[] array; // array that represents the stack
    private int current; // the current index of the array to be pushed/popped from (tracks the back since FILO)

    public ArrayStack() {
        current = 0;
        capacity = 10;
        array = (E[])new Object[capacity];
    }

    @Override
    public void add(E work) {
        if (current == capacity){
            capacity = capacity * 2;
            E[] copy = (E[])new Object[capacity];
            for (int i = 0; i < current; i++){
                copy[i] = array[i];
            }
            array = copy;
        }
        array[current] = work;
        current++;
    }

    @Override
    public E peek() {
        if (current == 0) {
            throw new NoSuchElementException();
        }
        return array[current - 1];
    }

    @Override
    public E next() {
        if (current == 0){
            throw new NoSuchElementException();
        }
        E data = array[current - 1];
        current--;
        return data;
    }

    @Override
    public int size() {
        return current;
    }

    @Override
    public void clear() {
        current = 0;
        capacity = 10;
        array = (E[])new Object[capacity];
    }
}
