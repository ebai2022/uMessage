package datastructures.dictionaries;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.trie.TrieMap;
import cse332.types.BString;

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
    public class HashTrieNode extends TrieNode<Map<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new HashMap<A, HashTrieNode>();
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
            return pointers.entrySet().iterator();
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
        int depth = 1;
        V prev = node.value;
        if (key.size() == 0){
            if (node.value == null){
                this.size++;
            }
            node.value = value;
            return prev;
        }
        for (A part : key){
            HashTrieNode children = node.pointers.get(part);
            if (children == null && depth != key.size()){
                node.pointers.put(part, new HashTrieNode());
            } else if (depth == key.size()){
                if (children != null){
                    prev = children.value;
                    if (prev == null){
                        this.size++;
                    }
                    children.value = value;
                    return prev;
                } else{
                    node.pointers.put(part, new HashTrieNode(value));
                    this.size++;
                }
            }
            depth++;
            node = node.pointers.get(part);
        }
        return null;
    }

    @Override
    public V find(K key) {
        if (key == null){
            throw new IllegalArgumentException();
        }
        HashTrieNode node = (HashTrieNode)this.root;
        int depth = 1;
        if (key.size() == 0){
            return node.value;
        }
        for (A part : key){
            if (node.pointers.get(part) != null){
                if (depth == key.size()){
                    return node.pointers.get(part).value;
                }
                node = node.pointers.get(part);
            }
            depth++;
        }
        return null;
    }

    @Override
    public boolean findPrefix(K key) {
        if (key == null){
            throw new IllegalArgumentException();
        }
        HashTrieNode node = (HashTrieNode) this.root;
        if (this.size() == 0){
            return false;
        }
        for (A part : key){
            HashTrieNode children = node.pointers.get(part);
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
            HashTrieNode children = node.pointers.get(part);
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
            deleteUnder.pointers.remove(deleteAlphabet);
        } else if (deleteUnder == null && node.pointers.isEmpty()){
            deleteUnder = (HashTrieNode) this.root;
            deleteUnder.pointers.remove(deleteAlphabet);
        }
    }

    @Override
    public void clear() {
        root = new HashTrieNode();
        this.size = 0;
    }
}
