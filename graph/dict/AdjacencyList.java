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
        HashTableChained table = (HashTableChained) ((HashEntry) find(key)).value();
        if (table.remove(target) != null) {
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
            HashTableChained table = (HashTableChained) ((HashEntry) find(key)).value();
            if (table.find(target) == null) {
                table.insert(target, weight);
                edges++;
            }
            table.remove(target);
            table.insert(target, weight);
            edges++;
        }
    }
}
