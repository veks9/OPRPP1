package hr.fer.oprpp1.hw08.jnotepadpp.models;

import java.awt.BorderLayout;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;

/**
 * Klasa predstavlja implementaciju {@link MultipleDocumentModel}. 
 * Nasljeduje {@link JTabbedPane} i u sebi ima internu kolekciju 
 * {@link SingleDocumentModel}a 
 * @author vedran
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	List<SingleDocumentModel> singleDocumentModels = new ArrayList<>();
	SingleDocumentModel currentDocument;
	List<MultipleDocumentListener> listeners = new ArrayList<>();
	private LocalizationProvider provider = LocalizationProvider.getInstance();
	private SingleDocumentListener changeListener;
	private Icon notModifiedIcon;
	private Icon modifiedIcon;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DefaultMultipleDocumentModel() {
		this.notModifiedIcon = getIcon("../icons/Basic_green_dot.png");
		this.modifiedIcon = getIcon("../icons/Basic_red_dot.png");
		addChangeListener(e -> {
			changeCurrentDocument(getSelectedIndex() == -1 ? null : singleDocumentModels.get(getSelectedIndex()));
		});

		this.changeListener = new SingleDocumentListener() {

			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				if (getNumberOfDocuments() == 0)
					return;
				if (model.isModified()) {
					setIconAt(getSelectedIndex(), modifiedIcon);
				} else {
					setIconAt(getSelectedIndex(), notModifiedIcon);
				}
			}

			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				setToolTipTextAt(getSelectedIndex(), model.getFilePath().toAbsolutePath().normalize().toString());
				setTitleAt(getSelectedIndex(), model.getFilePath().getFileName().toString());

			}
		};
	}

	/**
	 * Metoda dohvaca i vraca ikonicu
	 * @param path mjesto sa kojeg se dohvaca ikonica
	 * @return
	 */
	private Icon getIcon(String path) {
		try (InputStream is = this.getClass().getResourceAsStream(path)) {
			if (is == null) {
				throw new IllegalArgumentException("Ne postoji datoteka na zadanoj putanji!");
			}
			byte[] bytes = is.readAllBytes();
			return new ImageIcon(bytes);
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return singleDocumentModels.iterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel newDocument = new DefaultSingleDocumentModel(null, null);
		registerDocument(newDocument);
		changeCurrentDocument(newDocument);
		return newDocument;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		if (path == null)
			throw new NullPointerException("Staza ne smije biti null!");

		for (SingleDocumentModel model : singleDocumentModels) {
			if (model.getFilePath() != null && model.getFilePath().equals(path)) {
				setSelectedIndex(singleDocumentModels.indexOf(model));
				currentDocument = model;
				return currentDocument;
			}
		}

		String input;
		try {
			input = Files.readString(path);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, provider.getString("loaderrormessage"), provider.getString("loaderror"),
					JOptionPane.ERROR_MESSAGE);
			return null;
		}

		SingleDocumentModel newModel = new DefaultSingleDocumentModel(path, input);

		registerDocument(newModel);
		return newModel;
	}

	/**
	 * Pomocna metoda koja dodaje tab kad se on otvori na bilo koji nacin
	 * @param model
	 */
	private void registerDocument(SingleDocumentModel model) {
		singleDocumentModels.add(model);
		String title = model.getFilePath() == null ? provider.getString("untitled")
				: model.getFilePath().getFileName().toString();

		JPanel panel = new JPanel(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(model.getTextComponent());
		panel.add(scrollPane, BorderLayout.CENTER);

		Path filePath = model.getFilePath();

		if (filePath == null) {
			addTab(title, modifiedIcon, panel, provider.getString("filenotsaved"));
			model.setModified(true);
			changeCurrentDocument(model);
		} else {
			addTab(title, notModifiedIcon, panel, model.getFilePath().toAbsolutePath().normalize().toString());
			changeCurrentDocument(model);
			model.setModified(false);
		}

		setSelectedIndex(singleDocumentModels.size() - 1);
		listeners.forEach(l -> l.documentAdded(model));

	}

	/**
	 * Pomocna metoda koja mijenja sve potrebno kad se mijenja trenutni dokument
	 * @param newCurrent
	 */
	private void changeCurrentDocument(SingleDocumentModel newCurrent) {
		if (newCurrent == null) {
			currentDocument = null;
			return;
		}
		if (currentDocument == null) {
			currentDocument = newCurrent;
			listeners.forEach(l -> l.currentDocumentChanged(null, newCurrent));
		} else {
			SingleDocumentModel previousModel = currentDocument;
			previousModel.removeSingleDocumentListener(changeListener);
			currentDocument = newCurrent;
			listeners.forEach(l -> l.currentDocumentChanged(previousModel, newCurrent));
		}
		currentDocument.addSingleDocumentListener(changeListener);
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		if (newPath == null) {
			newPath = model.getFilePath();
		} else if (Files.exists(newPath)) {
			JOptionPane.showMessageDialog(this, provider.getString("existingfilemessage"),
					provider.getString("existingfile"), JOptionPane.WARNING_MESSAGE);
			return;
		}

		byte[] data = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
		try {
			Files.write(newPath, data);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(this, provider.getString("saveerrormessage"), provider.getString("saveerror"),
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		JOptionPane.showMessageDialog(this, provider.getString("savesuccessmessage"), provider.getString("savesuccess"),
				JOptionPane.INFORMATION_MESSAGE);
		model.setModified(false);
		if (model.getFilePath() == null)
			model.setFilePath(newPath);
		changeListener.documentFilePathUpdated(model);
		changeListener.documentModifyStatusUpdated(model);
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		singleDocumentModels.remove(model);

		if (singleDocumentModels.size() == 0) {
			currentDocument = null;
		}
		remove(getSelectedIndex());

		listeners.forEach(l -> l.documentRemoved(model));
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);

	}

	@Override
	public int getNumberOfDocuments() {
		return singleDocumentModels.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return singleDocumentModels.get(index);
	}

}
