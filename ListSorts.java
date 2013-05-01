/* ListSorts.java */

import list.*;

public class ListSorts {


	/**
	 * makeQueueOfQueues() makes a queue of queues, each containing one item of
	 * q. Upon completion of this method, q is empty.
	 * 
	 * @param q
	 *            is a LinkedQueue of objects.
	 * @return a LinkedQueue containing LinkedQueue objects, each of which
	 *         contains one object from q.
	 **/
	public static LinkedQueue makeQueueOfQueues(LinkedQueue q) {
		LinkedQueue a = new LinkedQueue();
		while (q.size() != 0) {
			try {
				Object o = q.dequeue();
				LinkedQueue n = new LinkedQueue();
				n.enqueue(o);
				a.enqueue(n);
			} catch (QueueEmptyException e) {
				e.printStackTrace();
			}
		}
		return a;
	}

	/**
	 * mergeSortedQueues() merges two sorted queues into a third. On completion
	 * of this method, q1 and q2 are empty, and their items have been merged
	 * into the returned queue.
	 * 
	 * @param q1
	 *            is LinkedQueue of Comparable objects, sorted from smallest to
	 *            largest.
	 * @param q2
	 *            is LinkedQueue of Comparable objects, sorted from smallest to
	 *            largest.
	 * @return a LinkedQueue containing all the Comparable objects from q1 and
	 *         q2 (and nothing else), sorted from smallest to largest.
	 **/
	public static LinkedQueue mergeSortedQueues(LinkedQueue q1, LinkedQueue q2) {
		LinkedQueue a = new LinkedQueue();
		try {
			while (q1.size() + q2.size() > 0) {
				if (q1.size() == 0) {
					a.append(q2);
				} else if (q2.size() == 0) {
					a.append(q1);
				} else {
					if (((Comparable) q1.front()).compareTo(q2.front()) > 0) {
						a.enqueue(q2.dequeue());
					} else {
						a.enqueue(q1.dequeue());
					}
				}
			}
		} catch (QueueEmptyException e) {
			e.printStackTrace();
		}
		return a;
	}

	/**
	 * partition() partitions qIn using the pivot item. On completion of this
	 * method, qIn is empty, and its items have been moved to qSmall, qEquals,
	 * and qLarge, according to their relationship to the pivot.
	 * 
	 * @param qIn
	 *            is a LinkedQueue of Comparable objects.
	 * @param pivot
	 *            is a Comparable item used for partitioning.
	 * @param qSmall
	 *            is a LinkedQueue, in which all items less than pivot will be
	 *            enqueued.
	 * @param qEquals
	 *            is a LinkedQueue, in which all items equal to the pivot will
	 *            be enqueued.
	 * @param qLarge
	 *            is a LinkedQueue, in which all items greater than pivot will
	 *            be enqueued.
	 **/
	public static void partition(LinkedQueue qIn, Comparable pivot,
			LinkedQueue qSmall, LinkedQueue qEquals, LinkedQueue qLarge) {
		while (qIn.size() > 0) {
			Comparable c;
			try {
				c = (Comparable) qIn.dequeue();

				if (c.compareTo(pivot) > 0) {
					qLarge.enqueue(c);
				} else if (c.compareTo(pivot) < 0) {
					qSmall.enqueue(c);
				} else {
					qEquals.enqueue(c);
				}
			} catch (QueueEmptyException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * mergeSort() sorts q from smallest to largest using mergesort.
	 * 
	 * @param q
	 *            is a LinkedQueue of Comparable objects.
	 **/
	public static void mergeSort(LinkedQueue q) {
		LinkedQueue a = makeQueueOfQueues(q);
		if (a.size() == 0) {
			return;
		}
		try {
			while (a.size() > 1) {

				a.enqueue(mergeSortedQueues((LinkedQueue) a.dequeue(),
						(LinkedQueue) a.dequeue()));
			}
			q.append((LinkedQueue) a.dequeue());
		} catch (QueueEmptyException e) {
			e.printStackTrace();
		}
	}

	/**
	 * quickSort() sorts q from smallest to largest using quicksort.
	 * 
	 * @param q
	 *            is a LinkedQueue of Comparable objects.
	 **/
	public static void quickSort(LinkedQueue q) {
		if (q.size() > 1) {
			LinkedQueue qSmall = new LinkedQueue();
			LinkedQueue qEquals = new LinkedQueue();
			LinkedQueue qLarge = new LinkedQueue();
			partition(q, (Comparable) q.nth(1 + ((int) ((q.size()) * Math
					.random()))), qSmall, qEquals, qLarge);
			quickSort(qLarge);
			quickSort(qSmall);
			q.append(qSmall);
			q.append(qEquals);
			q.append(qLarge);
		}
	}

	/**
	 * makeRandom() builds a LinkedQueue of the indicated size containing
	 * Integer items. The items are randomly chosen between 0 and size - 1.
	 * 
	 * @param size
	 *            is the size of the resulting LinkedQueue.
	 **/
	public static LinkedQueue makeRandom(int size) {
		LinkedQueue q = new LinkedQueue();
		for (int i = 0; i < size; i++) {
			q.enqueue(new Integer((int) (size * Math.random())));
		}
		return q;
	}

	/**
	 * main() performs some tests on mergesort and quicksort. Feel free to add
	 * more tests of your own to make sure your algorithms works on boundary
	 * cases. Your test code will not be graded.
	 **/
	
}
