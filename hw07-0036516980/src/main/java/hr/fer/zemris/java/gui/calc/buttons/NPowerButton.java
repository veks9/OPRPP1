package hr.fer.zemris.java.gui.calc.buttons;

import java.util.function.DoubleBinaryOperator;

import javax.swing.JCheckBox;

import hr.fer.zemris.java.gui.calc.CalcModel;
import static hr.fer.zemris.java.gui.calc.MathOperations.*;

/**
 * Klasa koja predstavlja gumb x^n na kalkulatoru
 * @author vedran
 *
 */
public class NPowerButton extends BinaryOperationButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JCheckBox invCheckBox;
	private DoubleBinaryOperator xPowerN;
	private DoubleBinaryOperator nthRoot;
	
	public NPowerButton(String name, String invertedName, 
			CalcModel model, JCheckBox invCheckBox) {
		super(name, invertedName, model, invCheckBox);
		
		this.invCheckBox = invCheckBox;
		this.xPowerN = xPowerN();
		this.nthRoot = nthRoot();
		
		addActionListener(e -> {
			NPowerOperation();
		});
	}
	
	/**
	 * Metoda izvodi operaciju koja se treba dogoditi pritiskom na gumb
	 */
	public void NPowerOperation() {
		if(!invCheckBox.isSelected()) {
			setOperation(xPowerN);
		} else {
			setOperation(nthRoot);;
		}
		
		super.binaryOperation();
	}
}
