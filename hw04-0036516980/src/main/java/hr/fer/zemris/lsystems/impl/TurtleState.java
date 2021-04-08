package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.oprpp1.math.Vector2D;

/**
 * Klasa predstvlja stanje u kojem se kornjača nalazi
 * 
 * @author vedran
 *
 */
public class TurtleState {

	Vector2D turtlePosition;
	Vector2D turtleDirection;
	Color color;
	double shift;

	/**
	 * Getter za poziciju kornjače
	 * 
	 * @return pozicija kornjače
	 */
	public Vector2D getTurtlePosition() {
		return turtlePosition;
	}

	/**
	 * Setter za poziciju kornjače
	 * 
	 * @param turtlePosition nova pozicija kornjače
	 */
	public void setTurtlePosition(Vector2D turtlePosition) {
		this.turtlePosition = turtlePosition;
	}

	/**
	 * Getter za smjer gledanja kornjače
	 * 
	 * @return smjer gledanja kornjače
	 */
	public Vector2D getTurtleDirection() {
		return turtleDirection;
	}

	/**
	 * Setter za smjer gledanja kornjače
	 * 
	 * @param turtleDirection novi smjer gledanja kornjače
	 */
	public void setTurtleDirection(Vector2D turtleDirection) {
		this.turtleDirection = turtleDirection;
	}

	/**
	 * Getter za boju s kojom kornjača crta
	 * 
	 * @return boja
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Setter za boju s kojom kornjača crta
	 * 
	 * @param color nova boja
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Getter efektivne duljine pomaka
	 * 
	 * @return efektivna duljina pomaka
	 */
	public double getShift() {
		return shift;
	}

	/**
	 * Setter za efektivnu duljinu pomaka
	 * 
	 * @param shift
	 */
	public void setShift(double shift) {
		this.shift = shift;
	}

	/**
	 * Konstruktor koji radi stanje kornjače
	 * 
	 * @param turtlePosition  trenutna pozicija
	 * @param turtleDirection trenutni smjer gledanja
	 * @param color           boja crtanja
	 * @param shift           efektivna duljina pomaka
	 */
	public TurtleState(Vector2D turtlePosition, Vector2D turtleDirection, Color color, double shift) {
		this.turtlePosition = turtlePosition;
		this.turtleDirection = turtleDirection;
		this.color = color;
		this.shift = shift;
	}

	/**
	 * Metoda kopira trenutno stanje kornjače
	 * 
	 * @return kopija trenutnog stanja kornjače
	 */
	public TurtleState copy() {
		double newShift = shift;
		return new TurtleState(turtlePosition.copy(), turtleDirection.copy(), new Color(color.getRGB()), newShift);
	}
}
