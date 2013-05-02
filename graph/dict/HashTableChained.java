/* HashTableChained.java */

package graph.dict;
import graph.list.*;

/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

  /**
   *  Place any data fields here.
   **/

    private DList[] table;
    private int size;

  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

  public HashTableChained(int sizeEstimate) {
      table = new DList[findPrime(sizeEstimate)];
      size = 0;
  }

  /** 
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/

  public HashTableChained() {
      table = new DList[101];
      size = 0; 
  }

  /**
   * Find a prime near n
   *
   * @param n target number
   */
  private int findPrime(int n) {
      n = (n * 105) / 100;
      int p = n;
      boolean[] prime = new boolean[n + 1];
      for (int i = 2; i <= n; i++) {
          prime[i] = true;
      }
      for (int divisor = 2; divisor * divisor <= n; divisor++) {
          if (prime[divisor]) {
              for (int i = 2 * divisor; i <= n; i += divisor) {
                  prime[i] = false;
              }
          }
      }
      while (p > 2 && !prime[p]) {
          p--;
      }
      return p;
  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  int compFunction(int code) {
      int n = table.length;
      int p = findPrime((n * n) + (50 * n) + 100);
      int a = n * 177 / 100;
      int b = a * 123 / 100;
      int result = ((a * code + b) % p) % n;
      if (result < 0) {
          return (result * -1);
      }
      return result;
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
      return size;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
    return size == 0;
  }

  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/

  public Entry insert(Object key, Object value) {
      int bucket = compFunction(key.hashCode());
      Entry entry = new HashEntry(key, value);
      if (table[bucket] == null) {
          DList list = new DList();
          table[bucket] = list;
      }
      table[bucket].insertFront(entry);
      size++;
      if ((double) size / (double) table.length > 0.75) {
          resize(2);
      }
      return entry;
  }

  /**
   * Resizes the hash table
   *
   * @param n the factor to resize the hash table with
   */
  private void resize(double n) {
      HashTableChained tmp = new HashTableChained((int)(table.length * n));
      for (int i = 0; i < table.length; i++) {
          if (table[i] != null) {
              DList list = table[i];
              DListNode node = list.front();
              while (node != null) {
                  tmp.insert(((HashEntry) node.item).key, ((HashEntry) node.item).value);
                  node = list.next(node);
              }
          }
      }
      this.table = tmp.table;
  }

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/

  public Entry find(Object key) {
      int bucket = compFunction(key.hashCode());
      DList list = table[bucket];
      DListNode node;
      if (list != null) {
          node = list.front();
          for (int i = 0; i < list.length(); i++) {
              Entry match = (Entry) node.item;
              if (match.key().equals(key)) {
                  return match;
              }
              node = list.next(node);
          }
      }
      return null;
  }

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *  If several entries have the specified key, choose one arbitrarily, then
   *  remove and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   */

  public Entry remove(Object key) {
      int bucket = compFunction(key.hashCode());
      DList list = table[bucket];
      DListNode node;
      Entry match;
      if (list != null) {
          node = list.front();
          for (int i = 0; i < list.length(); i++) {
              match = (Entry) node.item;
              if (match.key().equals(key)) {
                  list.remove(node);
                  size--;
                  if ((double) size / (double) table.length < 0.25) {
                      resize(.5);
                  }
                  return match;
              }
              node = list.next(node);
          }
      }
      return null;
  }

  public Object[] keys() {
      Object[] obj = new Object[size];
      int objcount = 0;
      for (int i = 0; i < table.length; i++) {
          if (table[i] != null) {
              DList list = table[i];
              DListNode node = list.front();
              while (node != null) {
                  obj[objcount] = ((HashEntry) node.item).key;
                  objcount++;
                  node = list.next(node);
              }
          }
      }
      return obj;
  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
      table = new DList[table.length];
      size = 0;
  }

  public void printCollisions() {
      int collisions = 0;
      int counter = 0;
      System.out.println("length: " + table.length);
      for (int i = 0; i < table.length; i++) {
          if (table[i] != null) {
              DList list = table[i];
              DListNode node = list.front();
              while (node != null) {
                  if (counter >= 1) {
                      collisions++;
                  }
                  counter++;
                  node = list.next(node);
              }
          }
          System.out.print(counter + "_");
          counter = 0;
      }
      System.out.println();
      System.out.println("number of collisions: " + collisions);
  }

}
