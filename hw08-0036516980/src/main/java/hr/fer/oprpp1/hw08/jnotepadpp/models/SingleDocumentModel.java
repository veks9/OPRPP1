package hr.fer.oprpp1.hw08.jnotepadpp.models;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Sucelje predstavlja model dokumenta
 * @author vedran
 *
 */
public interface SingleDocumentModel {
	/**
	 * Metoda vraca editor tipa {@link JTextArea}
	 * @return
	 */
	JTextArea getTextComponent();

	/**
	 * Metoda vraca stazu modela
	 * @return staza modela
	 */
	Path getFilePath();

	/**
	 * Metoda postavlja stazu modela
	 * @param path staza modela
	 */
	void setFilePath(Path path);

	/**
	 * Metoda vraca <code>true</code> ako je model modificiran, inače <code>false</code>
	 * @return
	 */
	boolean isModified();

	/**
	 * Metoda postavlja status modifikacije
	 * @param modified
	 */
	void setModified(boolean modified);

	/**
	 * Metoda dodaje {@link SingleDocumentListener}a
	 * @param l
	 */
	void addSingleDocumentListener(SingleDocumentListener l);

	/**
	 * Metoda miče {@link SingleDocumentListener}a
	 * @param l
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}
