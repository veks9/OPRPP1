package hr.fer.oprpp1.hw04.db;

/**
 * Klasa predstavlja implementaciju operatora usporedivanja
 * 
 * @author vedran
 *
 */
public class ComparisonOperators {
	public static final IComparisonOperator LESS = (value1, value2) -> value1.compareTo(value2) < 0;
	public static final IComparisonOperator LESS_OR_EQUALS = (value1, value2) -> value1.compareTo(value2) <= 0;
	public static final IComparisonOperator GREATER = (value1, value2) -> value1.compareTo(value2) > 0;
	public static final IComparisonOperator GREATER_OR_EQUALS = (value1, value2) -> value1.compareTo(value2) >= 0;
	public static final IComparisonOperator EQUALS = (value1, value2) -> value1.compareTo(value2) == 0;
	public static final IComparisonOperator NOT_EQUALS = (value1, value2) -> value1.compareTo(value2) != 0;
	public static final IComparisonOperator LIKE = (value1, value2) -> {
		char[] data = value2.toCharArray();
		if (value2.indexOf('*') == 0) {
			if (value1.endsWith(value2.substring(1, data.length - 1)))
				return true;
			else
				return false;
		} else if (value2.indexOf('*') == value2.length() - 1) {
			if (value1.startsWith(value2.substring(0, data.length - 1)))
				return true;
			else
				return false;
		} else {
			int index = value2.indexOf('*');
			if (value1.startsWith(value2.substring(0, index - 1))
					&& value1.endsWith(value2.substring(index + 1, value2.length() - 1))
					&& value1.length() >= value2.length() - 1)
				return true;
			else
				return false;
		}
	};
}
