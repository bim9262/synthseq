package synthseq;

import synthseq.clojureinterop.ClojureListener;
import synthseq.oscserver.DefaultActionMap;
import synthseq.oscserver.OSCListener;

public class Main {
	public static void main(String[] args) throws Exception {
		ClojureListener.start(9000);
		OSCListener.start(new DefaultActionMap(),8000);
	}
}
