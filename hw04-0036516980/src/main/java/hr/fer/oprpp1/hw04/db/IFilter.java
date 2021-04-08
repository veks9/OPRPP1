package hr.fer.oprpp1.hw04.db;

/**
 * Sučelje ima metodu accepts koja ispituje uvjet
 * 
 * @author vedran
 *
 */
public interface IFilter {
	/**
	 * Metoda ispituje uvjet i vraća <code>true</code> ako je uvjet ispunjen, inače
	 * <code>false</code>
	 * 
	 * @param record redak tablice
	 * @return <code>true</code> ako je uvjet ispunjen, inale <code>false</code>
	 */
	public boolean accepts(StudentRecord record);
}
