package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Klasa predstavlja poveznicu izmedu prozora i providera. Moze ga spojiti i odspojiti
 * @author vedran
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	private JFrame frame;

	public FormLocalizationProvider(ILocalizationProvider parent, JFrame frame) {
		super(parent);
		this.frame = frame;
		this.frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
			
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
		});
	}
}
