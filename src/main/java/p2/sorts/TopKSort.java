package p2.sorts;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.WorkList;
import datastructures.worklists.MinFourHeap;

import java.util.Comparator;

public class TopKSort {
    public static <E extends Comparable<E>> void sort(E[] array, int k) {
        sort(array, k, (x, y) -> x.compareTo(y));
    }

    /**
     * Behaviour is undefined when k > array.length
     */
    public static <E> void sort(E[] array, int k, Comparator<E> comparator) {
        WorkList<E> heap = new MinFourHeap<>(comparator);
        for (int i = 0; i < array.length; i++){
            heap.add(array[i]);
        }
        int numToTakeOut = heap.size() - k;
        for (int i = 0; i < numToTakeOut; i++){
            heap.next();
            array[array.length - i - 1] = null;
        }
        for (int i = 0; i < k; i++){
            array[i] = heap.next();
        }
    }
}
