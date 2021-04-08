package hr.fer.oprpp1.hw08.jnotepadpp.models;

/**
 * Sucelje predstavlja listenera koji se obavijesti kad se mijenja dokument
 * @author vedran
 *
 */
public interface SingleDocumentListener {
	/**
	 * Metoda se zove kad se promijeni status modificiranja dokumenta
	 * @param model dokument
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);

	/**
	 * Metoda se zove kad se promijeni staza dokumenta
	 * @param model dokumtn
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}
