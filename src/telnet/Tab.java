package telnet;

public abstract class Tab {

	protected static StringTrie trie = new StringTrie();

	public static void addDefinition(String def) {
		trie.add(def);
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
			s = s.substring(front, end);
			return new SuperString(s, end);
		}
		return null;
	}

	private static boolean containsSeparator(String s, int caretPos) {
		String toCheck = " \n\t().";
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
}
