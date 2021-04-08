package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Klasa predstavlja naredbu koja crta
 * 
 * @author vedran
 *
 */
public class DrawCommand implements Command {
	private double step;

	/**
	 * Konstruktor prima step koji predstavlja količinu efektivnih pomaka koje treba
	 * napraviti
	 * 
	 * @param step
	 */
	public DrawCommand(double step) {
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();

		double pathLength = step * currentState.getShift();
		double angle = currentState.getTurtleDirection().getAngle();

		Vector2D newVector = new Vector2D(currentState.getTurtlePosition().getX() + pathLength * Math.cos(angle),

				currentState.getTurtlePosition().getY() + pathLength * Math.sin(angle));
		painter.drawLine(currentState.getTurtlePosition().getX(), currentState.getTurtlePosition().getY(),
				newVector.getX(), newVector.getY(), currentState.getColor(), 1f);

		currentState.setTurtlePosition(newVector);
	}

}
