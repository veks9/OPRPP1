package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Sučelje koje ima metodu execute koja implementira izvođenje naredbe
 * 
 * @author vedran
 *
 */
public interface Command {
	/**
	 * Metoda koja implementira izvođenje naredbe
	 * 
	 * @param ctx     kontekst
	 * @param painter painter
	 */
	void execute(Context ctx, Painter painter);
}
