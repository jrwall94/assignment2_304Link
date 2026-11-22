package implementations;

import java.util.EmptyStackException;

import utilities.Iterator;
import utilities.StackADT;

/**
 * An array-backed implementation of the {@link StackADT} interface,
 * for use in CPRG 304 at SAIT.
 * <p>
 * This class delegates all storage to an internal {@link MyArrayList},
 * treating the end of the list as the top of the stack. {@link #push(String)}
 * appends to the end; {@link #pop()} and {@link #peek()} access the last
 * element. A stack follows a <strong>Last-In, First-Out (LIFO)</strong>
 * ordering policy: the last element pushed is the first one returned.
 * </p>
 *
 * <p><strong>Design Note – String-Specific Implementation:</strong><br>
 * This class is intentionally designed to store {@link String} elements only.
 * A fully reusable, type-safe design would use Java generics (e.g.,
 * {@code MyStack<E>}), allowing any object type to be stored in the stack.
 * Generic programming is outside the scope of CPRG 304 and has therefore
 * been omitted. All element fields, parameters, and return types are fixed
 * as {@link String} throughout this class and its implementations.
 * </p>
 */
public class MyStack implements StackADT
{
	/**
	 * The list that stores this stack. Index {@code 0} is the bottom and
	 * the last index is the top.
	 */
	private final MyArrayList elements;


	/**
	 * Creates an empty stack.
	 */
	public MyStack()
	{
		elements = new MyArrayList();
	}


	/**
	 * Pushes {@code toAdd} onto the top of this stack.
	 * <p>
	 * After this call, {@code toAdd} is the first element returned by
	 * {@link #pop()} or {@link #peek()}.
	 * </p>
	 *
	 * @param toAdd the text to push; must not be {@code null}
	 * @throws NullPointerException if {@code toAdd} is {@code null}
	 */
	@Override
	public void push( String toAdd ) throws NullPointerException
	{
		if( toAdd == null )
		{
			throw new NullPointerException( "cannot push a null String" );
		}
		elements.add( toAdd );
	}


	/**
	 * Removes and returns the element at the top of this stack.
	 *
	 * @return the text removed from the top
	 * @throws EmptyStackException if the stack contains no elements
	 */
	@Override
	public String pop() throws EmptyStackException
	{
		if( elements.isEmpty() )
		{
			throw new EmptyStackException();
		}
		return elements.remove( elements.size() - 1 );
	}


	/**
	 * Returns the element at the top of this stack without removing it.
	 *
	 * @return the text currently at the top
	 * @throws EmptyStackException if the stack contains no elements
	 */
	@Override
	public String peek() throws EmptyStackException
	{
		if( elements.isEmpty() )
		{
			throw new EmptyStackException();
		}
		return elements.get( elements.size() - 1 );
	}


	/**
	 * Removes every element from this stack.
	 */
	@Override
	public void clear()
	{
		elements.clear();
	}


	/**
	 * Returns {@code true} when this stack contains no elements.
	 *
	 * @return {@code true} if {@link #size()} is {@code 0}
	 */
	@Override
	public boolean isEmpty()
	{
		return elements.isEmpty();
	}


	/**
	 * Copies this stack from bottom to top.
	 * <p>
	 * {@code holder} is used when it has room for every element. Otherwise
	 * a new array of the required length is returned. The list stores the
	 * bottom at index {@code 0}, so its copy is already in stack order.
	 * </p>
	 *
	 * @param holder the destination array; must not be {@code null}
	 * @return {@code holder} when it is long enough, otherwise a new array
	 * @throws NullPointerException if {@code holder} is {@code null}
	 */
	@Override
	public String[] toArray( String[] holder ) throws NullPointerException
	{
		if( holder == null )
		{
			throw new NullPointerException( "Destination array cannot be null." );
		}
		return elements.toArray( holder );
	}


	/**
	 * Returns {@code true} when {@code toFind} occurs at least once.
	 *
	 * @param toFind the text to search for; must not be {@code null}
	 * @return {@code true} if an equal element is present
	 * @throws NullPointerException if {@code toFind} is {@code null}
	 */
	@Override
	public boolean contains( String toFind ) throws NullPointerException
	{
		if( toFind == null )
		{
			throw new NullPointerException( "cannot search for a null element" );
		}
		return elements.contains( toFind );
	}


	/**
	 * Returns the distance of {@code toFind} from the top of this stack.
	 * <p>
	 * The top element is position {@code 1}. If the same text occurs more
	 * than once, the occurrence closest to the top is returned.
	 * </p>
	 *
	 * @param toFind the text to search for; must not be {@code null}
	 * @return the 1-based position from the top, or {@code -1} if absent
	 * @throws NullPointerException if {@code toFind} is {@code null}
	 */
	@Override
	public int search( String toFind )
	{
		if( toFind == null )
		{
			throw new NullPointerException( "cannot search for a null element" );
		}

		int position = 1;
		for( int i = elements.size() - 1; i >= 0; i-- )
		{
			if( toFind.equals( elements.get( i ) ) )
			{
				return position;
			}
			position++;
		}
		return -1;
	}


	/**
	 * Returns an iterator that travels from the bottom of this stack to the top.
	 *
	 * @return an iterator in bottom-to-top order
	 */
	@Override
	public Iterator iterator()
	{
		return elements.iterator();
	}


	/**
	 * Compares this stack with {@code that} from bottom to top.
	 *
	 * @param that the other stack; may be {@code null}
	 * @return {@code true} when both stacks contain the same texts in the
	 *         same bottom-to-top order
	 */
	@Override
	public boolean equals( StackADT that )
	{
		if( that == null || this.size() != that.size() )
		{
			return false;
		}

		Iterator mine = this.iterator();
		Iterator theirs = that.iterator();
		while( mine.hasNext() )
		{
			if( !mine.next().equals( theirs.next() ) )
			{
				return false;
			}
		}
		return true;
	}


	/**
	 * Returns the number of elements in this stack.
	 *
	 * @return the current depth; never negative
	 */
	@Override
	public int size()
	{
		return elements.size();
	}
}
