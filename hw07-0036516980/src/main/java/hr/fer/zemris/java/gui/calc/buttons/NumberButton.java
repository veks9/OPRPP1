package hr.fer.zemris.java.gui.calc.buttons;

import hr.fer.zemris.java.gui.calc.CalcModel;

/**
 * Klasa predstavlja gumb koji služi za unos znamenki
 * @author vedran
 *
 */
public class NumberButton extends MyButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int digit;
	private CalcModel model;
	
	public NumberButton(int digit, CalcModel model) {
		super(Integer.toString(digit));
		this.digit = digit;
		this.model = model;
		
		setFont(getFont().deriveFont(30f));

		addActionListener(e -> {
			number();
		});
	}
	
	/**
	 * Metoda izvodi operaciju koja se treba dogoditi pritiskom na gumb
	 */
	public void number() {
		model.insertDigit(digit);
	}
}
