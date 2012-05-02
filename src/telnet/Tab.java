package telnet;

import java.util.ArrayList;

public abstract class Tab {

	protected static Trie trie = new Trie();

	public static void addDefinition(String def){
		trie.addWord(def);
	}

	abstract String autoComplete(String s, int caretPos);

	abstract String suggestions(String s, int caretPos);

	protected static SuperString findWordAndPos(String s, int caretPos) {
		caretPos--;
		if (s.length() != 0 && containsSeparator(s, caretPos)) {
			caretPos--;
		} else if (caretPos > 0) {

			int front;
			int end;
			do {
				front = caretPos--;
			} while (caretPos != -1 && !containsSeparator(s, caretPos));
			do {
				end = caretPos++;
			} while (caretPos != s.length() && !containsSeparator(s, caretPos));
			end++;
			return new SuperString(s.substring(front, end), end);
		}
		return null;
	}

	private static boolean containsSeparator(String s, int caretPos) {
		String toCheck = " \n().";
		for (char c : toCheck.toCharArray()) {
			if (s.charAt(caretPos) == c)
				return true;
		}
		return false;
	}

	protected static class SuperString {
		public String string;
		public int end;

		public SuperString(String string, int end) {
			this.string = string;
			this.end = end;
		}
	}

	protected static class Trie {
		private Node top;
		private Node location;

		public Trie() {
			top = new Node();
			location = top;
		}

		public void addWord(String word) {
			for (Character c : (word + "\r").toCharArray()) {
				location = location.addNode(c);
			}
			location = top;
		}

		public String autoComplete(String word) {
			String toReturn = "";
			for (Character c : word.toCharArray()) {
				if (location != null) {
					location = location.getNode(c);
				}
			}
			if (location != null) {
				while (location.branchCount() == 1) {
					location = location.getNode(0);
					toReturn += location.getCharacter();
				}
			}
			location = top;
			return toReturn.replaceAll("\r+$", "");
		}

		public ArrayList<String> getMutations(String word) {
			ArrayList<String> toReturn = new ArrayList<String>();
			for (Character c : word.toCharArray()) {
				if (location != null) {
					location = location.getNode(c);
				}
			}
			if (location != null) {
				ArrayList<Node> nodes = new ArrayList<Node>();
				nodes.add(location);
				for (String s : getPossibleCombos(nodes)) {
					toReturn.add(word + s.substring(1, s.length() - 1));
				}
			}
			location = top;
			return toReturn;
		}

		private ArrayList<String> getPossibleCombos(ArrayList<Node> nodes) {
			ArrayList<String> toReturn = new ArrayList<String>();
			for (Node n : nodes.get(nodes.size() - 1).getBranch()) {
				ArrayList<Node> newFoundTiles = new ArrayList<Node>();
				for (int i = 0; i < nodes.size(); i++)
					newFoundTiles.add(nodes.get(i));
				newFoundTiles.add(n);
				if (n.branchCount() == 0) {
					String toAdd = "";
					for (Node nf : newFoundTiles) {
						toAdd += nf.getCharacter();
					}
					toReturn.add(toAdd);
				}
				for (String s : getPossibleCombos(newFoundTiles)) {
					toReturn.add(s);
				}
			}
			return toReturn;
		}

		private class Node {
			private Character c;
			private ArrayList<Node> branch = new ArrayList<Node>();

			public Node() {
			}

			public Node(Character c) {
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
	}
}
