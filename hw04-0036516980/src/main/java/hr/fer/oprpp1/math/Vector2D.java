package hr.fer.oprpp1.math;

import static java.lang.Math.*;

/**
 * Klasa predstavlja implementaciju vektora u 2D-u
 * 
 * @author vedran
 *
 */
public class Vector2D {
	private double x;
	private double y;
	public static final Vector2D I = new Vector2D(1, 0);
	public static final Vector2D J = new Vector2D(0, 1);

	/**
	 * Konstruktor koji prima x i y
	 * 
	 * @param x
	 * @param y
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Defaultni konstruktor koji postavlja x i y na 0
	 */
	public Vector2D() {
		x = 0;
		y = 0;
	}

	/**
	 * Metoda vraća x
	 * 
	 * @return x
	 */
	public double getX() {
		return x;
	}

	/**
	 * Metoda vraća y
	 * 
	 * @return y
	 */
	public double getY() {
		return y;
	}

	/**
	 * Metoda zbraja this vektor i vektor offset
	 * 
	 * @param offset vektor koji se zbraja sa this
	 */
	public void add(Vector2D offset) {
		this.x += offset.x;
		this.y += offset.y;
	}

	/**
	 * Metoda zbraja this i offset vekktor i stvara novi vektor sa tim rezultatom
	 * 
	 * @param offset vektor koji se zbraja sa this
	 * @return novi vektor sa rezultatom zbroja
	 */
	public Vector2D added(Vector2D offset) {
		return new Vector2D(this.x + offset.x, this.y + offset.y);

	}

	/**
	 * Metoda prima kut angle i rotira ga za taj kut u suprotnom smjeru od kazaljke
	 * na satu
	 * 
	 * @param angle kut za koji se this treba rotirati
	 */
	public void rotate(double angle) {
		double temp = Double.valueOf(x);
		this.x = cos(angle) * this.x - sin(angle) * this.y;
		this.y = sin(angle) * temp + cos(angle) * this.y;
	}

	/**
	 * Metoda prima kut angle i rotira ga za taj kut u suptornom smjeru od kazaljke
	 * na satu. Ne mijenja this i vraća novi vektor koji je zarotiran
	 * 
	 * @param angle kut za koji se vektor rotira
	 * @return novi vektor koji je zarotiran za angle
	 */
	public Vector2D rotated(double angle) {
		return new Vector2D(cos(angle) * this.x - sin(angle) * this.y, sin(angle) * this.x + cos(angle) * this.y);
	}

	/**
	 * Metoda prima skalar za koji se vektor povećava
	 * 
	 * @param scaler skalar s kojim se množi vektor
	 */
	public void scale(double scaler) {
		this.x *= scaler;
		this.y *= scaler;
	}

	/**
	 * Metoda prima skalar za koji se vektor povećava. Vraća novi vektor koji je
	 * pomnožen skalarom
	 * 
	 * @param scaler skalar s kojim se množi vektor
	 * @return novi vektor pomnožen skalarom
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(this.x * scaler, this.y * scaler);
	}

	/**
	 * Metoda vraća novi vektor koji predstavlja kopiju this
	 * 
	 * @return novi vektor koji predstavlja kopiju this
	 */
	public Vector2D copy() {
		return new Vector2D(this.x, this.y);
	}

	/**
	 * Metoda vraća kut vektora
	 * 
	 * @return
	 */
	public double getAngle() {
		return atan2(y, x);
	}

}
