package hr.fer.oprpp1.hw04.db;

/**
 * Klasa predstavlja lexer koji dobiva tekst i razbija ga u tokene
 * 
 * @author vedran
 *
 */
class Lexer {
	private char[] data; // ulazni tekst
	private Token token;// trenutni token
	private int currentIndex; // indeks prvog neobrađenog znaka
	private LexerState state = LexerState.BASIC;

	/**
	 * konstruktor prima ulazni tekst koji se tokenizira
	 * 
	 * @param text ulazni tekst koji se tokenizira
	 */
	public Lexer(String text) {
		if (text == null)
			throw new NullPointerException();

		data = text.toCharArray();
	}

	/**
	 * generira i vraća sljedeći token
	 * 
	 * @return sljedeći token
	 * @throws LexerException ako dođe do pogreške
	 */
	public Token nextToken() {
		createNextToken();
		return token;

	}

	/**
	 * vraća zadnji generirani token; može se pozivati više puta; ne pokreće
	 * generiranje sljedećeg tokena
	 * 
	 * @return zadnji generirani token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Metoda stvara novi token
	 */
	private void createNextToken() {
		if (token != null && token.getType() == TokenType.EOF)
			throw new LexerException("Ne može se napraviti novi token ako je prošli bio null");

		jumpOverBlanks();

		if (data.length <= currentIndex) {
			token = new Token(TokenType.EOF, null);
			return;
		} else if (LexerState.STRING == state && !(data[currentIndex] == '"')) {
			String s = "";
			do {
				if (data[currentIndex] == '"')
					break;
				s += data[currentIndex++];
				if (data.length == currentIndex)
					break;
			} while (!Character.isWhitespace(data[currentIndex]));

			token = new Token(TokenType.WORD, s);
			return;
		}

		else if (Character.isLetter(data[currentIndex])) {
			wordToken();
			return;
		}

		else if (Character.isDigit(data[currentIndex])) {
			numberToken();
			return;
		}

		else {
			String s = "";
			if (data[currentIndex] == '"') {
				token = new Token(TokenType.SYMBOL, data[currentIndex++]);
				return;
			}
			do {
				s += data[currentIndex++];
				if (data.length == currentIndex)
					break;
			} while (data[currentIndex] != '"');

			String temp = s;
			temp = temp.trim();
			if (temp.equals("<")) {
				token = new Token(TokenType.OPERATOR, temp);
				return;
			} else if (temp.equals("<=")) {
				token = new Token(TokenType.OPERATOR, temp);
				return;
			} else if (temp.equals(">")) {
				token = new Token(TokenType.OPERATOR, temp);
				return;
			} else if (temp.equals(">=")) {
				token = new Token(TokenType.OPERATOR, temp);
				return;
			} else if (temp.equals("=")) {
				token = new Token(TokenType.OPERATOR, temp);
				return;
			} else if (temp.equals("!=")) {
				token = new Token(TokenType.OPERATOR, temp);
				return;
			}

			token = new Token(TokenType.SYMBOL, data[currentIndex++]);
			return;
		}

	}

	/**
	 * Metoda preskače sve vrste bjelina
	 */
	private void jumpOverBlanks() {
		while (currentIndex < data.length) {
			char symbol = data[currentIndex];
			if (symbol == ' ' || symbol == '\n' || symbol == '\t' || symbol == '\r') {
				currentIndex++;
				continue;
			}
			break;
		}

	}

	/**
	 * Metoda stvara novi token tipa word
	 */
	private void wordToken() {
		String s = "";
		do {
			s += data[currentIndex++];
			if (data.length == currentIndex)
				break;
		} while (Character.isLetter(data[currentIndex]));

		if (s.equalsIgnoreCase("and")) {
			token = new Token(TokenType.AND, s.toUpperCase());
			return;
		} else if (s.equalsIgnoreCase("like")) {
			token = new Token(TokenType.OPERATOR, s.toUpperCase());
			return;
		}

		token = new Token(TokenType.WORD, s);
	}

	/**
	 * Metoda stvara novi token tipa number
	 */
	private void numberToken() {
		String s = "";

		do {
			s += data[currentIndex++];
			if (data.length == currentIndex)
				break;
		} while (Character.isDigit(data[currentIndex]));

		token = new Token(TokenType.NUMBER, s);

	}

	/**
	 * Metoda postavlja stanje lexera
	 * 
	 * @param state novo stanje lexera
	 */
	public void setState(LexerState state) {
		if (state == null)
			throw new NullPointerException();
		this.state = state;
	}

	/**
	 * Metoda resetira lexer
	 */
	public void reset() {
		currentIndex = 0;
		token = null;
	}
}