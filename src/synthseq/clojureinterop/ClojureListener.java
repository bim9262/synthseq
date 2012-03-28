package synthseq.clojureinterop;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ClojureListener {

	public static void start(int i) {
		try {
			clojure.lang.Compiler.loadFile("src/Clojure_Bindings.clj");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		while (true) {
			ServerSocket serv = null;
			Socket sock = null;
			try {
				serv = new ServerSocket(i);
				sock = serv.accept();
				PrintWriter output = new PrintWriter(sock.getOutputStream());
				BufferedReader commandInput = new BufferedReader(
						new InputStreamReader(sock.getInputStream()));
				while (true) {
					Object out;
					try{
						out = clojure.lang.Compiler.load(new StringReader(
					
							commandInput.readLine()));
					}catch(Exception e){
						out = e;
					}
					output.println(out);
					output.flush();
				}
			} catch (Exception e) {
				e.printStackTrace();
				try{
				serv.close();
				sock.close();
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}
	}
}
