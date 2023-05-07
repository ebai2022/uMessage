package p2.sorts;

import cse332.exceptions.NotYetImplementedException;
import cse332.sorts.InsertionSort;

import java.util.Comparator;

public class QuickSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        QuickSort.sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        partition(array, 0, array.length - 1, comparator);
    }

    private static <E> void partition(E[] array, int lo, int hi, Comparator<E> comparator) {
        if (lo < hi) {
            int pivotIndex = pickPivot(array, lo, hi, comparator);
            E pivotValue = array[pivotIndex];
            swap(array, pivotIndex, hi);
            int newPivot = lo;
            for (int i = lo; i < hi; i++) {
                if (comparator.compare(array[i], pivotValue) < 0) {
                    swap(array, i, newPivot);
                    newPivot++;
                }
            }
            swap(array, newPivot, hi);
            partition(array, lo, newPivot - 1, comparator);
            partition(array, newPivot + 1, hi, comparator);
        }
    }

    private static <E> int pickPivot(E[] array, int lo, int hi, Comparator<E> comparator) {
        int mid = (lo + hi) / 2;
        if (comparator.compare(array[lo], array[mid]) > 0) {
            swap(array, lo, mid);
        }
        if (comparator.compare(array[lo], array[hi]) > 0) {
            swap(array, lo, hi);
        }
        if (comparator.compare(array[mid], array[hi]) > 0) {
            swap(array, mid, hi);
        }
        return mid;
    }

    private static <E> void swap(E[] array, int i, int j) {
        E temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
