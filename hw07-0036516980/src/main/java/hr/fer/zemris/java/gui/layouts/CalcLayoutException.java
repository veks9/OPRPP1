package hr.fer.zemris.java.gui.layouts;

/**
 * Klasa predstavlja iznimku koja se baca kad se dogodi pogreška u radu
 * kalkulatora
 * 
 * @author vedran
 *
 */
public class CalcLayoutException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CalcLayoutException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CalcLayoutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public CalcLayoutException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public CalcLayoutException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public CalcLayoutException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
