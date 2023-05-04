package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeap<E> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int size;
    private int capacity;
    private Comparator<E> compareType;

    public MinFourHeap(Comparator<E> c) {
        capacity = 22;
        data = (E[])new Object[capacity];
        size = 0;
        compareType = c;
    }

    @Override
    public boolean hasWork() {
        if (size < 1){
            return false;
        }
        return true;
    }

    @Override
    public void add(E work) {
        if (size == data.length-1){
            //amoritized O(1) resize
            capacity = capacity * 2;
            E[] copy = (E[])new Object[capacity];
            for (int i = 0; i < size+1; i++){
                copy[i] = data[i];
            }
            data = copy;
        }
        size++;
        // place at "size-1" (0 indexed) and then check where it should belong
        int i = percolateUp(size - 1,work);
        data[i] = work;
    }

    @Override
    public E peek() {
        if (!hasWork()){
            throw new NoSuchElementException();
        }
        return data[0];
    }

    @Override
    public E next() {
        if (!hasWork()){
            throw new NoSuchElementException();
        }
        E ans = data[0];
        //replace the empty root
        int hole = percolateDown(0, data[size - 1]);
        data[hole] = data[size - 1];
        size--;
        return ans;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        capacity = 22;
        data = (E[])new Object[capacity];
        size = 0;
    }

    private int percolateDown(int hole, E val) {
        // 0 indexed 4 heap
        int child = 4 * hole + 1;
        while (child < size) {
            int minChild = child;
            for (int i = 1; i < 4; i++) {
                int nextChild = child + i;
                if (nextChild < size && compareType.compare(data[nextChild], data[minChild]) < 0) {
                    minChild = nextChild;
                }
            }
            if (compareType.compare(data[minChild], val) < 0) {
                data[hole] = data[minChild];
                hole = minChild;
                child = 4 * hole + 1;
            } else {
                break;
            }
        }
        return hole;
    }

    private int percolateUp(int hole, E val){
        // 0 indexed 4 heap
        while (hole > 0 && compareType.compare(val, data[(hole-1)/4]) < 0){
            data[hole] = data[(hole-1)/4];
            hole = (hole-1)/4;
        }
        return hole;
    }

}
