//Alejandra Zapata.
import java.util.*;

public class BinarySearchTreeWithDups<T extends Comparable<? super T>> extends BinarySearchTree<T>
		implements SearchTreeInterface<T>, java.io.Serializable {

	public BinarySearchTreeWithDups() {
		super();
	}

	public BinarySearchTreeWithDups(T rootEntry) {
		super(rootEntry);
		setRootNode(new BinaryNode<T>(rootEntry));
	}

	@Override
	public T add(T newEntry) {
		T result = newEntry;
		if (isEmpty()) {
			setRootNode(new BinaryNode<T>(newEntry));
		} else {
			addEntryHelperNonRecursive(newEntry);
		}
		return result;
	}
	
	// YOUR CODE HERE! THIS METHOD CANNOT BE RECURSIVE.
	private void addEntryHelperNonRecursive(T newEntry) {
		// Assertion: rootNode != null
		BinaryNode<T> currentNode = getRootNode();
		int comparison = getComparison(newEntry, currentNode.getData());
		boolean added = false;
		
		if(currentNode.isLeaf()) {	
			added = isAdded(newEntry, currentNode, comparison);
		}
		else{ 
			while(!currentNode.isLeaf() && !added) {
				comparison = getComparison(newEntry, currentNode.getData());
			
				if(isLessOrEqual(comparison) && currentNode.hasLeftChild()) {
					currentNode = currentNode.getLeftChild();
				}else if(isGreater(comparison) && currentNode.hasRightChild()) {
					currentNode = currentNode.getRightChild();
				}
				
				comparison = getComparison(newEntry, currentNode.getData());
				added = isAdded(newEntry, currentNode, comparison);
			}
		}
	}
	
	private int getComparison(T data, T anotherData) {
		return data.compareTo(anotherData);
	}
	private boolean isLessOrEqual(int comparison) {
		return comparison < 0 || comparison == 0;
	}
	private boolean isGreater(int comparison) {
		return comparison > 0;
	}
	private boolean isAdded(T newEntry, BinaryNode<T> currentNode, int comparison) {
		if(isLessOrEqual(comparison) && !currentNode.hasLeftChild()) {
			currentNode.setLeftChild(new BinaryNode<T>(newEntry));
			return true;
		}else if(isGreater(comparison) && !currentNode.hasRightChild()) {
			currentNode.setRightChild(new BinaryNode<T>(newEntry));
			return true;
		}
		return false;
	}

	// YOUR CODE HERE! THIS METHOD CANNOT BE RECURSIVE.
	// MAKE SURE TO TAKE ADVANTAGE OF THE SORTED NATURE OF THE BST!
	public int countEntriesNonRecursive(T target) {
		int count = 0;
		BinaryNode<T> currentNode = getRootNode();
		
		while(currentNode != null) {
			int comparison = getComparison(target, currentNode.getData());
			
			if(comparison == 0) {
				count ++;
			}
			
			if(isLessOrEqual(comparison)) {
				currentNode = currentNode.getLeftChild();
			}else { // isGreater(comparison);
				currentNode = currentNode.getRightChild();
			}
		}	
		return count;
	}

	// YOUR CODE HERE! MUST BE RECURSIVE! YOU ARE ALLOWED TO CREATE A PRIVATE HELPER.
	// MAKE SURE TO TAKE ADVANTAGE OF THE SORTED NATURE OF THE BST!
	public int countGreaterRecursive(T target) {
		BinaryNode<T> rootNode = getRootNode();

		return countGreaterRecursiveHelper(target, rootNode);
	}
	private int countGreaterRecursiveHelper(T target, BinaryNode<T> node) {
		if(node != null) {
			if(node.getData().compareTo(target) > 0) {
				return 1 + countGreaterRecursiveHelper(target, node.getLeftChild()) + countGreaterRecursiveHelper(target, node.getRightChild());
			}else {
				return 0 + countGreaterRecursiveHelper(target, node.getLeftChild()) + countGreaterRecursiveHelper(target, node.getRightChild());
			}
		}// base case when node == null;
		return 0;
	}
		
	// YOUR CODE HERE! MUST USE A STACK!! MUST NOT BE RECURSIVE! 
	// MAKE SURE TO TAKE ADVANTAGE OF THE SORTED NATURE OF THE BST!
	public int countGreaterWithStack(T target) {
		int count = 0;
		BinaryNode<T> currentNode = getRootNode();
		Stack<BinaryNode<T>> nodeStack = new Stack<>();
		
		while(!nodeStack.isEmpty() || currentNode != null) {
			
			while(currentNode != null) {
				nodeStack.push(currentNode);
				currentNode = currentNode.getLeftChild();
			}
			
			if(!nodeStack.isEmpty()) {
				BinaryNode<T> nextNode = nodeStack.pop();
				if(nextNode.getData().compareTo(target) > 0) {
					count++;
				}
				currentNode = nextNode.getRightChild();
			}
		}
		return count;
	}
		
	// YOUR EXTRA CREDIT CODE HERE! THIS METHOD MUST BE O(n). 
	// YOU ARE ALLOWED TO USE A HELPER METHOD. THE METHOD CAN BE ITERATIVE OR RECURSIVE. 
	public int countUniqueValues() {
		BinaryNode<T> currentNode = getRootNode();
		HashSet<T> uniqueValuesSet = new HashSet<T>();
		
		countUniqueValuesHelper(currentNode, uniqueValuesSet);
		
		return uniqueValuesSet.size();
	}
	private void  countUniqueValuesHelper(BinaryNode<T> currentNode, HashSet<T> uniqueValuesSet) {
		if(currentNode != null) {
			uniqueValuesSet.add(currentNode.getData());

			if(currentNode.hasLeftChild()) {
				countUniqueValuesHelper(currentNode.getLeftChild(), uniqueValuesSet);
			}
			if(currentNode.hasRightChild()) {
				countUniqueValuesHelper(currentNode.getRightChild(), uniqueValuesSet);
			}
		}
	}	
	
	public int getLeftHeight() {
		BinaryNode<T> rootNode = getRootNode();
		if(rootNode==null) {
			return 0;
		} else if(!rootNode.hasLeftChild()) {
			return 0;
		} else {
			return rootNode.getLeftChild().getHeight();
		}
	}

	public int getRightHeight() {
		BinaryNode<T> rootNode = getRootNode();
		if(rootNode==null) {
			return 0;
		} else if(!rootNode.hasRightChild()) {
			return 0;
		} else {
			return rootNode.getRightChild().getHeight();
		}
	}
	


}