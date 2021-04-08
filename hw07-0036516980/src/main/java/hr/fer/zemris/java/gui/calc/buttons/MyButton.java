package hr.fer.zemris.java.gui.calc.buttons;


import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;

/**
 * Klasa predstavlja implementaciju {@link JButton} i koristi se za
 * prikazivanje svih gumba na kalkulatoru
 * @author vedran
 *
 */
public class MyButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JCheckBox inverted;
	private String name;
	private String invertedName;
	
	public MyButton(String name, String invertedName, JCheckBox inverted) {
		super();
		this.name = name;
		this.invertedName = invertedName;
		this.inverted = inverted;
		
		initButton();

	}
	
	public MyButton(String name) {
		super();
		this.name = name;
		
		initButton();
	}
	
	/**
	 * Pomoćna metoda koja delegira kako će izgledati gumb
	 */
	protected void initButton() {
		setText(name);
		setBorder(BorderFactory.createLineBorder(Color.black, 2));
	}

	/**
	 * Pomoćna metoda koja za gumbe koji imaju obrnute operacije
	 * mijenja njihovo ime
	 */
	public void invertName() {
		if(inverted.isSelected()) 
			setText(invertedName);
		else 
			setText(name);
	}
}
