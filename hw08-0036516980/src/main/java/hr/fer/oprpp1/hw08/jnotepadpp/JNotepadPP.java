package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LJLabel;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LJMenu;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.models.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

/**
 * Klasa predstavlja implementaciju Notepad++ text editora
 * @author vedran
 *
 */
public class JNotepadPP extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private Path openedFilePath;
	private MultipleDocumentModel multipleDocumentModel;
	private JPanel statusBarPanel;
	private CaretListener caretListener;
	private SingleDocumentListener modifiedListener;
	private String clipboard = "";
	private FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
	private Collator collator = Collator.getInstance(Locale.forLanguageTag(flp.getString("collator")));;

	public JNotepadPP() {

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(0, 0);
		setSize(600, 600);

		initGUI();
	}

	/**
	 * Pomoćna metoda koja ispituje jesu li sve otvorene datoteke spremljene, ako nisu onda iskace popup koji preusmjerava na spremanje, nazad u 
	 * aplikaciju ili izlaz bez spremanja
	 */
	private void isUnsavedWork() {
		Iterator<SingleDocumentModel> iter = multipleDocumentModel.iterator();

		while (iter.hasNext()) {
			SingleDocumentModel document = iter.next();
			if (document.isModified()) {
				String[] options = new String[] { flp.getString("yes"), flp.getString("no"), flp.getString("cancel") };
				int result = JOptionPane.showOptionDialog(JNotepadPP.this, flp.getString("closingsavemessage"),
						flp.getString("warning"), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
						options, options[0]);
				switch (result) {
				case JOptionPane.CLOSED_OPTION:
					return;
				case 0:
					if(!saveDocument(document))
						return;
					break;
				case 1:
					dispose();
					return;
				case 2:
					return;
				}
			}
		}
		dispose();
	}

	/**
	 * Metoda koja inicijalizira izgled Notepad++-a
	 */
	private void initGUI() {
		this.getContentPane().setLayout(new BorderLayout());
		addListeners();
		createActions();
		createMenus();
		createToolbars();

		JPanel panel = new JPanel(new BorderLayout());
		panel.add((JTabbedPane) multipleDocumentModel, BorderLayout.CENTER);
		statusBarPanel = addStatusBar(panel);

		getContentPane().add(panel);
		setFrameTitle();

	}

	/**
	 * Pomoćna metoda koja dodaje listenere na komponente
	 */
	private void addListeners() {
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				isUnsavedWork();
			}
		});

		multipleDocumentModel = new DefaultMultipleDocumentModel();
		multipleDocumentModel.addMultipleDocumentListener(new MultipleDocumentListener() {

			@Override
			public void documentRemoved(SingleDocumentModel model) {
				setFrameTitle();
				updateStatusBar(model.getTextComponent());
				setEnableDisableActions(multipleDocumentModel.getNumberOfDocuments() != 0, closeDocumentAction,
						saveAsDocumentAction, saveDocumentAction);
				if (multipleDocumentModel.getNumberOfDocuments() == 0)
					setEnableDisableActions(false, copyAction, cutAction, pasteAction);
			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
				setFrameTitle();
			}

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				currentChanged(currentModel, previousModel);
			}
		});

		flp.addLocalizationListener(() -> {
			setFrameTitle();
		});

		caretListener = new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				boolean b = multipleDocumentModel.getCurrentDocument().getTextComponent().getCaret().getDot()
						- multipleDocumentModel.getCurrentDocument().getTextComponent().getCaret().getMark() != 0;
				setEnableDisableActions(b, uppercaseAction, lowerCaseAction, toggleCaseAction, copyAction, cutAction, sortAscending, sortDescending, uniqueAction);
				setEnableDisableActions(clipboard.length() != 0, pasteAction);
				updateStatusBar((JTextArea) e.getSource());
			}
		};

		modifiedListener = new SingleDocumentListener() {
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				setEnableDisableActions(JNotepadPP.this.multipleDocumentModel.getCurrentDocument().isModified(),
						saveDocumentAction);
			}

			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
			}
		};
	}

	/**
	 * Pomoćna metoda koja se bavi zamjenom svih potrebnih svojstava kad se mijenja trenutni otvoreni model(kad se mijenja tab ili radi novi ili gasi postojeci)
	 * @param currentModel trenutni model tipa {@link SingleDocumentModel}
	 * @param previousModel model koji je bio otvoren do prije ove zamjene
	 */
	private void currentChanged(SingleDocumentModel currentModel, SingleDocumentModel previousModel) {
		setFrameTitle();

		if (previousModel != null) {
			previousModel.removeSingleDocumentListener(modifiedListener);
		}
		currentModel.addSingleDocumentListener(modifiedListener);

		if (previousModel != null) {
			previousModel.getTextComponent().removeCaretListener(caretListener);
		}

		updateStatusBar(currentModel.getTextComponent());

		setEnableDisableActions(multipleDocumentModel.getCurrentDocument().isModified(), saveDocumentAction,
				saveAsDocumentAction);
		setEnableDisableActions(!multipleDocumentModel.getCurrentDocument().getTextComponent().getText().isEmpty(),
				copyAction, cutAction);
		setEnableDisableActions(clipboard.length() != 0, pasteAction);
		setEnableDisableActions(multipleDocumentModel.getNumberOfDocuments() != 0, closeDocumentAction);
		currentModel.getTextComponent().addCaretListener(caretListener);

	}

	/**
	 Akcija koja dodaje novi prazni dokument u editor
	 */
	private Action newDocumentAction = new LocalizableAction("new", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			multipleDocumentModel.createNewDocument();
			multipleDocumentModel.getCurrentDocument().getTextComponent().addCaretListener(new CaretListener() {

				@Override
				public void caretUpdate(CaretEvent e) {
					updateStatusBar((JTextArea) e.getSource());
				}
			});

		}
	};

	/**
	 * Akcija koja otvara postojecu datoteku s diska u editor
	 */
	private Action openDocumentAction = new LocalizableAction("open", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle(flp.getString("open"));
			if (fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();
			if (!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(JNotepadPP.this,
						flp.getString("file") + " " + fileName.getAbsolutePath() + flp.getString("notexist"),
						flp.getString("error"), JOptionPane.ERROR_MESSAGE);
				return;
			}
			multipleDocumentModel.loadDocument(filePath);
		}
	};

	/**
	 * Akcija koja sprema promjene
	 */
	private Action saveDocumentAction = new LocalizableAction("save", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel document = multipleDocumentModel.getCurrentDocument();
			saveDocument(document);

		}
	};

	/**
	 * Akcija koja sprema promjene kroz fileChooser
	 */
	private final Action saveAsDocumentAction = new LocalizableAction("saveas", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel document = multipleDocumentModel.getCurrentDocument();
			saveWithFileChooser(document);
			setFrameTitle();
		}
	};

	/**
	 * Akcija koja zatvara tab
	 */
	private final Action closeDocumentAction = new LocalizableAction("close", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel document = multipleDocumentModel.getCurrentDocument();
			if (document == null)
				return;
			if (document.isModified()) {
				
				String[] options = new String[] { flp.getString("yes"), flp.getString("no"), flp.getString("cancel") };
				int input =	JOptionPane.showOptionDialog(JNotepadPP.this, flp.getString("closingsavemessage"),
							flp.getString("warning"), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
							options, options[0]);
				
				switch (input) {
				case -1:
					return;
				case 0:
					if(!saveDocument(document))
						return;
					break;
				case 2:
					return;
				}
			}
			multipleDocumentModel.closeDocument(document);
		}
	};

	/**
	 * Akcija koja kopira označeni tekst
	 */
	private final Action copyAction = new LocalizableAction("copy", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			cutOrCopy("copy");
		}
	};

	/**
	 * Akcija koja izreže označeni tekst
	 */
	private final Action cutAction = new LocalizableAction("cut", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			cutOrCopy("cut");
		}
	};

	/**
	 * Pomoćna metoda koja selektirani tekst izreže ili kopira
	 * @param action "copy" za kopiranje, "cut" za izreži
	 */
	private void cutOrCopy(String action) {
		if(!(action.equalsIgnoreCase("copy") || action.equalsIgnoreCase("cut")))
			return;
		
		JTextArea editor = multipleDocumentModel.getCurrentDocument().getTextComponent();
		Document doc = editor.getDocument();
		int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
		if (len == 0)
			return;
		int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());

		try {
			setClipboard(doc.getText(offset, len));
			if (action.equalsIgnoreCase("cut"))
				doc.remove(offset, len);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Akcija koja zalijepi tekst koji je bio kopiran ili izrezan
	 */
	private final Action pasteAction = new LocalizableAction("paste", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea editor = multipleDocumentModel.getCurrentDocument().getTextComponent();
			Document doc = editor.getDocument();
			int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
			try {
				doc.insertString(offset, getClipboard(), null);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
	};

	/**
	 * Akcija koja prikazuje broj svih znakova, broj znakova koji nisu bjeline i broj redaka
	 */
	private final Action staticticalInfoAction = new LocalizableAction("info", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel doc = multipleDocumentModel.getCurrentDocument();
			if (doc == null) {
				JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString("noactivefilemessage"),
						flp.getString("noactivefile"), JOptionPane.WARNING_MESSAGE);
				return;
			}
			;
			char[] text = multipleDocumentModel.getCurrentDocument().getTextComponent().getText().toCharArray();
			int chars = 0;
			int lines = 0;
			int nonBlanks = 0;
			for (int i = 0; i < text.length; i++) {
				chars++;
				if (!Character.isWhitespace(text[i]))
					nonBlanks++;
				if (text[i] == '\n')
					lines++;
			}
			lines = chars > 0 ? lines + 1 : lines;
			JOptionPane.showMessageDialog(JNotepadPP.this,
					String.format(flp.getString("infomessageformat"), chars, nonBlanks, lines),
					flp.getString("infotitle"), JOptionPane.INFORMATION_MESSAGE);
		}
	};

	/**
	 * Akcija koja označeni tekst pretvori u velika slova
	 */
	private final Action uppercaseAction = new LocalizableAction("uppercase", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			changeCase("upper");
		}
	};

	/**
	 * Akcija koja označeni tekst pretvori u mala slova
	 */
	private final Action lowerCaseAction = new LocalizableAction("lowercase", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			changeCase("lower");
		}
	};

	/**
	 * Akcija koja zamijeni velicinu slova oznalenom tekstu
	 */
	private final Action toggleCaseAction = new LocalizableAction("togglecase", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			changeCase("toggle");
		}
	};

	/**
	 * Akcija koja sortira označene retke rastuce
	 */
	private final Action sortAscending = new LocalizableAction("ascending", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			sort("asc");
		}
	};

	/**
	 * Akcija koja sortira označene retke padajuce
	 */
	private final Action sortDescending = new LocalizableAction("descending", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			sort("desc");
		}
	};
	
	/**
	 * Akcija koja izbacuje duplicirane linije u označenom tekstu
	 */
	private final Action uniqueAction = new LocalizableAction("unique", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (multipleDocumentModel.getNumberOfDocuments() == 0)
				return;
			JTextArea editor = multipleDocumentModel.getCurrentDocument().getTextComponent();
			try {
				int startingLine = editor
						.getLineOfOffset(Math.min(editor.getCaret().getDot(), editor.getCaret().getMark()));
				int endingLine = editor
						.getLineOfOffset(Math.max(editor.getCaret().getDot(), editor.getCaret().getMark()));
				removeDuplicates(startingLine, endingLine, editor);
			} catch (BadLocationException e2) {
				e2.printStackTrace();
			}
		}
	};
	
	/**
	 * Pomoćna metoda koja izbacuje duplikate
	 * @param startingLine pocetak oznacenog teksta
	 * @param endingLine kraj oznacenog teksta
	 * @param editor referenca na trenutni editor
	 */
	private void removeDuplicates(int startingLine, int endingLine, JTextArea editor) {
		Document doc = editor.getDocument();
		List<String> lines = getLines(startingLine, endingLine, editor, doc, true);
		addToDocument(startingLine, endingLine, editor, doc, new ArrayList<>(lines));
	}

	/**
	 * Pomoćna metoda koja mijenja velicinu slova oznacenom tekstu
	 * @param type "upper" za mijenjanje u velika slova, "lower" u mala i "toggle" za zamjenu velicine svim
	 */
	private void changeCase(String type) {
		JTextArea editor = multipleDocumentModel.getCurrentDocument().getTextComponent();
		Document doc = editor.getDocument();
		int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
		int offset = 0;
		if (len != 0) {
			offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
		} else {
			len = doc.getLength();
		}
		try {
			String text = doc.getText(offset, len);
			text = changeCase(text, type);
			doc.remove(offset, len);
			doc.insertString(offset, text, null);
		} catch (BadLocationException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Pomoćna metoda koja mijenja velicinu slova oznacenom tekstu
	 * @param text oznaceni tekst
	 * @param type "upper" za mijenjanje u velika slova, "lower" u mala i "toggle" za zamjenu velicine svim
	 */
	private String changeCase(String text, String type) {
		if (type.equalsIgnoreCase("upper"))
			return text.toUpperCase();
		if (type.equalsIgnoreCase("lower"))
			return text.toLowerCase();

		char[] chars = text.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (Character.isLowerCase(c)) {
				chars[i] = Character.toUpperCase(c);
			} else if (Character.isUpperCase(c)) {
				chars[i] = Character.toLowerCase(c);
			}
		}
		return new String(chars);
	}

	/**
	 * Akcija koja izlazi iz aplikacije
	 */
	private Action exitAction = new LocalizableAction("exit", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			isUnsavedWork();

		}
	};

	/**
	 * Pomocna metoda koja kreira akcije i njihove opisnike
	 */
	private void createActions() {
		newDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift N"));
		newDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);

		openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);

		saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocumentAction.setEnabled(false);

		saveAsDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift S"));
		saveAsDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveAsDocumentAction.setEnabled(false);

		closeDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		closeDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		closeDocumentAction.setEnabled(false);

		copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control K"));
		copyAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		copyAction.setEnabled(false);

		cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		cutAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		cutAction.setEnabled(false);

		pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control P"));
		pasteAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
		pasteAction.setEnabled(false);

		staticticalInfoAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		staticticalInfoAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);

		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);

		lowerCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F1"));
		lowerCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		lowerCaseAction.setEnabled(false);

		uppercaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F2"));
		uppercaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		uppercaseAction.setEnabled(false);

		toggleCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F3"));
		toggleCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		toggleCaseAction.setEnabled(false);
		
		
		sortAscending.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift A"));
		sortAscending.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		sortAscending.setEnabled(false);
		
		sortDescending.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift D"));
		sortDescending.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		sortDescending.setEnabled(false);
		
		uniqueAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		uniqueAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		uniqueAction.setEnabled(false);
		
	}

	/**
	 * Pomocna metoda koja kreira menije i dodaje sve potrebne akcije unutra
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();

		LJMenu fileMenu = new LJMenu("file", flp);
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(newDocumentAction));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(closeDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitAction));

		LJMenu editMenu = new LJMenu("edit", flp);
		menuBar.add(editMenu);

		editMenu.add(new JMenuItem(copyAction));
		editMenu.add(new JMenuItem(cutAction));
		editMenu.add(new JMenuItem(pasteAction));
		editMenu.addSeparator();
		editMenu.add(new JMenuItem(staticticalInfoAction));

		LJMenu languagesMenu = new LJMenu("languages", flp);
		menuBar.add(languagesMenu);

		JMenuItem en = new JMenuItem("English");
		en.addActionListener(e -> {
			LocalizationProvider.getInstance().setLanguage("en");
		});
		JMenuItem de = new JMenuItem("Deutsch");
		de.addActionListener(e -> {
			LocalizationProvider.getInstance().setLanguage("de");
			;
		});
		JMenuItem hr = new JMenuItem("Hrvatski");
		hr.addActionListener(e -> {
			LocalizationProvider.getInstance().setLanguage("hr");
		});
		languagesMenu.add(en);
		languagesMenu.add(de);
		languagesMenu.add(hr);

		LJMenu toolsMenu = new LJMenu("tools", flp);
		menuBar.add(toolsMenu);

		LJMenu changeCase = new LJMenu("changecase", flp);
		toolsMenu.add(changeCase);
		changeCase.add(new JMenuItem(lowerCaseAction));
		changeCase.add(new JMenuItem(uppercaseAction));
		changeCase.add(new JMenuItem(toggleCaseAction));

		LJMenu sortMenu = new LJMenu("sort", flp);
		toolsMenu.add(sortMenu);
		sortMenu.add(new JMenuItem(sortAscending));
		sortMenu.add(new JMenuItem(sortDescending));

		toolsMenu.add(new JMenuItem(uniqueAction));
		this.setJMenuBar(menuBar);
	}

	/**
	 * Pomocna metoda koja kreira toolbar i dodaje sve potrebne akcije unutra
	 */
	private void createToolbars() {
		JToolBar toolBar = new JToolBar(flp.getString("tools"));
		toolBar.setFloatable(true);

		toolBar.add(new JButton(newDocumentAction));
		toolBar.add(new JButton(openDocumentAction));
		toolBar.add(new JButton(saveDocumentAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(copyAction));
		toolBar.add(new JButton(cutAction));
		toolBar.add(new JButton(pasteAction));
		toolBar.add(new JButton(staticticalInfoAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(closeDocumentAction));
		
		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	/**
	 * Pomocna metoda koja sprema dokument
	 * @param document
	 */
	private boolean saveDocument(SingleDocumentModel document) {
		if (document.getFilePath() == null)
			return saveWithFileChooser(document);
		else {
			multipleDocumentModel.saveDocument(document, null);
			return true;
		}
	}

	/**
	 * Metoda koja otvara {@link JFileChooser} i tako omogucava spremanje
	 * @param document
	 */
	private boolean saveWithFileChooser(SingleDocumentModel document) {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Save document");
		if (jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString("nothingwassaved"), flp.getString("warning"),
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		openedFilePath = jfc.getSelectedFile().toPath();
		multipleDocumentModel.saveDocument(document, openedFilePath);
		return true;
	}

	/**
	 * Metoda koja postavlja naziv prozora
	 */
	private void setFrameTitle() {
		if (multipleDocumentModel.getCurrentDocument() == null) {
			setTitle("Notepad++");
		} else if (multipleDocumentModel.getCurrentDocument().getFilePath() == null) {
			setTitle(flp.getString("untitled") + " - Notepad++");
		} else {
			setTitle(multipleDocumentModel.getCurrentDocument().getFilePath().toAbsolutePath().normalize().toString()
					+ " - Notepad++");
		}
	}

	/**
	 * Metoda koja dohvaca vrijednost sa clipboarda
	 * @return
	 */
	public String getClipboard() {
		return clipboard;
	}

	/**
	 * Metoda koja stavlja vrijednost na clipboard
	 * @param clipboard
	 */
	public void setClipboard(String clipboard) {
		this.clipboard = clipboard;
	}

	/**
	 * Metoda koja dodaje status bar na prikaz
	 * @param panel panel
	 * @return panel
	 */
	private JPanel addStatusBar(JPanel panel) {
		JPanel p = new JPanel(new GridLayout(1, 3));
		LJLabel lengthLabel = new LJLabel(flp, "length");
		LJLabel lnColsSelLabel = new LJLabel(flp, new String[] { "ln", "col", "sel" });
		JLabel time = new JLabel("");
		time.setHorizontalAlignment(SwingConstants.RIGHT);
		new Clock(time);
		p.add(lengthLabel);
		p.add(lnColsSelLabel);
		p.add(time);
		p.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(150, 150, 150)));

		panel.add(p, BorderLayout.PAGE_END);
		return p;
	}

	/**
	 * Pomocna metoda koja azurira vrijednosti na status baru
	 * @param editor
	 */
	private void updateStatusBar(JTextArea editor) {
		int pos = editor.getCaret().getDot();
		try {
			int line = editor.getLineOfOffset(pos);
			int column = pos - editor.getLineStartOffset(line) + 1;
			line++;
			int selection = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
			((LJLabel) statusBarPanel.getComponent(0)).update(editor.getText().length());
			((LJLabel) statusBarPanel.getComponent(1)).update(line, column, selection);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Pomocna metoda koja omogucava ili onemogucava akcije
	 * @param b true ako se omogucava inace false
	 * @param actions vargars za akcije koje se trebaju omoguciti ili onemoguciti
	 */
	private void setEnableDisableActions(boolean b, Action... actions) {
		for (Action a : actions) {
			a.setEnabled(b);
		}
	}
	
	/**
	 * Metoda koja dodaje tekst u editor
	 * @param startingLine pocetak selektiranog teksta
	 * @param endingLine kraj selektiranog teksta
	 * @param editor referenca na otvoreni editor
	 * @param doc referenca na trenutni dokument
	 * @param lines linije u editoru
	 */
	private void addToDocument(int startingLine, int endingLine, JTextArea editor, 
			Document doc, List<String> lines) {
		try {
			int offset = editor.getLineStartOffset(startingLine);
			int end = editor.getLineEndOffset(endingLine);
			doc.remove(offset, end - offset);
			for(String s : lines) {
				doc.insertString(offset, s, null);
				offset += s.length();
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}	
	}

	/**
	 * Pomocna metoda koja dohvaca selektirane linije
	 * @param startingLine pocetak selektiranog teksta
	 * @param endingLine kraj selektiranog teksta
	 * @param editor referenca na otvoreni editor
	 * @param doc referenca na trenutni dokument
	 * @param unique true ako se traze samo jedinstene linije, inace false
	 * @return
	 */
	private List<String> getLines(int startingLine, int endingLine, 
			JTextArea editor, Document doc, boolean unique) {
		List<String> lines = new ArrayList<>();
		for(int i = startingLine; i <= endingLine; i++) {
			try {
				int start = editor.getLineStartOffset(i);
				int end = editor.getLineEndOffset(i);
				String line = doc.getText(start, end - start);
				
				if(unique && lines.contains(line)) continue;
				lines.add(doc.getText(start, end - start));
				
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
		return lines;
	}
	
	/**
	 * Pomoćna metoda koja dohvaca vrijednosti za sortiranje
	 * @param type "asc" za rastuce i "desc" za padajuce
	 */
	private void sort(String type) {
		if(multipleDocumentModel.getNumberOfDocuments() == 0) return;
		JTextArea editor = multipleDocumentModel.getCurrentDocument().getTextComponent();
		try {
			int startingLine = editor.getLineOfOffset(
					Math.min(editor.getCaret().getDot(), editor.getCaret().getMark()));
			int endingLine = editor.getLineOfOffset(
					Math.max(editor.getCaret().getDot(), editor.getCaret().getMark()));
			sortLines(startingLine, endingLine, editor, type);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metoda koja sortira linije
	 * @param startingLine pocetak selektiranog teksta
	 * @param endingLine kraj selektiranog teksta
	 * @param editor referenca na otvoreni editor
	 * @param type "asc" za rastuce i "desc" za padajuce
	 */
	private void sortLines(int startingLine, int endingLine, JTextArea editor, String type) {
		Document doc = editor.getDocument();
		List<String> lines = getLines(startingLine, endingLine, editor, doc, false);
		
		lines.sort(collator);
		if(type.equalsIgnoreCase("desc")) Collections.reverse(lines);
		addToDocument(startingLine, endingLine, editor, doc, lines);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new JNotepadPP().setVisible(true);
			}
		});
	}

}
