package hr.fer.oprpp1.math;

public class Demo {
	public static void main(String[] args) {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(new Complex(2, 0), Complex.ONE, Complex.ONE_NEG,
				Complex.IM, Complex.IM_NEG);

		System.out.println(crp);

		ComplexPolynomial cp = crp.toComplexPolynom();

		System.out.println(cp);
		System.out.println(cp.derive());

		System.out.println();
		
		ComplexRootedPolynomial crp1 = new ComplexRootedPolynomial(Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG);
		ComplexPolynomial cp1 = crp1.toComplexPolynom();
		System.out.println(crp1);
		System.out.println(cp1);
	}
}
