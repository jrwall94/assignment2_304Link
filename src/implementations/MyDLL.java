package implementations;

import java.util.Arrays;
import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.ListADT;

/**
 * A doubly linked list (DLL) implementation of the {@link ListADT} interface,
 * for use in CPRG 304 at SAIT.
 * <p>
 * This class stores {@link String} elements in a chain of {@link MyDLLNode}
 * objects, each of which holds a reference to both its predecessor ({@code pred})
 * and its successor ({@code succ}). The list maintains explicit {@code head}
 * and {@code tail} references for O(1) access to both ends of the chain.
 * Insertions and removals at arbitrary positions require O(n) traversal.
 * All index parameters are zero-based unless otherwise noted.
 * </p>
 *
 * <p><strong>Design Note – String-Specific Implementation:</strong><br>
 * This class is intentionally designed to store {@link String} elements only.
 * A fully reusable, type-safe design would use Java generics (e.g.,
 * {@code MyDLL<E>}), allowing any object type to be stored in the list.
 * Generic programming is outside the scope of CPRG 304 and has therefore
 * been omitted. All element fields, parameters, and return types are fixed
 * as {@link String} throughout this class and its implementations.
 * </p>
 */
public class MyDLL implements ListADT
{
    // Attributes
	
	/**
     * Reference to the first {@link MyDLLNode} in the list, or {@code null}
     * if the list is empty.
     */
	private MyDLLNode head;
	
	/**
     * Reference to the last {@link MyDLLNode} in the list, or {@code null}
     * if the list is empty.
     */
	private MyDLLNode tail;
	
	 /** The number of {@link String} elements currently stored in the list. */
	private int size;
	
	// Constructors
	
