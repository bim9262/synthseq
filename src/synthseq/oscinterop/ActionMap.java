package synthseq.oscinterop;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import synthseq.playables.readables.ReadableSound;
import synthseq.playables.readables.Variable;

public class ActionMap {
	private static HashMap<String, Bind> bindings = new HashMap<String, Bind>();
	private static boolean printNext = false;
	public static void interpret(String label, float x, float y, long time) {
		Bind bind = bindings.get(label);
		if(printNext){
			printNext = false;
			try {
				boolean isBound = bind!=null;
				clojure.lang.Compiler.load(new StringReader("(println \""+label+" "+x+" "+y+" "+isBound+"\")"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (bind != null)
			try{
			bind.trigger(x, y, time);
			}catch(Exception e){
				e.printStackTrace();
			}
	}
	public static void printNext(){
		printNext = true;
	}

	public static void bindToggle(String s, final Runnable codeDown,
			final Runnable codeUp) {
		bindings.put(s, new Bind() {
			@Override
			public void trigger(float x, float y, long t) {
				if (x == 0) {
					new Thread(codeUp).start();
				} else {
					new Thread(codeDown).start();
				}
			}
		});
	}

	public static void bindToggle(String s, final ReadableSound r) {
		bindings.put(s, new Bind() {
			@Override
			public void trigger(float x, float y, long t) {
				if (x == 0)
					r.stop();
				else
					r.play();
			}
		});
	}

	public static void bindTouch(String s, final Runnable codeDown) {
		bindings.put(s, new Bind() {
			@Override
			public void trigger(float x, float y, long t) {
				if (x != 0.0)
					new Thread(codeDown).start();
			}
		});
	}

	public static void bindTouch(String s, final ReadableSound r) {
		bindings.put(s, new Bind() {
			@Override
			public void trigger(float x, float y, long t) {
				if (x != 0.0)
					r.play();
			}
		});
	}

	public static void bindSlider(String s, final Variable v) {
		bindings.put(s, new Bind() {
			@Override
			public void trigger(float x, float y, long t) {
				v.setValue(x);
			}
		});
	}

	public static void bind2D(String s, final Variable vx, final Variable vy) {
		bindings.put(s, new Bind() {
			@Override
			public void trigger(float x, float y, long t) {
				vx.setValue(x);
				vy.setValue(y);
			}
		});
	}

	public static Collection<String> generateBindings(String s,
			Collection<Integer> xVals, Collection<Integer> yVals) {
		ArrayList<String> strings = new ArrayList<String>();
		if (s.contains("!1") && s.contains("!2")) {
			for (int x : xVals)
				for (int y : yVals)
					strings.add(s.replace("!1", "" + x).replace("!2", "" + y));
		} else if (s.contains("!1")) {
			for (int x : xVals)
				strings.add(s.replace("!1", "" + x));
		} else {
			strings.add(s);
		}
		return strings;
	}
}
