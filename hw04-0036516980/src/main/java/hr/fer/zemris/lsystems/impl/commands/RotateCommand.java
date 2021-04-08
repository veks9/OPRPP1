package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import static java.lang.Math.*;

/**
 * Klasa predstavlja naredbu koja rotira kornjaču
 * 
 * @author vedran
 *
 */
public class RotateCommand implements Command {

	private double angle;

	public RotateCommand(double angle) {
		this.angle = angle;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState()
		.getTurtleDirection()
		.rotate(Math.toRadians(angle));
	}

}
