package hr.fer.oprpp1.hw08.jnotepadpp.models;

import java.nio.file.Path;

/**
 * Sucelje koje predstavlja model koji moze imati vise dokumenata
 * @author vedran
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
	/**
	 * Metoda radi novi dokument
	 * @return novi dokument
	 */
	SingleDocumentModel createNewDocument();

	/**
	 * Metoda dohvaca trenutni dokument
	 * @return trenutni dokument
	 */
	SingleDocumentModel getCurrentDocument();

	/**
	 * Metoda sa staze dohvaca i ucitava dokument
	 * @param path
	 * @return
	 */
	SingleDocumentModel loadDocument(Path path);

	/**
	 * Metoda sprema dokument na danu stazu
	 * @param model dokument
	 * @param newPath staza
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);

	/**
	 * Metoda zatvara trenutni dokument
	 * @param model dokument
	 */
	void closeDocument(SingleDocumentModel model);

	/**
	 * Metoda dodaje {@link MultipleDocumentListener} u internu listu
	 * @param l
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Metoda miče {@link MultipleDocumentListener} iz interne liste
	 * @param l
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Metoda vraca broj {@link SingleDocumentModel}a u internoj listi
	 * @return
	 */
	int getNumberOfDocuments();

	/**
	 * Metoda dohvaca {@link SingleDocumentModel} na indexu iz interne liste
	 * @param index 
	 * @return dokument na indexu
	 */
	SingleDocumentModel getDocument(int index);
}
