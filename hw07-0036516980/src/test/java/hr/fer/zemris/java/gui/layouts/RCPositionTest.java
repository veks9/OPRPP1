package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;


import static hr.fer.zemris.java.gui.layouts.RCPosition.*;

public class RCPositionTest {

	@Test
	public void fromStringTest() {
		RCPosition p = parse("2,3");
		assertEquals(2, p.getRow());
		assertEquals(3, p.getColumn());

		p = parse("  8 , 1  ");
		assertEquals(8, p.getRow());
		assertEquals(1, p.getColumn());
	}

	@Test
	public void fromStringTestInvalidNumbers() {
		assertThrows(IllegalArgumentException.class, ()->{parse("1$, 1");});
	}

	@Test
	public void fromStringTest2() {
		RCPosition p = parse("-5,  5");
		assertEquals(-5, p.getRow());
		assertEquals(5, p.getColumn());

		p = parse("43, 11 ");
		assertEquals(43, p.getRow());
		assertEquals(11, p.getColumn());
	}

}
