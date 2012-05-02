package synthseq;

import telnet.ClojureTab;
import telnet.ManagedFile;
import synthseq.clojureinterop.ClojureServer;
import synthseq.oscinterop.OSCServer;
import telnet.Telnet;

public class Main {
	public static void main(String[] args) throws Exception {
		ClojureServer.start(9000);
		OSCServer.start(8000);

		new Telnet("localhost", 9000, new ManagedFile("Demo Scripts", ".clj",
				"Clojure Scipt"), new ClojureTab());
	}

}
