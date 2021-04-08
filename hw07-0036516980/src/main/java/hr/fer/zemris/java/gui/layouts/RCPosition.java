package hr.fer.zemris.java.gui.layouts;

/**
 * Klasa koja predstavlja ograničenje gdje se dodaje komponenta na layoutu
 * 
 * @author vedran
 *
 */
public class RCPosition {
	private int row;
	private int column;

	public RCPosition(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	/**
	 * Metoda parsira ulazni string tipa "x,y" i vraća objekt tipa
	 * {@link RCPosition}
	 * 
	 * @param input string tipa "x,y"
	 * @return objekt tipa {@link RCPosition} sa parsiranim x i y
	 */
	public static RCPosition parse(String input) {
		if (input == null)
			throw new IllegalArgumentException("Input parseru ne smije biti null!");

		String[] arr = input.trim().split(",");
		int row = 0;
		int column = 0;
		try {
			row = Integer.parseInt(arr[0].trim());
			column = Integer.parseInt(arr[1].trim());
		} catch (NumberFormatException n) {
			throw new IllegalArgumentException("Greška pri parsiranju stringa!");
		}

		return new RCPosition(row, column);
	}

	/**
	 * Getter za red
	 * 
	 * @return
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Getter za stupac
	 * 
	 * @return
	 */
	public int getColumn() {
		return column;
	}
}
