/* Kruskal.java */

import list.LinkedQueue;
import list.QueueEmptyException;
import graph.*;
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
		Object[] v = g.getVertices();
		LinkedQueue edges = new LinkedQueue();
		DisjointSets trees = new DisjointSets(v.length);
		HashTableChained table = new HashTableChained();

		for (int i = 0; i < v.length; i++) {            //TAKES V time
			table.insert(v[i], i);
			Neighbors n = g.getNeighbors(v[i]);
			for (int ii = 0; ii < n.neighborList.length; ii++) {
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
		ListSorts.mergeSort(edges);   //TAKES E LOG E time. 

		int edgecounter = 0;

		while (edgecounter < v.length - 1) {         //TAKES CLOSE TO V time?
			try {
				Edge e = (Edge) edges.dequeue();
				if (trees.find(table.find(e.v1)) != trees
						.find(table.find(e.v2))) {
					g.addEdge(e.v1, e.v2, e.weight);
					edgecounter++;
				}
			} catch (QueueEmptyException e) {
				e.printStackTrace();
			}
		}

		return t;

	}

}
