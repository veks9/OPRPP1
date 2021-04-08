package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class LinkedListIndexedCollectionTest {

	@Test
	public void testConstructor() {
		LinkedListIndexedCollection col1 = new LinkedListIndexedCollection();
		
		assertTrue(col1.size() == 0);
		
		col1.add(Integer.valueOf(10));
		col1.add("Zagreb");
		col1.add(Boolean.FALSE);
		
		LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col1);
		
		assertTrue(col2.size() == 3);
		assertThrows(NullPointerException.class, () -> new LinkedListIndexedCollection(null));
	}
	
	@Test
	public void testAddMethodAddsToCollection() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		
		col.add(Integer.valueOf(10));
		col.add("Zagreb");
		col.add(Boolean.FALSE);
		
		Object[] expected = new Object[] {Integer.valueOf(10), "Zagreb", Boolean.FALSE};
		
		assertArrayEquals(expected, col.toArray());
		assertThrows(NullPointerException.class, () -> col.add(null));
	}
	
	@Test
	public void testGetMethodGetsFromIndexFromCollection() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		
		col.add(Integer.valueOf(10));
		col.add("Zagreb");
		col.add(Boolean.FALSE);
		
		Object expected = "Zagreb";
		
		assertTrue(expected.equals(col.get(1)));
	}
	
	@Test
	public void testClearsAllElementsFromCollection() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		
		col.add(Integer.valueOf(10));
		col.add("Zagreb");
		col.add(Boolean.FALSE);
		
		col.clear();
		assertTrue(col.size() == 0);
	}
	
	@Test
	public void testInsertsToFrontMiddleAndEnd() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		
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
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		
		col.add(Integer.valueOf(10));
		col.add("Zagreb");
		col.add(Boolean.FALSE);
		
		assertTrue(col.indexOf(Boolean.FALSE) == 2);
		assertFalse(col.indexOf(Boolean.FALSE) == 0);
	}
	
	@Test
	public void testRemovesGivenObjectItUsesRemoveIndex() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		
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
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		
		col.add(Integer.valueOf(10));
		col.add("Zagreb");
		col.add(Boolean.FALSE);
		
		assertTrue(col.contains("Zagreb"));
		assertFalse(col.contains(Integer.valueOf(76)));
	}	
}
