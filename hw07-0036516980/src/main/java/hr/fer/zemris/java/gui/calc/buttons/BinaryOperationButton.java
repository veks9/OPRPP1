package hr.fer.zemris.java.gui.calc.buttons;

import java.util.function.DoubleBinaryOperator;

import javax.swing.JCheckBox;

import hr.fer.zemris.java.gui.calc.CalcModel;

/**
 * Klasa predstavlja gumb za binarne operacije na kalkulatoru
 * @author vedran
 *
 */
public class BinaryOperationButton extends MyButton {

	private static final long serialVersionUID = 1L;
	private DoubleBinaryOperator operation;
	private CalcModel model;
	
	public BinaryOperationButton(String name, CalcModel model, DoubleBinaryOperator operation) {
		super(name);
		this.operation = operation;	
		this.model = model;

		addActionListener(e -> {
			binaryOperation();
		});
	}
	
	public BinaryOperationButton(String name, String invertedName, CalcModel model, JCheckBox inverted) {
		super(name, invertedName, inverted);
		this.model = model;	
		
	}
	/**
	 * Pomoćna metoda koja operaciju setta na predanu operaciju
	 * @param operation
	 */
	public void setOperation(DoubleBinaryOperator operation) {
		this.operation = operation;
	}
	
	/**
	 * Metoda izvodi operaciju koja se treba dogoditi pritiskom na gumb
	 */
	public void binaryOperation() {
		if(model.isActiveOperandSet()) {
			double firstOperand = model.getActiveOperand();
			double secondOperand = model.getValue();
			double result = model.getPendingBinaryOperation().applyAsDouble(firstOperand, secondOperand);
			model.setActiveOperand(result);
		} else {
			model.setActiveOperand(model.getValue());
		}
		model.setPendingBinaryOperation(operation);
		model.clear();
	}
}
