package common;

import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Set;

public class Trie<E extends Comparable<? super E>> implements Set<ArrayList<E>> {

	private Node top;
	private Node location;
	private int size;

	public Trie() {
		clear();
	}

	public ArrayList<E> autoComplete(ArrayList<E> word) {
		ArrayList<E> toReturn = new ArrayList<E>();
		advanceLocationToNode(word);
		if (location != null) {
			while (location.getBranch().size() == 1) {
				location = location.getBranch().get(0);
				if (location.getCharacter() != null) {
					toReturn.add(location.getCharacter());
				}
			}
		}
		location = top;
		return toReturn;
	}

	public boolean locationIsTop() {
		return top == location;
	}

	public ArrayList<ArrayList<E>> getMutations(ArrayList<E> word) {
		ArrayList<ArrayList<E>> toReturn = new ArrayList<ArrayList<E>>();
		ArrayList<Node> nodes = advanceLocationToNode(word);
		nodes.remove(nodes.size() - 1);
		nodes.add(location);
		if (location != null) {
			for (ArrayList<E> combo : getPossibleCombos(nodes)) {
				combo.remove(combo.size() - 1);
				toReturn.add(combo);
			}
		}
		location = top;
		return toReturn;
	}

	private ArrayList<Node> advanceLocationToNode(ArrayList<E> word) {
		ArrayList<Node> nodes = new ArrayList<Node>();
		for (E c : word) {
			if (location != null) {
				location = location.getNode(c);
				nodes.add(location);
			}
		}
		return nodes;
	}

	private ArrayList<ArrayList<E>> getPossibleCombos(ArrayList<Node> nodes) {
		ArrayList<ArrayList<E>> toReturn = new ArrayList<ArrayList<E>>();
		for (Node n : nodes.get(nodes.size() - 1).getBranch()) {
			ArrayList<Node> newFoundTiles = new ArrayList<Node>();
			for (int i = 0; i < nodes.size(); i++)
				newFoundTiles.add(nodes.get(i));
			newFoundTiles.add(n);
			if (n.getBranch().size() == 0) {
				ArrayList<E> toAdd = new ArrayList<E>();
				for (Node nf : newFoundTiles) {
					toAdd.add(nf.getCharacter());
				}
				toReturn.add(toAdd);
			}
			for (ArrayList<E> s : getPossibleCombos(newFoundTiles)) {
				toReturn.add(s);
			}
		}
		return toReturn;
	}

