package hr.fer.oprpp1.hw05.shell;

/**
 * Klasa predstavlja implementaciju iznimke koju baca aplikacija
 * @author vedran
 *
 */
public class ShellIOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ShellIOException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShellIOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ShellIOException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ShellIOException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ShellIOException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
