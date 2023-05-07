package p2.sorts;

import cse332.exceptions.NotYetImplementedException;
import cse332.sorts.InsertionSort;

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
        partition(array, 0, array.length - 1, comparator);

    }

    /*
    public static <E> void quicksort(E[] arr, int lo, int hi, Comparator<E> c){
        if (lo < hi){
            int pivot = pickPivot(arr, lo, hi, c);
            //partition(arr, lo, pivot, hi, c);
            quicksort(arr, lo, pivot - 1, c);
            quicksort(arr, pivot + 1, hi, c);
        }
    }
     */

    public static <E> int pickPivot (E[] arr, int lo, int hi, Comparator<E> c){
        int mid = (hi + lo) / 2;
        if (mid == lo){
            if (c.compare(arr[lo], arr[hi]) < 0) {
                return hi;
            } else{
                return lo;
            }
        }
        if ((c.compare(arr[lo], arr[mid]) < 0 && c.compare(arr[lo], arr[hi]) > 0) ||
                (c.compare(arr[lo], arr[mid]) >= 0 && c.compare(arr[lo], arr[hi]) <= 0)){
            return lo;
        } else if ((c.compare(arr[mid], arr[lo]) < 0 && c.compare(arr[mid], arr[hi]) > 0) ||
                (c.compare(arr[mid], arr[lo]) > 0 && c.compare(arr[mid], arr[hi]) < 0)){
            return mid;
        }
        return hi;
    }

    public static <E> void partition(E[] arr, int lo, int hi, Comparator<E> comparator){
        if (lo >= hi){
            return;
        }
        int pivot = pickPivot(arr, lo, hi, comparator);
        E pivotVal = arr[pivot];
        arr[pivot] = arr[lo];
        arr[lo] = pivotVal;
        int i = lo + 1;
        int j = hi;
        while (i < j){
            if (comparator.compare(pivotVal, arr[j]) < 0){
                j--;
            } else if (comparator.compare(arr[i], pivotVal) <= 0){
                i++;
            } else{
                E temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        arr[lo] = arr[i];
        arr[i] = pivotVal;
        partition(arr, lo, i - 1, comparator);
        partition(arr, j + 1, hi, comparator);
    }
}
