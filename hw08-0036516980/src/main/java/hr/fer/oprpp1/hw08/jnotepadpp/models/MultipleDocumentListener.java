package hr.fer.oprpp1.hw08.jnotepadpp.models;

/**
 * Sucelje predstavlja listenera koji se zove kad se mijenja trenutni dokument, kad se
 * dodaje dokument i kad se mice dokument
 * @author vedran
 *
 */
public interface MultipleDocumentListener {
	/**
	 * Metoda se zove kad se trenutni dokument mijenja
	 * @param previousModel onaj dokument s kojeg se promijenilo
	 * @param currentModel onaj dokument na koji se promijenilo
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

	/**
	 * Metoda se zove kad se doda dokument
	 * @param model dokument
	 */
	void documentAdded(SingleDocumentModel model);

	/**
	 * Metoda se zove kad se miče dokument
	 * @param model dokument
	 */
	void documentRemoved(SingleDocumentModel model);
}