package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.misc.SimpleIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

/**
 * 1. You must implement a generic chaining hashtable. You may not
 * restrict the size of the input domain (i.e., it must accept
 * any key) or the number of inputs (i.e., it must grow as necessary).
 * 3. Your HashTable should rehash as appropriate (use load factor as
 * shown in class!).
 * 5. HashTable should be able to resize its capacity to prime numbers for more
 * than 200,000 elements. After more than 200,000 elements, it should
 * continue to resize using some other mechanism.
 * 6. You should use the prime numbers in the given PRIME_SIZES list to resize
 * your HashTable with prime numbers.
 * 7. When implementing your iterator, you should NOT copy every item to another
 * dictionary/list and return that dictionary/list's iterator.
 */
public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V> {
    private Supplier<Dictionary<K, V>> newChain;
    private Dictionary<K, V>[] hashTable;
    private int tableSize;
    // edit this load factor
    private final double LOAD_FACTOR = 0.75;
    private int primeIndex;

    static final int[] PRIME_SIZES =
            {11, 23, 47, 97, 193, 389, 773, 1549, 3089, 6173, 12347, 24697, 49393, 98779, 197573, 395147};

    public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {
        this.newChain = newChain;
        this.size = 0;
        this.primeIndex = 0;
        this.tableSize = PRIME_SIZES[primeIndex];
        this.hashTable = new Dictionary[tableSize];
        for (int i = 0; i < hashTable.length; i++){
            hashTable[i] = newChain.get();
        }
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null){
            throw new IllegalArgumentException();
        }
        // finding the index of the array to check
        int index = Math.abs(key.hashCode() % tableSize);
        System.out.println(key.hashCode());
        // use whatever dictionaries insert method
        System.out.println("inserting at " + index);
        V oldVal = hashTable[index].insert(key, value);
        if (oldVal == null){
            this.size++;
            // rehash if needed LOAD_FACTOR = size / tableSize
            if (size > LOAD_FACTOR * tableSize){
                rehash();
            }
        }
        return oldVal;
    }

    @Override
    public V find(K key) {
        if (key == null){
            throw new IllegalArgumentException();
        }
        // finding the index of the array to check
        int index = Math.abs(key.hashCode() % tableSize);
        // finding the generic dictionary to check whether the key exists
        System.out.println(key.hashCode());
        System.out.println("finding at " + index);
        Dictionary<K, V> list = hashTable[index];
        // using an iterator to go through this dictionary
        Iterator<Item<K, V>> itr = list.iterator();
        V value = null;
        while (itr.hasNext()){
            Item<K, V> item = itr.next();
            // if I found the key, set the value and break. if not, continue checking
            if (item.key.equals(key)){
                value = item.value;
                break;
            }
        }
        // if not value was found, the value given is null
        return value;
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new ChainingHashTableIterator();
    }

    private class ChainingHashTableIterator implements Iterator<Item<K, V>> {
        private int index;
        private Iterator<Item<K, V>> elementIterator;

        public ChainingHashTableIterator() {
            // starting at index 0 in my array
            this.index = 0;
            // nothing to iterate yet
            this.elementIterator = null;
        }

        @Override
        public boolean hasNext() {
            // I still have elements at this index so keep going
            if (elementIterator != null && elementIterator.hasNext()) {
                return true;
            }
            // I'm either not at the end of the table yet (meaning I haven't gone through every element, or there
            // is either nothing at this index (this can't happen?) or there are no elements left at this index
            while (index < hashTable.length && (hashTable[index] == null || hashTable[index].isEmpty())) {
                // go to the next index
                index++;
            }
            if (index < hashTable.length) {
                // if I'm done with all elements at the array in the table, assign to the next index iterator
                if (elementIterator == null) {
                    elementIterator = hashTable[index].iterator();
                }
                // there are sitll elements at this index
                if (elementIterator.hasNext()) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public Item<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            // can this ever be null? double check
            Item<K, V> next = elementIterator.next();
            // if I have finished all the elements at this index, go to the next index and set the iterator to null
            if (!elementIterator.hasNext()) {
                index++;
                elementIterator = null;
            }
            return next;
        }
    }


    // for when my table gets too big for my PRIME_SIZES list to handle
    private int nextPrime(){
        int candidatePrime = tableSize + 1; // staring on the next number after the last prime
        while (!isPrime(candidatePrime)){
            candidatePrime++;
        }
        return candidatePrime;
    }

    // yeeks prime implementation
    private boolean isPrime(int candidatePrime){
        if (candidatePrime % 2 == 0 || candidatePrime % 3 == 0){
            return false;
        }
        for (int i = 5; i*i <= candidatePrime; i=i+6){
            if (candidatePrime % i == 0 || candidatePrime % (i+2) == 0){
                return false;
            }
        }
        return true;
    }

    private void rehash(){
        // checking if we are still in the range of the given prime numbers
        primeIndex++;
        // storing my old table
        Dictionary<K, V>[] oldTable = hashTable;
        if (primeIndex >= PRIME_SIZES.length){
            hashTable = new Dictionary[nextPrime()];
        } else {
            hashTable = new Dictionary[PRIME_SIZES[primeIndex]];
        }
        // new size of the hashTable
        this.tableSize = hashTable.length;
        // putting in all the generic dictionaries for the "linked list"
        for (int i = 0; i < hashTable.length; i++){
            hashTable[i] = newChain.get();
        }
        // resetting the size so that size tracking is consistent
        this.size = 0;
        // putting in all the old elements into the new table
        for (int i = 0; i < oldTable.length; i++){
            for (Item<K, V> item : oldTable[i]){
                insert(item.key, item.value);
            }
        }
    }

    /**
     * Temporary fix so that you can debug on IntelliJ properly despite a broken iterator
     * Remove to see proper String representation (inherited from Dictionary)
     */
    @Override
    public String toString() {
        return "ChainingHashTable String representation goes here.";
    }
}
