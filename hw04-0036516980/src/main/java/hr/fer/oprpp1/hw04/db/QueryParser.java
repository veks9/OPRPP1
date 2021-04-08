package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa predstavlja parser koji koristi {@link Lexer} u pozadini i parsira
 * ulazne upite
 * 
 * @author vedran
 *
 */
public class QueryParser {
	private Lexer lexer;
	private String jmbag;
	private Token token;

	/**
	 * Konstruktor koji prima string i predaje ga lexeru
	 * 
	 * @param s ulazni string
	 */
	public QueryParser(String s) {
		lexer = new Lexer(s);
	}

	/**
	 * Vraća <code>true</code> ako je upit direktan, inače <code>false</code>
	 * 
	 * @return <code>true</code> ako je upit direktan, inače <code>false</code>
	 */
	public boolean isDirectQuery() {
		lexer.reset();
		Token name = lexer.nextToken();
		if (!name.getValue().toString().equals("jmbag"))
			return false;

		Token symbolEquals = lexer.nextToken();
		if (!symbolEquals.getValue().toString().equals("="))
			return false;

		Token symbolInLiteral = lexer.nextToken();
		if (symbolInLiteral.getValue().toString().equals("\"")) {
			lexer.setState(LexerState.STRING);
		}
		Token jmbag = lexer.nextToken();
		Token symbolOutLiteral = lexer.nextToken();
		if (symbolOutLiteral.getValue().toString().equals("\"")) {
			lexer.setState(LexerState.BASIC);
		}
		if (lexer.nextToken().getType() == TokenType.EOF) {
			this.jmbag = jmbag.getValue().toString();
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Metoda vraća JMBAG iz upita ako je taj upit bio direktan
	 * 
	 * @return JMBAG iz upita
	 * @throws IllegalStateException ako direktni upit nije postojao
	 */
	public String getQueriedJMBAG() {
		if (isDirectQuery())
			return jmbag;
		throw new IllegalStateException("Tražili ste jmbag iz direktnog upita, ali direktnog upita nije bilo!");
	}

	/**
	 * Metoda od predanog upita radi uvjetni izraz
	 * 
	 * @return uvjetni izraz
	 */
	public List<ConditionalExpression> getQuery() {
		List<ConditionalExpression> list = new ArrayList<>();

		if (isDirectQuery()) {
			list.add(new ConditionalExpression(FieldValueGetters.JMBAG, this.jmbag, null));
			return list;
		}
		lexer.reset();
		token = lexer.nextToken();

		while (token.getType() != TokenType.EOF) {

			if (token.getValue().equals("jmbag")) {
				list.add(createConditionalExpression(FieldValueGetters.JMBAG));
			} else if (token.getValue().equals("lastName")) {
				list.add(createConditionalExpression(FieldValueGetters.LAST_NAME));
			} else if (token.getValue().equals("firstName")) {
				list.add(createConditionalExpression(FieldValueGetters.FIRST_NAME));
			} else if (token.getType() == TokenType.AND) {
				token = lexer.nextToken();
				continue;
			} else {
				throw new IllegalArgumentException("Krivi unos!");
			}
		}
		return list;
	}

	/**
	 * Metoda radi uvjetni izraz tipa {@link ConditionalExpression}
	 * 
	 * @param fieldValueGetter JMBAG, ime ili prezime
	 * @return uvjetni izraz
	 * @throws IllegalArgumentException ako je nevaljan operator
	 */
	private ConditionalExpression createConditionalExpression(IFieldValueGetter fieldValueGetter) {
		IFieldValueGetter fvg = fieldValueGetter;
		IComparisonOperator co = null;
		String stringLiteral = "";

		while (token.getType() != TokenType.AND) {

			token = lexer.nextToken();
			if (TokenType.EOF == token.getType()) {
				break;
			}
			if (token.getType() == TokenType.OPERATOR) {
				switch (token.getValue().toString()) {
				case "<":
					co = ComparisonOperators.LESS;
					break;
				case "<=":
					co = ComparisonOperators.LESS_OR_EQUALS;
					break;
				case ">":
					co = ComparisonOperators.GREATER;
					break;
				case ">=":
					co = ComparisonOperators.GREATER_OR_EQUALS;
					break;
				case "=":
					co = ComparisonOperators.EQUALS;
					break;
				case "!=":
					co = ComparisonOperators.NOT_EQUALS;
					break;
				case "LIKE":
					co = ComparisonOperators.LIKE;
					break;
				default:
					throw new IllegalArgumentException("Očekuje se operator nakon ključne riječi!);");
				}
			}
			if (token.getValue().toString().equals("\"")) {
				lexer.setState(LexerState.STRING);
				token = lexer.nextToken();
				while (!token.getValue().toString().equals("\"")) {
					stringLiteral = token.getValue().toString();
					token = lexer.nextToken();
				}
				lexer.setState(LexerState.BASIC);
			}
		}
		return new ConditionalExpression(fvg, stringLiteral, co);
	}
}
