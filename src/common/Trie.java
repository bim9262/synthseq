package common;

import java.util.List;
import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Set;
import static java.util.Collections.binarySearch;

public class Trie<T extends List<E>, E> implements Set<T>, Cloneable {

	private Node top;
	private Node location;

	public Trie() {
		clear();
	}

	public ArrayList<E> autoComplete(T word) {
		ArrayList<E> toReturn = new ArrayList<E>();
		advanceLocationToNode(word);
		if (location != null) {
			while (location.size() == 1) {
				location = location.getNode(0);
				if (location.getElement() != null) {
					toReturn.add(location.getElement());
				}
			}
		}
		location = top;
		return toReturn;
	}

	public List<ArrayList<E>> getMutations(T word) {
		ArrayList<Node> nodes = advanceLocationToNode(word);
		return generateMutations(nodes);
	}

	private List<ArrayList<E>> getAllMutations() {
		List<Node> nodes  = new ArrayList<Node>();
		nodes.add(top);
		return generateMutations(nodes);
	}

	private List<ArrayList<E>> generateMutations(List<Node> nodes) {
		List<ArrayList<E>> toReturn = new ArrayList<ArrayList<E>>();
		if (location != null) {
			for (ArrayList<E> combo : getPossibleCombos(nodes)) {
				ArrayList<E> toAdd = new ArrayList<E>();
				for (E e : combo) {
					if (e != null) {
						toAdd.add(e);
					}
				}
				toReturn.add(toAdd);
			}
		}
		location = top;
		return toReturn;
	}

	private ArrayList<Node> advanceLocationToNode(T word) {
		ArrayList<Node> nodes = new ArrayList<Node>();
		for (E c : word) {
			if (location != null) {
				location = location.getNode(c);
				nodes.add(location);
			}
		}
		return nodes;
	}

	private ArrayList<ArrayList<E>> getPossibleCombos(List<Node> nodes) {
		ArrayList<ArrayList<E>> toReturn = new ArrayList<ArrayList<E>>();
		for (Node n : nodes.get(nodes.size() - 1)) {
			ArrayList<Node> newFoundTiles = new ArrayList<Node>();
			for (int i = 0; i < nodes.size(); i++)
				newFoundTiles.add(nodes.get(i));
			newFoundTiles.add(n);
			if (n.isEmpty()) {
				ArrayList<E> toAdd = new ArrayList<E>();
				for (Node nf : newFoundTiles) {
					toAdd.add(nf.getElement());
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
	public void clear() {
		top = new Node();
		location = top;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean contains(Object word) {
		boolean toReturn = false;
		if (word instanceof List<?>) {
			advanceLocationToNode((T) word);
			if (location != null) {
				toReturn = location.getNode(null) != null;
			}
			location = top;
		}
		return toReturn;
	}

	@Override
	public boolean containsAll(Collection<?> words) {
		for (Object word : words) {
			if (!contains(word)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isEmpty() {
		return top.isEmpty();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object word) {
		boolean toReturn = false;
		if (word instanceof List<?>) {
			int nodeSize = advanceLocationToNode((T) word).size();
			if (location != null) {
				location = location.getNode(null);
				if (location != null) {
					for (int i = 0; i <= nodeSize; i++) {
						Node parent = location.getParent();
						if (location.isEmpty()) {
							parent.remove(location);
						} else {
							break;
						}
						location = parent;
					}
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
	public int size() {
		return getAllMutations().size();
	}

	@Override
	public Object[] toArray() {
		return getAllMutations().toArray();
	}

	@Override
	public Trie<T, E> clone() {
		Trie<T, E> toReturn = new Trie<T, E>();
		for (T e : this) {
			toReturn.add(e);
		}
		return toReturn;
	}


	@Override
	public boolean equals(Object o) {
		return o instanceof Trie<?, ?>
				&& getAllMutations().equals(((Trie<?, ?>) o).getAllMutations());
	}


	private class Node implements Comparable<Node>, Iterable<Node> {
		private E c;
		private MyArrayList branch = new MyArrayList();
		private Node parent;

		public Node() {
		}

		public boolean remove(Node location) {
			return branch.remove(location);
		}

		public boolean isEmpty() {
			return branch.isEmpty();
		}

		public int size() {
			return branch.size();
		}

		public Node(E c, Node parent) {
			this.c = c;
			this.parent = parent;
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

		public Node getNode(int i){
			return branch.get(i);
		}

		public Node getNode(E c) {
			return branch.get(c);
		}

		public Node getParent() {
			return parent;
		}

		public E getElement() {
			return c;
		}

		@SuppressWarnings("unchecked")
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
				toReturn = ((Comparable<E>) c).compareTo(o.getElement());
			}
			return toReturn;
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object o) {
			boolean toReturn = false;
			if (o instanceof Trie.Node) {
				toReturn = compareTo((Node) o) == 0;
			} else if (o instanceof Comparable<?>) {
				toReturn = compareTo(new Node((E) o, null)) == 0;
			}
			return toReturn;
		}

		@Override
		public Iterator<Node> iterator() {
			return branch.iterator();
		}
	}

	@SuppressWarnings("serial")
	private class MyArrayList extends ArrayList<Node> {

		public boolean add(Node n) {
			boolean toReturn = contains(n.c);
			if (!toReturn) {
				if (n != null && n.c != null && n.c instanceof Comparable<?>) {
						add(-1 * binarySearch(this, n) - 1, n);

				} else {
					super.add(n);
				}
			}
			return toReturn;
		}

		@SuppressWarnings("unchecked")
		public boolean contains(Object c) {
			return get((E) c) != null;
		}

		public Node get(E c) {
			if (c instanceof Comparable<?>) {
				int index = binarySearch(this, new Node(c, null));
				if (index >= 0) {
					return get(index);
				}
			} else {
				for (Node n : this) {
					if ((c == null && n.c == null)
							|| (n.c != null && n.c.equals(c))) {
						return n;
					}
				}
			}
			return null;
		}

	}

	@Override
	public boolean add(T word) {
		if (!word.isEmpty() && !contains(word)) {
			for (E c : word) {
				location = location.addNode(c);
			}
			location.addNode();
			location = top;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean addAll(Collection<? extends T> words) {
		boolean toReturn = false;
		for (T word : words) {
			if (add(word)) {
				toReturn = true;
			}
		}
		return toReturn;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<T> iterator() {
		return (Iterator<T>) getAllMutations().iterator();

	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean retainAll(Collection<?> words) {
		boolean toReturn = equals(words) && !words.containsAll(this);
		if (!toReturn){
			clear();
			addAll((Collection<? extends T>) words);
		}
		return toReturn;
	}

	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] a) {
		return getAllMutations().toArray(a);

	}

}
