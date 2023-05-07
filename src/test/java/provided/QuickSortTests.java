package provided;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import p2.sorts.QuickSort;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuickSortTests {

	@Test()
    @Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
	public void test_sort_integerSorted_correctSort() {
		Integer[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		Integer[] arr_sorted = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		QuickSort.sort(arr, Integer::compareTo);
		for(int i = 0; i < arr.length; i++) {
			assertEquals(arr[i], arr_sorted[i]);
		}
	}

	@Test()
    @Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
	public void test_sort_integerRandom_correctSort() {
		Integer[] arr = {3, 1, 4, 5, 9, 2, 6, 7, 8};
		Integer[] arr_sorted = {1, 2, 3, 4, 5, 6, 7, 8, 9};
		QuickSort.sort(arr, Integer::compareTo);
		for(int i = 0; i < arr.length; i++) {
			assertEquals(arr[i], arr_sorted[i]);
		}
	}

	@Test()
	@Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
	public void fuzztest(){
		for (int i = 0; i < 100; i++) {
			// Generate a random array of integers
			Integer[] arr = new Integer[new Random().nextInt(500)];
			Random rand = new Random();
			for (int j = 0; j < arr.length; j++) {
				arr[j] = rand.nextInt(500); // generate integers between -1000 and 1000
			}
			// Make a copy of the array
			Integer[] arrCopy = arr.clone();
			// Sort the array using quicksort
			QuickSort.sort(arr, Integer::compareTo);
			// Sort the copy using Java's built-in Arrays.sort() function
			Arrays.sort(arrCopy);
			// Check that the two arrays are the same
			int index = 0;
			for (Integer integer : arr){
				System.out.println(integer + " " + arrCopy[index]);
				assertEquals(integer, arrCopy[index], index);
				index++;
			}
		}
	}
}