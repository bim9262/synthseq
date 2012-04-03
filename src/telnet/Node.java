package telnet;

import java.util.ArrayList;

public class Node {
	private Character c;
	private Node parrent;
	private ArrayList<Node> branch = new ArrayList<Node>();

	public Node() {
	}

	private Node(Node parrent, Character c) {
		this.c = c;
		this.parrent = parrent;
	}

	public Node getParent() {
		return parrent;
	}

	public Node addNode(Node parrent, Character c) {
		Node toReturn = getNode(c);
		if (toReturn == null) {
			toReturn = new Node(parrent, c);
			branch.add(toReturn);
		}
		return toReturn;

	}

	public Node getNode(Character c) {
		for (Node n : branch) {
			if (n.c.equals(c)) {
				return n;
			}
		}
		return null;
	}

	public Node getNode(int i) {
		return branch.get(i);
	}

	public int branchCount() {
		return branch.size();
	}

	public char getCharacter() {
		return c;
	}

}
