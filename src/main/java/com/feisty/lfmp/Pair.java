package com.feisty.lfmp;

// making a Pair class because I can't find it in the standard library and I'm too lazy to look elsewhere
public class Pair<K, V> {
    public K key;
    public V val;
    public Pair(K key, V val) {
        this.key = key;
        this.val = val;
    }
}
