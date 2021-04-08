package hr.fer.oprpp1.hw04.db;

import java.util.List;

/**
 * Klasa koja nad zadanom listom {@link ConditionalExpression}-a ispituje preko
 * metode accepts je li redak u tablici zadovoljio taj uvjet i vraća
 * <code>true</code> ako je, inače false
 * 
 * @author vedran
 *
 */
public class QueryFilter implements IFilter {
	private List<ConditionalExpression> list;

	/**
	 * Konstruktor koji prima listu uvjetnih izraza koje se trebaju ispitati za
	 * svaki redak tablice
	 * 
	 * @param list
	 */
	public QueryFilter(List<ConditionalExpression> list) {
		this.list = list;
	}

	@Override
	public boolean accepts(StudentRecord record) {
		for (ConditionalExpression expression : list) {

			if (expression.getComparisonOperator().satisfied(
					expression.getFieldValueGetter().get(record),
					expression.getLiteral()) == false)
				return false;
		}
		;
		return true;
	}
}
