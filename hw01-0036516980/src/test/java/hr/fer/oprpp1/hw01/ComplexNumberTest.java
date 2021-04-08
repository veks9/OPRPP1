package hr.fer.oprpp1.hw01;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static hr.fer.oprpp1.hw01.ComplexNumber.*;
import static java.lang.Math.*;

public class ComplexNumberTest {

	@Test
	public void testIfMethodCreatesComplexNumberWithRealPartOnly() {
		ComplexNumber z1 = fromReal(5.3);
		
		ComplexNumber z2 = new ComplexNumber(5.3, 0);
		
		assertTrue(z1.getReal() == z2.getReal() && z1.getImaginary() == z2.getImaginary());
	}
	
	@Test
	public void testIfMethodCreatesComplexNumberWithImaginaryPartOnly() {
		ComplexNumber z1 = fromImaginary(5.3);
		
		ComplexNumber z2 = new ComplexNumber(0, 5.3);
		
		assertTrue(z1.getReal() == z2.getReal() && z1.getImaginary() == z2.getImaginary());
	}
	
	@Test
	public void testIfMethodCreatesComplexNumberUsingMagnitudeAndAngle() {
		ComplexNumber z1 = fromMagnitudeAndAngle(2, PI/6);
		
		ComplexNumber z2 = new ComplexNumber(sqrt(3), 1);
		
		assertTrue(round(z1.getReal()*100)/100 == round(z2.getReal()*100)/100 && 
				round(z1.getImaginary()*100)/100 == round(z2.getImaginary()*100)/100);
	}
	
	@Test
	public void testParsingComplexNumber() {
		ComplexNumber z1 = parse("2+2i");
		ComplexNumber z2 = parse("+i");
		ComplexNumber z3 = parse("-2i");
		ComplexNumber z4 = parse("3.5");
		ComplexNumber z5 = parse("3+i");
		ComplexNumber z6 = parse("3-i");
		
		assertTrue(z1.getReal() == 2.0 && z1.getImaginary() == 2.0);
		assertTrue(z2.getReal() == 0.0 && z2.getImaginary() == 1.0);
		assertTrue(z3.getReal() == 0.0 && z3.getImaginary() == -2.0);
		assertTrue(z4.getReal() == 3.5 && z4.getImaginary() == 0.0);
		assertTrue(z5.getReal() == 3.0 && z5.getImaginary() == 1.0);
		assertTrue(z6.getReal() == 3.0 && z6.getImaginary() == -1.0);	
		assertThrows(IllegalArgumentException.class, () -> parse("-+3+-i"));
	}
	
	@Test
	public void testIfGetsRealPart() {
		ComplexNumber z = new ComplexNumber(3, 1);
		
		assertTrue(z.getReal() == 3);
	}
	
	@Test
	public void testIfGetsImaginaryPart() {
		ComplexNumber z = new ComplexNumber(3, 1);
		
		assertTrue(z.getImaginary() == 1);
	}
	
	@Test
	public void testIfGetsMagnitude() {
		ComplexNumber z = new ComplexNumber(3, 1);
		
		assertTrue(z.getMagnitude() == sqrt(10));
	}
	
	@Test
	public void testIfGetsAngle() {
		ComplexNumber z = new ComplexNumber(0, -1);
		
		assertTrue(round(z.getAngle()*100)/100 == round(3*PI/2*100)/100);
	}
	
	@Test
	public void testAddingTwoComplexNumbers() {
		ComplexNumber z1 = new ComplexNumber(2, -1);
		ComplexNumber z2 = new ComplexNumber(3, 2);
		
		ComplexNumber result = z1.add(z2);
		assertTrue(round(result.getReal()*100)/100 == 5.0 && round(result.getImaginary()*100)/100 == 1.0);
	}
	
	@Test
	public void testSubstractingTwoComplexNumbers() {
		ComplexNumber z1 = new ComplexNumber(2, -1);
		ComplexNumber z2 = new ComplexNumber(3, 2);
		
		ComplexNumber result = z1.sub(z2);
		assertTrue(round(result.getReal()*100)/100 == -1.0 && round(result.getImaginary()*100)/100 == -3.0);
	}
	
	@Test
	public void testMultiplyingTwoComplexNumbers() {
		ComplexNumber z1 = new ComplexNumber(1, 3);
		ComplexNumber z2 = new ComplexNumber(2, 1);
		
		ComplexNumber result = z1.mul(z2);
		assertTrue(round(result.getReal()*100)/100 == -1.0 && round(result.getImaginary()*100)/100 == 7.0);
	}
	
	@Test
	public void testDividingTwoComplexNumbers() {
		ComplexNumber z1 = new ComplexNumber(1, -3);
		ComplexNumber z2 = new ComplexNumber(1, 2);
		
		ComplexNumber result = z1.div(z2);
		assertTrue(round(result.getReal()*100)/100 == -1.0 && round(result.getImaginary()*100)/100 == -1.0);
	}
	
	@Test
	public void testPowerComplexNumbers() {
		ComplexNumber z1 = new ComplexNumber(2, 3);
		
		ComplexNumber result = z1.power(5);
		assertTrue(round(result.getReal()*100)/100 == 122.0 && round(result.getImaginary()*100)/100 == -597.0);
	}
	
	@Test
	public void testRootComplexNumbers() {
		ComplexNumber z = fromMagnitudeAndAngle(1, PI/2);
		
		ComplexNumber expected = new ComplexNumber(-(sqrt(3.0))/2.0, 0.5);
		ComplexNumber[] result = z.root(3);
		
		assertTrue(round(expected.getReal()*100)/100 == round(result[1].getReal()*100)/100 && 
				round(expected.getImaginary()*100)/100 == round(result[1].getImaginary()*100)/100);
	}
	
	@Test
	public void testToString() {
		ComplexNumber z1 = parse("2+2i");
		ComplexNumber z2 = parse("+i");
		ComplexNumber z3 = parse("-2i");
		ComplexNumber z4 = parse("3.5");
		ComplexNumber z5 = parse("3+i");
		ComplexNumber z6 = parse("3-i");
		
		assertTrue("2.0+2.0i".equals(z1.toString()));
		assertTrue("i".equals(z2.toString()));
		assertTrue("-2.0i".equals(z3.toString()));
		assertTrue("3.5".equals(z4.toString()));
		assertTrue("3.0+i".equals(z5.toString()));
		assertTrue("3.0-i".equals(z6.toString()));
	}
}
