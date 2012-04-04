package telnet;

import java.util.ArrayList;

public class Node {
	private Character c;
	private ArrayList<Node> branch = new ArrayList<Node>();

	public Node() {
	}

	private Node(Character c) {
		this.c = c;
	}

	public ArrayList<Node> getBranch() {
		return branch;
	}

	public Node addNode(Character c) {
		Node toReturn = getNode(c);
		if (toReturn == null) {
			toReturn = new Node(c);
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