	public MyDLL() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}

	 @Override
	 public int size() {
		return this.size;
	 }

	 @Override
	 public void clear() {
		 head = tail = null;
	     size = 0;
	 }

	 @Override
	 public boolean add(int index, String toAdd) throws NullPointerException, IndexOutOfBoundsException {
		 boolean status = false;
		 
		 if( toAdd == null ) {
			 throw new NullPointerException( "Cannot store null element");
		 }
		 
		 if(index < 0 || index > size ) {
			 throw new IndexOutOfBoundsException( "Index outside boudns: " + index );
		 }
		 
		 if( !isEmpty() ) {
			 if( size == 1 && index == 0 ) {
				 head = new MyDLLNode( toAdd, null, tail );
				 tail.setPred(head);
				 size++;
				 status = true;
			 }
			 else if( index == 0 ) {
				 MyDLLNode succ = head;
				 head = new MyDLLNode( toAdd, null, succ);
				 succ.setPred(head);
				 size++;
				 status = true;
			 }
			 else if( index == size ) {
				 MyDLLNode before = tail;
				 tail = new MyDLLNode (toAdd, before, null);
				 before.setSucc(tail);
				 size++;
				 status = true;
			 }
			 else {
				 MyDLLNode before = head;
				 MyDLLNode after = head.getSucc();
				 
				 for( int i = 0; i< index - 1; i++ ) {
					 before = before.getSucc();
					 after = after.getSucc();
				 }
				 
				 MyDLLNode insert = new MyDLLNode( toAdd, before, after );
				 before.setSucc( insert );
				 after.setPred(insert);
				 size++;
				 status = true;
			 }
			 
		 }
		 else {
			 head = tail = new MyDLLNode( toAdd, null, null);
			 size++;
			 status = true;
		 }
		 return status;
	 }

	 @Override
	 public boolean add(String toAdd) throws NullPointerException {
		return add( size, toAdd);
	 }

	 @Override
	 public boolean addAll(ListADT toAdd) throws NullPointerException {
		if( toAdd == null ) {
			throw new NullPointerException( "Cannot add a null list." );
		}
		int count = toAdd.size();
		for( int i = 0; i < count; i++ ) {
			this.add( toAdd.get(i));
		}
		return true;
	 }

	 @Override
	 public String get(int index) throws IndexOutOfBoundsException {
		if( index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException( "Index out of bounds: " + index);
		}
		MyDLLNode curr = head;
		for( int i = 0; i < index; i++ ) {
			curr = curr.getSucc();
		}
		return curr.getElement();
	 }

	 @Override
	 public String remove(int index) throws IndexOutOfBoundsException {
		if( index < 0 || index >= size() ) {
			throw new IndexOutOfBoundsException( "Index out of bounds: " + index );
		}
		if( index == 0) {
			MyDLLNode del = head;
			head = head.getSucc();
			if( head == null ) {
				tail = null; 
			}
			
			else {
				head.setPred(null);
			}
			size--;
			return del.getElement();
		}
		MyDLLNode del = head.getSucc();
		MyDLLNode before = head;
		
		for( int i = 1; i < index; i++ ) {
			del = del.getSucc();
			before = before.getSucc();
		}
		
		String removed = del.getElement();
		before.setSucc( del.getSucc() );
		
		if( del.getSucc() == null ) {
			tail = before;
		}
		else {
			del.getSucc().setPred(before);
		}
		
		size--;
		return removed;
	 }

	 @Override
	 public String remove(String toRemove) throws NullPointerException {
		 if( toRemove == null ) {
	            throw new NullPointerException( "Cannot remove a null element." );
	        }
	        MyDLLNode curr = head;
	        int index = -1;

	        for( int i = 0; i < size; i++ ) {
	            if( curr.getElement().equals( toRemove ) ) {
	                index = i;
	                break;
	            }
	            curr = curr.getSucc();
	        }

	        if( index > -1 ) {
	            return remove( index );
	        }
		return null;
	 }

	 @Override
	 public String set(int index, String toChange) throws NullPointerException, IndexOutOfBoundsException {
		 
		 if( toChange == null ) {
			 throw new NullPointerException( "Cannot store a null element." );
		 }
		 if( index < 0 || index >= size() ) {
			 throw new IndexOutOfBoundsException( "Index is out of bounds: " + index);
		 }
		 
		 MyDLLNode curr = head;
		 for( int i = 0; i < index; i++ ) {
			 curr = curr.getSucc();
		 }
		 
		 String old = curr.getElement();
		 curr.setElement( toChange );
		 return old;
	 }

	 @Override
	 public boolean isEmpty() {
		return head == null;
	 }

	 @Override
	 public boolean contains(String toFind) throws NullPointerException {
		 if( toFind == null ) {
			 throw new NullPointerException( "Cannot search for null element");
		 }
		 if( isEmpty() ) {
			 return false;
		 }
		 MyDLLNode curr = head;
		 while( curr != null ) {
			 if( toFind.equals( curr.getElement() )) {
				 return true;
			 }
			 curr = curr.getSucc();
		 }
		return false;
	 }

	 @Override
	 public int indexOf(String toFind) throws NullPointerException {
		if( toFind == null ) {
			throw new NullPointerException( "Cannot search for a null element. ");
		}
		if( isEmpty() ) {
			return -1;
		}
		MyDLLNode curr = head;
		int index = 0;
		
		while( curr != null ) {
			if( toFind.equals( curr.getElement() )) {
				return index;
			}
			curr = curr.getSucc();
			index++;
		}
		return -1;
	 }

	 @Override
	 public String[] toArray(String[] toHold) throws NullPointerException {
		if( toHold == null ) {
			throw new NullPointerException( "Cannot copy to a null array." );
		}
		if( toHold.length < size ) {
			toHold = Arrays.copyOf( toHold, size);
		}
		MyDLLNode curr = head;
		for( int i = 0; i < size; i++ ) {
			toHold[ i ] = curr.getElement();
			curr = curr.getSucc();
		}
		return toHold;
	 }

	 @Override
	 public Iterator iterator() {
		return new DLLIterator();
	 }
	 
	 //Inner Classes 
	 private class DLLIterator implements Iterator {
		 private String[] copyOfElements;
		 private int pos;
		 
		 public DLLIterator() {
			 copyOfElements = new String[ size ];
			 MyDLLNode curr = head;
			 
			 for( int i = 0; i < size; i++ ) {
				 copyOfElements[ i ] = curr.getElement();
				 curr = curr.getSucc();
			 }
			 pos = 0;
		 }
		 
		 @Override
		 public boolean hasNext() {
			 return pos < copyOfElements.length;
		 }
		 
		 @Override
		 public String next() throws NoSuchElementException {
			 if( !hasNext() ) {
				 throw new NoSuchElementException ( "No more elements in the iteration. ");
			 }
			 return copyOfElements[ pos++ ];
		 }
	 }
}