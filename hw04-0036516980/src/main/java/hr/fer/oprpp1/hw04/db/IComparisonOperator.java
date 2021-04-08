package hr.fer.oprpp1.hw04.db;

/**
 * Sučelje koje ima metodu satisfied koja ispituje je li 2 predana argumenta
 * tipa string zadovoljavaju uvjete
 * 
 * @author vedran
 *
 */
public interface IComparisonOperator {
	/**
	 * ispituje je li 2 predana argumenta tipa string zadovoljavaju uvjete
	 * 
	 * @param value1 argument 1
	 * @param value2 argument 2
	 * @return <code>true</code> ako zadovoljavaju, inače <code>false</code>
	 */
	public boolean satisfied(String value1, String value2);
}
