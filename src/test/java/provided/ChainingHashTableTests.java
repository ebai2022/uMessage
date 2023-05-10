package provided;

import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.Dictionary;
import cse332.types.AlphabeticString;
import datastructures.dictionaries.AVLTree;
import datastructures.dictionaries.ChainingHashTable;
import datastructures.dictionaries.MoveToFrontList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ChainingHashTableTests {

	private void incrementValueWithKey(Dictionary<String, Integer> list, String key) {
		Integer find = list.find(key);
		if (find == null)
			list.insert(key, 1);
		else
			list.insert(key, find + 1);
	}

	@Test()
    @Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
	public void test_insertFind_manyElements_correctStructure() {
		ChainingHashTable<String, Integer> list = new ChainingHashTable<>(MoveToFrontList::new);

		int n = 1000;

		// Add them
		for (int i = 0; i < 5 * n; i++) {
			int k = (i % n) * 37 % n;
			String str = String.format("%05d", k);
			for (int j = 0; j < k + 1; j ++)
				incrementValueWithKey(list, str);
		}

		// Delete them all
		int totalCount = 0;
		for (Item<String, Integer> dc : list) {
			assertEquals((Integer.parseInt(dc.key) + 1) * 5, (int) dc.value);
			totalCount += dc.value;
		}
		assertEquals(totalCount, (n * (n + 1)) / 2 * 5);
		assertEquals(list.size(), n);
		assertNotNull(list.find("00851"));
		assertEquals(4260, (int) list.find("00851"));
	}

	@Test()
	@Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
	public void fuzztest(){
		Random rand = new Random();
		ChainingHashTable<Integer, String> map = new ChainingHashTable<>(MoveToFrontList::new);
		HashMap<Integer, String> map2 = new HashMap<Integer, String>();
		for (int i = 0; i < 1000; i++) {
			int key = rand.nextInt();
			String value = getRandomString(rand.nextInt(100));

			switch (rand.nextInt(5)) {
				case 0:
					map.insert(key, value);
					map2.put(key, value);
					break;
				case 1:
					String s1 = map.find(key);
					String s2 = map2.get(key);
					assertEquals(s1, s2);
					break;
			}
			assertEquals(map.size(), map2.size());
		}
	}

	@Test()
	@Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
	public void fuzztest2(){
		Random rand = new Random();
		ChainingHashTable<Integer, String> map = new ChainingHashTable<>(AVLTree<Integer,String>::new);
		HashMap<Integer, String> map2 = new HashMap<Integer, String>();
		for (int i = 0; i < 1000; i++) {
			int key = rand.nextInt();
			String value = getRandomString(rand.nextInt(100));

			switch (rand.nextInt(5)) {
				case 0:
					map.insert(key, value);
					map2.put(key, value);
					break;
				case 1:
					String s1 = map.find(key);
					String s2 = map2.get(key);
					assertEquals(s1, s2);
					break;
			}
			assertEquals(map.size(), map2.size());
		}
	}

	private static String getRandomString(int length) {
		Random rand = new Random();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			builder.append((char) (rand.nextInt(26) + 'a'));
		}
		return builder.toString();
	}
}
