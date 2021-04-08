package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import static java.lang.Math.*;

/**
 * Pomoćna klasa u kojoj se nalaze implementacije svih operacije koje
 * su nam potrebne na kalkulatoru
 * @author vedran
 *
 */
public class MathOperations {
	
	public static DoubleUnaryOperator reciprocal() {
		return o -> 1/o;
	}
	
	public static DoubleUnaryOperator sin() {
		return o -> Math.sin(o);
	}
	
	public static DoubleUnaryOperator asin() {
		return o -> Math.asin(o);
	}
	
	public static DoubleUnaryOperator cos() {
		return o -> Math.cos(o);
	}
	
	public static DoubleUnaryOperator acos() {
		return o -> Math.acos(o);
	}
	
	public static DoubleUnaryOperator tan() {
		return o -> Math.tan(o);
	}
	
	public static DoubleUnaryOperator atan() {
		return o -> Math.atan(o);	
	}
	
	public static DoubleUnaryOperator ctg() {
		return o -> 1/Math.tan(o);
	}
	
	public static DoubleUnaryOperator actg() {
		return o -> PI/2 - Math.atan(o);
	}
	
	public static DoubleBinaryOperator xPowerN() {
		return (x,n) -> Math.pow(x, n);
	}
	
	public static DoubleBinaryOperator nthRoot() {
		return (x,n) -> Math.pow(x, 1/n);
	}
	
	public static DoubleUnaryOperator ln() {
		return o ->  Math.log(o);
	}
	
	public static DoubleUnaryOperator powerE() {
		return o -> pow(E, o);
	}
	
	public static DoubleUnaryOperator log10() {
		return o -> Math.log10(o);
	}
	
	public static DoubleUnaryOperator power10() {
		return o -> pow(10, o);
	}
	
}
