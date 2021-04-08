package hr.fer.oprpp1.hw02.prob1;

/**
 * Klasa predstavlja lexer koji dobiva tekst i razbija ga u tokene
 * 
 * @author vedran
 *
 */
public class Lexer {
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
		} else if (LexerState.EXTENDED == state && !(data[currentIndex] == '#')) {
			String s = "";
			do {
				if (data[currentIndex] == '#')
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

		else if (data[currentIndex] == '\\') {

			// currentIndex++;
			isCurrentLegal();
			wordToken();
			return;
		}

		else if (Character.isDigit(data[currentIndex])) {
			numberToken();
			return;
		}

		else {
			token = new Token(TokenType.SYMBOL, data[currentIndex++]);
			return;
		}

	}

	/**
	 * Metoda ispituje je li escapeanje legalno
	 * 
	 * @return <code>true</code> ako je
	 * @throws LexerException ako nije
	 */
	private boolean isCurrentLegal() {
		if (currentIndex == data.length || !(data[currentIndex] == '\\' || Character.isDigit(data[currentIndex])))
			throw new LexerException();
		return true;
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
			if (data[currentIndex] == '\\') {
				currentIndex++;
				isCurrentLegal();
			}

			s += data[currentIndex++];
			if (data.length == currentIndex)
				break;
		} while (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\');

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

		long n;
		try {
			n = Long.parseLong(s);
		} catch (NumberFormatException e) {
			throw new LexerException();
		}

		token = new Token(TokenType.NUMBER, n);

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
}