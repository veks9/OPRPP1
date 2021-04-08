package hr.fer.oprpp1.hw04.db;

/**
 * Klasa predstavlja uvjetni izraz
 * 
 * @author vedran
 *
 */
public class ConditionalExpression {
	private IFieldValueGetter fieldValueGetter;
	private String literal;
	private IComparisonOperator comparisonOperator;

	/**
	 * Konstruktor koji radi uvjetni izraz iz predanih parametara
	 * 
	 * @param fieldValueGetter
	 * @param string             literal
	 * @param comparisonOperator
	 */
	public ConditionalExpression(IFieldValueGetter fieldValueGetter, String s, IComparisonOperator comparisonOperator) {
		this.fieldValueGetter = fieldValueGetter;
		this.literal = s;
		this.comparisonOperator = comparisonOperator;
	}

	/**
	 * Getter za comparisonOperator
	 * 
	 * @return comparisonOperator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

	/**
	 * Getter za string literal
	 * 
	 * @return string literal
	 */
	public String getLiteral() {
		return literal;
	}

	/**
	 * Getter za fieldValueGetter
	 * 
	 * @return fieldValueGetter
	 */
	public IFieldValueGetter getFieldValueGetter() {
		return fieldValueGetter;
	}
}
