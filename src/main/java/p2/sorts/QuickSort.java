package p2.sorts;

import cse332.exceptions.NotYetImplementedException;

import java.util.Comparator;

public class QuickSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        QuickSort.sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        // pick pivot out of 3 (arr[lo] arr[hi-lo] arr[hi-lo / 2])
        // partition data into elements less than the pivot (A), the pivot (B),
        // and elements greater than the pivot (C)
        // recursively sort A and C
    }

    /*
    public <E> void quicksort (E[] arr, int hi, int lo){
        if (hi - lo < 10){
            insertionSort(arr,lo,hi);
        } else{

        }
    }
    */
}
