package telnet;

import java.io.FileNotFoundException;
import java.io.FileReader;
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
			System.out.print("\n");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		trie.addWord(".play");
	}

	public static Tab getInstance() {
		return instance;
	}

	public String autoComplete(String s, int caretPos) {
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
			String autoComplete = trie.autoComplete(s.substring(front, end));
			System.out.println("\"" + s.substring(front, end) + "\"" + front + ", " + end + " \"" + autoComplete + "\"");
		return s.substring(0, end) + autoComplete
				+ s.substring(end, s.length());
		}
		return s;
	}
	
	private boolean containsSeparator(String s, int caretPos){
		String toCheck = " \n";
		for (char c : toCheck.toCharArray()){
			if (s.charAt(caretPos) == c) return true;
		}
		return false;
	}
}
