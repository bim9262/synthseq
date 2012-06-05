package common;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TrieTest {

	Trie<Character> trie;
	ArrayList<ArrayList<Character>> list;

	@Before
	public void setUp() throws Exception {
		trie = new Trie<Character>();
		list = new ArrayList<ArrayList<Character>>();
	}

	@Test
	public void testAddAll() {
		listAdd("test");
		assertTrue(trie.addAll(list));
		listAdd("test2");
		assertTrue(trie.addAll(list));
		listAdd("test");
		assertFalse(trie.addAll(list));

	}

	@Test
	public void testAddAndContains() {
		assertTrue(trieAdd("test2"));
		assertFalse(trieContains("test"));
		assertTrue(trieAdd("test"));
		assertTrue(trieContains("test"));
		assertFalse(trieAdd("test"));
		assertFalse(trieAdd(""));
		assertFalse(trieContains(""));
	}


	@Test
	public void testContainsAll() {
		listAdd("test");
		listAdd("test2");
		trie.addAll(list);
		assertTrue(trie.containsAll(list));
		trieAdd("test3");
		assertTrue(trie.containsAll(list));
		listAdd("test4");
		assertFalse(trie.containsAll(list));
	}


	@Test
	public void testIsEmptyAndClear() {
		assertTrue(trie.isEmpty());
		trieAdd("test");
		assertFalse(trie.isEmpty());
		trie.clear();
		assertTrue(trie.isEmpty());

	}

	@Test
	public void testRemove() {
		assertFalse(trieRemove("test"));
		trieAdd("test");
		trieAdd("test2");
		trieAdd("abc");
		assertTrue(trieRemove("test"));
		assertFalse(trieContains("test"));
		assertTrue(trieContains("test2"));
		assertTrue(trieContains("abc"));

		trieAdd("test");
		assertTrue(trieRemove("test2"));
		assertFalse(trieContains("test2"));
		assertTrue(trieContains("test"));
		assertTrue(trieContains("abc"));

		assertTrue(trieRemove("abc"));
		assertFalse(trieContains("abc"));

		trieAdd("");
		assertFalse(trieRemove(""));

	}

	@Test
	public void testRemoveAll() {

		listAdd("test");
		listAdd("test2");
		trieAdd("abc");
		assertFalse(trie.removeAll(list));
		trie.addAll(list);
		assertTrue(trie.removeAll(list));
		assertFalse(trieContains("test"));
		assertFalse(trieContains("test2"));
		assertTrue(trieContains("abc"));


	}

	@Test
	public void testRetainAll() {
		 fail("Not yet implemented");
	}

	@Test
	public void testSize() {
		 assertTrue(trie.size()==0);
		 trieAdd("test");
		 assertTrue(trie.size()==1);
		 trieAdd("test");
		 assertTrue(trie.size()==1);
		 trieRemove("test");
		 assertTrue(trie.size()==0);

		 trieAdd("");
		 assertTrue(trie.size()==0);
		 trieRemove("");
		 assertTrue(trie.size()==0);
	}

	@Test
	public void testCloneAndEquals() {
		trieAdd("test");
		Trie<Character> trie2 = trie.clone();
		assertFalse(trie==trie2);
		assertTrue(trie.equals(trie2));
		trie.clear();
		assertFalse(trie.equals(trie2));


	}

	protected void listAdd(String word){
		list.add(stringToArrayList(word));
	}

	protected boolean trieAdd(String word){
		return trie.add(stringToArrayList(word));
	}

	protected boolean trieRemove(String word){
		return trie.remove(stringToArrayList(word));
	}

	protected boolean trieContains(String word){
		return trie.contains(stringToArrayList(word));
	}

	private ArrayList<Character> stringToArrayList(String word) {
		ArrayList<Character> toReturn = new ArrayList<Character>();
		for (Character c : word.toCharArray()) {
			toReturn.add(c);
		}
		return toReturn;
	}
}
