package hr.fer.zemris.java.gui.calc;

import java.awt.Container;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.buttons.BinaryOperationButton;
import hr.fer.zemris.java.gui.calc.buttons.MyButton;
import hr.fer.zemris.java.gui.calc.buttons.NumberButton;
import hr.fer.zemris.java.gui.calc.buttons.NPowerButton;
import hr.fer.zemris.java.gui.calc.buttons.UnaryOperationButton;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;
import static hr.fer.zemris.java.gui.calc.MathOperations.*;

/**
 * Klasa predstavlja kalkulator
 * 
 * @author vedran
 *
 */
public class Calculator extends JFrame {

	public static void main(String[] args) {
		Calculator calc = new Calculator();
		SwingUtilities.invokeLater(() -> {
			calc.setVisible(true);
		});
	}

	private static final long serialVersionUID = 1L;
	private CalcModelImpl model;
	private Stack<Double> stack;
	private List<MyButton> operatorHasInv = new ArrayList<>();

	public Calculator() {
		super();

		setTitle("Java Calculator v1.0");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		model = new CalcModelImpl();
		stack = new Stack<>();

		initGUI();
		pack();
	}

	/**
	 * Metoda inicijalizira GUI i sve elemente smješta na njihova mjesta
	 */
	private void initGUI() {
		Container c = getContentPane();
		c.setLayout(new CalcLayout(8));

		getRootPane().setBorder(BorderFactory.createLineBorder(getContentPane().getBackground(), 5));
		
		CalcDisplay display = new CalcDisplay();
		c.add(display, new RCPosition(1, 1));
		model.addCalcValueListener(display);

		JCheckBox invCheckBox = new JCheckBox("Inv");
		invCheckBox.addActionListener(e -> {
			operatorHasInv.forEach(o -> {
				o.invertName();
			});
		});
		c.add(invCheckBox, new RCPosition(5, 7));

		numberButtonsInit(c);
		unaryOperationsButtonsInit(c, invCheckBox);
		binaryOperationsButtonsInit(c, invCheckBox);
		restOfButtons(c);

	}

	/**
	 * Metoda dodaje sve gumbe koji nisu unarni, binarni ili znamenke
	 * 
	 * @param c
	 */
	private void restOfButtons(Container c) {
		MyButton equals = new MyButton("=");
		equals.addActionListener(e -> {
			if (model.isActiveOperandSet()) {
				double result = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(),
						model.getValue());
				model.setValue(result);
				model.clearActiveOperand();
				model.clear();
			}
			;
		});

		MyButton clr = new MyButton("clr");
		clr.addActionListener(e -> {
			model.clearScreen();
		});

		MyButton reset = new MyButton("reset");
		reset.addActionListener(e -> {
			model.clearAll();
		});

		MyButton push = new MyButton("push");
		push.addActionListener(e -> {
			stack.push(model.getValue());
		});

		MyButton pop = new MyButton("pop");
		pop.addActionListener(e -> {

			if (!stack.isEmpty()) {
				double popped = stack.pop();

				model.setValue(popped);
			}
		});

		MyButton swap = new MyButton("+/-");
		swap.addActionListener(e -> {
			model.swapSign();
		});

		MyButton decimalDot = new MyButton(".");
		decimalDot.addActionListener(e -> {
			model.insertDecimalPoint();
		});

		c.add(equals, new RCPosition(1, 6));
		c.add(clr, new RCPosition(1, 7));
		c.add(reset, new RCPosition(2, 7));
		c.add(push, new RCPosition(3, 7));
		c.add(pop, new RCPosition(4, 7));
		c.add(swap, new RCPosition(5, 4));
		c.add(decimalDot, new RCPosition(5, 5));

	}

	/**
	 * Metoda dodaje gumbe svih binarnih operatora na kalkulator
	 * 
	 * @param c
	 * @param invCheckBox
	 */
	private void binaryOperationsButtonsInit(Container c, JCheckBox invCheckBox) {
		BinaryOperationButton add = new BinaryOperationButton("+", model, (a, b) -> a + b);
		BinaryOperationButton div = new BinaryOperationButton("/", model, (a, b) -> a / b);
		BinaryOperationButton mul = new BinaryOperationButton("*", model, (a, b) -> a * b);
		BinaryOperationButton sub = new BinaryOperationButton("-", model, (a, b) -> a - b);
		NPowerButton powerN = new NPowerButton("x^n", "x^(1/n)", model, invCheckBox);

		c.add(div, new RCPosition(2, 6));
		c.add(mul, new RCPosition(3, 6));
		c.add(sub, new RCPosition(4, 6));
		c.add(add, new RCPosition(5, 6));
		c.add(powerN, new RCPosition(5, 1));

		operatorHasInv.add(powerN);
	}

	/**
	 * Metoda dodaje sve gumbe unarnih operatora na kalkulator
	 * 
	 * @param c
	 * @param invCheckBox
	 */
	private void unaryOperationsButtonsInit(Container c, JCheckBox invCheckBox) {
		UnaryOperationButton reciprocal = new UnaryOperationButton("1/x", "1/x", invCheckBox, model, reciprocal(),
				null);
		UnaryOperationButton ln = new UnaryOperationButton("ln", "e^x", invCheckBox, model, ln(), powerE());
		UnaryOperationButton log = new UnaryOperationButton("log", "10^x", invCheckBox, model, log10(), power10());
		UnaryOperationButton sin = new UnaryOperationButton("sin", "arcsin", invCheckBox, model, sin(), asin());
		UnaryOperationButton cos = new UnaryOperationButton("cos", "arccos", invCheckBox, model, cos(), acos());
		UnaryOperationButton tan = new UnaryOperationButton("tan", "arctan", invCheckBox, model, tan(), atan());
		UnaryOperationButton ctg = new UnaryOperationButton("ctg", "arcctg", invCheckBox, model, ctg(), actg());

		c.add(reciprocal, new RCPosition(2, 1));
		c.add(log, new RCPosition(3, 1));
		c.add(ln, new RCPosition(4, 1));
		c.add(sin, new RCPosition(2, 2));
		c.add(cos, new RCPosition(3, 2));
		c.add(tan, new RCPosition(4, 2));
		c.add(ctg, new RCPosition(5, 2));

		operatorHasInv.add(ln);
		operatorHasInv.add(log);
		operatorHasInv.add(sin);
		operatorHasInv.add(cos);
		operatorHasInv.add(tan);
		operatorHasInv.add(ctg);

	}

	/**
	 * Metoda dodaje sve gumbe znamenki na kalkulator
	 * 
	 * @param c
	 */
	private void numberButtonsInit(Container c) {
		for (int i = 1, number = 7; i <= 3; i++) {
			for (int j = 2; j <= 4; j++) {
				c.add(new NumberButton(number, model), new RCPosition(i + 1, j + 1));
				number++;
			}
			number -= 6;
		}
		c.add(new NumberButton(0, model), new RCPosition(5, 3));

	}

}
