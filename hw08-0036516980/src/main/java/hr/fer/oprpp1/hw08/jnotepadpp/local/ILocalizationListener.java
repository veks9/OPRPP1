package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * Sucelje predstavlja listenera koji se informira kad se promijeni jezik
 * @author vedran
 *
 */
public interface ILocalizationListener {
	/**
	 * Metoda koja se zove kad se jezik promijeni
	 */
	void localizationChanged();
}
