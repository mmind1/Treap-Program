package Assign5;
import java.util.Stack;
import java.util.Random;

public class Treap <E extends Comparable<E> >{
	/**
	 * A private class Node to create and manipulate node
	 * @param <E>
	 */
	private static class Node<E>{
		public E data; // A key for the search
		public int priority; // A random heap priority
		public Node<E > left;
		public Node<E > right;
	/**
	* A constructor
	* @param data key to be stored for sorting
	* @param priority .. Priority to create the max-heap
   	*/    
		public Node(E data, int priority) {
			if(data == null)
				throw new NullPointerException("Data not provided");
			
			else {
			this.left = null;
			this.right =null;
			this.data = data;
			this.priority = priority;
		}}
		
		/** Methods
		 * This performs Right rotation 
		 * @return The root node of result tree
		 */
		
		public Node<E > rotateRight() {
			Node<E> rootNode = new Node<E>(data, priority);
			if(this.left!=null){
				rootNode.left = this.left.right;
				rootNode.right = this.right;
				this.priority = this.left.priority;
				this.data = this.left.data;			
				this.left = this.left.left;
				this.right = rootNode;
			}
			
			return this;
			
			}
		
		/** Methods
		 * This performs Left Rotation
		 * @return The root node of result tree
		 */
		
		public Node<E > rotateLeft(){
			Node<E> rootNode = new Node<E>(data, priority);
			if(this.right!=null) {
			
				rootNode.left = this.left;
				rootNode.right = this.right.left;
				this.priority = this.right.priority;
				this.data = this.right.data;				
				this.right = this.right.right;
				this.left = rootNode;
			}
			
			return this;
		
		}}
	
	// Data fields are here 
	
	private Node<E> root;
	private Random priorityGenerator;
	
	/**
	 * An empty constructor
	 * This creates an empty Treap
	 */
	
	public Treap() {
		priorityGenerator = new Random();
		root = null;
	}
	
	/**
	 * Constructor with a parameter, in this case one parameter
	 * Initializes priorityGenerator using new Random(seed)
	 * @param seed
	 */
	
	public Treap(long seed) {
		priorityGenerator = new Random(seed);
		root = null;
	}
	
	/**
	 * This function takes in the key and creates a priority and perform insertion in the tree
	 * @param key, the key which will be added to the respective tree
	 * @return In this case, Boolean value of addition operation
	 */
	
	boolean add(E key) {
		return add(key, priorityGenerator.nextInt());
	}
	
	/**
	 * This function takes in the key and creates a priority. Also, inserts it and arranges the tree
	 * @param key, the key which will be added to the tree
	 * @param priority, or priority on which the max-heap will be arranged
	 * @return In this case, Boolean value of addition operation
	 */
	
	boolean add(E key, int priority) {
	
		Node<E > newNode = new Node<E>(key, priority);
		Node<E > tempRoot = root;
		Stack<Node<E>> stack = new Stack<>();
		if(root == null)
		{
			root = new Node<E>(key, priority);
			return true;
		}
		if(find(key)) {
			return false;
		}
		while(tempRoot!=null)
		{
			stack.push(tempRoot);
			if((key.compareTo(tempRoot.data)) < 0)
				tempRoot = tempRoot.left;
			else
				tempRoot = tempRoot.right;
		}
		
		if((key.compareTo((E)stack.peek().data)) < 0)	
			stack.peek().left = newNode;
		else
		{		
			stack.peek().right = newNode;			
		}
		stack.push(newNode);
		reheap(stack);
		return true;
	}
	
	/**Methods
	 * Helper function to arrange tree as per max-heap
	 * @param stack Arranging Stack of nodes
	 */
	
	private void reheap(Stack<Node<E>> stack){
		Node<E> child = stack.pop();
		Node<E> parent = stack.pop();
		while(parent!= null && child.priority > parent.priority)
		{
			if((child.data.compareTo(parent.data)) > 0)
				parent.rotateLeft();
			else
				parent.rotateRight();
			if(!stack.isEmpty())
				parent = stack.pop();
			else
				parent = null;
		}}
    
	/**
	 * This function deletes the node with given key
	 * @param key The key to be deleted from the node
	 * @return boolean value for the deletion of node
	 */
	
