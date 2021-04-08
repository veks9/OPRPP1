package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

/**
 * Klasa predstavlja implementaciju {@link CalcModel}-a
 * 
 * @author vedran
 *
 */
public class CalcModelImpl implements CalcModel {

	private boolean editable = true;
	private boolean negative = false;
	private String input = "";
	private double inputParsed = 0.;
	private String frozen = null;
	private double activeOperand;
	private boolean isActiveOperandSet = false;
	private DoubleBinaryOperator pendingOperation;
	private List<CalcValueListener> listeners = new ArrayList<>();
	private final static double EPSILON = 1E-10;

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		if (l == null)
			throw new NullPointerException("Listener ne smije biti null!");

		listeners.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		if (l == null)
			throw new NullPointerException("Listener ne smije biti null!");

		listeners.remove(l);
	}

	@Override
	public double getValue() {
		return negative ? -inputParsed : inputParsed;
	}

	@Override
	public void setValue(double value) {
		inputParsed = value;
		input = Double.toString(value);
		editable = false;
		freezeValue(input);
		valueWasChanged();
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void clear() {
		input = "";
		editable = true;
		negative = false;
		valueWasChanged();
	}

	public void clearScreen() {
		input = "";
		editable = true;
		negative = false;
		frozen = null;
		valueWasChanged();
	}

	@Override
	public void clearAll() {
		activeOperand = 0.;
		isActiveOperandSet = false;
		pendingOperation = null;
		input = "";
		inputParsed = 0.;
		negative = false;
		frozen = null;
		editable = true;
		valueWasChanged();
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if (!editable)
			throw new CalculatorInputException("Model nije editabilan");

		negative = !negative;
		valueWasChanged();
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if (!editable)
			throw new CalculatorInputException("Model nije editabilan");
		if (input.contains("."))
			throw new CalculatorInputException("Već postoji decimalna točka!");
		if (input.length() == 0)
			throw new CalculatorInputException("Decimalna točka se ne može dodati!");

		if (input == null) {
			input = "0.";
			return;
		}

		input += ".";
		freezeValue(input);
		valueWasChanged();

	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if (!editable)
			throw new CalculatorInputException("Model nije editabilan!");
		if (numberTooBig(digit))
			throw new CalculatorInputException("Dodavanjem znamenke broj postaje prevelik!");

		if (multipleZerosStartingNumber(digit))
			return;

		try {
			input += digit;

			if (input.charAt(0) == '0' && !(input.indexOf("0") == 0 && input.indexOf(".") == 1)) {
				while (input.indexOf("0") == 0 || input.indexOf(".") == 1) {
					input = input.substring(1);
				}
			}

			if (input.isEmpty())
				input = "0";

			inputParsed = Double.parseDouble(input);
		} catch (NumberFormatException n) {
			throw new CalculatorInputException();
		}
		freezeValue(input);
		valueWasChanged();

	}

	/**
	 * Pomoćna metoda koja služi za identificiranje višestrukih 0 na početku
	 * 
	 * @param digit znamenka za koju se ispituje
	 * @return
	 */
	private boolean multipleZerosStartingNumber(int digit) {
		if (input.length() == 0)
			return false;
		if (input.contains("."))
			return false;
		if (digit != 0)
			return false;
		if (Math.abs(getValue()) > EPSILON)
			return false;
		return true;

	}

	/**
	 * Pomoćna metoda koja ispituje je li broj prevelik za reprezentaciju u double
	 * obliku
	 * 
	 * @param digit znamenka koja se dodaje na kraj broja pa se ispituje je li
	 *              prevelik
	 * @return <code>true</code> ako je, inače <code>false</code>
	 */
	private boolean numberTooBig(int digit) {
		return !Double.isFinite(Double.parseDouble(input + digit));
	}

	@Override
	public boolean isActiveOperandSet() {
		return isActiveOperandSet;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (!isActiveOperandSet)
			throw new IllegalStateException("Aktivni operand ne smije biti null!");

		return this.activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		isActiveOperandSet = true;
		// freezeValue(activeOperand);

	}

	@Override
	public void clearActiveOperand() {
		activeOperand = 0.;
		isActiveOperandSet = false;

	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		this.pendingOperation = op;
	}

	@Override
	public void freezeValue(String value) {
		this.frozen = value;

	}

	@Override
	public boolean hasFrozenValue() {
		return frozen == null;
	}

	@Override
	public String toString() {
		if (hasFrozenValue()) {
			return negative ? "-0" : "0";
		}

		return negative ? "-" + frozen : frozen;
	}

	/**
	 * Pomoćna metoda koja informira sve listenere da se dogodila promjena
	 */
	private void valueWasChanged() {
		listeners.forEach(l -> l.valueChanged(this));
	}
}
