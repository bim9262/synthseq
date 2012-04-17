package synthseq.clojureinterop;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ClojureServer {
	public static PrintWriter clojureOut;
	public static void interpretInternal(String s){
		try {
			clojure.lang.Compiler.load(new StringReader(s));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void start(final int i) {
		new Thread() {
			public void run() {
				try {
					clojure.lang.Compiler.load(new FileReader(
							"src/Clojure_Bindings.clj"));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				while (true) {
					ServerSocket serv = null;
					Socket sock = null;
					try {
						serv = new ServerSocket(i);
						sock = serv.accept();
						PrintWriter output = new PrintWriter(
								sock.getOutputStream());
						BufferedReader commandInput = new BufferedReader(
								new InputStreamReader(sock.getInputStream()));
						clojureOut = output;
						clojure.lang.Compiler
								.load(new StringReader(
										"(use 'Clojure-Bindings)"
												+ "(def *out* "
												+ "synthseq.clojureinterop.ClojureServer/clojureOut)"));
						while (true) {
							Object out = null;
							try {
								out = clojure.lang.Compiler
										.load(new StringReader(commandInput
												.readLine()));
								
							} catch (Exception e) {
								out = e;
							}
							output.println(out);
							output.flush();
						}
					} catch (java.net.BindException e) {
						System.out.println("Server already open. Exiting.");
						System.exit(1);
					} catch (Exception e) {
						e.printStackTrace();
						try {
							serv.close();
							sock.close();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		}.start();
	}
}
