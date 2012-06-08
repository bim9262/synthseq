package common;

import java.util.List;
import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Set;
import static java.util.Collections.binarySearch;

public class Trie<E> implements Set<List<E>>, Cloneable {

	private Node top;
	private Node location;

	public Trie() {
		clear();
	}

	public ArrayList<E> autoComplete(List<E> word) {
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

	public List<ArrayList<E>> getMutations(List<E> word) {
		ArrayList<Node> nodes = advanceLocationToNode(word);
		return generateMutations(nodes);
	}

	private List<ArrayList<E>> getAllMutations() {
		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.add(top);
		return generateMutations(nodes);
	}

	private List<ArrayList<E>> generateMutations(ArrayList<Node> nodes) {
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

	private ArrayList<Node> advanceLocationToNode(List<E> word) {
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
			advanceLocationToNode((List<E>) word);
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
			int nodeSize = advanceLocationToNode((List<E>) word).size();
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
	public Trie<E> clone() {
		Trie<E> toReturn = new Trie<E>();
		for (List<E> e : this) {
			toReturn.add(e);
		}
		return toReturn;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof Trie<?>
				&& getAllMutations().equals(((Trie<?>) o).getAllMutations());
	}

	private class Node implements Comparable<Node>, Iterable<Node> {
		private E c;
		private ArrayList<Node> branch = new ArrayList<Node>();
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
				add(toReturn);
			}
			return toReturn;
		}

		public boolean add(Node n) {
			boolean toReturn = contains(n.c);
			if (!toReturn) {
				if (n != null && n.c != null && n.c instanceof Comparable<?>) {
					branch.add(-1 * binarySearch(branch, n) - 1, n);

				} else {
					branch.add(n);
				}
			}
			return toReturn;
		}

		public Node addNode() {
			return addNode(null);
		}

		public Node getNode(int i) {
			return branch.get(i);
		}

		public Node getNode(E c) {
			Node searchNode = new Node(c, null);
			if (c instanceof Comparable<?>) {
				int index = binarySearch(branch, searchNode);
				if (index >= 0) {
					return branch.get(index);
				}
			} else {
				for (Node n : this) {
					if (n.equals(searchNode)) {
						return n;
					}
				}
			}
			return null;
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

		public boolean contains(E c) {
			return getNode(c) != null;
		}

		@Override
		public Iterator<Node> iterator() {
			return branch.iterator();
		}
	}



	@Override
	public boolean add(List<E> word) {
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
	public boolean addAll(Collection<? extends List<E>> words) {
		boolean toReturn = false;
		for (List<E> word : words) {
			if (add(word)) {
				toReturn = true;
			}
		}
		return toReturn;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<List<E>> iterator() {
		return (Iterator<List<E>>) ((List<E>)getAllMutations()).iterator();

	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean retainAll(Collection<?> words) {
		boolean toReturn = equals(words) && !words.containsAll(this);
		if (!toReturn) {
			clear();
			addAll((Collection<? extends List<E>>) words);
		}
		return toReturn;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return getAllMutations().toArray(a);
	}


}
