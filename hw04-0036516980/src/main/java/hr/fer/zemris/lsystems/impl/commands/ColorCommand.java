package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Klasa predstavlja naredbu koja sprema boju
 * 
 * @author vedran
 *
 */
public class ColorCommand implements Command {

	private Color color;

	/**
	 * Konstruktor koji prima boju i sprema ju
	 * 
	 * @param color
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setColor(color);
	}

}
