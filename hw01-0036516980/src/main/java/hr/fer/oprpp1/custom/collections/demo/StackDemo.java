package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

public class StackDemo {

	public static void main(String[] args) {
		if (args.length != 1)
			throw new IllegalArgumentException("Format unosa je krivi, predaj sve u obliku 'text'");

		String[] expressionString = args[0].split("\\s+");
		ObjectStack stack = new ObjectStack();

		for (int i = 0; i < expressionString.length; i++) {
			try {
				int number = Integer.parseInt(expressionString[i]);
				stack.push(number);
			} catch (NumberFormatException e) {
				int secondOperator = (int) stack.pop();
				int firstOperator = (int) stack.pop();
				switch (expressionString[i]) {
				case "+":
					stack.push(firstOperator + secondOperator);
					break;
				case "-":
					stack.push(firstOperator - secondOperator);
					break;
				case "*":
					stack.push(firstOperator * secondOperator);
					break;
				case "/":
					stack.push(firstOperator / secondOperator);
					break;
				case "%":
					stack.push(firstOperator % secondOperator);
					break;
				default:
					System.err.println("Greška sa operatorom! Operator " + expressionString[i] + " ne postoji");

				}
			}
		}

		if (stack.size() != 1) {
			System.err.println("Greška u operacijama! Stog ima više ili manje od jednog elementa!");
		} else {
			System.out.println(stack.pop());
		}
	}

}
