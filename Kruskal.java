/* Kruskal.java */

import list.*;
import graph.*;
import hashForKruskal.*;
import set.*;

/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */

public class Kruskal {

	/**
	 * minSpanTree() returns a WUGraph that represents the minimum spanning tree
	 * of the WUGraph g. The original WUGraph g is NOT changed.
	 */
	public static WUGraph minSpanTree(WUGraph g) {

		WUGraph t = new WUGraph();
		Object[] v = g.getVertices();       //takes V time
		LinkedQueue edges = new LinkedQueue();
		DisjointSets trees = new DisjointSets(v.length);
		HashTableChained table = new HashTableChained();

		for (int i = 0; i < v.length; i++) { //TAKES E time worst case.
			table.insert(v[i], i);          
			Neighbors n = g.getNeighbors(v[i]);   //O(d) where d is degree of vertex. 
			for (int ii = 0; ii < n.neighborList.length; ii++) {//reason outer loop takes E time. 
				if (!t.isVertex(n.neighborList[ii])) {  
					Edge e = new Edge();
					e.v1 = v[i];
					e.v2 = n.neighborList[ii];
					e.weight = n.weightList[ii];
					edges.enqueue(e);                
				}
			}
			t.addVertex(v[i]);
		}
		ListSorts.mergeSort(edges); //TAKES E LOG E time. 

		int edgecounter = 0;
		while (edgecounter < v.length - 1) { //Takes max E time. 
			try {
				Edge e = (Edge) edges.dequeue();
				int root1=trees.find((int)((Integer) table.find(e.v1).value())); 
				int root2=trees.find((int)((Integer) table.find(e.v2).value()));
				if (root1 != root2) {   
					t.addEdge(e.v1, e.v2, e.weight); 
					trees.union(root1, root2);    //merge if not previously connected.
					edgecounter++;
				}
			} catch (QueueEmptyException e) {
				e.printStackTrace();
			}
		}
		return t;
	}

}
