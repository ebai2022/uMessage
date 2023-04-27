package datastructures.dictionaries;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.trie.TrieMap;
import cse332.types.BString;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<ChainingHashTable<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new ChainingHashTable<A, HashTrieNode>(MoveToFrontList::new);
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
            Iterator itr = pointers.iterator();
            Dictionary d = new ChainingHashTable<A, HashTrieNode>(MoveToFrontList::new);
            while (itr.hasNext()){
                AbstractMap.SimpleEntry i = (AbstractMap.SimpleEntry) itr.next();
                d.insert(i.getKey(), i.getValue());
            }
            return d.iterator();
        }
    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        HashTrieNode node = (HashTrieNode)this.root;
        for (A part : key){
            if (node.pointers.find(part) == null){
                node.pointers.insert(part, new HashTrieNode());
            }
            node = node.pointers.find(part);
        }
        V prev = node.value;
        node.value = value;
        if (prev == null){
            this.size++;
        }
        return prev;
    }

    @Override
    public V find(K key) {
        if (key == null){
            throw new IllegalArgumentException();
        }
        HashTrieNode node = (HashTrieNode)this.root;
        for (A part : key){
            if (node.pointers.find(part) == null){
                return null;
            }
            node = node.pointers.find(part);
        }
        return node.value;
    }

    @Override
    public boolean findPrefix(K key) {
        if (key == null){
            throw new IllegalArgumentException();
        }
        if (this.size == 0){
            return false;
        }
        HashTrieNode node = (HashTrieNode) this.root;
        for (A part : key){
            HashTrieNode children = node.pointers.find(part);
            if (children == null){
                return false;
            }
            node = children;
        }
        return true;
    }

    @Override
    public void delete(K key) {
        if (key == null){
            throw new IllegalArgumentException();
        }
        HashTrieNode node = (HashTrieNode) this.root;
        HashTrieNode deleteUnder = null;
        A deleteAlphabet = null;
        int i = 0;
        for (A part : key){
            if (i == 0){
                deleteAlphabet = part;
                i++;
            }
            HashTrieNode children = node.pointers.find(part);
            if (children == null){
                return;
            }
            if (node.pointers.size() > 1 || node.value != null){
                deleteUnder = node;
                deleteAlphabet = part;
            }
            node = children;
        }
        if (node.value != null){
            this.size--;
        }
        node.value = null;
        if (deleteUnder != null && node.pointers.isEmpty()){
            deleteUnder.pointers.delete(deleteAlphabet);
        } else if (deleteUnder == null && node.pointers.isEmpty()){
            deleteUnder = (HashTrieNode) this.root;
            deleteUnder.pointers.delete(deleteAlphabet);
        }
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }
}
