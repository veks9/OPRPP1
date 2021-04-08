package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa predstavlja most izmedu singletion providera i okvira
 * @author vedran
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	private boolean connected;
	private ILocalizationProvider parent;
	private ILocalizationListener listener = () -> {
		getListeners().forEach(l -> l.localizationChanged());
	};

	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.parent = provider;
	}

	@Override
	public String getString(String key) {
		return parent.getString(key);
	}

	/**
	 * Metoda spaja listenera na providera
	 */
	public void connect() {
		if (connected)
			return;
		parent.addLocalizationListener(listener);
		connected = true;
	}

	/**
	 * Metoda odspaja listenera sa providera
	 */
	public void disconnect() {
		if (!connected)
			return;
		parent.removeLocalizationListener(listener);
		connected = false;
	}

	@Override
	public String getCurrentLanguage() {
		return parent.getCurrentLanguage();
	}
}
