package hr.fer.oprpp1.lsystems.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

public class GenerateTest {
	
	@Test
	public void GenerateLevel0Test() {
		LSystemBuilderImpl lSystemBuilder = new LSystemBuilderImpl();
		LSystem lSystem = lSystemBuilder.setAxiom("F").registerProduction('F', "F+F--F+F").build();
		
		String s = lSystem.generate(0);
		Assertions.assertEquals("F", s);
	
	}
	
	@Test
	public void GenerateLevel1Test() {
		LSystemBuilderImpl lSystemBuilder = new LSystemBuilderImpl();
		LSystem lSystem = lSystemBuilder.setAxiom("F").registerProduction('F', "F+F--F+F").build();
		
		String s = lSystem.generate(1);
		Assertions.assertEquals("F+F--F+F", s);
	
	}
	
	@Test
	public void GenerateLevel2Test() {
		LSystemBuilderImpl lSystemBuilder = new LSystemBuilderImpl();
		LSystem lSystem = lSystemBuilder.setAxiom("F").registerProduction('F', "F+F--F+F").build();
		
		String s = lSystem.generate(2);
		Assertions.assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", s);
	
	}
	
	@Test
	public void GenerateLevel3Test() {
		LSystemBuilderImpl lSystemBuilder = new LSystemBuilderImpl();
		LSystem lSystem = lSystemBuilder.setAxiom("F").registerProduction('F', "F+F--F+F").build();
		
		String s = lSystem.generate(3);
		Assertions.assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F+F+F--F+F+F+F--F+F--"
				+ "F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F+F+F--F+F+F+F--"
				+ "F+F--F+F--F+F+F+F--F+F", s);
	
	}
	
}
