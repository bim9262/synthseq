package synthseq.oscinterop;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import synthseq.clojureinterop.ClojureServer;
import synthseq.playables.readables.ReadableSound;
import synthseq.playables.readables.Variable;

public class ActionMap {
	private static HashMap<String, Bind> bindings = new HashMap<String, Bind>();

	public static void interpret(String label, float x, float y) {
		System.out.println(label);
		try {
			clojure.lang.Compiler.load(new StringReader(label));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Bind bind = bindings.get(label);
		if(bind!=null)
			bind.trigger(x, y);
	}

	public static void bindToggle(String s, final String codeDown, final String codeUp) {
		bindings.put(s, new Bind() {
			@Override
			public void trigger(float x, float y) {
				if (x == 0) {
					ClojureServer.interpretInternal(codeUp);
				} else {
					ClojureServer.interpretInternal(codeDown);
				}
			}
		});
	}

	public static void bindToggle(String s, final ReadableSound r) {
		bindings.put(s, new Bind() {
			@Override
			public void trigger(float x, float y) {
				if (x == 0)
					r.stop();
				else
					r.play();
			}
		});
	}

	public static void bindTouch(String s, final String codeDown) {
		bindings.put(s,new Bind(){
			@Override
			public void trigger(float x, float y) {
				ClojureServer.interpretInternal(codeDown);
			}});
	}

	public static void bindTouch(String s, final ReadableSound r) {
		bindings.put(s,new Bind(){
			@Override
			public void trigger(float x, float y) {
				r.play();
			}});
	}

	public static void bindSlider(String s, final Variable v) {
		bindings.put(s,new Bind(){
			@Override
			public void trigger(float x, float y) {
				v.setValue(x);
			}});
	}

	public static void bind2D(String s, final Variable vx, final Variable vy) {
		bindings.put(s,new Bind(){
			@Override
			public void trigger(float x, float y) {
				vx.setValue(x);
				vy.setValue(y);
			}});
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
