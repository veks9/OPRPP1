package hr.fer.oprpp1.hw04.db;

/**
 * Klasa predstavlja gettere za argumente tipa {@link StudentRecord}
 * 
 * @author vedran
 *
 */
public class FieldValueGetters {
	public static final IFieldValueGetter FIRST_NAME = record -> record.getFirstName();
	public static final IFieldValueGetter LAST_NAME = record -> record.getLastName();
	public static final IFieldValueGetter JMBAG = record -> record.getJmbag();
}
