package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Predstavlja apstraktni model {@link LocalizationProvider}-a, zna dodavati i micati
 * listenere i informirati ih kad je doslo do promjene
 * @author vedran
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider{

	private List<ILocalizationListener> listeners = new ArrayList<>();
	
	/**
	 * Vraca listu listenera koji su dodani
	 * @return
	 */
	public List<ILocalizationListener> getListeners() {
		return listeners;
	}

	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		listeners.add(listener);
	}
	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		listeners.remove(listener);
	}
	
	/**
	 * Informira sve listenere da se promjena dogodila
	 */
	public void fire() {
		listeners.forEach(l -> l.localizationChanged());
	}
}
