package telnet;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class ClojureTab extends Tab {
	static {
		Scanner f;
		try {
			f = new Scanner(new FileReader("src/Clojure_Bindings.clj"));
			while (f.hasNextLine()) {
				String word = f.nextLine();
				if (word.startsWith("def", 1))
					addDefinition(word.split(" ")[1]);
			}
			f = new Scanner(new FileReader("src/Builtin_Clojure_Vars"));
			while (f.hasNext()) {
				addDefinition(f.next());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public String autoComplete(String s, int caretPos) {
		SuperString string = findWordAndPos(s, caretPos);
		if (string != null) {
			String word = trie.autoComplete(string.string);
			return s.substring(0, string.end)
					+ word
					+ s.substring(string.end, s.length());
		}
		return s;
	}

	public String suggestions(String s, int caretPos) {
		String toReturn = "";
		SuperString str = findWordAndPos(s, caretPos);
		if (str != null) {
			s = str.string;
			toReturn = "Sugestions for " + s + ": ";
			ArrayList<String> posibilities = trie.getMutations(s);
			if (posibilities.size() == 0) {
				toReturn += "NONE FOUND";
			} else if (posibilities.size() == 1
					&& posibilities.get(0).equals(s)) {
				toReturn = "(doc " + s + ")";
				Telnet.getSocketInput().println(toReturn);
			} else {
				for (int i = 0; i < posibilities.size(); i++) {
					toReturn += posibilities.get(i)
							+ (i != posibilities.size() - 1 ? ", " : "");
				}
			}
		}
		return toReturn;
	}
}
