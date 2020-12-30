
/**	This class implements a dictionary using a hash table with separate chaining. 
 * 
 * @author Sasa Vecerak (#250470100)
 *
 */

import java.util.LinkedList;

public class Dictionary implements DictionaryADT {

	private int elementsCount; // instance variable keeping a running total of Record objects in dictionary
	private LinkedList<Record>[] dictionary;

	public Dictionary(int size) {
		dictionary = new LinkedList[size];
	}

	/**
	 * Hash function to hash the string key
	 * 
	 * @param key string key
	 * @return integer representation of the hash
	 */
	private int hash(String key) {

		int prime = 47; // prime number
		int size = dictionary.length;

		int value = (int) key.charAt(key.length() - 1);
		for (int i = key.length() - 2; i >= 0; i--) {
			value = (value * prime + (int) key.charAt(i)) % size;
		}
		return value % size;
	}

	/**
	 * Inserts given Record pair in the dictionary. Throws exception if
	 * pair.getConfig() is already in dictionary.
	 * 
	 * @param pair Record pair
	 * @return value 1 if insertion produces collision; return 0 otherwise
	 */
	public int insert(Record pair) throws DictionaryException {

		int pos = hash(pair.getConfig());
		LinkedList<Record> items = dictionary[pos];

//		if (items == null) { // If this position is empty
//			dictionary[pos] = new LinkedList<Record>(); // Inputs pair in newly created linked list.
//			dictionary[pos].add(pair);
//			elementsCount++; // increment count of total objects
//			return 0;
//		} else {
		while (items!=null) {	
			for (Record item : items) { // Iterates through linked list
				if (item.getConfig().equals(pair.getConfig())) { // If configuration is found, throws exception
					throw new DictionaryException();
				}
			}
			items.add(pair); // If configuration is not found after iterating through items, we add pair
			elementsCount++; // increment count of total objects
			return 1;
		}
		dictionary[pos] = new LinkedList<Record>(); // Inputs pair in newly created linked list.
		dictionary[pos].add(pair);
		elementsCount++; // increment count of total objects
		return 0;
		}
	

	/**
	 * Removes the entry with the given configuration from the dictionary. Throws
	 * DictionaryException if the configuration is not in the dictionary.
	 * 
	 * @param config configuration
	 */
	public void remove(String config) throws DictionaryException {
		int pos = hash(config); // position 
		LinkedList<Record> items = dictionary[pos];

		if (items == null) {
			throw new DictionaryException(); // Can't remove key if nothing is there; throw exception
		} else {
			
			for (Record item : items) { // Iterate through linked list
				if (item.getConfig().equals(config)) { // if match found, remove config
					items.remove(config);
					//System.out.println("we have: "+elementsCount);

					elementsCount--; // decrement running total of objects in dictionary
					//System.out.println(elementsCount);
				}
			}
		}

	}

	/**
	 * Returns the score stored in the dictionary for the given configuration;
	 * returns -1 if the configuration is not in the dictionary.
	 * 
	 * @param config configuration
	 * @return -1 if configuration is not in dictionary; otherwise returns score
	 */
	public int get(String config) {

		int pos = hash(config); // position
		LinkedList<Record> items = dictionary[pos];

//		if (items == null)
//			return -1;
		while (items != null) {
		for (Record item : items) { // Iterates through linked list
			if (item.getConfig().equals(config)) // If a match is found, return score; otherwise return -1
				return item.getScore();
		}
		}
		return -1;
	}

	/**
	 * Returns the number of Record objects stored in the dictionary.
	 * 
	 * @return integer value representing the number of objects in dictionary
	 */
	public int numElements() {
		return elementsCount;
	}
}
