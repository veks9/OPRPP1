package hr.fer.oprpp1.hw08.jnotepadpp.models;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Klasa predstavlja implementaciju {@link SingleDocumentModel}
 * @author vedran
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel{

	private JTextArea textEditor;
	private Path filePath;
	private List<SingleDocumentListener> listeners = new ArrayList<>();
	private boolean modified;
	
	public DefaultSingleDocumentModel(Path filePath, String content) {
		this.filePath = filePath;
		this.textEditor = new JTextArea(content);
		this.textEditor.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				setModified(true);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				setModified(true);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				setModified(true);
			}
		});
	}
	
	@Override
	public JTextArea getTextComponent() {
		return textEditor;
	}

	@Override
	public Path getFilePath() {
		return filePath;
	}

	@Override
	public void setFilePath(Path path) {
		if(path == null)
			throw new NullPointerException("Staza do datoteke ne smije biti null!");
	
		filePath = path;
		listeners.forEach(l -> l.documentFilePathUpdated(this));
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		if(this.modified == modified) 
			return;
		this.modified = modified;
		listeners.forEach(l -> l.documentModifyStatusUpdated(this));
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);		
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}

}
