// Delta College - CST 283 - Klingler & Gaddis Text                  
// This class implements an unordered singly-linked list of objects.

class LinkedListR<ItemType> {
	// --------------------------------------------------------------
	// Private inner class definition of standard data node
	private class Node {
		ItemType value;
		Node next;

		// Construct node with data and reference to successor
		Node(ItemType val, Node n) {
			value = val;
			next = n;
		}

		// Construct node with data null sucessor
		Node(ItemType val) {
			this(val, null);
		}
	}
	// --------------------------------------------------------------

	private Node first; // list head
	private Node last; // last element in list

	private Node currentPos; // Current position for iterator action

	// --------------------------------------------------------------
	// No-arg constructor for linked list. Declare empty; null out pointers
	public LinkedListR()
    {
        first = null;
        last = null;        
    }

	// --------------------------------------------------------------
	// Observer to determine if list empty
	public boolean isEmpty() {
		return first == null;
	}

	// --------------------------------------------------------------
	// Get current (integer) length of list
	public int size() {
		int count = 0;
		Node currNode = first;
		while (currNode != null) {
			count++; // Increment counter
			currNode = currNode.next; // Advance to next node
		}
		return count;
	}

	// --------------------------------------------------------------
	// Method adds an element to the front of the list
	public void add(ItemType newElementData) {
		if (isEmpty()) // If list empty, simply add new element
		{
			first = new Node(newElementData);
			last = first;
		} else // Otherwise, add parameter element to front of list
		{
			Node newNode = new Node(newElementData);
			newNode.next = first;
			first = newNode;
		}
	}

	// --------------------------------------------------------------
	// Method removes an element by searching for and matching the value
	public boolean remove(ItemType element) {
		// Special case: empty list - do nothing
		if (isEmpty())
			return false;

		// Special case: element matched for deletion is first element
		if (element.equals(first.value)) {
			first = first.next;
			if (first == null)
				last = null;
			return true;
		}

		// Find the predecessor of the element to remove
		Node pred = first;
		while (pred.next != null && !pred.next.value.equals(element)) {
			pred = pred.next;
		}

		// If not found return false
		if (pred.next == null)
			return false;

		// Otherwise, element found and pred.next.value is element
		pred.next = pred.next.next; // Bypass element to delete it

		// Special case: check if pred is now last
		if (pred.next == null)
			last = pred;

		return true;
	}

	// --------------------------------------------------------------
	// Method searches for existence of target in list using linear
	// search. If matching (i.e. equals method returns true) object
	// in list overwrites parameter hence returning entire list object
	// by reference.
	// Param: target. Search target.
	// Return: boolean. Found target or did not find target
	public boolean contains(ItemType target) {
		boolean moreToSearch;
		Node nodePtr;

		nodePtr = first; // Start search from head of list
		boolean found = false; // Assume value not found
		moreToSearch = (nodePtr != null);

		while (moreToSearch && !found) {
			if (target.equals(nodePtr.value)) {
				found = true;
			} else {
				nodePtr = nodePtr.next;
				moreToSearch = (nodePtr != null);
			}
		}
		return found;
	}

	// --------------------------------------------------------------
	// Method searches calls private recursive search method,
	public boolean containsRecurse(ItemType item) {
		return searchRecursive(item);
	}

	// --------------------------------------------------------------
	// Method searches recursively for an item specified in the list.
	private boolean searchRecursive(ItemType item) {
		Node curr = first;

		// Recursively search through list
		for (int i = 0; i < size(); i++) {
			if (item.equals(curr.value)) {
				return true; // Exit out of method once found
			}
			curr = curr.next;
		}

		return false;
	}

	// --------------------------------------------------------------
	// Method searches for existence of target in list using linear
	// search. If matching (i.e. equals method returns true) object.
	// Object from list is return.
	public ItemType retrieve(ItemType target) {
		boolean moreToSearch;
		Node nodePtr;
		ItemType returnItem;

		nodePtr = first; // Start search from head of list
		boolean found = false; // Assume value not found
		moreToSearch = (nodePtr != null);

		while (moreToSearch && !found) {
			if (target.equals(nodePtr.value)) {
				found = true;
				return nodePtr.value;
			} else {
				nodePtr = nodePtr.next;
				moreToSearch = (nodePtr != null);
			}
		}
		return null; // If value not found, return null
	}

