package hr.fer.oprpp1.hw04.db;

/**
 * Sučelje koje ima metodu get koja vraća argument od {@link StudentRecord}
 * 
 * @author vedran
 *
 */
public interface IFieldValueGetter {
	/**
	 * Metoda vraća argument od {@link StudentRecord}
	 * 
	 * @param record predani redak tablice
	 * @return String argument
	 */
	public String get(StudentRecord record);
}
