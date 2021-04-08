package hr.fer.oprpp1.math;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ComplexRootedPolynomialTest {
	@Test
	public void testToComplexPolynom() {
	ComplexRootedPolynomial crp2 = buildPolynom();
			ComplexPolynomial cp2 = crp2.toComplexPolynom();
			Complex c = new Complex(1.4, -3.2);
			assertEquals("(2.0+i1.0)*(z-(2.0-i3.5))*(z-(1.2+i3.2))*(z-(-2.7+i1.0))"
					, crp2.toString()
				);
			assertEquals("(2.0+i1.0)*z^3+(0.3+i1.9)*z^2+(4.3+i17.7)*z^1+(-85.5-i23.6)"
					, cp2.toString()
				);
			Complex res3 = crp2.apply(c);
			System.out.println(res3);
			Complex res4 = cp2.apply(c);
			System.out.println(res4);
			assertEquals(res3.getRe(), res4.getRe(), 0.000001);
			assertEquals(res3.getIm(), res4.getIm(), 0.000001);
		}

	private ComplexRootedPolynomial buildPolynom() {
			// (2+1i)(z-(2-3.5i))(z-(1.2+3.2i))(z-(-2.7+1i))
			Complex c1 = new Complex(2, 1);
			Complex c2 = new Complex(2, -3.5);
			Complex c3 = new Complex(1.2, 3.2);
			Complex c4 = new Complex(-2.7, 1);

			return new ComplexRootedPolynomial(c1, c2, c3, c4);
		}
}
