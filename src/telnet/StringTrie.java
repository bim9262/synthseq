package telnet;

import java.util.ArrayList;
import java.util.List;

import common.Trie;

public class StringTrie extends Trie<Character> {

	public String autoComplete(String string) {
		return arrayListToString(autoComplete(stringToArrayList(string)));
	}

	public ArrayList<String> getMutations(String string) {
		return multiArrayListToString(getMutations(stringToArrayList(string)));
	}

	public boolean add(String def) {
		return add(stringToArrayList(def));
	}

	private ArrayList<Character> stringToArrayList(String word) {
		ArrayList<Character> toReturn = new ArrayList<Character>();
		for (Character c : word.toCharArray()) {
			toReturn.add(c);
		}
		return toReturn;
	}
	
	private ArrayList<String> multiArrayListToString(
			List<ArrayList<Character>> pos) {
		ArrayList<String> toReturn = new ArrayList<String>();
		for (ArrayList<Character> w : pos) {
			toReturn.add(arrayListToString(w));
		}
		return toReturn;
	}

	private String arrayListToString(ArrayList<Character> w) {
		String word = "";
		for (Character c : w) {
			word += c;
		}
		return word;
	}

}
