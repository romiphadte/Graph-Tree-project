package graph.dict;
import graph.list.*;

public class AdjacencyList extends HashTableChained {

    public int edges;

    /**
     * Creates a new adjacency list with a prime number of buckets
     *
     * @param sizeEstimate estimated size (because true size will be nearest prime)
     */
    public AdjacencyList(int sizeEstimate) {
        super(sizeEstimate);
        edges = 0;
    }

    /**
     * Constructs a new adjacency list with a default size
     */
    public AdjacencyList() {
        super();
        edges = 0;
    }

    /**
     * Returns the number of edges stored in this adjacency list
     */
    public int edges() {
        return edges;
    }

    /**
     * if target and key both exist (not null)
     * removes target from key's hash table of adjacent vertices and vice versa
     * only decrements edges once even if two remove operations are perfomed
     *
     * @param key the original vertex object
     * @param target the connected vertex object
     */
    public void removeAdj(Object key, Object target) {
        boolean removed = false;
        if (find(key) != null) {
            HashTableChained table1 = (HashTableChained) ((HashEntry) find(key)).value();
            if (table1.remove(target) != null) {
                removed = true;
            }
        }
        if (find(target) != null) {
            HashTableChained table2 = (HashTableChained) ((HashEntry) find(target)).value();
            if (table2.remove(key) != null) {
                removed = true;
            }
        }
        if (removed == true) {
            edges--;
        }
    }
    /**
     * if target and key both exist (not null)
     * inserts an edge that consists of target and weight into the corresponding hash tables
     * in the hashentry of key and vice versa
     * if there is already an entry that exists, it is removed and a new entry inserted
     *
     * @param key the original vertex object
     * @param target the connected vertex object
     */
    public void insertAdj(Object key, Object target, int weight) {
        if (find(key) != null && find(target) != null) {
            HashTableChained table1 = (HashTableChained) ((HashEntry) find(key)).value();
            if (table1.find(target) == null) {
                table1.insert(target, weight);
                edges++;
                HashTableChained table2 = (HashTableChained) ((HashEntry) find(target)).value();
                if (!key.equals(target)) {
                    if (table2.find(key) == null) {
                        table2.insert(key, weight);
                    } else {
                        table2.remove(target);
                        table2.insert(target, weight);
                    }
                }
            } else {
                table1.remove(target);
                table1.insert(target, weight);
                HashTableChained table2 = (HashTableChained) ((HashEntry) find(target)).value();
                if (!key.equals(target)) {
                    if (table2.find(key) == null) {
                        table2.insert(key, weight);
                    } else {
                        table2.remove(key);
                        table2.insert(key, weight);
                    }
                }
            }
        }
    }
}
