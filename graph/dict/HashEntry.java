/* HashEntry.java */

package graph.dict;

/**
 *  A class for Hash entries.
 **/

public class HashEntry extends Entry {
    public HashEntry() { }
    public HashEntry(Object key, Object value) {
        this.key = key;
        this.value = value;
    }
}
