package synthseq;


public class INS {
	public static void main(String[] args) throws Exception {
		/*while(true){
		System.out.println(clojure.lang.Compiler.load(new StringReader(new Scanner(System.in).nextLine())));
		}*/
		ClojureListener.start(9000);
		OSCListener.start(new DefaultActionMap(),8000);
	}
}
