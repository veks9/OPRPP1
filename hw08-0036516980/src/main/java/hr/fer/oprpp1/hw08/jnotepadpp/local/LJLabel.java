package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.JLabel;

/**
 * Klasa predstavlja labelu koja je osjetljiva na promjene jezika
 * @author vedran
 *
 */
public class LJLabel extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] keys;
	private String[] texts;
	private int[] values;
	private ILocalizationProvider provider;
	
	public LJLabel(ILocalizationProvider provider, String... keys) {
		this.keys = keys;
		this.provider = provider;
		
		texts = new String[keys.length];
		updateTexts();
		
		this.provider.addLocalizationListener(() -> {
			updateTexts();
			update(values);
		});
	}
	
	/**
	 * Metoda ažurira labele uz pomoc providera
	 */
	private void updateTexts() {
		for(int i = 0; i < keys.length; i++) {
			texts[i] = provider.getString(keys[i]);
		}
		
	}

	/**
	 * Metoda azurira labele
	 * @param values 
	 */
	public void update(int... values) {
		if(values == null) return;
		if(values.length != keys.length) {
			throw new IllegalArgumentException("Broj ključeva i vrijednosti mora biti jednak");
		}
		this.values = values;
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < keys.length; i++) {
			sb.append(texts[i] + " : " + (values[i] == -1 ? "" : values[i]) + "  ");
		}
		setText(sb.toString());
	}
	

}
