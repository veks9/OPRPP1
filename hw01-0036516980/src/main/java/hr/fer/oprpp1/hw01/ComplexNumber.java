package hr.fer.oprpp1.hw01;

import static java.lang.Math.*;

/**
 * Klasa predstavlja implementaciju kompleksnog broja
 * 
 * @author vedran
 *
 */
public class ComplexNumber {

	private final double real;
	private final double imaginary;

	/**
	 * Konstruktor koji prima realni i imaginarni dio kompleksnog broja
	 * 
	 * @param real      realni dio
	 * @param imaginary imaginarni dio
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Pretpostavljeni konstruktor koji postavlja realni i imaginarni dio na 0
	 */
	public ComplexNumber() {
		real = 0.;
		imaginary = 0.;
	}

	/**
	 * Metoda radi novi kompleksni broj sa realnim dijelom(imaginarni je 0)
	 * 
	 * @param real realni dio novog kompleksnog broja
	 * @return novi kompleksni broj
	 */
	public static ComplexNumber fromReal(double real) {
		ComplexNumber z = new ComplexNumber(real, 0.);
		return z;
	}

	/**
	 * 
	 * Metoda radi novi kompleksni broj sa imaginarnim dijelom(realni je 0)
	 * 
	 * @param imaginary imaginarnidio novog kompleksnog broja
	 * @return novi kompleksni broj
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		ComplexNumber z = new ComplexNumber(0., imaginary);
		return z;
	}

	/**
	 * Metoda radi novi kompleksni broj od modula i kuta.
	 * 
	 * @param magnitude modul kompleksnog broja
	 * @param angle     kut
	 * @return novi kompleksni broj
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		ComplexNumber z = new ComplexNumber(magnitude * cos(angle), magnitude * sin(angle));
		return z;
	}

	/**
	 * Metoda prima string u kojem je napisan kompleksni broj i vraća taj kompleksni
	 * broj kao objekt klase {@link ComplexNumber}
	 * 
	 * @param s ulazni string u kojem se nalazi zapis kompleksnog broja
	 * @return novi kompleksni broj
	 * @throws IllegalAccessException ako je format kompleksnog broja predan parseru
	 *                                nevaljan
	 * 
	 */
	public static ComplexNumber parse(String s) {
		try {
			if (s.charAt(0) == '+') {
				if (s.charAt(1) == '+' || s.charAt(1) == '-')
					throw new IllegalArgumentException("Format koji je predan parseru nije valjan");
				s = s.substring(1);
			}

			String realString = "";
			String imaginaryString = "";
			if (s.length() == 1 && s.endsWith("i")) {
				s = s.replaceAll("i", "1i");
			} else if ((s.lastIndexOf('+') == s.length() - 2 || s.lastIndexOf('-') == s.length() - 2)
					&& s.endsWith("i")) {
				s = s.replaceAll("i", "1i");
			}

			if (s.indexOf('+') > 0) {
				s = s.replaceAll("i", "");

				realString = s.substring(0, s.lastIndexOf('+'));
				imaginaryString = s.substring(s.lastIndexOf('+') + 1, s.length());

				return new ComplexNumber(Double.parseDouble(realString), Double.parseDouble(imaginaryString));

			} else if (s.lastIndexOf('-') > 0) {
				s = s.replaceAll("i", "");
				realString = s.substring(0, s.lastIndexOf('-'));
				imaginaryString = s.substring(s.lastIndexOf('-'), s.length());

				return new ComplexNumber(Double.parseDouble(realString), Double.parseDouble(imaginaryString));

			} else if (!s.endsWith("i")) {

				return fromReal(Double.parseDouble(s));

			} else if (s.endsWith("i")) {
				s = s.replaceAll("i", "");

				return fromImaginary(Double.parseDouble(s));

			} else {

				return new ComplexNumber();
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Format koji je predan parseru nije valjan");
		}
	}

	/**
	 * Metoda vraća realni dio kompleksnog broja
	 * 
	 * @return realni dio kompleksnog broja
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Metoda vraća imaginarni dio kompleksnog broja
	 * 
	 * @return imaginarni dio komlpeksnog broja
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Metoda vraća modul kompleksnog broja
	 * 
	 * @return modul kompleksnog broja
	 */
	public double getMagnitude() {
		return sqrt(square());
	}

	/**
	 * Metoda radi zbroj kvadrata realnog i imaginarnog dijela kompleksnog broja
	 * 
	 * @return zbroj kvadrata realnog i imaginarnog dijela kompleksnog broja
	 */
	private double square() {
		return real * real + imaginary * imaginary;
	}

	/**
	 * Metoda vraća kut kompleksnog broja
	 * 
	 * @return kut kompleksnog broja
	 */
	public double getAngle() {
		if (real == 0 && imaginary == 0)
			return 0;
		else {
			double ret = atan2(imaginary, real);
			return ret > 0 ? ret : ret + 2 * PI;
		}
	}

	/**
	 * Metoda zbraja dva kompleksna broja. This i c. Vraća novi kompleksni broj
	 * 
	 * @param c kompleksni broj koji se treba zbrojiti s this
	 * @return novi kompleksni broj dobiven zbrojem this i c
	 */
	public ComplexNumber add(ComplexNumber c) {
		ComplexNumber z = new ComplexNumber(this.real + c.getReal(), this.imaginary + c.getImaginary());
		return z;
	}

	/**
	 * Metoda oduzima dva kompleksna broja. This i c. Vraća novi kompleksni broj
	 * 
	 * @param c kompleksni broj kojeg treba oduzeti od this
	 * @return novi kompleksni broj dobiven oduzimanjem this i c
	 */
	public ComplexNumber sub(ComplexNumber c) {
		ComplexNumber z = new ComplexNumber(this.real - c.getReal(), this.imaginary - c.getImaginary());
		return z;
	}

	/**
	 * Metoda množi dva kompleksna broja. This i c. Vraća novi kompleksni broj
	 * 
	 * @param c kompleksni broj koji se treba pomnožiti s this
	 * @return novi kompleksni broj dobiven množenjem this i c
	 */
	public ComplexNumber mul(ComplexNumber c) {
		ComplexNumber z = new ComplexNumber((this.real * c.getReal()) - (this.imaginary * c.getImaginary()),
				(this.real * c.getImaginary()) + (this.imaginary * c.getReal()));

		return z;
	}

	/**
	 * Metoda dijeli dva kompleksna broja. This i c. Vraća novi kompleksni broj
	 * 
	 * @param c je djelitelj
	 * @return novi kompleksni broj dobiven dijeljenjem this i c
	 */
	public ComplexNumber div(ComplexNumber c) {
		double module = getMagnitude() / c.getMagnitude();
		double angle = getAngle() - c.getAngle();

		ComplexNumber z = new ComplexNumber(module * cos(angle), module * sin(angle));

		return z;

	}

	/**
	 * Metoda potencira kompleksni broj this sa eksponentom n. Vraća novi kompleksni
	 * broj
	 * 
	 * @param n eksponoent
	 * @return novi kompleksni broj z^n
	 */
	public ComplexNumber power(int n) { // n >= 0
		if (n < 0)
			throw new IllegalArgumentException("Eksponent mora biti veći ili jednak nuli");

		double rN = pow(getMagnitude(), n);
		double realN = cos(n * getAngle());
		double imagN = sin(n * getAngle());
		ComplexNumber z = new ComplexNumber(rN * realN, rN * imagN);

		return z;

	}

	/**
	 * Metoda vadi korijene iz kompleksnog broja. Vraća polje kompleksnih brojeva.
	 * 
	 * @param n broj korijena
	 * @return novo polje kompleksnih brojeva
	 */
	public ComplexNumber[] root(int n) { // n > 0
		if (n < 0)
			throw new IllegalArgumentException("Korijen mora biti veći od nule");

		ComplexNumber[] rootArray = new ComplexNumber[n];

		for (int i = 0; i < n; i++) {
			double rN = pow(getMagnitude(), pow(n, -1));
			double realN = cos((getAngle() + 2 * i * PI) / (double) n);
			double imagN = sin((getAngle() + 2 * i * PI) / (double) n);
			ComplexNumber z = new ComplexNumber(rN * realN, rN * imagN);
			rootArray[i] = z;
		}

		return rootArray;
	}

	@Override
	public String toString() {
		String s = "";
		if (real != 0.0) {
			s = String.valueOf(real);
			if (imaginary > 0)
				s += "+";
			s += imaginaryToString();
		} else {
			s = imaginaryToString();
		}

		return s;
	}

	/**
	 * Pomoćna funkcija koja određuje oblik zapisivanja imaginarnog dijela
	 * kompleksnog broja
	 * 
	 * @return korektno formatiran imaginarni dio kompleksnog broja
	 */
	private String imaginaryToString() {
		String s = "";
		if (imaginary != 0) {
			if (imaginary == 1) {
				s = "i";
			} else if (imaginary == -1) {
				s = "-i";
			} else {
				s = String.valueOf(imaginary) + "i";
			}
		}

		return s;
	}
}
