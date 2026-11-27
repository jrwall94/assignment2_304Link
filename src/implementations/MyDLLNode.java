package implementations;

/**
 * A single node in a doubly linked list (DLL), used as the building block for
 * {@link MyDLL} in CPRG 304 at SAIT.
 * <p>
 * Each node stores a {@link String} element and maintains two directional
 * references: a predecessor ({@code pred}) pointing to the node immediately
 * before it in the chain, and a successor ({@code succ}) pointing to the node
 * immediately after it. A {@code null} predecessor indicates the node is the
 * head of the list; a {@code null} successor indicates it is the tail.
 * </p>
 *
 * <p>
 * <strong>Design Note – String-Specific Implementation:</strong><br>
 * This class is intentionally designed to store {@link String} elements only. A
 * fully reusable, type-safe design would use Java generics (e.g.,
 * {@code DLLNode<E>}), allowing any object type to be stored in the node.
 * Generic programming is outside the scope of CPRG 304 and has therefore been
 * omitted. All element fields, parameters, and return types are fixed as
 * {@link String} throughout this class and its usages.
 * </p>
 */
public class MyDLLNode {
	// Attributes

	/** The {@link String} element stored in this node. */
	private String element;

	/**
	 * Reference to the predecessor node immediately before this node in 
	 * the chain, or {@code null} if this node is the head.
	 */
	private MyDLLNode pred;

	/**
	 * Reference to the successor node immediately after this node in the
	 * chain, or {@code null} if this node is the tail.
	 */
	private MyDLLNode succ;

	// Constructors

	/**
	 * Constructs a new {@link MyDLLNode} that holds given {@link String}
	 * element that is linked to the nodes on either side.
	 * <p>
	 * Use this constructor when inserting a node into an existing chain. The
	 * {@code pred} parameter may be {@code null} to indicate that this node is 
	 * the head of the list. The {@code succ} parameter may be {@code null} to 
	 * indicate that this node is the tail of the list.
	 * </p>
	 *
	 * @param element the {@link String} element to store in this node
	 * @param pred    the {@link MyDLLNode} immediately before this node in the
	 *                chain, or {@code null} if this node is the head
	 * @param succ    the {@link MyDLLNode} immediately after this node in the
	 *                chain, or {@code null} if this node is the tail
	 */
	public MyDLLNode(String element, MyDLLNode pred, MyDLLNode succ) {
		this.element = element;
		this.pred = pred;
		this.succ = succ;
	}

	/**
	 * Constructs a new {@link MyDLLNode} that holds given {@link String}
	 * element with no nodes on either side.
	 * <p>
	 * Both {@code pred} and {@code succ} are initialized to {@code null}, so 
	 * the node is not linked to any other node when it is created. The links 
	 * can be set afterwards using {@link #setPred(MyDLLNode)} 
	 * and {@link #setSucc(MyDLLNode)}.
	 * </p>
	 *
	 * @param element the {@link String} element to store in this node
	 */
	public MyDLLNode(String element) {
		this.element = element;
		this.pred = null;
		this.succ = null;
	}

	// Getters and Setters

	/**
	 * Returns the {@link String} element stored in this node.
	 *
	 * @return the {@link String} element held by this node; 
	 * may be {@code null} if {@code null} was explicitly stored
	 */
	public String getElement() {
		return this.element;
	}

	/**
	 * Replaces the {@link String} element stored in this node with 
	 * the specified value.
	 * <p>
	 * The {@code succ} and {@code pred} references of this node are unaffected
	 * by this call.
	 * </p>
	 *
	 * @param element the new {@link String} element to store in this node
	 */
	public void setElement(String element) {
		this.element = element;
	}

	/**
	 * Returns the predecessor of this node - the {@link MyDLLNode} immediately
	 * before this node in the doubly linked chain.
	 * 
	 * @return the predecessor {@link MyDLLNode}, or {@code null} if this node is
	 *         the head of the list
	 */
	public MyDLLNode getPred() {
		return this.pred;
	}

	/**
	 * Sets the predecessor of this node to the specified {@link MyDLLNode}
	 * <p>
	 * Pass {@code null} to make this node the new head of the list. The
	 * {@link String} element stored in this node is unaffected by this call.
	 * </p>
	 * 
	 * @param pred the {@link MyDLLNode} to set as this node's predecessor, or
	 *             {@code null} to indicate no predecessor
	 */
	public void setPred(MyDLLNode pred) {
		this.pred = pred;
	}

	/**
	 * Returns the successor of this node — the {@link MyDLLNode} immediately after
	 * this node in the doubly linked chain.
	 *
	 * @return the successor {@link MyDLLNode}, or {@code null} if this node is the
	 *         tail of the list
	 */
	public MyDLLNode getSucc() {
		return this.succ;
	}

	/**
	 * Sets the successor of this node to the specified {@link MyDLLNode}.
	 * <p>
	 * Pass {@code null} to make this node the new tail of the list. The
	 * {@link String} element stored in this node is unaffected by this call.
	 * </p>
	 *
	 * @param succ the {@link MyDLLNode} to set as this node's successor, or
	 *             {@code null} to indicate no successor
	 */
	public void setSucc(MyDLLNode succ) {
		this.succ = succ;
	}

	/**
	 * Returns a {@link String} representation of this node
	 * <p>
	 * The output includes the {@link String} element stored in this node and the
	 * elements of its predecessor and successor. If either neighbour is
	 * {@code null}, the string {@code "null"} is shown in its place.
	 * </p>
	 * <p>
	 * Only the neighbours' elements are printed, not the neighbour nodes
	 * themselves. Printing the nodes would call their {@code toString()}, and
	 * because each node links back to the other, this would recurse forever and
	 * cause a {@link StackOverflowError}.
	 * </p>
	 *
	 * <p>
	 * Example output for a node holding {@code "B"} between nodes holding
	 * {@code "A"} and {@code "C"}:
	 * </p>
	 * 
	 * <pre>
	 *   Node element: B, predecessor: A, successor: C
	 * </pre>
	 *
	 * @return a {@link String} describing this node's element and the elements of
	 *         its predecessor and successor
	 */
	@Override
	public String toString() {
		String predElement;
		if (pred == null) {
			predElement = "null";
		} else {
			predElement = pred.getElement();
		}

		String succElement;
		if (succ == null) {
			succElement = "null";
		} else {
			succElement = succ.getElement();
		}

		return "Node element: " + this.element + ", predecessor:" + predElement + ", successor: " + succElement;
	}

}