package synthseq;


import synthseq.clojureinterop.ClojureServer;
import synthseq.oscinterop.DefaultActionMap;
import synthseq.oscinterop.OSCServer;
import telnet.Telnet;

public class Main {
	public static void main(String[] args) throws Exception {
		
		ClojureServer.start(9000);
		OSCServer.start(new DefaultActionMap(), 8000);
		new Telnet();
	}
}
