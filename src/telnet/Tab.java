package telnet;

import java.util.List;
import java.util.ArrayList;
import common.Trie;

public abstract class Tab {

	protected static Trie<Character> trie = new Trie<Character>();

	public static void addDefinition(String def) {
		trie.add(stringToArrayList(def));
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

	protected static ArrayList<Character> stringToArrayList(String word) {
		ArrayList<Character> toReturn = new ArrayList<Character>();
		for (Character c : word.toCharArray()) {
			toReturn.add(c);
		}
		return toReturn;
	}

	protected static ArrayList<String> multiArrayListToString(
			List<ArrayList<Character>> pos) {
		ArrayList<String> toReturn = new ArrayList<String>();
		for (ArrayList<Character> w : pos) {
			toReturn.add(arrayListToString(w));
		}
		return toReturn;
	}

	protected static String arrayListToString(ArrayList<Character> w) {
		String word = "";
		for (Character c : w) {
			word += c;
		}
		return word;
	}
}
