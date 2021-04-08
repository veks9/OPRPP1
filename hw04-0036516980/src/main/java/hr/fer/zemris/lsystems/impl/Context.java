package hr.fer.zemris.lsystems.impl;

import hr.fer.oprpp1.custom.collections.ObjectStack;

public class Context {

	ObjectStack<TurtleState> stack = new ObjectStack<>();

	/**
	 * Vraća trenutno odnosno aktivno stanje kornjače
	 * 
	 * @return trenutno stanje kornjače
	 */
	public TurtleState getCurrentState() {
		return stack.peek();
	}

	/**
	 * Stavlja na vrh stoga predano stanje
	 * 
	 * @param state stanje kornjače
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}

	/**
	 * Metoda briše jedno stanje s vrha stoga
	 */
	public void popState() {
		stack.pop();
	}

}
