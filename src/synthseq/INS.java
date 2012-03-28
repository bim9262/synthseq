package synthseq;

public class INS {
	public static void main(String[] args) throws Exception {
		ClojureListener.start(9000);
		OSCListener.start(new DefaultActionMap(),8000);
	}
}
