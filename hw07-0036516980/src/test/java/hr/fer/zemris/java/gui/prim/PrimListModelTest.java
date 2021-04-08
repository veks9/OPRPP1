package hr.fer.zemris.java.gui.prim;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PrimListModelTest {
	@Test
	public void nextSizeTest() {
		PrimListModel model = new PrimListModel();
		assertEquals(1, model.getSize());
		
		for(int i = 0; i < 12; i++) {
			model.next();
		}
		assertEquals(13, model.getSize());
		
		model.next();
		model.next();
		model.next();
		assertEquals(16, model.getSize());
	}
	
	@Test
	public void nextTestSmallNumbers() {
		PrimListModel model = new PrimListModel();
		assertEquals(Integer.valueOf(1), (Integer)model.getElementAt(0));
		
		for(int i = 0; i < 30; i++) {
			model.next();
		}
		
		assertEquals(Integer.valueOf(7), (Integer)model.getElementAt(4));
		assertEquals(Integer.valueOf(5), (Integer)model.getElementAt(3));
		assertEquals(Integer.valueOf(23), (Integer)model.getElementAt(9));
	}

}
