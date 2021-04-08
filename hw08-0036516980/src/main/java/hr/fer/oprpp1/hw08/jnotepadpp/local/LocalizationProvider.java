package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Singleton koji ima svoj resource bundle i dohvaca prijevode po kljucevima
 * @author vedran
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider{
	private String language;
	private ResourceBundle bundle;
	private final static String DEFAULT_LANGUAGE = "en";
	private static final LocalizationProvider provider = new LocalizationProvider();
	
	private LocalizationProvider() {
		this.language = DEFAULT_LANGUAGE;
		Locale locale = Locale.forLanguageTag(language);
		this.bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi", locale);
	}
	
	/**
	 * Metoda vraca instancu {@link LocalizationProvider}
	 * @return
	 */
	public static LocalizationProvider getInstance() {
		return provider;
	}
	
	/**
	 * Metoda postavlja jezik
	 * @param language jezik
	 */
	public void setLanguage(String language) {
		if(language == null)
			throw new NullPointerException("Jezik ne smije biti null!");
		
		this.language = language;
		Locale locale = Locale.forLanguageTag(language);
		this.bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi", locale);
		fire();
	}
	
	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		return language;
	}
}
