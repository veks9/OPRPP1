package hr.fer.oprpp1.math;

import static java.lang.Math.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa predstavlja implementaciju kompleksnog broja
 * @author vedran
 *
 */
public class Complex {

	public static final Complex ZERO = new Complex(0, 0);
	public static final Complex ONE = new Complex(1, 0);
	public static final Complex ONE_NEG = new Complex(-1, 0);
	public static final Complex IM = new Complex(0, 1);
	public static final Complex IM_NEG = new Complex(0, -1);
	private double re;
	private double im;

	public Complex() {
		this(0, 0);

	}

	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	/**
	 * Getter realnog dijela kompleksnog broja
	 * 
	 * @return realni dio kompleksnog broja
	 */
	public double getRe() {
		return re;
	}

	/**
	 * Getter imaginarnog dijela kompleksnog broja
	 * 
	 * @return imaginarni dio kompleksnog broja
	 */
	public double getIm() {
		return im;
	}

	/**
	 * Metoda vraća modul kompleksnog broja
	 * 
	 * @return modul kompleksnog broja
	 */
	public double module() {
		return sqrt(pow(re, 2) + pow(im, 2));
	}

	/**
	 * Metoda množi dva kompleksna broja. This i c. Vraća novi kompleksni broj
	 * 
	 * @param c kompleksni broj koji se treba pomnožiti s this
	 * @return novi kompleksni broj dobiven množenjem this i c
	 */
	public Complex multiply(Complex c) {
		return new Complex((this.re * c.re) - (this.im * c.im), (this.re * c.im) + (this.im * c.re));
	}

	/**
	 * Metoda dijeli dva kompleksna broja. This i c. Vraća novi kompleksni broj
	 * 
	 * @param c je djelitelj
	 * @return novi kompleksni broj dobiven dijeljenjem this i c
	 */
	public Complex divide(Complex c) {
		double module = module() / c.module();
		double angle = getAngle() - c.getAngle();

		return new Complex(module * cos(angle), module * sin(angle));
	}

	/**
	 * Metoda zbraja dva kompleksna broja. This i c. Vraća novi kompleksni broj
	 * 
	 * @param c kompleksni broj koji se treba zbrojiti s this
	 * @return novi kompleksni broj dobiven zbrojem this i c
	 */
	public Complex add(Complex c) {
		return new Complex(c.re + re, c.im + im);
	}

	/**
	 * Metoda oduzima dva kompleksna broja. This i c. Vraća novi kompleksni broj
	 * 
	 * @param c kompleksni broj kojeg treba oduzeti od this
	 * @return novi kompleksni broj dobiven oduzimanjem this i c
	 */
	public Complex sub(Complex c) {
		return new Complex(re - c.re, im - c.im);
	}

	/**
	 * Metoda vraća novi kompleksni broj koji je negativan this.
	 * 
	 * @return negativan this
	 */
	public Complex negate() {
		return new Complex(-re, -im);
	}

	/**
	 * Metoda potencira kompleksni broj this sa eksponentom n. Vraća novi kompleksni
	 * broj
	 * 
	 * @param n eksponoent
	 * @return novi kompleksni broj z^n
	 */
	public Complex power(int n) {
		if (n < 0)
			throw new IllegalArgumentException("n treba biti nenegativan");

		double rN = pow(module(), n);
		double reN = cos(n * getAngle());
		double imN = sin(n * getAngle());

		return new Complex(rN * reN, rN * imN);
	}

	/**
	 * Metoda vadi korijene iz kompleksnog broja. Vraća listu kompleksnih brojeva.
	 * 
	 * @param n broj korijena
	 * @return nova lista kompleksnih brojeva
	 */
	public List<Complex> root(int n) {
		if (n <= 0)
			throw new IllegalArgumentException("Korijen mora biti veći od nule");

		List<Complex> list = new ArrayList<>();

		for (int i = 0; i < n; i++) {
			double rN = pow(module(), pow(n, -1));
			double reN = cos((getAngle() + 2 * i * PI) / n);
			double imN = sin((getAngle() + 2 * i * PI) / n);

			list.add(new Complex(rN * reN, rN * imN));
		}

		return list;
	}

	/**
	 * Metoda vraća kut kompleksnog broja
	 * 
	 * @return kut kompleksnog broja
	 */
	public double getAngle() {
		if (re == 0 && im == 0)
			return 0;
		else {
			double ret = atan2(im, re);
			return ret > 0 ? ret : ret + 2 * PI;
		}
	}

	@Override
	public String toString() {
		return im > 0 ? String.format("%.1f+i%.1f", re, im) : String.format("%.1f-i%.1f", re, abs(im));
	}
}
