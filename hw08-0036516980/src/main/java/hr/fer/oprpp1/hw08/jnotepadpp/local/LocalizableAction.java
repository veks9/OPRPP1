package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * Apstraktna klasa koja predstavlja akciju cije se ime mijenja
 * kada korisnik promijeni jezik
 * @author vedran
 *
 */
public abstract class LocalizableAction extends AbstractAction {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String key;
	ILocalizationProvider provider;
	
	
	public LocalizableAction(String key, ILocalizationProvider provider) {
		this.key = key;
		this.provider = provider;
		
		putValue(NAME, provider.getString(this.key));
		putValue(SHORT_DESCRIPTION, provider.getString(this.key + "sd"));
		this.provider.addLocalizationListener(() -> {
			putValue(NAME, provider.getString(this.key));
			putValue(SHORT_DESCRIPTION, provider.getString(this.key + "sd"));
		});
	}
	
	@Override
	public abstract void actionPerformed(ActionEvent e);
}
