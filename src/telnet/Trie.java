package telnet;

import java.util.ArrayList;

public class Trie {
	private Node top;
	private Node location;

	/**
	 * Constructs a new Trie
	 */
	public Trie() {
		top = new Node();
		location = top;
	}

	public void addWord(String word) {
		for (Character c : word.toCharArray()) {
			location = location.addNode(location, c);
		}
		location = top;
	}
	
	public String autoComplete(String word){
		String toReturn = "";
		for (Character c : word.toCharArray()) {
			location = location.getNode(c);
		}
		while (location.branchCount()==1){
			location = location.getNode(0);
			toReturn += location.getCharacter();
		}
		return toReturn;
	}

	public void pop() {
		location = location.getParent();
	}

	
}
