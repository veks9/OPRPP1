package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.JMenu;

/**
 * Klasa predstavlja meni koja je osjetljiv na promjene jezika
 * @author vedran
 *
 */
public class LJMenu extends JMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ILocalizationProvider provider;
	private String key;

	public LJMenu(String key, ILocalizationProvider provider) {
		this.provider = provider;
		this.key = key;

		setText(this.provider.getString(this.key));
		this.provider.addLocalizationListener(() -> {
			setText(this.provider.getString(this.key));
		});
	}
}
