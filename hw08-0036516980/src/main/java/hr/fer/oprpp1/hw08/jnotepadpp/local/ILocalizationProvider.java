package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * Sucelje predstavlja providera koji moze spojiti i odspojiti listenera
 * i dohvatiti prijevod po ključu 
 * @author vedran
 *
 */
public interface ILocalizationProvider {
	/**
	 * Metoda dodaje predanog listenera u internu listu listenera
	 * @param listener
	 */
	void addLocalizationListener(ILocalizationListener listener);
	/**
	 * Metoda miče predanog listenera iz interne liste listenera
	 * @param listener
	 */
	void removeLocalizationListener(ILocalizationListener listener);
	/**
	 * Metoda primi ključ i dohvati prijevod
	 * @param key ključ
	 * @return prijevod
	 */
	String getString(String key);
	/**
	 * Metoda vraća trenutni jezik
	 * @return
	 */
	String getCurrentLanguage();
}
