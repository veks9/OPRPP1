package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ArrayIndexedCollectionTest {

	@Test
	public void testConstructor() {
		ArrayIndexedCollection col1 = new ArrayIndexedCollection();
		ArrayIndexedCollection col2 = new ArrayIndexedCollection(4);
		//ArrayIndexedCollection col2a = new ArrayIndexedCollection(0);
		
		assertTrue(col1.size() == 0);
		assertTrue(col2.size() == 0);
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(0));
		
		col2.add(Integer.valueOf(10));
		col2.add("Zagreb");
		col2.add(Boolean.FALSE);
		
		ArrayIndexedCollection col3 = new ArrayIndexedCollection(col2);
		ArrayIndexedCollection col4 = new ArrayIndexedCollection(col2, 8);
		
		assertTrue(col3.size() == 3);
		assertTrue(col4.size() == 3);
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null));
	}
	
	@Test
	public void testAddMethodAddsToCollection() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		
		col.add(Integer.valueOf(10));
		col.add("Zagreb");
		col.add(Boolean.FALSE);
		
		Object[] expected = new Object[] {Integer.valueOf(10), "Zagreb", Boolean.FALSE};
		
		assertArrayEquals(expected, col.toArray());
		assertThrows(NullPointerException.class, () -> col.add(null));
	}
	
	@Test
	public void testGetMethodGetsFromIndexFromCollection() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		
		col.add(Integer.valueOf(10));
		col.add("Zagreb");
		col.add(Boolean.FALSE);
		
		Object expected = "Zagreb";
		
		assertTrue(expected.equals(col.get(1)));
	}
	
	@Test
	public void testClearsAllElementsFromCollection() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		
		col.add(Integer.valueOf(10));
		col.add("Zagreb");
		col.add(Boolean.FALSE);
		
		col.clear();
		assertTrue(col.size() == 0);
	}
	
	@Test
	public void testInsertsToFrontMiddleAndEnd() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		
		col.add(Integer.valueOf(10));
		col.add("Zagreb");
		col.add(Boolean.FALSE);
		
		col.insert("Rijeka", 0);
		Object[] expected = new Object[] {"Rijeka", Integer.valueOf(10), "Zagreb", Boolean.FALSE};
		assertArrayEquals(expected, col.toArray());
		
		col.insert("Split", 2);
		Object[] expected1 = new Object[] {"Rijeka", Integer.valueOf(10), "Split", "Zagreb", Boolean.FALSE};
		assertArrayEquals(expected1, col.toArray()); 
		
		col.insert("Dubrovnik", col.size());
		Object[] expected2 = new Object[] {"Rijeka", Integer.valueOf(10), "Split", "Zagreb", Boolean.FALSE, "Dubrovnik"};
		assertArrayEquals(expected2, col.toArray());
	}
	
	@Test
	public void testReturnsIndexOfGivenValue() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		
		col.add(Integer.valueOf(10));
		col.add("Zagreb");
		col.add(Boolean.FALSE);
		
		assertTrue(col.indexOf(Boolean.FALSE) == 2);
		assertFalse(col.indexOf(Boolean.FALSE) == 0);
	}
	
	@Test
	public void testRemovesGivenObjectItUsesRemoveIndex() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		
		col.add(Integer.valueOf(10));
		col.add("Zagreb");
		col.add(Boolean.FALSE);
		

		col.remove(Integer.valueOf(10));
		Object[] expected1 = new Object[] {"Zagreb", Boolean.FALSE};
		assertArrayEquals(expected1, col.toArray());
		col.insert(Integer.valueOf(10), 0);
		
		col.remove("Zagreb");
		Object[] expected2 = new Object[] {Integer.valueOf(10), Boolean.FALSE};
		assertArrayEquals(expected2, col.toArray());
		col.insert("Zagreb", 1);
		
		col.remove(Boolean.FALSE);
		Object[] expected3 = new Object[] {Integer.valueOf(10), "Zagreb"};
		assertArrayEquals(expected3, col.toArray());
	}
	
	@Test
	public void testContainsGivenObject() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		
		col.add(Integer.valueOf(10));
		col.add("Zagreb");
		col.add(Boolean.FALSE);
		
		assertTrue(col.contains("Zagreb"));
		assertFalse(col.contains(Integer.valueOf(76)));
	}	
}
