package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import java.util.Scanner;
import hr.fer.oprpp1.custom.collections.Dictionary;
import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;

public class LSystemBuilderImpl implements LSystemBuilder {

	private Dictionary<Character, String> productions = new Dictionary<>();
	private Dictionary<Character, Command> commands = new Dictionary<>();
	private double unitLength = 0.1f;
	private double unitLengthDegreeScaler = 1.f;
	private Vector2D origin = new Vector2D(0, 0);
	private double angle;
	private String axiom = "";

	private class LSystemImpl implements LSystem {
		private Context ctx;
		private TurtleState turtleState;

		@Override
		public void draw(int level, Painter painter) {
			ctx = new Context();
			turtleState = new TurtleState(origin, Vector2D.I.rotated(angle), Color.black,
					unitLength * Math.pow(unitLengthDegreeScaler, level));
			ctx.pushState(turtleState);

			String generatedString = generate(level);

			for (char symbol : generatedString.toCharArray()) {
				Command command = commands.get(symbol);
				if (command != null)
					command.execute(ctx, painter);
			}
		}

		@Override
		public String generate(int level) {
			if (level == 0)
				return axiom;

			String s = generate(level - 1);

			String result = "";
			for (char symbol : s.toCharArray()) {

				String value = productions.get(symbol);

				if (value != null)
					result += value;

				else
					result += symbol;
			}

			return result;
		}

	}

	@Override
	public LSystem build() {
		return new LSystemImpl();
	}

	@Override
	public LSystemBuilder configureFromText(String[] lines) {
		for (int i = 0; i < lines.length; i++) {
			if (lines[i].length() == 0)
				continue;

			Scanner sc = new Scanner(lines[i]);
			String next = sc.next().toLowerCase();

			if (next.equals("origin")) {
				setOrigin(sc.nextDouble(), sc.nextDouble());
			} else if (next.equals("angle")) {
				setAngle(sc.nextDouble());
			} else if (next.equals("unitlength")) {
				setUnitLength(sc.nextDouble());
			} else if (next.equals("unitlengthdegreescaler")) {
				double num, den;
				String[] frac = sc.nextLine().split("/");
				num = Double.parseDouble(frac[0]);
				if (frac.length > 1) {
					den = Double.parseDouble(frac[1]);
					setUnitLengthDegreeScaler(num / den);
					continue;
				}

				setUnitLengthDegreeScaler(num);
			} else if (next.equals("command")) {
				char symbol = sc.next().charAt(0);
				String command = sc.nextLine();

				registerCommand(symbol, command);
			} else if (next.equals("axiom")) {
				setAxiom(sc.next());
			} else if (next.equals("production")) {
				char symbol = sc.next().charAt(0);
				String production = sc.nextLine();

				registerProduction(symbol, production);
			} else {
				System.err.println("Nedozvoljena naredba!");
			}

			sc.close();
		}

		return this;
	}

	@Override
	public LSystemBuilder registerCommand(char symbol, String command) {
		Command com = findCommand(command);

		commands.put(symbol, com);
		return this;
	}

	private Command findCommand(String s) {
		Scanner sc = new Scanner(s);
		String commandName = sc.next();
		switch (commandName) {
		case "color":
			int color = Integer.parseInt(sc.next(), 16);
			sc.close();
			return new ColorCommand(new Color(color));
		case "draw":
			double step = sc.nextDouble();
			sc.close();
			return new DrawCommand(step);
		case "pop":
			sc.close();
			return new PopCommand();
		case "push":
			sc.close();
			return new PushCommand();
		case "rotate":
			double angle = sc.nextDouble();
			sc.close();
			return new RotateCommand(angle);
		case "scale":
			double scaler = sc.nextDouble();
			sc.close();
			return new ScaleCommand(scaler);
		case "skip":
			double skip = sc.nextDouble();
			sc.close();
			return new SkipCommand(skip);
		default:
			sc.close();
			throw new IllegalArgumentException("Naziv naredbe je neispravan!");
		}
	}

	@Override
	public LSystemBuilder registerProduction(char symbol, String production) {
		productions.put(Character.valueOf(symbol), production);
		return this;
	}

	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = Math.toRadians(angle);
		return this;
	}

	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}

	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		origin = new Vector2D(x, y);
		return this;
	}

	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		this.unitLength = unitLength;
		return this;
	}

	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		return this;
	}

}
