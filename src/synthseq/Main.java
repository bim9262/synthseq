package synthseq;

import telnet.Tab;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import telnet.ManagedFile;
import synthseq.clojureinterop.ClojureServer;
import synthseq.oscinterop.OSCServer;
import telnet.Telnet;

public class Main {
	public static void main(String[] args) throws Exception {
		ClojureServer.start(9000);
		OSCServer.start(8000);

		Scanner f;
		try {
			f = new Scanner(new FileReader("src/Clojure_Bindings.clj"));
			while (f.hasNextLine()) {
				String word = f.nextLine();
				if (word.startsWith("def", 1))
					Tab.addDefinition(word.split(" ")[1]);
			}
			f = new Scanner(new FileReader("src/Builtin_Clojure_Vars"));
			while (f.hasNext()) {
				Tab.addDefinition(f.next());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		new Telnet("localhost", 9000, new ManagedFile("Demo Scripts", ".clj",
				"Clojure Scipt"));
	}

}
