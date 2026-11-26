package implementations;

import java.util.NoSuchElementException;
import utilities.Iterator;
import utilities.ListADT;

/**
 * A dynamic array-based implementation of the {@link ListADT} interface,
 * for use in CPRG 304 at SAIT.
 * <p>
 * This class stores {@link String} elements in an internally managed array
 * that grows automatically when capacity is reached. The growth amount is
 * determined by a configurable growth rate expressed as a decimal percentage
 * of the current array length (e.g., {@code 0.25} means 25% growth).
 * All index parameters are zero-based unless otherwise noted.
 * </p>
 *
 * <p><strong>Design Note – String-Specific Implementation:</strong><br>
 * This class is intentionally designed to store {@link String} elements only.
 * A fully reusable, type-safe design would use Java generics (e.g.,
 * {@code MyArrayList<E>}), allowing any object type to be stored in the list.
 * Generic programming is outside the scope of CPRG 304 and has therefore
 * been omitted. All element fields, parameters, and return types are fixed
 * as {@link String} throughout this class and its implementations.
 * </p>
 */
public class MyArrayList implements ListADT
{
	private int size = 0;
	private String[] elements = new String[10];
	
	//Doubles growth by default
	private double growthRate = 1.0;
	
	

	@Override
	public int size() {
		return this.size;
	}


	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
		
	}

	@Override
	public boolean add(int index, String toAdd) throws NullPointerException, IndexOutOfBoundsException {
		
		if (index < 0 | index > size) {
			throw new IndexOutOfBoundsException("Invalid index: " + index);
		}
		if (toAdd == null) {
			throw new NullPointerException("Added string cannot be null");
		}
		if (size == elements.length) {
			growArray();
		}
		
	
		
		for (int i = size; i > index; i--) {
			elements[i] = elements[i - 1];
		}
		
		elements[index] = toAdd;
		size++;
		return true;
		
	}

	@Override
	public boolean add(String toAdd) throws NullPointerException {
		if (toAdd == null) {
			throw new NullPointerException("Added string cannot be null");
		}
		if (size == elements.length) {
		    growArray();
		  }
		elements[size] = toAdd;
		  size++;
		return true;
	}
	
	

	@Override
	public boolean addAll(ListADT toAdd) throws NullPointerException {
		if (toAdd == null) {
			throw new NullPointerException("The list to add cannot be null");
		}
		
		if (toAdd.size() == 0) {
			return false;
		};
		
		String[] items = new String[toAdd.size()];
		Iterator source = toAdd.iterator();
		
		
			
		for (int i = 0; i < items.length; i++) {
			items[i] = source.next();
		}
		
		for (String item : items) {
			add(item);
		}
		
		
		return true;
	}

	@Override
	public String get(int index) throws IndexOutOfBoundsException {
		if (index < 0 ) {
			throw new IndexOutOfBoundsException("The index cannot be less than 0");
		}
		if (index >= size ) {
			throw new IndexOutOfBoundsException("The index " + index + ", is out of bounds for list size " + size);
		}
		return elements[index];
		
	}

	@Override
	public String remove(int index) throws IndexOutOfBoundsException {
		if (index < 0 ) {
			throw new IndexOutOfBoundsException("The index cannot be less than 0");
		}
		if (index >= size ) {
			throw new IndexOutOfBoundsException("The index " + index + ", is out of bounds for list size " + size);
		}
		
		String temp = elements[index];
		
		for (int i = index; i < size-1; i++) {
			elements[i] = elements[i + 1];
		}
		size--;
		elements[size] = null;
		return temp;
		
	}

	@Override
	public String remove(String toRemove) throws NullPointerException {
		if (toRemove == null) {
			throw new NullPointerException("The String to remove cannot be null");
		}
		
		int foundIndex = -1;
		
		for (int i = 0; i < size; i++) {
			if (toRemove.equals(elements[i])) {
				foundIndex = i;
				break; 
			}
			
		}
		
		//If not found
		if (foundIndex == -1) {
			return null;
		}
		
		String foundString = elements[foundIndex];
		
		for (int i = foundIndex; i < size-1; i++) {
			elements[i] = elements[i + 1];
		}
		size--;
		elements[size] = null;
		return foundString;
		
	}

	@Override
	public String set(int index, String toChange) throws NullPointerException, IndexOutOfBoundsException {
		if (toChange == null) {
			throw new NullPointerException("The String to change cannot be null");
		}
		if (index < 0 ) {
			throw new IndexOutOfBoundsException("The index cannot be less than 0");
		}
		if (index >= size ) {
			throw new IndexOutOfBoundsException("The index " + index + ", is out of bounds for list size " + size);
		}
		
		String changed = elements[index];
		elements[index] = toChange;
		return changed;
		
	}

	@Override
	public boolean isEmpty() {
		if (size == 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean contains(String toFind) throws NullPointerException {
		if (toFind == null) {
			throw new NullPointerException("The String to find cannot be null");
		}
		
		for (String string : elements) {
			if (toFind.equals(string)) {
				return true;
			}
		}
		
		return false;
		
	}

	@Override
	public int indexOf(String toFind) throws NullPointerException {
		if (toFind == null) {
			throw new NullPointerException("The String to find cannot be null");
		}
		
		for (int i = 0; i < size; i++) {
			if (toFind.equals(elements[i])) {
				return i;
			}
		}
		
		return -1;
		
	}

	
	@Override
	public String[] toArray(String[] toHold) throws NullPointerException {
		if (toHold == null) {
			throw new NullPointerException("The provided array cannot be null");
		}
		
		if (toHold.length < size) {
			toHold = new String[size];
		}
		
		for (int i = 0; i < size; i++) {
			toHold[i] = elements[i];
		}
		
		return toHold;
	
	}

	@Override
	public Iterator iterator() {
		return new ArrayListIterator();
	}
	
	private class ArrayListIterator implements Iterator {
		private final String[] deepCopy;
		private int nextIndex = 0;
		
		private ArrayListIterator() {
			deepCopy = new String[size];
			for (int i = 0; i < size; i++) {
				deepCopy[i] = elements[i];
			}
		}

		@Override
		public boolean hasNext() {
			if (nextIndex < deepCopy.length) {
				return true;
			}
			return false;
		}

		@Override
		public String next() throws NoSuchElementException {
			if (!hasNext()) {
				throw new NoSuchElementException("No more elements in the array!");
			}
			
			String next = deepCopy[nextIndex];
			nextIndex++;
			return next;
			
		}
	}
	
	/**
     * Grows the ArrayList capacity by the configured growth rate, double by default.
     */
	private void growArray() {
		int increase = (int) Math.max(1, Math.floor(elements.length * growthRate));
		int newSize = elements.length + increase;
		
		String[] larger = new String[newSize];
		for (int i=0; i < elements.length; i++) {
			larger[i] = elements[i];
		}
		elements = larger;
	}
	
	/**
     * Sets the growth rate
     */
	public void changeGrowthRate(double newGrowthRate) {
		if (newGrowthRate <= 0) {
			throw new IllegalArgumentException("Growth rate must be positive and not 0");
		}
		this.growthRate = newGrowthRate;
		
	}
	

   
}