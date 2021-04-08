
package hr.fer.zemris.java.gui.calc.buttons;

import java.util.function.DoubleUnaryOperator;

import javax.swing.JCheckBox;

import hr.fer.zemris.java.gui.calc.CalcModel;

/**
 * Klasa predstavlja gumb za unarne operacije
 * 
 * @author vedran
 *
 */
public class UnaryOperationButton extends MyButton {

	private static final long serialVersionUID = 1L;
	private JCheckBox inverted;
	private CalcModel model;
	private DoubleUnaryOperator operation;
	private DoubleUnaryOperator invertedOperation;

	public UnaryOperationButton(String name, String invertedName, JCheckBox inverted, CalcModel model,
			DoubleUnaryOperator operation, DoubleUnaryOperator invertedOperation) {
		super(name, invertedName, inverted);
		this.model = model;
		this.operation = operation;
		this.invertedOperation = invertedOperation;
		this.inverted = inverted;

		addActionListener(e -> {
			unaryOperation();
		});
	}

	public UnaryOperationButton(String name, CalcModel model, DoubleUnaryOperator operation) {
		super(name);
		this.model = model;
		this.operation = operation;

		addActionListener(e -> {
			unaryOperation();
		});
	}

	/**
	 * Metoda izvodi operaciju koja se treba dogoditi pritiskom na gumb
	 */
	public void unaryOperation() {
		if (invertedOperation != null && inverted.isSelected()) {
			model.setValue(invertedOperation.applyAsDouble(model.getValue()));

		} else {
			model.setValue(operation.applyAsDouble(model.getValue()));
		}
	}
}