	// --------------------------------------------------------------
	// Method adds an element to the end of the list
	public void append(ItemType newElementData) {
		if (isEmpty()) // If list empty, simply add new element
		{
			first = new Node(newElementData);
			last = first;
		} else // Otherwise, add parameter element to end of list
		{
			last.next = new Node(newElementData);
			last = last.next;
		}
	}

	// --------------------------------------------------------------
	// The add method adds an element at a position.
	// PRE: index >= 0 && index < size
	public void addAt(int index, ItemType newElementData) {
		// Special case: index is beginning
		if (index == 0) {
			first = new Node(newElementData, first);
			if (last == null)
				last = first;
			return;
		}

		// Set a reference pred to point to the node that
		// will be the predecessor of the new node
		Node pred = first;
		for (int k = 1; k <= index - 1; k++) {
			pred = pred.next;
		}

		// Splice in a node containing the new element
		pred.next = new Node(newElementData, pred.next);

		// Special case: new node requirst adjusting last element
		if (pred.next.next == null)
			last = pred.next;
	}

	// --------------------------------------------------------------
	// Method removes the element at an index. Returns object being
	// removed
	// PRE: index >= 0 && index < size
	public ItemType removeAt(int index) {
		// Special case: remove element at front of list
		ItemType element; // The element to return
		if (index == 0) {
			// Adjust reference pointers at front
			element = first.value;
			first = first.next;
			if (first == null)
				last = null;
		} else {
			// To remove an element other than the first, find the predecessor
			// of the element to be removed by marching variable pred
			// forward index - 1 times
			Node pred = first;
			for (int k = 1; k <= index - 1; k++)
				pred = pred.next;

			element = pred.next.value; // Capture return value

			pred.next = pred.next.next; // Bypass element to be removed

			// Special case: check if pred is now last; adjust if necessary
			if (pred.next == null)
				last = pred;
		}
		return element;
	}

	// --------------------------------------------------------------
	// Observer function to return current list length
	public void resetList() {
		currentPos = first;
	}

	// --------------------------------------------------------------
	// Function: Gets the next element in list as
	// referenced by currPtr
	// Pre: Current position is defined.
	// Post: Current position is updated to next position.
	// item is a copy of element at current position.
	public ItemType getNextItem() {
		ItemType item;

		if (currentPos == null)
			currentPos = first; // Wrap if position is at end

		item = currentPos.value; // Get item at current position
		currentPos = currentPos.next; // Advance to next position

		return item; // Return item
	}

	// --------------------------------------------------------------
	// Observer function to determine if current
	// is the end of the list
	public boolean atEnd() {
		if (currentPos == null)
			return true;
		else
			return false;
	}

	// --------------------------------------------------------------
	// The toString method computes the string representation of the list.
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();

		// Use currPos to walk down the linked list
		Node currPos = first;
		while (currPos != null) {
			strBuilder.append(currPos.value + "\n");
			currPos = currPos.next;
		}
		return strBuilder.toString();
	}

	// --------------------------------------------------------------
	// Method counts number of occurrences of target item in list
	public int countItems(ItemType target) {
		int returnCount = 0;
		ItemType currentItem;

		// Utilize iterators to navigate through list and count
		resetList();
		while (!atEnd()) {
			currentItem = getNextItem();
			if (currentItem.equals(target))
				returnCount++;
		}
		return returnCount;
	}

	// --------------------------------------------------------------
	// Get current (integer) length of list - recursively
	public int sizeRecurse() {
		return sizeCount(first);
	}

	// Recurseive helper method to count number of elements in list
	private int sizeCount(Node nodePtr) {
		if (nodePtr == null)
			return 0;
		else
			return sizeCount(nodePtr.next) + 1;
	}

	// --------------------------------------------------------------
	// This method launches a recursive chain of method calls that
	// will formulate a string reprepresentation of the list in reverse
	// order.
	public String reverseToString() {
		return reverseListStrings(first);
	}

	// Private recursive counterpart designed to recursively traverse a
	// list. During backtracking, string representations of list elements
	// are concatenated line-by-line in reverse order.
	private String reverseListStrings(Node nodePtr) {
		String outputString = "";
		if (nodePtr.next != null)
			return reverseListStrings(nodePtr.next) + nodePtr.value.toString() + "\n";
		else
			return nodePtr.value.toString() + "\n";
	}

}