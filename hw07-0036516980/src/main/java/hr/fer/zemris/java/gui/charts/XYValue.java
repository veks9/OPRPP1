package hr.fer.zemris.java.gui.charts;

/**
 * Klasa predstavlja uređeni par koji se prikazuje na grafu u obliku barova
 * @author vedran
 *
 */
public class XYValue {

	private int x;
	private int y;
	
	
	public XYValue(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Getter x vrijednosti
	 * @return
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Getter y vrijednosti
	 * @return
	 */
	public int getY() {
		return y;
	}
	
	
	
	
	
}
