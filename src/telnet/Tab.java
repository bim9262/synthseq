package telnet;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Tab {

	private static Tab instance = new Tab();
	private Trie trie = new Trie();

	private Tab() {
		Scanner f;
		try {
			f = new Scanner(new FileReader("src/Clojure_Bindings.clj"));
			while (f.hasNextLine()) {
				String word = f.nextLine();
				if (word.startsWith("def", 1))
					trie.addWord(word.split(" ")[1]);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Tab getInstance() {
		return instance;
	}

	public String autoComplete(String s, int caretPos) {
		SuperString string = findWordAndPos(s, caretPos);
		if (string != null) {
			return s.substring(0, string.end)
					+ trie.autoComplete(string.string)
					+ s.substring(string.end, s.length());
		}
		return s;
	}

	public String suggestions(String s, int caretPos) {
		SuperString string = findWordAndPos(s, caretPos);
		String toReturn = "";
		if (string != null) {
			toReturn = "Sugestions for " + string.string + ": ";
			ArrayList<String> posibilities = trie.getMutations(string.string);
			if (posibilities.size() == 0) {
				toReturn += "NONE FOUND";
			} else {
				for (int i = 0; i < posibilities.size(); i++) {
					toReturn += posibilities.get(i)
							+ (i != posibilities.size() - 1 ? ", " : "");
				}
			}
		}
		return toReturn;
	}

	private SuperString findWordAndPos(String s, int caretPos) {
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

	private boolean containsSeparator(String s, int caretPos) {
		String toCheck = " \n().";
		for (char c : toCheck.toCharArray()) {
			if (s.charAt(caretPos) == c)
				return true;
		}
		return false;
	}

	private class SuperString {
		public String string;
		public int end;

		public SuperString(String string, int end) {
			this.string = string;
			this.end = end;
		}
	}
}
