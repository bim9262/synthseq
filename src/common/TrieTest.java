package common;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.fail;

public class TrieTest {

	Trie<Character> trie;
	ArrayList<ArrayList<Character>> list;

	@Before
	public void setUp() throws Exception {
		trie = new Trie<Character>();
		list = new ArrayList<ArrayList<Character>>();
	}

	 @Test
	public void testAutoComplete() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMutations() {
		fail("Not yet implemented");
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
	}


	@Test
	public void testContainsAll() {
		 fail("Not yet implemented");
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
		assertTrue(trieRemove(""));
		assertFalse(trieContains(""));


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
	public void testToArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testToArrayTArray() {
		 fail("Not yet implemented");
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

	protected void assertTrue(boolean b){
		 org.junit.Assert.assertTrue(b);
		 org.junit.Assert.assertTrue(trie.locationIsTop());
	}

	protected void assertFalse(boolean b){
		 org.junit.Assert.assertFalse(b);
		 org.junit.Assert.assertTrue(trie.locationIsTop());
	}


	private ArrayList<Character> stringToArrayList(String word) {
		ArrayList<Character> toReturn = new ArrayList<Character>();
		for (Character c : word.toCharArray()) {
			toReturn.add(c);
		}
		return toReturn;
	}

	private ArrayList<String> multiArrayListToString(
			ArrayList<ArrayList<Character>> pos) {
		ArrayList<String> toReturn = new ArrayList<String>();
		for (ArrayList<Character> w : pos) {
			toReturn.add(arrayListToString(w));
		}
		return toReturn;
	}

	private String arrayListToString(ArrayList<Character> w) {
		String word = "";
		for (Character c : w) {
			word += c;
		}
		return word;
	}
}
