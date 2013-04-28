package graph.dict;
import graph.list.*;

public class AdjacencyList extends HashTableChained {

    public int edges;

    public AdjacencyList(int sizeEstimate) {
        super(sizeEstimate);
        edges = 0;
    }

    public AdjacencyList() {
        super();
        edges = 0;
    }

    public int edges() {
        return edges;
    }

    /**
     * assuming that key and target are not null
     * removes target from the list of adjacent values attached to key
     * "key" (key) "value" (list of adjacent values). basically remove target from "value"
     *
     * @param key the original vertex object
     * @param target the connected vertex object
     */
    public void removeAdj(Object key, Object target) {
        /*HashTableChained table1 = (HashTableChained) ((HashEntry) find(key)).value();
        if (table1.remove(target) != null) {
            edges--;
            if (find(target) != null) {
                HashTableChained table2 = (HashTableChained) ((HashEntry) find(target)).value();
                table2.remove(key);
            }
        }*/
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
     * assuming that key and target are not null
     * inserts and edge that consists of target and weight into the corresponding dlist
     * to the hashentry of key
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