	@Override
	public boolean add(ArrayList<E> word) {
		if (!contains(word)) {
			for (E c : word) {
				location = location.addNode(c);
			}
			location.addNode();
			location = top;
			size++;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean addAll(Collection<? extends ArrayList<E>> words) {
		boolean toReturn = false;
		for (ArrayList<E> word : words) {
			if (add(word)) {
				toReturn = true;
			}
		}
		return toReturn;
	}

	@Override
	public void clear() {
		top = new Node();
		location = top;
		size = 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean contains(Object word) {
		boolean toReturn = false;
		if (word instanceof ArrayList<?>) {
			advanceLocationToNode((ArrayList<E>) word);
			if (location != null) {
				toReturn = location.getNode(null) != null;
			}
			location = top;
		}
		return toReturn;
	}
	@Override
	public boolean containsAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		return top.getBranch().isEmpty();
	}

	@Override
	public Iterator<ArrayList<E>> iterator() {
		return getMutations(new ArrayList<E>()).iterator();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object word) {
		boolean toReturn = false;
		if (word instanceof ArrayList<?>) {
			ArrayList<Node> nodes = advanceLocationToNode((ArrayList<E>) word);
			if (location != null) {
				location = location.getNode(null);
				if (location != null) {
					for (int i = nodes.size() - 1; i >= 0; i--) {
						location = nodes.get(i).getParent();
						ArrayList<Node> branch = location.getBranch();
						if (branch.size() == 1) {
							branch.clear();
						} else {
							break;
						}
					}
					size--;
					toReturn = true;
				}
			}
		}
		location = top;
		return toReturn;

	}

	@Override
	public boolean removeAll(Collection<?> words) {
		boolean toReturn = false;
		for (Object s : words) {
			if (remove(s)) {
				toReturn = true;
			}
		}
		return toReturn;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	private class Node implements Comparable<Node> {
		private E c;
		private ArrayList<Node> branch = new ArrayList<Node>();
		private Node parent;

		public Node() {
		}

		public Node(E c, Node parent) {
			this.c = c;
			this.parent = parent;
		}

		public ArrayList<Node> getBranch() {
			return branch;
		}

		public Node addNode(E c) {
			Node toReturn = getNode(c);
			if (toReturn == null) {
				toReturn = new Node(c, this);
				branch.add(toReturn);
			}
			return toReturn;
		}

		public Node addNode() {
			return addNode(null);
		}

		public Node getNode(E c) {
			for (Node n : branch) {
				if ((c==null && n.c==null) || (n.c!=null && n.c.equals(c))) {
					return n;
				}
			}
			return null;
		}

		public Node getParent() {
			return parent;
		}

		public E getCharacter() {
			return c;
		}

		@Override
		public int compareTo(Node o) {
			int toReturn;
			if (o == null) {
				toReturn = -1;
			} else if (c == null && o.c == null) {
				toReturn = 0;
			} else if (c == null) {
				toReturn = 1;
			} else if (o.c == null) {
				toReturn = -1;
			} else {
				toReturn = c.compareTo(o.getCharacter());
			}
			return toReturn;
		}
	}

	/*private class BinarySearchTree<T extends Comparable<? super T>>
			implements
				Iterable<T> {
		private BinaryNode<T> root;

		public BinarySearchTree() {
			root = null;
		}

		public void add(T x) {
				root = add(x, root);
		}

		public T get(T x) {
			return elementAt(get(x, root));
		}

		public void clear() {
			root = null;
		}

		public boolean isEmpty() {
			return root == null;
		}

		public int size() {
			return root.size();
		}

		private T elementAt(BinaryNode<T> node) {
			return (node == null) ? null : node.getElement();
		}

		private BinaryNode<T> add(T x, BinaryNode<T> node) {
			if (node == null) {
				return new BinaryNode<T>(x);
			} else if (x.compareTo(node.getElement()) < 0) {
				node.setLeft(add(x, node.getLeft()));
			} else if (x.compareTo(node.getElement()) > 0) {
				node.setRight(add(x, node.getRight()));
			}

			 else {
				throw new RuntimeException(x.toString() + " already exists");
			}


			return node;
		}

		private BinaryNode<T> get(T x, BinaryNode<T> node) {
			if (node == null) {
				return null;
			} else if (x.compareTo(node.getElement()) < 0) {
				return get(x, node.getLeft());
			} else if (x.compareTo(node.getElement()) > 0) {
				return get(x, node.getRight());
			} else {
				return node;
			}
		}

		@Override
		public Iterator<T> iterator() {
			return root.toInOrderArray().iterator();
		}
	}

	private class BinaryNode<T extends Comparable<? super T>> {

		private T element;
		private BinaryNode<T> left;
		private BinaryNode<T> right;

		BinaryNode(T theElement) {
			element = theElement;
			left = null;
			right = null;
		}

		public T getElement() {
			return element;
		}

		public BinaryNode<T> getLeft() {
			return left;
		}

		public void setLeft(BinaryNode<T> value) {
			left = value;
		}

		public BinaryNode<T> getRight() {
			return right;
		}

		public void setRight(BinaryNode<T> value) {
			right = value;
		}

		public int size() {
			int size = 1;
			if (left != null) {
				size += left.size();
			}

			if (right != null) {
				size += right.size();
			}

			return size;
		}

		public ArrayList<T> toInOrderArray()
	    {
	       ArrayList<T> toReturn = new ArrayList<T>();
	        if (left != null)
	        {
	            toReturn.addAll(left.toInOrderArray());
	        }
	        toReturn.add(element);
	        if (right != null)
	        {
	         // Add a space before the next element
	            toReturn.addAll(right.toInOrderArray());
	        }

	        return toReturn;
	    }
	}
	*/
}
