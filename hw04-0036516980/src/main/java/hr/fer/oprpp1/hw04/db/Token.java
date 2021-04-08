package hr.fer.oprpp1.hw04.db;

/**
 * Klasa predstavlja token koji {@link Lexer} napravi
 * 
 * @author vedran
 *
 */
class Token {
	private TokenType type;
	private Object value;

	/**
	 * Konstruktor koji radi token
	 * 
	 * @param type  tip tokena
	 * @param value vrijednost tokena
	 */
	public Token(TokenType type, Object value) {
		if (type == null)
			throw new IllegalArgumentException("Tip tokena ne smije biti null!");

		this.type = type;
		this.value = value;
	}

	/**
	 * Getter za vrijednost tokena
	 * 
	 * @return vrijednost tokena
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Getter za tip tokena
	 * 
	 * @return tip tokena
	 */
	public TokenType getType() {
		return type;
	}
}