	public boolean delete(E key){
		Node<E> nodeFound = null ;
		Node<E> lastParent = null;
		Node<E> tempRoot = null;
		if (find(key) == false || root==null)
			return false;
		else
		{
			while(root!= null)
			{
				if(key.compareTo(root.data) < 0){
					tempRoot = root;
					root = root.left;
				}
				else if((key.compareTo(root.data)) > 0){
					tempRoot = root;
					root = root.right;
				}
				else{
					nodeFound = root;
					break;
				}
			}
			while((nodeFound.right!=null)||(nodeFound.left!=null))
			{
				if(nodeFound.right==null )
				{
					lastParent = nodeFound.rotateRight();
					nodeFound = lastParent.right;
				}
				else if(nodeFound.left == null)
				{
					lastParent = nodeFound.rotateLeft();
					nodeFound = lastParent.left;
				}
				else if (nodeFound.left.priority < nodeFound.right.priority)
				{
					lastParent = nodeFound.rotateLeft();
					nodeFound = lastParent.left;
				}
				else if(nodeFound.left.priority > nodeFound.right.priority)
				{
					lastParent = nodeFound.rotateRight();
					nodeFound = lastParent.right;
				}
			}	
			if(lastParent == null)
				lastParent = root;
			if((lastParent.left!= null)&&(lastParent.left.data.compareTo(key))==0)
				lastParent.left = null;
			else
			{
				lastParent.right = null;
			}
			return true;
			}}
	/**
	 * Finds a node with the given key in the Treap rooted
	 * @param root it takes the root node of the tree
	 * @param key it takes the key to find in the tree
	 * @return boolean value for find operation..returns true if it is found otherwise false
	 */
	
	private boolean find(Node<E> root,E key) {
		if(root==null)
			return false;
		else if((key.compareTo(root.data))>0)
			return find(root.right, key);
		else if((key.compareTo(root.data))<0)
			return find(root.left, key);
		else 
			return true;
	}
	
	/**
	 * Takes only the element to perform find 
	 * It finds a node with given key in treap
	 * It returns true if found, otherwise false will be returned instead
	 * @param key, The key to be found in the tree
	 * @return boolean value for the find operation
	 */
	
	public boolean find(E key) {
		if(key == null) {
			throw new NullPointerException("Key can't be null");
		}
		return find(root, key);
	}
	
	/**
	 * Returns string value of tree
	 * @return String value of the tree
	 */
	
	public String toString() {
		StringBuilder strbuild = new StringBuilder();
		return getPreOrderTraverse(root, 1, strbuild);
	}
	
	/**
	 * Gets the tree in the pre-order form
	 * @param node Takes the root as a node
	 * @param depth Takes the level of the starting node/root
	 * @param sb StringBuilder object to build a string for the tree
	 * @return
	 */
	
	private String getPreOrderTraverse(Node<E>node, int depth, StringBuilder strbuild) {
		for(int i=1; i< depth; i++) {
			strbuild.append("  ");
		}		
		if(node==null)
			strbuild.append("null\n");
		else {
			strbuild.append("(key = "+node.data+", priority = "+node.priority+")\n\n");
			getPreOrderTraverse(node.left, depth + 1,strbuild);
			getPreOrderTraverse(node.right, depth + 1,strbuild);
		}
		return strbuild.toString();
		}
    /**
	 * The main() method
	 * @param args
	 */
	
	public static void main(String[] args) {
		
		System.out.println("TEST CASE 1\n");
		Treap<Integer> testTree = new Treap < Integer >();
		
		// Test case already given
		
		testTree.add(4,19);
		testTree.add(2,31);
		testTree.add(6,70);
		testTree.add(1,84);
		testTree.add(3,12);
		testTree.add(5,83);
		testTree.add(7,26);
		System.out.println(testTree.toString());
		System.out.println("Find node with key '6'? "+ testTree.find(6));
		System.out.println("Find node with key '16'? "+ testTree.find(16));
		System.out.println("Delete when key '6' exists, so the boolean result is: "+ testTree.delete(6));
		System.out.println("Delete when key '16' exists, so the boolean result is: "+ testTree.delete(16));
		System.out.println("Find node with key '6' after deleting? "+ testTree.find(6));
		System.out.println("\n\n\nTEST CASE 2\n\n");
		Treap<Character> testTree2 = new Treap<Character>();
		
		// Test case already given
		
		testTree2.add('p',99);
		testTree2.add('g',80);
		testTree2.add('u',75);
		testTree2.add('a',60);
		testTree2.add('j',65);
		testTree2.add('r',40);
		testTree2.add('z',47);
		testTree2.add('w',32);
		testTree2.add('v',21);
		testTree2.add('x',25);
		System.out.println(testTree2.toString());
		testTree2.add('i',93);
		System.out.println("Delete when key with 'z' exists and so the boolean result is then: "+ testTree2.delete('z') +"\n");
		System.out.println(testTree2.toString());
		
		}
	}
